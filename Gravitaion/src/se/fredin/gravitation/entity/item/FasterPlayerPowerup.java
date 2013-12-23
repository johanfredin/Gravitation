package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.PlayerDefaults;

import com.badlogic.gdx.Gdx;

public class FasterPlayerPowerup extends Powerup {

	public FasterPlayerPowerup(float x, float y, float width, float height, Player player) {
		super(x, y, width, height, Gdx.files.internal(Paths.FAST_POWERUP_TEXTURE).path(), player);
	}

	@Override
	public void affectEntity(AbstractEntity player) {
		((Player) player).setSpeed(PlayerDefaults.DEFAULT_SPEED * 1.5f);
	}

	@Override
	public void removePower(AbstractEntity player) {
		((Player) player).setSpeed(PlayerDefaults.DEFAULT_SPEED);
	}

}
