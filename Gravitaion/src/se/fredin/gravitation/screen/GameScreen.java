package se.fredin.gravitation.screen;

import se.fredin.gravitation.level.Level;

import com.badlogic.gdx.Game;

public class GameScreen extends BaseScreen {
	
	private State state = State.PLAYING;
	private Level level;
	
	enum State {
		PLAYING,
		NOT_PLAYING,
		RETURN_TO_MENU,
		REPLAY,
		PAUSED,
		SWITCHING_LEVEL
	}
	
	public GameScreen(Game game) {
		super(game);
		level = new Level("data/maps/level_1.tmx", this);
	}

	@Override
	public void render(float delta) {
		level.render(batch, camera, camera2);
		level.tick(delta);
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
