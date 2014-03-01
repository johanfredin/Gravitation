package se.fredin.gravitation.screen;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * The basic screen setting class that all other screen classes extend from.
 * @author Johan Fredin.
 *
 */
public abstract class BaseScreen implements Disposable, Screen {
	
	/**
	 * The viewport width the camera will use.
	 */
	public static int viewportWidth = 320;
	/**
	 * The viewport height the camera will use.
	 */
	public static int viewportHeight = 120;
	
	protected Sound buttonPressedSound;
	protected OrthographicCamera camera, camera2;
	protected SpriteBatch batch;
	protected Game game;
	
	/**
	 * Creates a new BaseScreen with a Camera and a SpriteBatch. Also makes the viewport width and height smaller if game is being played on a mobile device.
	 */
	public BaseScreen() {
		this.camera = new OrthographicCamera();
		this.batch = new SpriteBatch();
		if(Gravitation.isMobileDevice()) {
			viewportWidth = 160;
			viewportHeight = 80;
		}
		camera.setToOrtho(false, viewportWidth, viewportHeight);
		buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal(Paths.MENU_SELECT_SOUND_EFFECT));
	}
	
	/**
	 * Creates a new BaseScreen with a Camera and a SpriteBatch. also sets the viewport for mobile devises and desktop.
	 * @param game The game instance this screen will use to switch screen.
	 */
	public BaseScreen(Game game) {
		this();
		this.game = game;
		camera2 = new OrthographicCamera();
		camera2.setToOrtho(false, viewportWidth, viewportHeight);
	}
	
	/**
	 * Get the game instance of the BaseScreen.
	 * @return The game instance.
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Get the camera.
	 * @return The camera object.
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, viewportWidth, viewportHeight);
		camera2.setToOrtho(false, viewportWidth, viewportHeight);
	}

	@Override
	public void show() {}
	
	@Override
	public void hide() {}
	
	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
	
	@Override
	public void dispose() {
		batch.dispose();
		game.dispose();
		buttonPressedSound.dispose();
	}

}
