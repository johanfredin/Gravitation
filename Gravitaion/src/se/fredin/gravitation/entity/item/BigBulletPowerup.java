package se.fredin.gravitation.entity.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

public class BigBulletPowerup extends Powerup {

	public BigBulletPowerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2) {
		super(spawnPoints, width, height, Gdx.files.internal(Paths.BIG_BULLET_TEXTURE).path(), player1, player2, "big bullets");
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
