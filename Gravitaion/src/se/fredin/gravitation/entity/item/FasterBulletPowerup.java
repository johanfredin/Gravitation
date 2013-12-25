package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.PlayerDefaults;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class FasterBulletPowerup extends Powerup {

	public FasterBulletPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2) {
		super(spawnPoints, width, height, Gdx.files.internal(Paths.FASTER_BULLET_TEXTURE).path(), player1, player2);
	}
	
	@Override
	public void affectEntity(Player player) {
		player.setBulletSpeed(PlayerDefaults.FAST_BULLET_SPEED);
	}
	
	@Override
	public void removePower(Player player) {
		player.setBulletSpeed(PlayerDefaults.DEFAULT_BULLET_SPEED);
	}

}
