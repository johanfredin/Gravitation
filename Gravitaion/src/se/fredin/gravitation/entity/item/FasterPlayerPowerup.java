package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class FasterPlayerPowerup extends Powerup {

	public FasterPlayerPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2) {
		super(spawnPoints, width, height, Gdx.files.internal(Paths.FAST_POWERUP_TEXTURE).path(), player1, player2, "speed up");
	}

	@Override
	public void affectEntity(Player player) {
		player.setSpeed(Settings.DEFAULT_SPEED * 2.5f);
	}

	@Override
	public void removePower(Player player) {
		player.setSpeed(Settings.DEFAULT_SPEED);
	}

}
