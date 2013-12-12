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
		level.render(batch, camera);
		level.tick(delta);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
