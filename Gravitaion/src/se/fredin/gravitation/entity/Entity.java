package se.fredin.gravitation.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * A common interface that contains the main methods that all entities should have.
 * @author Johan Fredin
 *
 */
public interface Entity extends Disposable {
	
	/**
	 * Renders to the screen.
	 * @param batch The SpriteBatch used for rendering.
	 */
	void render(SpriteBatch batch);
	
	/**
	 * Updates entity.
	 * @param delta The time interval since last render occurred.
	 */
	void tick(float delta);
	
	/**
	 * Get the position of the entity.
	 * @return The position of the entity.
	 */
	Vector2 getPosition();
	
	/**
	 * Get the boundaries of the entity, used for collision control.
	 * @return The boundaries of the entity.
	 */
	Rectangle getBounds();
	
	@Override
	public void dispose();

}
