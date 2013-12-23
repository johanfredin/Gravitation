package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;

public abstract class Powerup extends AbstractEntity {

	protected Player player;
	protected boolean isRepositioned;
	
	public Powerup(float x, float y, float width, float height, String texturePath, Player player) {
		super(x, y, width, height, texturePath);
		this.player = player;
	}
	
	public abstract void affectEntity(AbstractEntity entity);
	
	public abstract void removePower(AbstractEntity entity);
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		if(isAlive && player.getBounds().overlaps(bounds)) {
			if(isAlive) {
				affectEntity(player);
			}
			isAlive = false;
		}
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void setRepositioned(boolean isRepositioned) {
		this.isRepositioned = isRepositioned;
	}
	public boolean isRepositioned() {
		return isRepositioned;
	}
}
