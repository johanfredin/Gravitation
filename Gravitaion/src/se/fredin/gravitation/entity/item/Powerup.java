package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;

public abstract class Powerup extends AbstractEntity {

	protected Player player1, player2;
	protected boolean isRepositioned;
	
	public Powerup(float x, float y, float width, float height, String texturePath, Player player1, Player player2) {
		super(x, y, width, height, texturePath);
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public abstract void affectEntity(Player player);
	
	public abstract void removePower(Player player);
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		if(isAlive) {
			if(player1.getBounds().overlaps(bounds)) {
				affectEntity(player1);
				isAlive = false;
			} if(player2.getBounds().overlaps(bounds)) {
				affectEntity(player2);
				isAlive = false;
			}
		} else {
			if(player1.isCrashed()) {
				removePower(player1);
				isAlive = true;
			} if(player2.isCrashed()) {
				removePower(player2);
				isAlive = true;
			}
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
