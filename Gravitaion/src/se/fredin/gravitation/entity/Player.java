package se.fredin.gravitation.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
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
	private float speed = 7500f;
	private final float MAX_TURN_DEG = (float) (Math.PI);
	
	public Player(Vector2 position, String texturePath, World world, float width, float height) {
		super(position, texturePath, world, width, height);
		this.movement = new Vector2(0, 0);
		Gdx.input.setInputProcessor(new KeyInput());
		this.gamePad = new GamePad();
	}
	
	@Override
	public Body getSpecifiedBody(float width, float height) {
		// Body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(this.position);
				
		// Box shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width, height);
				
		// fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = .75f;
		fixtureDef.restitution = .1f;
		fixtureDef.density = 7.5f;
				
		// add to world
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		sprite.setSize(width * 2, height * 2);
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
		bounds.setPosition(getBodyPosition());
		sprite.setPosition(getBodyPosition().x - sprite.getWidth() / 2, getBodyPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
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
				break;
			default:
				return false;
			}
			return true;
		}
		@Override
		public boolean keyUp(int keycode) {
			switch(keycode) {
			case Keys.UP : case Keys.DOWN:
				movement.set(0, 0);
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
