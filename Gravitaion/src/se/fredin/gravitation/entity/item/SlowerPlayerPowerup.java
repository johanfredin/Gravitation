package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.PlayerDefaults;

import com.badlogic.gdx.Gdx;

public class SlowerPlayerPowerup extends Powerup {

	public SlowerPlayerPowerup(float x, float y, float width, float height, Player player) {
		super(x, y, width, height, Gdx.files.internal(Paths.SLOW_POWERUP_TEXTURE).path(), player);
	}

	@Override
	public void affectPlayer(Player player) {
		player.setSpeed(PlayerDefaults.DEFAULT_SPEED / 2);
	}

	@Override
	public void removePower(Player player) {
		player.setSpeed(PlayerDefaults.DEFAULT_SPEED);
	}

}
