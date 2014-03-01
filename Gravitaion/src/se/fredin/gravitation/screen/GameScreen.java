package se.fredin.gravitation.screen;

import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.level.MultiPlayerLevel;
import se.fredin.gravitation.level.SinglePlayerLevel;
import se.fredin.gravitation.utils.GameMode;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Game;

/**
 * Screen that handles rendering and updating the levels. No UI screens use the game screen class.
 * @author Johan Fredin
 *
 */
public class GameScreen extends BaseScreen {
	
	private Level level;
	private GameMode gameMode;
	
	/**
	 * Creates a new game screen and starts a new level, multiplayer or single player.
	 * @param game The game instance responsible for switching screens.
	 * @param gameMode The current game mode, multiplayer or single player.
	 * @param level_index The current index of the level.
	 */
	public GameScreen(Game game, GameMode gameMode, int level_index) {
		super(game);
		Settings.currentLevel = level_index;
		this.gameMode = gameMode;
		switch(gameMode) {
		case SINGLE_PLAYER:
			level = new SinglePlayerLevel("data/maps/level_" + level_index + ".tmx", this, gameMode);
			break;
		case MULTI_PLAYER:
			BaseScreen.viewportWidth = 160;
			level = new MultiPlayerLevel("data/maps/level_" + level_index + ".tmx", this, gameMode);
		}
	}

	@Override
	public void render(float delta) {
		switch(gameMode) {
		case SINGLE_PLAYER:
			level.tick(delta);
			level.render(batch, camera);
			break;
		case MULTI_PLAYER:
			level.tick(delta);
			level.render(batch, camera, camera2);
			break;
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		level.dispose();
	}
}
