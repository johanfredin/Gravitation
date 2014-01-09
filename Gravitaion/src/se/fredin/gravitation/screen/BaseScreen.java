package se.fredin.gravitation.screen;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class BaseScreen implements Disposable, Screen {
	
	public static int VIEWPORT_WIDTH = 320;
	public static int VIEWPORT_HEIGHT = 120;
	protected Sound buttonPressedSound;
	protected OrthographicCamera camera, camera2;
	protected SpriteBatch batch;
	protected Game game;					// used to switch screens
	
	protected BaseScreen() {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		if(Gravitation.isMobileDevice()) {
			VIEWPORT_WIDTH = 160;
			VIEWPORT_HEIGHT = 80;
		}
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal(Paths.MENU_SELECT_SOUND_EFFECT));
	}
	
	public BaseScreen(Game game) {
		this();
		this.game = game;
		camera2 = new OrthographicCamera();
		camera2.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	}
	
	public Game getGame() {
		return this.game;
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
		game.dispose();
		buttonPressedSound.dispose();
	}

}
