package se.fredin.gravitation.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class BaseScreen implements Disposable, Screen {
	
	public static final int VIEWPORT_WIDTH = 200;
	public static final int VIEWPORT_HEIGHT = 120;
	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected Game game;					// used to switch screens
	
	public BaseScreen() {}
	
	public BaseScreen(Game game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		batch = new SpriteBatch();
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
