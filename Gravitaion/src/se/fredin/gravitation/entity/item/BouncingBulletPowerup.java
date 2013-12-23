package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.PlayerDefaults;

import com.badlogic.gdx.Gdx;

public class BouncingBulletPowerup extends Powerup {

	public BouncingBulletPowerup(float x, float y, float width, float height, Player player) {
		super(x, y, width, height, Gdx.files.internal(Paths.BOUNCING_BULLET_TEXTURE).path(), player);
	}

	@Override
	public void affectEntity(AbstractEntity entity) {
		PlayerDefaults.bouncingBullets = true;
	}

	@Override
	public void removePower(AbstractEntity entity) {
		PlayerDefaults.bouncingBullets = false;
	}

}
