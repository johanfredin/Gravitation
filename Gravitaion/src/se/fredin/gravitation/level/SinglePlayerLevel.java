package se.fredin.gravitation.level;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.entity.handler.StationHandler;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.screen.ui.ingame.SinglePlayerDialogue;
import se.fredin.gravitation.utils.GameMode;
import se.fredin.gravitation.utils.KeyInput;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/***
 * Class handling single player levels.
 * @author Johan Fredin
 *
 */
public class SinglePlayerLevel extends Level {

	private StationHandler stationHandler;
	
	/**
	 * Creates a new single player level tied to a tiled map, using box2d physics.
	 * @param levelPath The path to the level.
	 * @param gameScreen The GameScreen in charge of changing levels.
	 * @param gameMode The current game mode.
	 */
	public SinglePlayerLevel(String levelPath, GameScreen gameScreen, GameMode gameMode) {
		super(levelPath, gameScreen, GameMode.SINGLE_PLAYER);
		// Setup player
		this.spawnPoint = new Vector2(launchPadHandler.getFirstLaunchPadPosition());
		this.player1 = new Player(spawnPoint.x, spawnPoint.y + 1, Paths.SHIP_TEXTUREPATH, this.world, 96, 64, 1, gameMode);
		this.stationHandler = new StationHandler(map, player1, UNIT_SCALE);
		
		// Add key support
		if(Gravitation.isMobileDevice()) {
			gameScreen.getCamera().setToOrtho(false, BaseScreen.viewportWidth / 2, BaseScreen.viewportHeight / 2);
			Gdx.input.setInputProcessor(player1.getTouchPadStage());
		} else {
			Gdx.input.setInputProcessor(new KeyInput(player1, null));
			addGamepadSupport();
		}
		this.inGameMenu = new SinglePlayerDialogue(gameScreen.getGame(), this, gameScreen.getCamera());
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		super.render(batch, camera);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		launchPadHandler.render(batch);
		player1.render(batch);
		stationHandler.render(batch);
		batch.end();
		
		if(Gravitation.DEBUG_MODE) {
			debugRender(camera);
		}
		
		if(stationHandler.isLastStationPassed()) {
			if(!controlsGivenToStage) {
				Gdx.input.setInputProcessor(inGameMenu.getStage());
				controlsGivenToStage = true;
			}
			inGameMenu.render();
		}
		
		moveCamera(camera, player1, 0, MAP_WIDTH, MAP_HEIGHT);
		camera.update();
	
	}
	
	@Override
	public void tick(float delta) {
		if(!Settings.isPaused) {
			if(!stationHandler.isLastStationPassed()) {
				player1.tick(delta);
				stationHandler.tick(delta);
						
				launchPadHandler.tick(delta);
				world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
				player1.checkForCollision(hardBlocks, launchPadHandler.getFirstLaunchPadPosition(), null);
			}
			inGameMenu.tick(delta);
		}
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		stationHandler.dispose();
		inGameMenu.dispose();
	}



}
