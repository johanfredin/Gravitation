package se.fredin.gravitation.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class PhysicalEntity implements Entity {
	
	protected World world;
	protected Body body;
	protected Rectangle bounds;
	protected Vector2 position;
	protected Sprite sprite;
	protected float bodyWidth, bodyHeight;

	public PhysicalEntity(Vector2 position, String texturePath, World world) {
		this.world = world;
		this.position = position;
		Texture texture = new Texture(Gdx.files.internal(texturePath).path());
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.sprite = new Sprite(texture);
	}
	
	public PhysicalEntity(Vector2 position, String texturePath, World world, float bodyWidth, float bodyHeight) {
		this(position, texturePath, world);
		this.bodyWidth = bodyWidth;
		this.bodyHeight = bodyHeight;
		this.body = getSpecifiedBody(bodyWidth, bodyHeight);
		this.bounds = new Rectangle(getBodyPosition().x, getBodyPosition().y, sprite.getWidth(), sprite.getHeight());
	}
	
	public abstract Body getSpecifiedBody(float bodyWidth, float bodyHeight);
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Vector2 getBodyPosition() {
		return body.getPosition();
	}
	
	public void setBodyPosition(float x, float y) {
		body.setTransform(x, y, 0);
		body.setLinearVelocity(0, 0);
	}
	
	@Override
	public void dispose() {
		world.dispose();
		sprite.getTexture().dispose();
	}
}
