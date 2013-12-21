package se.fredin.gravitation.screen;

import se.fredin.gravitation.Gravitation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class BaseScreen implements Disposable, Screen {
	
	public static final int VIEWPORT_WIDTH = Gravitation.multiPlayerMode ? 160 : 320;
	public static final int VIEWPORT_HEIGHT = 120;
	protected OrthographicCamera camera, camera2;
	protected SpriteBatch batch;
	protected Game game;					// used to switch screens
	
	public BaseScreen() {}
	
	public BaseScreen(Game game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera2 = new OrthographicCamera();
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		camera2.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		batch = new SpriteBatch();
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
