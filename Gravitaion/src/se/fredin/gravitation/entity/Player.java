package se.fredin.gravitation.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class Player {
	
	private World world;
	private Body body;
	private Rectangle bounds;
	private Vector2 position;
	private Sprite sprite;
	private Vector2 movement;
	private GamePad gamePad;
	private float speed = 5000f;
	private final float HALF_WIDTH = 2f, HALF_HEIGHT = 2f;
	private final float MAX_TURN_DEG = (float) (Math.PI);
	
	public Player(Vector2 position, String texturePath, World world) {
		this.world = world;
		Texture texture = new Texture(Gdx.files.internal(texturePath).path());
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.sprite = new Sprite(texture);
		this.movement = new Vector2(0, 0);
		this.position = position;
		this.body = getBody();
		this.bounds = new Rectangle(body.getPosition().x, body.getPosition().y, sprite.getWidth(), sprite.getHeight());
		Gdx.input.setInputProcessor(new KeyInput());
		this.gamePad = new GamePad();
	}
	
	private Body getBody() {
		// Body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(this.position);
				
		// Box shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(HALF_WIDTH, HALF_HEIGHT);
				
		// fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = .75f;
		fixtureDef.restitution = .1f;
		fixtureDef.density = 10;
				
		// add to world
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		sprite.setSize(HALF_WIDTH * 2, HALF_HEIGHT * 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		body.setUserData(sprite);
		
		body.setAngularDamping(1.1f);
		body.setLinearDamping(0.5f);
		boxShape.dispose();
		
		
		return body;
	}
	
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	public void tick(float delta) {
		body.applyForceToCenter(movement, true);
		bounds.setPosition(body.getPosition());
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		/*
		 * float rot = (float)(chassis.getTransform().getRotation() + Math.PI / 2);
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		
		chassis.applyForce(tmp.set(leftAcc * x, leftAcc * y), chassis.getWorldPoint(tmp2.set(-width / 2, 0)), true);
		chassis.applyForce(tmp.set(rightAcc * x, rightAcc * y), chassis.getWorldPoint(tmp2.set(width / 2, 0)), true);
		 */
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	public Vector2 getPosition() {
		return body.getPosition();
	}
	public void setPosition(float x, float y) {
		body.setTransform(x, y, 0);
		body.setLinearVelocity(0, 0);
	}
	public void setMovement(float x, float y) {
		this.movement.set(x, y);
	}
	public GamePad getGamePad() {
		return gamePad;
	}
	
	public void dispose() {
		world.dispose();
		sprite.getTexture().dispose();
	}
	
	private class KeyInput extends InputAdapter {
		@Override
		public boolean keyDown(int keycode) {
			switch(keycode) {
			case Keys.ESCAPE:
				Gdx.app.exit();
				break;
			case Keys.DOWN:
				movement.y = -speed;
				break;
			case Keys.LEFT:
				body.applyAngularImpulse(MathUtils.radDeg * MAX_TURN_DEG, true);
				break;
			case Keys.RIGHT:
				body.applyAngularImpulse(MathUtils.radDeg * -MAX_TURN_DEG, true);
				break;
			case Keys.UP:
				float rot = (float)(body.getTransform().getRotation() + MathUtils.PI / 2);
				float x = MathUtils.cos(rot);
				float y = MathUtils.sin(rot);
				movement.set(speed * x, speed * y);
			default:
				return false;
			}
			return true;
		}
		@Override
		public boolean keyUp(int keycode) {
			switch(keycode) {
			case Keys.UP : case Keys.DOWN:
				movement.y = 0;
			case Keys.LEFT : case Keys.RIGHT:
				movement.x = 0;
			}
			return true;
		}
	}
	
	public class GamePad extends ControllerAdapter {
		@Override
		public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
			switch(value) {
			case west:
				body.applyAngularImpulse(50000, true);
				movement.x = -speed;
				break;
			case east:
				movement.x = speed;
				body.applyAngularImpulse(-50000, true);
				break;
			case north:
				movement.y = speed;
				break;
			case south:
				movement.y = -speed;
				break;
			default:
				break;
			}
			return true;
		}
          
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			switch(buttonIndex) {
			case 0:
				movement.y = speed;
				break;
			}
			return true;
		}
          
		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			switch(buttonIndex) {
			case 0:
				movement.y = 0;
				break;
			}
			return true;
		}
	}
	
}
