package se.fredin.gravitation;

import se.fredin.gravitation.screen.ui.MainMenuScreen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;

/**
 * Starts up the game.
 * @author Johan Fredin
 *
 */
public class Gravitation extends Game {

	/**
	 * Used for testing the game in debug mode, once in debug mode the screen will render the boundaries of the ship and the level edges.
	 */
	public static final boolean DEBUG_MODE = false;
	private FPSLogger logger;
	
	@Override
	public void create() {
		logger = new FPSLogger();
		setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
		if(DEBUG_MODE) {
			logger.log();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	/**
	 * Used to check if the game is being played on a mobile device or not.
	 * @return <b>true</b> if game is played on Android or iOS.
	 */
	public static boolean isMobileDevice() {
		return Gdx.app.getType() == ApplicationType.iOS || Gdx.app.getType() == ApplicationType.Android;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
		
	
}
