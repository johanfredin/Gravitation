package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class StatisticsBar {
	
	private ShapeRenderer shapeRenderer;
	private Label scoreLabel;
	private Rectangle bounds;
	
	public StatisticsBar(float x, float y, float width, float height, Player player) {
		this.scoreLabel = new Label("Player " + player.getPlayerNum() + " score: " + player.getScore(), new LabelStyle(new BitmapFont(), Color.RED));
		this.shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.DARK_GRAY);
		this.bounds = new Rectangle(x, y, width, height);
		scoreLabel.setSize(5, 5);
	}
	
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(camera.position.x - Gdx.graphics.getWidth() / 2, camera.position.y - (camera.viewportHeight / 2), bounds.width, bounds.height);
		shapeRenderer.end();

		scoreLabel.setPosition((camera.position.x / 2), camera.position.y - (camera.viewportHeight / 2) + 5);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		scoreLabel.draw(batch, 1);
		batch.end();
	}
	
	public void tick(float delta, Player player) {
		scoreLabel.act(delta);
		scoreLabel.setText("Player " + player.getPlayerNum() + " score: " + player.getScore());
	}
	
	public void dispose() {
		shapeRenderer.dispose();
	}

}
