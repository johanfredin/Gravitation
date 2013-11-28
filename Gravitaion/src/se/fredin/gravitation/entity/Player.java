package se.fredin.gravitation.entity;

import java.io.IOException;

import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends PhysicalEntity {
	
	private Vector2 movement;
	private GamePad gamePad;
	private ParticleEmitter exhaust;
	private float speed = 7500f;
	private float xSpeed;
	private float ySpeed;
	private float rot;
	private final float MAX_TURN_DEG = (float)(Math.PI);
	private boolean leftPressed, rightPressed, gasPressed;
	
	public Player(float xPos, float yPos, String texturePath, World world, float bodyWidth, float bodyHeight) {
		super(xPos, yPos, texturePath, world, bodyWidth, bodyHeight);
		this.movement = new Vector2(0, 0);
		Gdx.input.setInputProcessor(new KeyInput());
		this.gamePad = new GamePad();
		this.exhaust = getExhaustEmitter();
	}
	
	private ParticleEmitter getExhaustEmitter() {
		ParticleEmitter emitter = new ParticleEmitter();
		try {
			emitter.load(Gdx.files.internal(Paths.EXHAUST_PARTICLE_PROPERTIES_PATH).reader(2024));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Texture exhaustTexture = new Texture(Gdx.files.internal(Paths.EXHAUST_TEXTUREPATH));
		Sprite exhaustSprite = new Sprite(exhaustTexture);
		emitter.setSprite(exhaustSprite);
		emitter.getScale().setHigh(0, 3);
		return emitter;
	}
	
	private void setExhaustRotation() {
		float angle = (float)(MathUtils.radDeg * body.getTransform().getRotation());
		exhaust.getAngle().setLow(angle + 270);
		exhaust.getAngle().setHighMin(angle + 270 - 90);
		exhaust.getAngle().setHighMax(angle + 270 + 90);
	}
	
	@Override
	public Body getSpecifiedBody(float xPos, float yPos, float bodyWidth, float bodyHeight) {
		// Body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(xPos, yPos);
				
		// Box shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(bodyWidth, bodyHeight);
				
		// fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = .75f;
		fixtureDef.restitution = .1f;
		fixtureDef.density = 7.5f;
				
		// add to world
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		sprite.setSize(bodyWidth * 2, bodyHeight * 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		body.setUserData(sprite);
		
		body.setAngularDamping(3.1f);
		body.setLinearDamping(0.5f);
		boxShape.dispose();
		return body;
	}
	
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		exhaust.draw(batch);
	}
	
	public void tick(float delta) {
		body.applyForceToCenter(movement, true);
		bounds.setPosition(getBodyPosition());
		if(gasPressed) {
			rot = (float)(body.getTransform().getRotation() + MathUtils.PI / 2);
			xSpeed = MathUtils.cos(rot);
			ySpeed = MathUtils.sin(rot);
			movement.set(speed * xSpeed, speed * ySpeed);
		}
		if(leftPressed) {
			body.applyAngularImpulse(MathUtils.radDeg * MAX_TURN_DEG, true);
		}
		if(rightPressed) {
			body.applyAngularImpulse(MathUtils.radDeg * -MAX_TURN_DEG, true);
		}
		sprite.setPosition(getBodyPosition().x - sprite.getWidth() / 2, getBodyPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
		// update exhaust
		exhaust.setPosition(getBodyPosition().x, getBodyPosition().y);
		setExhaustRotation();
		exhaust.update(delta);
		
	}
	
	public void setMovement(float x, float y) {
		this.movement.set(x, y);
	}
	
	public GamePad getGamePad() {
		return gamePad;
	}
	
	public void dispose() {
		super.dispose();
	}
	
	private class KeyInput extends InputAdapter {
		@Override
		public boolean keyDown(int keycode) {
			switch(keycode) {
			case Keys.ESCAPE:
				Gdx.app.exit();
				break;
			case Keys.LEFT:
				leftPressed = true;
				break;
			case Keys.RIGHT:
				rightPressed = true;
				break;
			case Keys.UP:
				exhaust.getLife().setHighMax(500);
				gasPressed = true;
				break;
			default:
				return false;
			}
			return true;
			
		}
		
		@Override
		public boolean keyUp(int keycode) {
			switch(keycode) {
			case Keys.UP:
				exhaust.getLife().setHighMax(20);
				gasPressed = false;
				movement.set(0, 0);
				break;
			case Keys.RIGHT:
				rightPressed = false;
				break;
			case Keys.LEFT:
				leftPressed = false;
				break;
			default:
				return false;
			}
			return true;
		}
	}
	
	public class GamePad extends ControllerAdapter {
		@Override
		public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
			switch(value) {
			case west:
				leftPressed = true;
				break;
			case east:
				rightPressed = true;
				break;
			default:
				rightPressed = false;
				leftPressed = false;
				break;
			}
			return true;
		}
          
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			switch(buttonIndex) {
			case 0:
				gasPressed = true;
				break;
			default:
				return false;
			}
			return true;
		}
          
		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			switch(buttonIndex) {
			case 0:
				gasPressed = false;
				movement.set(0, 0);
				break;
			default:
				return false;
			}
			return true;
		}
	}

	
}
