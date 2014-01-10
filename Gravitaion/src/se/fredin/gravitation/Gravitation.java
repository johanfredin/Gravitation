package se.fredin.gravitation;

import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.utils.GameMode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.FPSLogger;

public class Gravitation extends Game {

	private FPSLogger logger;
	public static final boolean DEBUG_MODE = false;
	
	@Override
	public void create() {
		logger = new FPSLogger();
		setScreen(new GameScreen(this, GameMode.SINGLE_PLAYER, 1));
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
	
	public static boolean isMobileDevice() {
		return Gdx.app.getType() == ApplicationType.iOS || Gdx.app.getType() == ApplicationType.Android;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
		
	
}
