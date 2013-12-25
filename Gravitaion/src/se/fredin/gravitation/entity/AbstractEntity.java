package se.fredin.gravitation.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Used for non box2d objects such as bullets and powerups.
 * @author johan
 *
 */
public abstract class AbstractEntity implements Entity {

	protected Vector2 position;
	protected Rectangle bounds;
	protected Sprite sprite;
	protected boolean isAlive = true;
	protected Array<Rectangle> spawnPoints;
	
	public AbstractEntity(float x, float y, float width, float height, String texturePath) {
		this.position = new Vector2(x, y);
		this.sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
		this.sprite.setSize(width, height);
		this.bounds = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
	}
	
	public AbstractEntity(Array<Rectangle> spawnPoints, float width, float height, String texturePath) {
		this.spawnPoints = spawnPoints;
		this.position = new Vector2(spawnPoints.get((int)(Math.random() * spawnPoints.size)).x, spawnPoints.get((int)(Math.random() * spawnPoints.size)).y);
		this.sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
		this.sprite.setSize(width, height);
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
	}

	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public void dispose() {
		isAlive = false;
		sprite.getTexture().dispose();
	}

}
