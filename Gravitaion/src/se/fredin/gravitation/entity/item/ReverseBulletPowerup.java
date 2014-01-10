package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * A powerup that makes the bullets travel in the opposite direction
 * @author johan
 *
 */
public class ReverseBulletPowerup extends Powerup {

	public ReverseBulletPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2) {
		super(spawnPoints, width, height, Gdx.files.internal(Paths.REVERSE_BULLET_TEXTURE).path(), player1, player2, "reversed bullets", false);
	}

	@Override
	public void affectEntity(Player player) {
		player.setBulletMovementReversed(true);
	}

	@Override
	public void removePower(Player player) {
		player.setBulletMovementReversed(false);
	}

}
