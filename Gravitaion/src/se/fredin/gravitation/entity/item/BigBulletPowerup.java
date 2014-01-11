package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * A powerup that makes the bullets bigger
 * @author johan
 *
 */
public class BigBulletPowerup extends Powerup {

	public BigBulletPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2) {
		super(spawnPoints, width, height, player1, player2, "big bullets", true);
	}

	@Override
	public void affectEntity(Player player) {
		player.setBigBullets(true);
	}

	@Override
	public void removePower(Player player) {
		player.setBigBullets(false);
	}

}
