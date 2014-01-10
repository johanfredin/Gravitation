package se.fredin.gravitation.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Iterface for dealing with the basics of a level
 * @author johan
 *
 */
public interface LevelBase {

	/**
	 * Updates the level
	 * @param deltatime - the time interval
	 */
	void tick(float deltatime);
	
	/**
	 * Render method used for two player mode, or when a second camera is needed
	 * @param renderer - the SpriteBatch responsible for rendering to the screen
	 * @param camera1 - the first camera
	 * @param camera2 - the second camera
	 */
	void render(SpriteBatch renderer, OrthographicCamera camera1, OrthographicCamera camera2);
	
	/**
	 * Render method used for two player mode, or when a second camera is needed
	 * @param renderer - the SpriteBatch responsible for rendering to the screen
	 * @param camera1 - the camera
	 */
	void render(SpriteBatch renderer, OrthographicCamera camera);
}
