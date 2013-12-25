package se.fredin.gravitation.entity.item;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;

public abstract class Powerup extends AbstractEntity {

	protected Player player1, player2;
	
	public Powerup(Array<Rectangle> spawnPoints, float width, float height, String texturePath, Player player1, Player player2) {
		super(spawnPoints, width, height, texturePath);
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
				setPosition(spawnPoints.get((int)(Math.random() * spawnPoints.size)).x, spawnPoints.get((int)(Math.random() * spawnPoints.size)).y);
				isAlive = true;
			} if(player2.isCrashed()) {
				removePower(player2);
				setPosition(spawnPoints.get((int)(Math.random() * spawnPoints.size)).x, spawnPoints.get((int)(Math.random() * spawnPoints.size)).y);
				isAlive = true;
			}
		}
	}

	
}
