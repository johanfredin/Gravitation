package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Station {

	private ShapeRenderer shapeRenderer;
	private Player player;
	private boolean isAlive;
	private boolean isTaken;
	private Rectangle bounds;
	public final int STATION_NR;
	
	public Station(float x, float y, float width, float height, Player player, int stationNr) {
		this.player = player;
		this.STATION_NR = stationNr;
		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setColor(Color.GREEN);
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public void render(SpriteBatch batch) {
		if(isAlive && !isTaken) {
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
			shapeRenderer.end();
		}
	}
	
	public void tick(float delta) {
		if(player.getBounds().overlaps(bounds) && isAlive) {
			isAlive = false;
			isTaken = true;
		}
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public boolean isTaken() {
		return isTaken;
	}
	
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public void dispose() {
		shapeRenderer.dispose();
		player.dispose();
	}

	
	
	

}
