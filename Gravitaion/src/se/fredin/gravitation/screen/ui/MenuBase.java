package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class MenuBase extends BaseScreen {

	protected Image whiteCanvasImage;
	protected Skin skin;
	protected TextureAtlas textureAtlas;
	protected Stage stage;
	
	public MenuBase() {
		super();
		stage = new Stage();
		stage.setViewport(camera.viewportWidth, camera.viewportHeight, false);
	}
	
	public MenuBase(Game game) {
		super(game);
		stage = new Stage();
		textureAtlas = new TextureAtlas(Gdx.files.internal(Paths.MENU_ITEMS));
		skin = new Skin();
		skin.addRegions(textureAtlas);
		whiteCanvasImage = new Image(skin.getDrawable("whiterect"));
		Gdx.input.setInputProcessor(stage);
	}
	
	public abstract void setListener(Actor actor, final int ACTION);

	protected float getScale() {
		if(Gravitation.isMobileDevice()) {
			return 0.5f;
		}
		return 1;
	}
	
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
	
	/*
	 * Flashes the button pressed, plays a sound effect and fades out the screen
	 * @button - the button that was pressed
	 * @duration - the fade in duration for the next screen.
	 */
	protected void animateActorAndFadeOutScreen(Actor actorToAnimate, Actor actorToFadeOut, float buttonFlashDuration, float fadeInDuration) {
		actorToAnimate.addAction(Actions.repeat(3, Actions.sequence(Actions.fadeOut(buttonFlashDuration), Actions.after(Actions.fadeIn(buttonFlashDuration)))));
		actorToFadeOut.addAction(Actions.sequence(Actions.delay(buttonFlashDuration * 6), Actions.fadeIn(fadeInDuration)));
	}
	
	protected void animateActor(Actor actor, float buttonFlashDuration) {
		actor.addAction(Actions.repeat(3, Actions.sequence(Actions.fadeOut(buttonFlashDuration), Actions.after(Actions.fadeIn(buttonFlashDuration)))));
	}
	
	protected boolean isFinishedActing(Actor actor) {
		return actor.getActions().size <= 0;
	}
	
	public void pause(){}

	@Override
	public void resume() {}
	
	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		skin.dispose();
		textureAtlas.dispose();
	}

}
