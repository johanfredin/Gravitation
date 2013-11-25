package se.fredin.gravitation;

import se.fredin.gravitation.screen.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;

public class Gravitation extends Game {

	public static FPSLogger logger;
	public static final String LOG = "Gravitation";
	
	@Override
	public void create() {
		logger = new FPSLogger();
		setScreen(new GameScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
	//	logger.log();
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
	
	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
	}
		
	
}
