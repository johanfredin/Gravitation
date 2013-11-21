package se.fredin.gravitation;


import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Demo extends Game {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERARIONS = 8;
	private final int POSITIONITERATIONS = 3;
	
	private Body box;
	private float speed = 200f;
	private Vector2 movement = new Vector2();
	private Array<Body> tmpBodies = new Array<Body>();
	private Sprite sprite;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		
		Gdx.input.setInputProcessor(new InputController(){
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
					box.applyAngularImpulse(5, true);
					movement.x = -speed;
					break;
				case Keys.RIGHT:
					box.applyAngularImpulse(-5, true);
					movement.x = speed;
					break;
				case Keys.UP:
					movement.y = speed;
					break;
				}
				return true;
			}
			@Override
			public boolean keyUp(int keycode) {
				switch(keycode) {
				case Keys.UP : case Keys.DOWN:
					movement.y = 0;
					break;
				case Keys.LEFT : case Keys.RIGHT:
					movement.x = 0;
					break;
				}
				return true;
			}
			
			@Override
			public boolean scrolled(int amount) {
				camera.zoom += amount / 25f;
				return true;
			}
		});
		
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		// BOX --------------------------------------------------------
		// Body definition
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(2.25f, 10);
		
		// Box shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(.5f, 1);
		
		// fixture definition
		fixtureDef.shape = boxShape;
		fixtureDef.friction = .75f;
		fixtureDef.restitution = .1f;
		fixtureDef.density = 5;
		
		// add to world
		box = world.createBody(bodyDef);
		box.createFixture(fixtureDef);
		boxShape.dispose();
		
		
		// add a sprite to the box
		sprite = new Sprite(new Texture(Gdx.files.internal(Paths.SHIP_TEXTUREPATH).path()));
		sprite.setSize(1, 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		box.setUserData(sprite);
		
		
		
		// BALL -----------------------------------------------------------
		// Shape definition
		CircleShape cirleShape = new CircleShape();
		cirleShape.setRadius(.5f);
		cirleShape.setPosition(new Vector2(0, 1.5f));
		
		// Fixture definition
		fixtureDef.shape = cirleShape;
		fixtureDef.density = 2.5f;
		fixtureDef.friction = .25f; // goes from 0 to 1
		fixtureDef.restitution = .68f; // restitution = how much it will bounce, goes from 0 to 1
		
		// add body to world and add a fixture
		world.createBody(bodyDef).createFixture(fixtureDef);
		cirleShape.dispose();
		// ---------------------------------------------------------------
	
		// GROUND -------------------------------------------------------
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
		// ground shape
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]{new Vector2(-50, 0), new Vector2(5, -20), new Vector2(40, 20)});
		
		
		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		
		world.createBody(bodyDef).createFixture(fixtureDef);
		
		groundShape.dispose();
		// -------------------------------------------------------------
	}
	
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VELOCITYITERARIONS, POSITIONITERATIONS);
		box.applyForceToCenter(movement.x, movement.y, true);
		
		camera.position.set(box.getPosition().x, box.getPosition().y, 0);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies) {
			if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
			}
		}
		batch.end();
		
		debugRenderer.render(world, camera.combined);
		
	};
	
	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(BaseScreen.VIEWPORT_WIDTH / 10, BaseScreen.VIEWPORT_HEIGHT / 10);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
		debugRenderer.dispose();
		batch.dispose();
		sprite.getTexture().dispose();
	}
	
	private class InputController implements InputProcessor {

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
}
