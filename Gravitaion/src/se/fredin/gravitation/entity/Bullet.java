package se.fredin.gravitation.entity;

import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Bullet extends AbstractEntity {
	
	private Vector2 movement;
	private float speed;
	private boolean isMovementReversed;
	private float bulletRot;
	private float bulletXSpeed;
	private float bulletYSpeed;
	
	public Bullet(float x, float y, float width, float height, float speed, Body body, boolean isMovementReversed) {
		super(x, y, width, height, Gdx.files.internal(Paths.BULLET_TEXTUREPATH).path());
		this.speed = speed;
		this.bulletRot = (float)(body.getTransform().getRotation() + MathUtils.PI / 2);
		this.bulletXSpeed = MathUtils.cos(bulletRot);
		this.bulletYSpeed = MathUtils.sin(bulletRot);
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
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setMovement(float xSpeed, float ySpeed) {
		this.movement.set(xSpeed, ySpeed);
	}
	
	public Vector2 getMovement() {
		return movement;
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	public boolean isMovementReversed() {
		return isMovementReversed;
	}
	public void setMovementReversed(boolean isMovementReversed) {
		this.isMovementReversed = isMovementReversed;
	}

}
