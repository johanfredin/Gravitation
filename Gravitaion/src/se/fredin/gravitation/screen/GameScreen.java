package se.fredin.gravitation.screen;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.level.MultiPlayerLevel;
import se.fredin.gravitation.level.SinglePlayerLevel;

import com.badlogic.gdx.Game;

public class GameScreen extends BaseScreen {
	
	private State state = State.PLAYING;
	private Level level;
	private GameMode gameMode;
	
	enum State {
		PLAYING,
		NOT_PLAYING,
		RETURN_TO_MENU,
		REPLAY,
		PAUSED,
		SWITCHING_LEVEL
	}
	
	public GameScreen(Game game, GameMode gameMode, int level_index) {
		super(game);
		this.gameMode = gameMode;
		switch(gameMode) {
		case SINGLE_PLAYER:
			level = new SinglePlayerLevel("data/maps/level_" + level_index + ".tmx", this, gameMode);
			break;
		case MULTI_PLAYER:
			BaseScreen.VIEWPORT_WIDTH = 160;
			level = new MultiPlayerLevel("data/maps/level_" + level_index + ".tmx", this, gameMode);
		}
	}

	@Override
	public void render(float delta) {
		switch(gameMode) {
		case SINGLE_PLAYER:
			level.render(batch, camera);
			break;
		case MULTI_PLAYER:
			level.render(batch, camera, camera2);
			break;
		}
		level.tick(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
