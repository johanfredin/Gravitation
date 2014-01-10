package se.fredin.gravitation.entity;

import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Class used for making projectiles.
 * @author johan
 *
 */
public class Bullet extends AbstractEntity {
	
	private Vector2 movement;
	private float speed;
	private boolean isMovementReversed;
	
	/**
	 * Creates a new bullet and sets it moving immediately.
	 * @param x - the x position of the bullet.
	 * @param y - the y position of the bullet.
	 * @param width - the width of the bullet.
	 * @param height - the height of the bullet.
	 * @param speed - the speed of the bullet.
	 * @param body - the box2D object that fired the bullet.
	 * @param isMovementReversed - sets the bullet to go forward or backward.
	 */
	public Bullet(float x, float y, float width, float height, float speed, Body body, boolean isMovementReversed) {
		super(x, y, width, height, Gdx.files.internal(Paths.BULLET_TEXTUREPATH).path());
		this.speed = speed;
		float bulletRot = (float)(body.getTransform().getRotation() + MathUtils.PI / 2);
		float bulletXSpeed = MathUtils.cos(bulletRot);
		float bulletYSpeed = MathUtils.sin(bulletRot);
		this.isMovementReversed = isMovementReversed;
		this.movement = new Vector2(speed * bulletXSpeed, speed * bulletYSpeed);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

	@Override
	public void tick(float delta) {
		super.tick(delta);
		if(isAlive) {
			if(isMovementReversed) {
				position.sub(movement);
			} else {
				position.add(movement);
			}
		}
	}
	
	/**
	 * Sets the speed of the bullet
	 * @param speed - the speed of the bullet.
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * Get the speed of the bullet
	 * @return - the speed of the bullet
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Set the movement of the bullet.
	 * @param xSpeed - the movement on the x-axis of the bullet
	 * @param ySpeed - the movement on the y-axis of the bullet
	 */
	public void setMovement(float xSpeed, float ySpeed) {
		this.movement.set(xSpeed, ySpeed);
	}
	
	/**
	 * Get the movement of the bullet
	 * @return - the movement of the bullet
	 */
	public Vector2 getMovement() {
		return movement;
	}

	/**
	 * Get weather or not the movement is reversed
	 * @return <b>true</b> if movement is reversed
	 */
	public boolean isMovementReversed() {
		return isMovementReversed;
	}
	
	/**
	 * Set weather or not the movement of the bullet should be reversed
	 * @param isMovementReversed - <b>true</b> will make movement reversed
	 */
	public void setMovementReversed(boolean isMovementReversed) {
		this.isMovementReversed = isMovementReversed;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
}
