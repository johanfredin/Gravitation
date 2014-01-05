package se.fredin.gravitation.level;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.entity.item.handler.PowerupHandler;
import se.fredin.gravitation.entity.physical.LaunchPad;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.utils.KeyInput;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MultiPlayerLevel extends Level {

	public MultiPlayerLevel(String levelPath, GameScreen gameScreen, GameMode gameMode) {
		super(levelPath, gameScreen, GameMode.MULTI_PLAYER);
		
		// Setup players
		this.spawnPoint = new Vector2(playerSpawnPoints.get((int)(Math.random() * playerSpawnPoints.size)));
		this.player1 = new Player(spawnPoint.x, spawnPoint.y, Paths.SHIP_TEXTUREPATH, this.world, 96, 64, 1, gameMode);
		
		this.spawnPoint = new Vector2(playerSpawnPoints.get((int)(Math.random() * playerSpawnPoints.size)));
		this.player2 = new Player(spawnPoint.x, spawnPoint.y, Paths.SHIP_TEXTUREPATH2, this.world, 96, 64, 2, gameMode);
		this.itemHandler = new PowerupHandler(map, player1, player2, UNIT_SCALE);
		
		// Add key support
		Gdx.input.setInputProcessor(new KeyInput(player1, player2));
		
		addGamepadSupport();
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera, OrthographicCamera camera2) {
		super.render(batch, camera, camera2);
	}
	
	@Override
	public void tick(float delta) {
		player1.tick(delta);
		
		player2.tick(delta);
		player2.checkForCollision(hardBlocks, playerSpawnPoints, player1);
		itemHandler.tick(delta);
		
		for(LaunchPad launchPad : launchPads) {
			launchPad.tick(delta);
			launchPad.checkIfTaken(player1, delta);
		}
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		player1.checkForCollision(hardBlocks, playerSpawnPoints, player2);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
