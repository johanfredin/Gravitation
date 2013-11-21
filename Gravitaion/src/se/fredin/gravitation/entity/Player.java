package se.fredin.gravitation.entity;

import se.fredin.gravitation.Gravitation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends AbstractEntity {
	
	private float speed = 30f;
	private Vector2 velocity;
	private GamePad gamePad;
	private Body shipBody;
	
	public Player(Vector2 position, String pathToTexture, TextureFilter filter, World world) {
		super(position.x, position.y, pathToTexture, filter);
		this.gamePad = new GamePad();
		this.velocity = new Vector2();
		shipBody = getShipPhysics(world);
		setupController();
	}
	
	private void setupController() {
		Gdx.input.setInputProcessor(new GamePad(){
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.ESCAPE:
					Gdx.app.exit();
					break;
				case Keys.DOWN:
					velocity.y = -speed;
					break;
				case Keys.LEFT:
					shipBody.applyAngularImpulse(5, true);
					velocity.x = -speed;
					break;
				case Keys.RIGHT:
					shipBody.applyAngularImpulse(-5, true);
					velocity.x = speed;
					break;
				case Keys.UP:
					velocity.y = speed;
					break;
				}
				return true;
			}
			@Override
			public boolean keyUp(int keycode) {
				switch(keycode) {
				case Keys.UP : case Keys.DOWN:
					velocity.y = 0;
					break;
				case Keys.LEFT : case Keys.RIGHT:
					velocity.x = 0;
					break;
				}
				return true;
			}
		});	
		
	}
	
	private Body getShipPhysics(World world) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		// BOX --------------------------------------------------------
		// Body definition
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		
		// Box shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(10f, 10);
		
		
		// fixture definition
		fixtureDef.shape = boxShape;
		fixtureDef.friction = .75f;
		fixtureDef.restitution = .1f;
		fixtureDef.density = 5;
		
		// add to world
		Body body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		return body;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	@Override
	public void tick(float delta) {
		sprite.setPosition(position.x, position.y);		
		
		position.add(velocity);
		shipBody.applyForceToCenter(velocity, true);
		bounds.setPosition(position);
	}
	
	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public GamePad getGamePad() {
		return this.gamePad;
	}
	
	public void setVelocity(float x, float y) {
		this.velocity.set(x, y);
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}
	
	public class GamePad extends ControllerAdapter implements InputProcessor {
		@Override
		public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
			Gdx.app.log(Gravitation.LOG, "#" + (controller) + ", pov " + povIndex + " pressed, direction = " + value);
			switch(value) {
			case west:
				velocity.x = -speed;
				break;
			case east:
				velocity.x = speed;
				break;
			case north:
				velocity.y = speed;
				break;
			case south:
				velocity.y = -speed;
				break;
			default:
				break;
			}
			return false;
		}
		
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			Gdx.app.log(Gravitation.LOG, "#" + (controller) + ", button " + buttonIndex + " pressed");
			return false;
		}
		
		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			Gdx.app.log(Gravitation.LOG, "#" + (controller) + ", button " + buttonIndex + " released");
			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}
	}
}
