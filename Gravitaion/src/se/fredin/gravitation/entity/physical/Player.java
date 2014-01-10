package se.fredin.gravitation.entity.physical;

import se.fredin.gravitation.entity.Bullet;
import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.utils.GameMode;
import se.fredin.gravitation.utils.ParticleLoader;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;

/**
 * Class that handles the player, also contains a nested class for gamepads and a touchpad for android/ios
 * @author johan
 *
 */
public class Player extends PhysicalEntity {
	
	private boolean isReversedSteering;
	private Vector2 movement;
	private float bulletSpeed = Settings.DEFAULT_BULLET_SPEED;
	private Touchpad movementTouchPad, gasTouchPad;
	private Stage touchPadStage;
	private GamePad gamePad;
	private ParticleEmitter exhaust;
	private ParticleEmitter explosion;
	private Array<Bullet> bullets;
	private GameMode gameMode;
	private float speed = Settings.DEFAULT_SPEED;
	private float xSpeed;
	private float ySpeed;
	private float shipRot;
	private final float MAX_TURN_DEG = (float)(Math.PI);
	private final float RESPAWN_TIME = 2.0f;
	public boolean leftPressed, rightPressed, gasPressed;
	private boolean crashed;
	private boolean ableToShoot = true;
	private float timePassed;
	private boolean isBulletMovementReversed;
	private boolean isBigBullets;
	private final int PLAYER_NUM;
	private int score;
	private Sound explosionSound;
	private Sound exhaustSound;
	private Sound shootSound;
	private Sound pauseSound;
	
	private boolean exhaustSoundPlaying;
	
	/**
	 * Creates a new player instance
	 * @param xPos the x position of the player
	 * @param yPos the y position of the player
	 * @param texturePath the path to the texture used for the sprite
	 * @param world the box2D world this player will live in
	 * @param bodyWidth the width of the player
	 * @param bodyHeight the height of the player 
	 * @param PLAYER_NUM the number of the player (1 or 2)
	 * @param gameMode the current game mode
	 */
	public Player(float xPos, float yPos, String texturePath, World world, float bodyWidth, float bodyHeight, final int PLAYER_NUM, GameMode gameMode) {
		super(xPos, yPos, texturePath, world, bodyWidth, bodyHeight);
		this.gameMode = gameMode;
		this.movement = new Vector2(0, 0);
		this.PLAYER_NUM = PLAYER_NUM;
		this.gamePad = new GamePad();
		this.exhaust = ParticleLoader.getEmitter(Paths.EXHAUST_PARTICLE_PROPERTIES_PATH, Paths.EXHAUST_TEXTUREPATH, 2.66f, 3);
		this.explosion = ParticleLoader.getEmitter(Paths.EXPLOSION_PARTICLE_PROPERTIES_PATH, Paths.EXHAUST_TEXTUREPATH, 4, 4f);
		this.bullets = new Array<Bullet>();
		this.movementTouchPad = getTouchPad("data/skins/padbg_s.png", "data/skins/knob_s.png", 5, 5);
		this.gasTouchPad = getTouchPad("data/skins/padbg_s.png", "data/skins/gasknob.png", 100, 5);
		this.touchPadStage = new Stage(BaseScreen.VIEWPORT_WIDTH, BaseScreen.VIEWPORT_HEIGHT / 4, true);
		gasTouchPad.setPosition(touchPadStage.getWidth() - gasTouchPad.getWidth() - 5, 5);
		touchPadStage.addActor(movementTouchPad);
		touchPadStage.addActor(gasTouchPad);
		
		this.explosionSound = Gdx.audio.newSound(Gdx.files.internal(Paths.EXPLOSION_SOUND_EFFECT));
		this.exhaustSound = Gdx.audio.newSound(Gdx.files.internal(Paths.EXHAUST_SOUND_EFFECT));
		this.shootSound = Gdx.audio.newSound(Gdx.files.internal(Paths.SHOOT_SOUND_EFFECT));
		this.pauseSound = Gdx.audio.newSound(Gdx.files.internal(Paths.PAUSE_SOUND_EFFECT));
	}

	// PROPERTIES -----------------------------------------------------------------------------------------
	/**
	 * get the number associated with the player
	 * @return the number associated with the player
	 */
	public int getPlayerNum() {
		return this.PLAYER_NUM;
	}
	
	/**
	 * Get the score the player has
	 * @return the score of the player
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Returns the exhaust particle emitter
	 * @return the exhaust particle emitter
	 */
	public ParticleEmitter getExhaust() {
		return exhaust;
	}
	
