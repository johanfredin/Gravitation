package se.fredin.gravitation.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interface for dealing with the basics of a level.
 * @author Johan Fredin
 *
 */
public interface LevelBase {

	/**
	 * Updates the level.
	 * @param deltatime The time interval since last render occurred.
	 */
	void tick(float deltatime);
	
	/**
	 * Render method used for two player mode, or when a second camera is needed.
	 * @param renderer The SpriteBatch responsible for rendering to the screen.
	 * @param camera1 The first camera.
	 * @param camera2 The second camera.
	 */
	void render(SpriteBatch renderer, OrthographicCamera camera1, OrthographicCamera camera2);
	
	/**
	 * Render method used for singleplayer mode and mobile versions.
	 * @param renderer The SpriteBatch responsible for rendering to the screen.
	 * @param camera1 The camera.
	 */
	void render(SpriteBatch renderer, OrthographicCamera camera);
}
