package se.fredin.gravitation;

import se.fredin.gravitation.screen.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;

public class Gravitation extends Game {

	public static FPSLogger logger;
	public static final String LOG = "Gravitation";
	public static final boolean DEBUG_MODE = false;
	public static boolean splitScreen = true;
	
	@Override
	public void create() {
		logger = new FPSLogger();
		setScreen(new GameScreen(this));
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
		//Gdx.app.exit();
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
	}
		
	
}
