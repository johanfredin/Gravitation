package se.fredin.gravitation.level;

import se.fredin.gravitation.entity.handler.PowerupHandler;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.screen.ui.ingame.MultiPlayerDialogue;
import se.fredin.gravitation.utils.GameMode;
import se.fredin.gravitation.utils.KeyInput;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/***
 * Class handling multiplayer levels.
 * @author Johan Fredin
 *
 */
public class MultiPlayerLevel extends Level {

	private float timer = 0f;
	private boolean isPlayer1Winner, isPlayer2Winner, isDraw;
	
	/**
	 * Creates a new multiplayer level tied to a tiled map, using box2d physics.
	 * @param levelPath The path to the level.
	 * @param gameScreen The GameScreen in charge of changing levels.
	 * @param gameMode The current game mode.
	 */
	public MultiPlayerLevel(String levelPath, GameScreen gameScreen, GameMode gameMode) {
		super(levelPath, gameScreen, GameMode.MULTI_PLAYER);
		
		// Setup players
		this.spawnPoint = new Vector2(launchPadHandler.getRandomAvailableSpawnPoint());
		this.player1 = new Player(spawnPoint.x, spawnPoint.y + 1, Paths.SHIP_TEXTUREPATH, this.world, 96, 64, 1, gameMode);
		this.spawnPoint = new Vector2(launchPadHandler.getRandomAvailableSpawnPoint());
		this.player2 = new Player(spawnPoint.x, spawnPoint.y + 1, Paths.SHIP_TEXTUREPATH2, this.world, 96, 64, 2, gameMode);
		
		this.itemHandler = new PowerupHandler(map, player1, player2, UNIT_SCALE);
		this.inGameMenu = new MultiPlayerDialogue(gameScreen.getGame(), this, gameScreen.getCamera());
		// Add key support
		Gdx.input.setInputProcessor(new KeyInput(player1, player2));
		// Add gamepad support if possible
		addGamepadSupport();
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera, OrthographicCamera camera2) {
		super.render(batch, camera, camera2);
		if(isPlayer1Winner) {
			if(!controlsGivenToStage) {
				Gdx.input.setInputProcessor(inGameMenu.getStage());
				controlsGivenToStage = true;
				multiPlayerMatchEnded = true;
			}
			inGameMenu.render("Player1");
		} else if(isPlayer2Winner) {
			if(!controlsGivenToStage) {
				Gdx.input.setInputProcessor(inGameMenu.getStage());
				controlsGivenToStage = true;
				multiPlayerMatchEnded = true;
			}
			inGameMenu.render("Player2");
		} else if(isDraw) {
			if(!controlsGivenToStage) {
				Gdx.input.setInputProcessor(inGameMenu.getStage());
				controlsGivenToStage = true;
				multiPlayerMatchEnded = true;
			}
			inGameMenu.render("Draw");
		}
	}
	
	@Override
	public void tick(float delta) {
		if(!Settings.isPaused) {
			if(!multiPlayerMatchEnded) {
				
				if(!Settings.isUnlimitedTime) {
					timer += delta;
				} 
				
				if(timer >= Settings.defaultTimeLimit) {
					if(player1.getScore() > player2.getScore()) {
						System.out.println("Player 1 wins");
						isPlayer1Winner = true;
						isPlayer2Winner = false;
						isDraw = false;
					} else if(player2.getScore() > player1.getScore()) {
						System.out.println("Player 2 wins");
						isPlayer2Winner = true;
						isPlayer1Winner = false;
						isDraw = false;
					} else {
						System.out.println("DRAW!");
						isDraw = true;
						isPlayer1Winner = false;
						isPlayer2Winner = false;
					}
					BaseScreen.viewportWidth = 320;
				}
				
				if(player1.getScore() >= Settings.defaultScoreLimit && !Settings.isUnlimitedcore) {
					System.out.println("Player 1 wins");
					isPlayer1Winner = true;
					isPlayer2Winner = false;
					isDraw = false;
					BaseScreen.viewportWidth = 320;
				} else if(player2.getScore() >= Settings.defaultScoreLimit && !Settings.isUnlimitedcore) {
					System.out.println("Player 2 wins");
					isPlayer2Winner = true;
					isPlayer1Winner = false;
					isDraw = false;
					BaseScreen.viewportWidth = 320;
				}
				
				player1.tick(delta);
				player2.tick(delta);
				player2.checkForCollision(hardBlocks, launchPadHandler.getRandomAvailableSpawnPoint(), player1);
				itemHandler.tick(delta);
				
				launchPadHandler.tick(delta, player1, player2);
				world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
				player1.checkForCollision(hardBlocks, launchPadHandler.getRandomAvailableSpawnPoint(), player2);
			}
			inGameMenu.tick(delta);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		inGameMenu.dispose();
	}

}
