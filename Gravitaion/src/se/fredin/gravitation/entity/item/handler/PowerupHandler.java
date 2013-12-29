package se.fredin.gravitation.entity.item.handler;

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

public class PowerupHandler {

	private Array<Rectangle> spawnPoints;
	private Array<Powerup> powerups, tmpPowerups;
	private final float TIME_FOR_POWERUP = 1f;
	private float timer = 0f;
	
	public PowerupHandler(TiledMap map, Player player1, Player player2, float unitScale) {
		this.spawnPoints = getWorldAdaptedPowerupLocations(map, unitScale);
		this.powerups = getPowerups(player1, player2);
		this.tmpPowerups = new Array<Powerup>();
	}
	
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
	
	private Powerup getRandomPowerup() {
		return powerups.get((int)(Math.random() * powerups.size));
	}
	
	public void render(SpriteBatch batch) {
		for(Powerup powerup : tmpPowerups) {
			powerup.render(batch);
		}
	}
	
	public void tick(float delta) {
		timer += delta;
		if(timer >= TIME_FOR_POWERUP && powerups.size < 10) {
			tmpPowerups.add(getRandomPowerup());
			timer = 0f;
		}
		for(Powerup powerup : tmpPowerups) {
			powerup.tick(delta);
		}
	}
	
	public void dispose() {
		for(Powerup powerup : powerups) {
			powerup.dispose();
		}
		for(Powerup powerup : tmpPowerups) {
			powerup.dispose();
		}
	}
	
}
