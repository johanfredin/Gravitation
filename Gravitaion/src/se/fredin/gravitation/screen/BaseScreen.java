package se.fredin.gravitation.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class BaseScreen implements Disposable, Screen {
	
	public static int VIEWPORT_WIDTH = 320;
	public static final int VIEWPORT_HEIGHT = 120;
	protected OrthographicCamera camera, camera2;
	protected SpriteBatch batch;
	protected Game game;					// used to switch screens
	
	protected BaseScreen() {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	}
	
	public BaseScreen(Game game) {
		this();
		this.game = game;
		camera2 = new OrthographicCamera();
		camera2.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		camera2.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
