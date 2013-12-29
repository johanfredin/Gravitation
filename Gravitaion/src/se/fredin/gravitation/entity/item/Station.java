package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Station extends AbstractEntity {

	private ShapeRenderer shapeRenderer;
	private Player player;
	private boolean isVertical;
	
	public Station(float x, float y, float width, float height, Player player) {
		super(x, y, width, height);
		this.player = player;
		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setColor(Color.CYAN);
		this.isAlive = true;
	}
	
	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}
	
	public boolean isVertical() {
		return isVertical;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(isAlive) {
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
			shapeRenderer.end();
		}
	}
	
	@Override
	public void tick(float delta) {
		if(player.getBounds().overlaps(bounds)) {
			isAlive = false;
		}
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		player.dispose();
	}
	
	

}
