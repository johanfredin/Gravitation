package se.fredin.gravitation.entity.physical;

import java.util.Iterator;

import se.fredin.gravitation.entity.Bullet;
import se.fredin.gravitation.utils.ParticleLoader;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
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
import com.badlogic.gdx.utils.Array;

public class Player extends PhysicalEntity {
	
	private Vector2 movement;
	private GamePad gamePad;
	private ParticleEmitter exhaust;
	private ParticleEmitter explosion;
	private Array<Bullet> bullets;
	private Iterator<Bullet> bulletIterator;
	private Bullet bullet;
	private float speed = 7500f;
	private float xSpeed;
	private float ySpeed;
	private float rot;
	private final float MAX_TURN_DEG = (float)(Math.PI);
	private final float RESPAWN_TIME = 2.0f;
	private boolean leftPressed, rightPressed, gasPressed;
	private boolean crashed;
	private boolean ableToShoot = true;
	private float timePassed;
	
	public Player(float xPos, float yPos, String texturePath, World world, float bodyWidth, float bodyHeight) {
		super(xPos, yPos, texturePath, world, bodyWidth, bodyHeight);
		this.movement = new Vector2(0, 0);
		Gdx.input.setInputProcessor(new KeyInput());
		this.gamePad = new GamePad();
		this.exhaust = ParticleLoader.getEmitter(Paths.EXHAUST_PARTICLE_PROPERTIES_PATH, Paths.EXHAUST_TEXTUREPATH, 2.66f, 3);
		this.explosion = ParticleLoader.getEmitter(Paths.EXPLOSION_PARTICLE_PROPERTIES_PATH, Paths.EXHAUST_TEXTUREPATH, 4, 4f);
		this.bullets = new Array<Bullet>();
	}
	
	private void setExhaustRotation() {
		float angle = sprite.getRotation();
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
		if(!crashed) {
			sprite.draw(batch);
			exhaust.draw(batch);
		} 
		// draw bullets
		bulletIterator = bullets.iterator();
		while(bulletIterator.hasNext()) {
			bullet = bulletIterator.next();
			bullet.render(batch);
		}
		explosion.draw(batch);
	}
	
	public void tick(float delta) {
		body.applyForceToCenter(movement, true);
		bounds.setPosition(getBodyPosition());
		
		if(gasPressed) {
			rot = (float)(body.getTransform().getRotation() + MathUtils.PI / 2);
			xSpeed = MathUtils.cos(rot);
			ySpeed = MathUtils.sin(rot);
			movement.set(speed * xSpeed, speed * ySpeed);
			setExhaustRotation();
		} if(leftPressed) {
			body.applyAngularImpulse(MathUtils.radDeg * MAX_TURN_DEG, true);
		} if(rightPressed) {
			body.applyAngularImpulse(MathUtils.radDeg * -MAX_TURN_DEG, true);
		}
		sprite.setPosition(getBodyPosition().x - sprite.getWidth() / 2, getBodyPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
		// update exhaust
		exhaust.setPosition(getBodyPosition().x, getBodyPosition().y);
		exhaust.update(delta);
		explosion.update(delta);
		
		bulletIterator = bullets.iterator();
		while(bulletIterator.hasNext()) {
			bullet = bulletIterator.next();
			bullet.tick(delta);
		}
		
		if(crashed) {
			timePassed += delta;
			ableToShoot = false;
			playExplosion();
		}
	}
	
	public void checkForCollision(Array<Rectangle> hardBlocks, Array<Vector2> spawnPoints) {
		for(Rectangle rect : hardBlocks) {
			if(bounds.overlaps(rect)) {
				crashed = true;
				explosion.setPosition(getBodyPosition().x, getBodyPosition().y);
				explosion.start();
				Vector2 spawnPoint = new Vector2(spawnPoints.get((int)(Math.random() * spawnPoints.size)));
				setBodyPosition(spawnPoint.x, spawnPoint.y);
			}
			// check if bullets collided with walls
			for(int i = 0; i < bullets.size; i++) {
				if(bullets.get(i).getBounds().overlaps(rect)) {
					bullets.get(i).dispose();
					bullets.removeIndex(i);
				}
			}
		}
	}
	
	private void playExplosion() {
		if(timePassed < RESPAWN_TIME) {
			setMovement(0, 0);
		} else {
			explosion.allowCompletion();
			crashed = false;
			timePassed = 0.0f;
			ableToShoot = true;
		}
	}
	
	public void setMovement(float x, float y) {
		this.movement.set(x, y);
	}
	
	public boolean isCrashed() {
		return crashed;
	}
	
	public ParticleEmitter getExplosion() {
		return explosion;
	}
	
	public GamePad getGamePad() {
		return gamePad;
	}
	
	public void dispose() {
		super.dispose();
	}
	
	private void shoot() {
		if(ableToShoot) {
			Bullet tmp = new Bullet(getBodyPosition());
			float bulletSpeed = 3f;
			float bulletRot = (float)(body.getTransform().getRotation() + MathUtils.PI / 2);
			float bulletXSpeed = MathUtils.cos(bulletRot);
			float bulletYSpeed = MathUtils.sin(bulletRot);
			tmp.setMovement(bulletSpeed * bulletXSpeed, bulletSpeed * bulletYSpeed);
			bullets.add(tmp);
		}
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
				exhaust.start();
				gasPressed = true;
				break;
			case Keys.SPACE:
				shoot();
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
				exhaust.allowCompletion();
				gasPressed = false;
				movement.set(0, 0);
				break;
			case Keys.RIGHT:
				rightPressed = false;
				break;
			case Keys.LEFT:
				leftPressed = false;
				break;
			case Keys.SPACE:
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
