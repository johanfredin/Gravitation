package se.fredin.gravitation.entity.physical;

import se.fredin.gravitation.entity.AbstractEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class PhysicalEntity extends AbstractEntity {
	
	protected World world;
	protected Body body;
	protected float bodyWidth, bodyHeight;
	protected final float PIXELS_TO_METER = 1 / 32f;
	
	public PhysicalEntity(float xPos, float yPos, String texturePath, World world, float bodyWidth, float bodyHeight) {
		super(xPos, yPos, bodyWidth, bodyHeight, texturePath);
		this.world = world;
		Texture texture = new Texture(Gdx.files.internal(texturePath).path());
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.sprite = new Sprite(texture);
		bodyWidth *= PIXELS_TO_METER;
		bodyHeight *= PIXELS_TO_METER;
		this.bodyWidth = bodyWidth;
		this.bodyHeight = bodyHeight;
		this.body = getSpecifiedBody(xPos, yPos, bodyWidth, bodyHeight);
		this.bounds = new Rectangle(getPosition().x - sprite.getWidth() / 2, getPosition().y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
	}
	
	public abstract Body getSpecifiedBody(float xPos, float yPos,float bodyWidth, float bodyHeight);
	
	@Override
	public void tick(float delta) {
		bounds.setPosition(getPosition().x - sprite.getWidth() / 2, getPosition().y - sprite.getHeight() / 2);
	}
	
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	@Override
	public void setPosition(float x, float y) {
		body.setTransform(x, y, 0);
		body.setLinearVelocity(0, 0);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
		sprite.getTexture().dispose();
	}
}
