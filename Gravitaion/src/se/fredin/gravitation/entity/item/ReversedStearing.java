package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;

public class ReversedStearing extends Powerup {

	public ReversedStearing(float x, float y, float width, float height, Player player, Player player2) {
		super(x, y, width, height, Gdx.files.internal(Paths.REVERSED_STEARING_TEXTURE).path(), player, player2);
	}

	@Override
	public void affectEntity(Player entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePower(Player entity) {
		// TODO Auto-generated method stub

	}

}
