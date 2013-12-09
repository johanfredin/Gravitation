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
	protected Sprite sprite;
	protected float bodyWidth, bodyHeight;
	protected float mass;
	protected final float PIXELS_TO_METER = 1 / 32f;
	
	public PhysicalEntity(float xPos, float yPos, String texturePath, World world, float bodyWidth, float bodyHeight) {
		this.world = world;
		Texture texture = new Texture(Gdx.files.internal(texturePath).path());
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.sprite = new Sprite(texture);
		bodyWidth *= PIXELS_TO_METER;
		bodyHeight *= PIXELS_TO_METER;
		this.bodyWidth = bodyWidth;
		this.bodyHeight = bodyHeight;
		this.body = getSpecifiedBody(xPos, yPos, bodyWidth, bodyHeight);
		this.bounds = new Rectangle(getBodyPosition().x, getBodyPosition().y, sprite.getWidth(), sprite.getHeight());
	}
	
	public abstract Body getSpecifiedBody(float xPos, float yPos,float bodyWidth, float bodyHeight);
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Vector2 getBodyPosition() {
		return body.getPosition();
	}
	
	public Sprite getSprite() {
		return sprite;
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
