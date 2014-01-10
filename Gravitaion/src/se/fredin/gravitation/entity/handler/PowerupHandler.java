package se.fredin.gravitation.entity.handler;

import se.fredin.gravitation.entity.item.BigBulletPowerup;
import se.fredin.gravitation.entity.item.ReverseBulletPowerup;
import se.fredin.gravitation.entity.item.FasterBulletPowerup;
import se.fredin.gravitation.entity.item.FasterPlayerPowerup;
import se.fredin.gravitation.entity.item.Powerup;
import se.fredin.gravitation.entity.item.ReversedStearingPowerup;
import se.fredin.gravitation.entity.item.SlowerBulletPowerup;
import se.fredin.gravitation.entity.item.SlowerPlayerPowerup;
import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Handler class for all the powerups in the game. Responsible for drawing them and updating them
 * @author johan
 *
 */
public class PowerupHandler implements Disposable {

	private Array<Rectangle> spawnPoints;
	private Array<Powerup> powerups, tmpPowerups;
	private final byte TIME_FOR_POWERUP = 10;
	private final byte MAX_AMOUNT_OF_POWERUPS_ALLOWED = 10;
	private float timer = 0f;
	
	/**
	 * Creates the powerups
	 * @param map the TiledMap to give the powerups to
	 * @param player1 the first player to interact with the powerups
	 * @param player2 the second player to interact with the powerups
	 * @param unitScale the unitscale of the map
	 */
	public PowerupHandler(TiledMap map, Player player1, Player player2, float unitScale) {
		this.spawnPoints = getWorldAdaptedPowerupLocations(map, unitScale);
		this.powerups = getPowerups(player1, player2);
		this.tmpPowerups = new Array<Powerup>();
	}
	
	/*
	 * Get the powerup locations from the map and put them in a Rectangle array.
	 */
	private Array<Rectangle> getWorldAdaptedPowerupLocations(TiledMap map, float unitScale) {
		Array<RectangleMapObject> rectangleMapObjects = map.getLayers().get("powerups").getObjects().getByType(RectangleMapObject.class);
		this.spawnPoints = new Array<Rectangle>();
		for(RectangleMapObject rect : rectangleMapObjects) {
			rect.getRectangle().set(rect.getRectangle().x * unitScale, rect.getRectangle().y * unitScale, 
				 rect.getRectangle().width * unitScale, rect.getRectangle().height * unitScale);
			spawnPoints.add(rect.getRectangle());
		}
		return spawnPoints;
	}
	
	/*
	 * Add the powerups to positions array
	 */
	private Array<Powerup> getPowerups(Player player1, Player player2) {
		Array<Powerup> powerups = new Array<Powerup>();
		powerups.add(new SlowerPlayerPowerup(spawnPoints, 5, 5, player1, player2));
		powerups.add(new FasterPlayerPowerup(spawnPoints, 5, 5, player1, player2));
		powerups.add(new FasterBulletPowerup(spawnPoints, 5, 5, player1, player2));
		powerups.add(new SlowerBulletPowerup(spawnPoints, 5, 5, player1, player2));
		powerups.add(new ReverseBulletPowerup(spawnPoints, 5, 5, player1, player2));
		powerups.add(new ReversedStearingPowerup(spawnPoints, 5, 5, player1, player2));
		powerups.add(new BigBulletPowerup(spawnPoints, 5, 5, player1, player2));
		return powerups;
	}
	
	/*
	 * Get a random powerup
	 */
	private Powerup getRandomPowerup() {
		return powerups.get((int)(Math.random() * powerups.size));
	}
	
	/**
	 * Renders the powerups 
	 * @param batch the SpriteBatch responsible for drawing to the screen
	 */
	public void render(SpriteBatch batch) {
		for(Powerup powerup : tmpPowerups) {
			powerup.render(batch);
		}
	}
	
	/**
	 * Updates the powerups.
	 * If there are less than 10 powerups on the map another powerup will be added to 
	 * a random location after a specified time interval
	 * @param delta the time interval
	 */
	public void tick(float delta) {
		timer += delta;
		if(timer >= TIME_FOR_POWERUP && powerups.size < MAX_AMOUNT_OF_POWERUPS_ALLOWED) {
			tmpPowerups.add(getRandomPowerup());
			timer = 0f;
		}
		for(Powerup powerup : tmpPowerups) {
			powerup.tick(delta);
		}
	}
	
	@Override
	public void dispose() {
		for(Powerup powerup : powerups) {
			powerup.dispose();
		}
		for(Powerup powerup : tmpPowerups) {
			powerup.dispose();
		}
	}
	
}
