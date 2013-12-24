package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.PlayerDefaults;

import com.badlogic.gdx.Gdx;

public class FasterPlayerPowerup extends Powerup {

	public FasterPlayerPowerup(float x, float y, float width, float height, Player player1, Player player2) {
		super(x, y, width, height, Gdx.files.internal(Paths.FAST_POWERUP_TEXTURE).path(), player1, player2);
	}

	@Override
	public void affectEntity(Player player) {
		player.setSpeed(PlayerDefaults.DEFAULT_SPEED * 2.5f);
	}

	@Override
	public void removePower(Player player) {
		player.setSpeed(PlayerDefaults.DEFAULT_SPEED);
	}

}
