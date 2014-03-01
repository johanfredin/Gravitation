package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * A powerup that will make the steering reversed.
 * @author Johan Fredin
 *
 */
public class ReversedStearingPowerup extends Powerup {

	public ReversedStearingPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player, Player player2) {
		super(spawnPoints, width, height, player, player2, "reversed steering", false);
	}

	@Override
	public void affectEntity(Player player) {
		player.setReversedSteering(true);
	}

	@Override
	public void removePower(Player player) {
		player.setReversedSteering(false);
	}

}