	/**
	 * Set reversed steering
	 * @param isReversedSteering <b>true</b> to set steering to reversed
	 */
	public void setReversedSteering(boolean isReversedSteering) {
		this.isReversedSteering = isReversedSteering;
	}
	
	/**
	 * Set the speed of the bullets
	 * @param bulletSpeed the speed of the bullets
	 */
	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	
	/**
	 * Set the bullet movement to reversed of not
	 * @param isBulletMovementReversed <b>true</b> to set bullet movement to reversed
	 */
	public void setBulletMovementReversed(boolean isBulletMovementReversed) {
		this.isBulletMovementReversed = isBulletMovementReversed;
	}
	
	/**
	 * Get the bullets of the player
	 * @return the bullets of the player
	 */
	public Array<Bullet> getBullets() {
		return bullets;
	}
	
	/**
	 * Set the speed of the player
	 * @param speed the speed of the player
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * Set the movement vector of the player
	 * @param x the movement on the x axis
	 * @param y the movement on the y axis
	 */
	public void setMovement(float x, float y) {
		this.movement.set(x, y);
	}
	
	/**
	 * Set the bullets to be big or not
	 * @param isBigBullets <b>true</b> to set the bullets to big
	 */
	public void setBigBullets(boolean isBigBullets) {
		this.isBigBullets = isBigBullets;
	}
	
	/**
	 * Check if player has crashed
	 * @return <b>true</b> if the player has crashed
	 */
	public boolean isCrashed() {
		return crashed;
	}
	
	/**
	 * Get the explosion particle emitter
	 * @return the explosion particle emitter
	 */
	public ParticleEmitter getExplosion() {
		return explosion;
	}
	
	/**
	 * Get the gamepad for the player
	 * @return the gamepad for the player
	 */
	public GamePad getGamePad() {
		return gamePad;
	}
	
	/**
	 * Get the touchpad stage for the player
	 * @return the touchpad stage for the player
	 */
	public Stage getTouchPadStage() {
		return touchPadStage;
	}
	
	// ---------------------------------------------------------------------------------------------------
	
	/**
	 * When player dies this should be called. Stops the players sound,
	 * resets position of the player and creates an explosion particle effect
	 * @param spawnPoint the spawnpoint on which the player will be transfered to
	 */
	public void die(Vector2 spawnPoint) {
		crashed = true;
		explosion.setPosition(getPosition().x, getPosition().y);
		shootSound.stop();
		exhaustSound.stop();
		explosion.start();
		explosionSound.play();
		setPosition(spawnPoint.x, spawnPoint.y + 1);
	}
	
