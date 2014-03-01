package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.UiHelper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The main UI handler for the menus that take up the whole screen.
 * @author Johan Fredin
 *
 */
public abstract class MenuBase extends BaseScreen {

	protected Image whiteCanvasImage;
	protected Stage stage;
	protected UiHelper uiHelper;
	
	/**
	 * Creates a new MenuBase instance with a stage and a UiHelper and gives input control to the stage.
	 * @param game The game instance used for switching screens.
	 */
	public MenuBase(Game game) {
		super(game);
		this.stage = new Stage(camera.viewportWidth, camera.viewportHeight, true);
		this.uiHelper = new UiHelper(stage, Paths.MENU_ITEMS);
		this.whiteCanvasImage = uiHelper.getImage("whiterect", 0, 0, stage.getWidth(), stage.getHeight());
		Gdx.input.setInputProcessor(stage);
	}
	
	/**
	 * Sets up a listener to passed in actor and depending on the action the actor will behave differently.
	 * @param actor The Actor to give a listener to.
	 * @param ACTION The action you wish to happen once this actor has been touched.
	 */
	protected abstract void setListener(Actor actor, final byte ACTION);

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	/**
	 * Updates the stage
	 * @param delta the time interval
	 */
	public void tick(float delta) {
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.setViewport(camera.viewportWidth, camera.viewportHeight);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		uiHelper.dispose();
	}

}
