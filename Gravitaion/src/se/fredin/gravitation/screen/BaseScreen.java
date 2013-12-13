package se.fredin.gravitation.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class BaseScreen implements Disposable, Screen {
	
	public static int VIEWPORT_WIDTH;
	public static int VIEWPORT_HEIGHT;
	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected Game game;					// used to switch screens
	
	public BaseScreen() {}
	
	public BaseScreen(Game game) {
		this.game = game;
		setScreenSize();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		batch = new SpriteBatch();
	}
	
	public void setScreenSize() {
		switch(Gdx.app.getType()) {
		case Android:
			VIEWPORT_WIDTH = 100;
			VIEWPORT_HEIGHT = 80;
			break;
		case Desktop:
			VIEWPORT_WIDTH = 200;
			VIEWPORT_HEIGHT = 120;
			break;
		default:
			break;
		}
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