	/**
	 * Handles collision with walls and opposing players bullets.
	 * @param hardBlocks the walls
	 * @param spawnPoint the spawnpoint on which this player will be transfered
	 * @param opponent the opposing player
	 */
	public void checkForCollision(Array<Rectangle> hardBlocks, Vector2 spawnPoint, Player opponent) {
		for(Rectangle rect : hardBlocks) {
			if(bounds.overlaps(rect)) {
				die(spawnPoint);
			}
			// check if bullets collided with walls
			for(int i = 0; i < bullets.size; i++) {
				if(bullets.get(i).getBounds().overlaps(rect)) {
					if(Settings.bouncingBullets) {
						bullets.get(i).setMovementReversed(true);
					} else {
						bullets.get(i).dispose();
						bullets.removeIndex(i);
					}
				} 
			}
		}
		if(gameMode == GameMode.MULTI_PLAYER) {
			for(Bullet bullet : bullets) {
				if(bullet.getBounds().overlaps(opponent.getBounds())) {
					opponent.die(spawnPoint);
					bullet.dispose();
					bullets.removeValue(bullet, true);
					score++;
				}
			}
		}
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
		fixtureDef.density = 8.5f;
				
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
	
	@Override
	public void render(SpriteBatch batch) {
		// draw bullets
		for(Bullet bullet : bullets) {
			bullet.render(batch);
		}
		if(!crashed) {
			exhaust.draw(batch);
			sprite.draw(batch);
		} 
		explosion.draw(batch);
		
		if(Gdx.app.getType() == ApplicationType.Android) {
			touchPadStage.draw();
		}
	}
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		
		body.applyForceToCenter(movement, true);
		if(gasPressed) {
			if(!exhaustSoundPlaying && !isCrashed()) {
				exhaustSound.play();
				exhaustSoundPlaying = true;
			}
			accelerate();
		} if(leftPressed) {
			body.applyAngularImpulse(isReversedSteering ? MathUtils.radDeg * -MAX_TURN_DEG : MathUtils.radDeg * MAX_TURN_DEG, true);
		} if(rightPressed) {
			body.applyAngularImpulse(isReversedSteering ? MathUtils.radDeg * MAX_TURN_DEG : MathUtils.radDeg * -MAX_TURN_DEG, true);
		} if(!gasPressed) {
			exhaustSound.stop();
			exhaustSoundPlaying = false;
		}
		
		
		if(Gdx.app.getType() == ApplicationType.Android) {
			handleTouchPadInput();
			touchPadStage.act(delta);
		}
		
		sprite.setPosition(getPosition().x - sprite.getWidth() / 2, getPosition().y - sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
		// update exhaust
		exhaust.setPosition(getPosition().x, getPosition().y);
		exhaust.update(delta);
		explosion.update(delta);
		
		for(Bullet bullet : bullets) {
			bullet.tick(delta);
		}
		
		if(crashed) {
			timePassed += delta;
			ableToShoot = false;
			playExplosion();
		}
	}
	
	/**
	 * Creates a new bullet and sets it moving in the direction the player is pointing.
	 * Also plays a bullet sound
	 */
	public void shoot() {
		if(ableToShoot) {
			shootSound.play();
			bullets.add(new Bullet(getPosition().x, getPosition().y, isBigBullets ? 8 : 2, isBigBullets ? 8 : 2, bulletSpeed, body, isBulletMovementReversed));
		}
	}
	
	private Touchpad getTouchPad(String backgroundTexturePath, String knobTexturePath, float xPos, float yPos) {
		Skin skin = new Skin();
		Texture padbgTex = new Texture(Gdx.files.internal(backgroundTexturePath));
		Texture knobTex = new Texture(Gdx.files.internal(knobTexturePath));
		padbgTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		knobTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Sprite padSprite = new Sprite(padbgTex);
		padSprite.setSize(30, 30);
		Sprite knobSprite = new Sprite(knobTex);
		knobSprite.setSize(13, 13);
		skin.add("padbg", padSprite);
		skin.add("knob", knobSprite);
		TouchpadStyle touchpadStyle = new TouchpadStyle(skin.getDrawable("padbg"), skin.getDrawable("knob"));
		Touchpad touchpad = new Touchpad(3, touchpadStyle);
		touchpad.setPosition(xPos, yPos);
		return touchpad;
	}
	
	private void setExhaustRotation() {
		float angle = sprite.getRotation();
		exhaust.getAngle().setLow(angle + 270);
		exhaust.getAngle().setHighMin(angle + 270 - 90);
		exhaust.getAngle().setHighMax(angle + 270 + 90);
	}

	private void accelerate() {
		shipRot = (float)(body.getTransform().getRotation() + MathUtils.PI / 2);
		xSpeed = MathUtils.cos(shipRot);
		ySpeed = MathUtils.sin(shipRot);
		movement.set(speed * xSpeed, speed * ySpeed);
		setExhaustRotation();
	}
	
	
	private void handleTouchPadInput() {
		if(movementTouchPad.isTouched()) {
			body.applyAngularImpulse(MathUtils.radDeg * -MAX_TURN_DEG * (movementTouchPad.getKnobPercentX() + movementTouchPad.getKnobPercentY()), true);
		}
					
		if(gasTouchPad.isTouched()) {
			gasPressed = true;
			exhaust.start();
		} if(!gasTouchPad.isTouched()) {
			gasPressed = false;
			movement.set(0, 0);
			exhaust.allowCompletion();
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
	
	@Override
	public void dispose() {
		super.dispose();
		touchPadStage.dispose();
		explosionSound.dispose();
		exhaustSound.dispose();
		shootSound.dispose();
	}
	
	/**
	 * Nested class for getting a gamepad controller to the player
	 * @author johan
	 *
	 */
	public class GamePad extends ControllerAdapter {
		private final byte A = 0;
		private final byte X = 2;
		private final byte START = 7;
		
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
			case A:
				gasPressed = true;
				exhaust.start();
				break;
			case X:
				shoot();
				break;
			case START:
				if(Settings.isPaused) {
					Settings.isPaused = false;
				} else {
					Settings.isPaused = true;
				}
				pauseSound.play();
				break;
			default:
				return false;
			}
			return true;
		}
          
		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			switch(buttonIndex) {
			case A:
				gasPressed = false;
				movement.set(0, 0);
				exhaust.allowCompletion();
				break;
			case X:
				break;
			default:
				return false;
			}
			return true;
		}
	}

	
	
}
