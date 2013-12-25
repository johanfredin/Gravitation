package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;

public class BouncingBulletPowerup extends Powerup {

	public BouncingBulletPowerup(float x, float y, float width, float height, Player player1, Player player2) {
		super(x, y, width, height, Gdx.files.internal(Paths.BOUNCING_BULLET_TEXTURE).path(), player1, player2);
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
