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

public abstract class MenuBase extends BaseScreen {

	protected Image whiteCanvasImage;
	protected Stage stage;
	protected UiHelper uiHelper;
	
	public MenuBase() {
		super();
		this.stage = new Stage(camera.viewportWidth, camera.viewportHeight, true);
		this.uiHelper = new UiHelper(stage, Paths.MENU_ITEMS);
	}
	
	public MenuBase(Game game) {
		super(game);
		this.stage = new Stage(camera.viewportWidth, camera.viewportHeight, true);
		this.uiHelper = new UiHelper(stage, Paths.MENU_ITEMS);
		this.whiteCanvasImage = uiHelper.getImage("whiterect", 0, 0, stage.getWidth(), stage.getHeight());
		Gdx.input.setInputProcessor(stage);
	}
	
	public abstract void setListener(Actor actor, final int ACTION);

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	public void tick(float delta) {
		stage.act(delta);
	}

	@Override
	public void show() {}

	@Override
	public void hide() {}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.setViewport(camera.viewportWidth, camera.viewportHeight, false);
	}
	
	public void pause(){}

	@Override
	public void resume() {}
	
	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		uiHelper.dispose();
	}

}
