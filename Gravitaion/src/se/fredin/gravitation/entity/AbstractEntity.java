package se.fredin.gravitation.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * AbstractEntity lets you create entities in different ways. Contains all the basic requirements for an entity.
 * @author johan
 * 
 */
public abstract class AbstractEntity implements Entity {

	protected Vector2 position;
	protected Rectangle bounds;
	protected Sprite sprite;
	protected boolean isAlive = true;
	protected Array<Rectangle> spawnPoints;
	
	/**
	 * Creates a new entity with given width and height at the specified position.
	 * Gives it a sprite with the given texture and creates a bounding box around it.
	 * @param x - the x position of the entity
	 * @param y - the y position of the entity
	 * @param width - the width of the entity
	 * @param height - the height of the entity
	 * @param texturePath - the path to the given texture used for the sprite
	 */
	public AbstractEntity(float x, float y, float width, float height, String texturePath) {
		this.position = new Vector2(x, y);
		this.sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
		this.sprite.setSize(width, height);
		this.bounds = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
	}
	
	/**
	 * Creates a new entity with given width and height.
	 * The position of the entity will be a random value from the array passed in.
	 * Gives it a sprite with the given texture and creates a bounding box around it.
	 * @param spawnPoints - The array with positions that the entity will randomly select from.
	 * @param width - the width of the entity.
	 * @param height - the height of the entity.
	 * @param texturePath - the path to the given texture used for the sprite.
	 */
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
	
	/**
	 * Sets the position of the entity
	 * @param x - the x position of the entity
	 * @param y - the y position of the entity
	 */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	/**
	 * Check if the entity is alive or not
	 * @return <b>true</b> if the entity is alive
	 */
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public void dispose() {
		isAlive = false;
		sprite.getTexture().dispose();
	}

}
