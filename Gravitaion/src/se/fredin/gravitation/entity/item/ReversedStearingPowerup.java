package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class ReversedStearingPowerup extends Powerup {

	public ReversedStearingPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player, Player player2) {
		super(spawnPoints, width, height, Gdx.files.internal(Paths.REVERSED_STEARING_TEXTURE).path(), player, player2, "reversed steering", false);
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
