package se.fredin.gravitation.entity.handler;

import se.fredin.gravitation.entity.physical.LaunchPad;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Handler class that is responsible for drawing and updating all the LaunchPad objects in the game
 * @author johan
 *
 */
public class LaunchPadHandler implements Disposable {
	
	private Array<LaunchPad> launchPads;
	private Array<Vector2> launchPadPositions;
	private Array<Vector2> playerSpawnPoints;
	
	/**
	 * Creates the launchpads and gives them to the map.
	 * @param map the TiledMap the launchpads should be given to
	 * @param UNIT_SCALE the unitscale that this map uses
	 * @param world the box2D World 
	 */
	public LaunchPadHandler(TiledMap map, final float UNIT_SCALE, World world) {
		launchPadPositions = new Array<Vector2>();
		playerSpawnPoints = new Array<Vector2>();
		launchPads = new Array<LaunchPad>();
		for(int i = 1; i <= 4; i++) {
			MapProperties spawnProperties = map.getLayers().get("spawn-points").getObjects().get("start" + i).getProperties();
			
			Vector2 launchPadPosition = new Vector2((Float)spawnProperties.get("x") * UNIT_SCALE, (Float)spawnProperties.get("y") * UNIT_SCALE);
			this.launchPadPositions.add(launchPadPosition);
			
			LaunchPad launchPad = new LaunchPad(launchPadPosition.x, launchPadPosition.y, Paths.LANDING_PAD_TEXTUREPATH, world, 180, 25f);
			this.launchPads.add(launchPad);
			this.playerSpawnPoints.add(new Vector2(launchPadPosition.x + launchPad.getSprite().getWidth() / 4, launchPadPosition.y + launchPad.getSprite().getHeight()));
		}
	}
	
	/**
	 * Renders the launchpads
	 * @param batch the SpriteBatch responsible for rendering the launchpads
	 */
	public void render(SpriteBatch batch) {
		for(LaunchPad launchPad : launchPads) {
			launchPad.render(batch);
		}
	}
	
	/**
	 * Updates the launchpads
	 * @param delta the time interval
	 */
	public void tick(float delta) {
		for(LaunchPad launchPad : launchPads) {
			launchPad.tick(delta);
		}
	}
	
	/**
	 * Updates the launchpads and checks if a player is currently on a launchpad
	 * @param delta the time interval
	 * @param player1 player one
	 * @param player2 player two
	 */
	public void tick(float delta, Player player1, Player player2) {
		for(LaunchPad launchPad : launchPads) {
			launchPad.tick(delta);
			launchPad.checkIfTaken(player1, player2, delta);
		}
	}
	
	/**
	 * Checks all the launchpads to see if a player has landed on one. Then returns a random
	 * launchpad position from the launchpads where a player is not currently positioned.
	 * @return a randomly selected spawnpoint
	 */
	public Vector2 getRandomAvailableSpawnPoint() {
		Array<Vector2> spawnPoints = new Array<Vector2>();
		for(LaunchPad launchPad : this.launchPads) {
			if(!launchPad.isTaken()) {
				spawnPoints.add(launchPad.getPosition());
			}
		}
		return spawnPoints.get((int)(Math.random() * spawnPoints.size));
	}
	
	/**
	 * Get the position of the first launchpad
	 * @return the position of the first launchpad
	 */
	public Vector2 getFirstLaunchPadPosition() {
		return launchPads.get(0).getPosition();
	}

	/**
	 * Get the launchpads
	 * @return the launchpads
	 */
	public Array<LaunchPad> getLaunchPads() {
		return launchPads;
	}
	
	/**
	 * Get the spawn points
	 * @return the spawn points
	 */
	public Array<Vector2> getPlayerSpawnPoints() {
		return playerSpawnPoints;
	}
	
	@Override
	public void dispose() {
		for(LaunchPad launchPad : launchPads) {
			launchPad.dispose();
		}
	}
}
