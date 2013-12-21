package se.fredin.gravitation.entity.item;

import com.badlogic.gdx.Gdx;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.PlayerDefaults;

public class FasterPlayerPowerup extends Powerup {

	public FasterPlayerPowerup(float x, float y, float width, float height, Player player) {
		super(x, y, width, height, Gdx.files.internal(Paths.FAST_POWERUP_TEXTURE).path(), player);
	}

	@Override
	public void affectPlayer(Player player) {
		player.setSpeed(PlayerDefaults.DEFAULT_SPEED * 1.5f);
	}

	@Override
	public void removePower(Player player) {
		player.setSpeed(PlayerDefaults.DEFAULT_SPEED);
	}

}
