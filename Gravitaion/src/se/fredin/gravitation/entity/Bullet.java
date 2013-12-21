package se.fredin.gravitation.entity;

import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends AbstractEntity {
	
	private Vector2 movement;
	
	public Bullet(float x, float y, float width, float height) {
		super(x, y, width, height, Gdx.files.internal(Paths.BULLET_TEXTUREPATH).path());
		this.movement = new Vector2(0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

	@Override
	public void tick(float delta) {
		super.tick(delta);
		if(isAlive) {
			position.add(movement);
		}
	}

	public void setMovement(float xSpeed, float ySpeed) {
		this.movement.set(xSpeed, ySpeed);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	

}
