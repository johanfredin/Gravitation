package se.fredin.gravitation.entity;

import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet implements Entity {
	
	private Vector2 position;
	private Vector2 movement;
	private Rectangle bounds;
	private Sprite sprite;
	private boolean isAlive = true;
	
	public Bullet(float x, float y) {
		this.position = new Vector2(x, y);
		this.movement = new Vector2(0, 0);
		this.bounds = new Rectangle();
		this.sprite = new Sprite(new Texture(Gdx.files.internal(Paths.BULLET_TEXTUREPATH)));
		this.sprite.setSize(2, 2);
		this.bounds = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(isAlive) {
			sprite.draw(batch);
		}
	}

	@Override
	public void tick(float delta) {
		bounds.setPosition(position);
		sprite.setPosition(bounds.getX(), bounds.getY());
		if(isAlive) {
			position.add(movement);
		}
	}

	@Override
	public Vector2 getBodyPosition() {
		return position;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setMovement(float xSpeed, float ySpeed) {
		this.movement.set(xSpeed, ySpeed);
	}

	@Override
	public void dispose() {
		isAlive = false;
		sprite.getTexture().dispose();
	}
	
	

}
