package se.fredin.gravitation.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractEntity implements Entity {

	protected Vector2 position;
	protected Rectangle bounds;
	protected Sprite sprite;
	
	public AbstractEntity() {
		this.position = new Vector2();
		this.bounds = new Rectangle();
		this.sprite = new Sprite();
	}
	
	public AbstractEntity(Vector2 position, String pathToTexture, TextureFilter filter) {
		this.position = position;
		Texture texture = new Texture(Gdx.files.internal(pathToTexture));
		texture.setFilter(filter, filter);
		this.sprite = new Sprite(texture);
		this.bounds = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
	}
	
	public AbstractEntity(float x, float y, String pathToTexture, TextureFilter filter) {
		this();
		position.set(x, y);
		Texture texture = new Texture(Gdx.files.internal(pathToTexture));
		texture.setFilter(filter, filter);
		this.sprite = new Sprite(texture);
		this.bounds = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
	}
	
	
	
	@Override
	public Vector2 getPosition() {
		return this.position;
	}

	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}
	

}
