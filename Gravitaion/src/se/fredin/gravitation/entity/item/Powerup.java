package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;

public abstract class Powerup extends AbstractEntity {

	protected Player player;
	
	public Powerup(float x, float y, float width, float height, String texturePath, Player player) {
		super(x, y, width, height, texturePath);
		this.player = player;
	}
	
	public abstract void affectPlayer(Player player);
	
	public abstract void removePower(Player player);
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		if(isAlive && player.getBounds().overlaps(bounds)) {
			if(isAlive) {
				affectPlayer(player);
			}
			isAlive = false;
		}
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	
}
