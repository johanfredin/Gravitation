package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * A powerup that will make the bullets slower.
 * @author johan
 *
 */
public class SlowerBulletPowerup extends Powerup {

	public SlowerBulletPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2) {
		super(spawnPoints, width, height, Gdx.files.internal(Paths.SLOWER_BULLET_TEXTURE).path(), player1, player2, "slow down", false);
	}

	@Override
	public void affectEntity(Player player) {
		player.setBulletSpeed(Settings.SLOW_BULLET_SPEED);
	}
	
	@Override
	public void removePower(Player player) {
		player.setBulletSpeed(Settings.DEFAULT_BULLET_SPEED);
	}
}
