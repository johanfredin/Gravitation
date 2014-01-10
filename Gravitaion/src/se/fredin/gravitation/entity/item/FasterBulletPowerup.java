package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * A powerup that makes the bullets faster
 * @author johan
 *
 */
public class FasterBulletPowerup extends Powerup {

	public FasterBulletPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2) {
		super(spawnPoints, width, height, Gdx.files.internal(Paths.FASTER_BULLET_TEXTURE).path(), player1, player2, "fast bullets", true);
	}
	
	@Override
	public void affectEntity(Player player) {
		player.setBulletSpeed(Settings.FAST_BULLET_SPEED);
	}
	
	@Override
	public void removePower(Player player) {
		player.setBulletSpeed(Settings.DEFAULT_BULLET_SPEED);
	}

}
