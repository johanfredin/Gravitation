package se.fredin.gravitation.utils;

import se.fredin.gravitation.Gravitation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class UiHelper implements Disposable {
	
	private Skin skin;
	private TextureAtlas atlas;
	private Stage stage;
	
	public UiHelper(Stage stage, String packFileLocation) {
		this.atlas = new TextureAtlas(Gdx.files.internal(packFileLocation));
		this.stage = stage;
		this.skin = new Skin(atlas);
	}
	
	public Image getImage(String nameOfImage) {
		return new Image(skin.getDrawable(nameOfImage));
	}
	
	public Button getButton(String nameOfButton) {
		return new Button(skin.getDrawable(nameOfButton));
	}
	
	public Image getImage(String nameOfImage, float width, float height) {
		Image image = new Image(skin.getDrawable(nameOfImage));
		image.setSize(width, height);
		return image;
	}
	
	public Image getImage(String nameOfImage, float xPos, float yPos, float width, float height) {
		Image image = new Image(skin.getDrawable(nameOfImage));
		image.setBounds(xPos, yPos, width, height);
		return image;
	}
	
	public Image getImage(Skin skin, String nameOfImage, float xPos, float yPos, float width, float height, boolean addToStage) {
		Image image = new Image(skin.getDrawable(nameOfImage));
		image.setBounds(xPos, yPos, width, height);
		if(addToStage) {
			stage.addActor(image);
		}
		return image;
	}
	
	/**
	 * Flashes the button pressed, plays a sound effect and fades out the screen
	 * @button - the button that was pressed
	 * @duration - the fade in duration for the next screen.
	 */
	public void animateActorAndFadeOutScreen(Actor actorToAnimate, Actor actorToFadeOut, float buttonFlashDuration, float fadeInDuration) {
		actorToAnimate.addAction(Actions.repeat(3, Actions.sequence(Actions.fadeOut(buttonFlashDuration), Actions.after(Actions.fadeIn(buttonFlashDuration)))));
		actorToFadeOut.addAction(Actions.sequence(Actions.delay(buttonFlashDuration * 3), Actions.fadeIn(fadeInDuration)));
	}
	
	public void animateActor(Actor actor, float buttonFlashDuration) {
		actor.addAction(Actions.repeat(3, Actions.sequence(Actions.fadeOut(buttonFlashDuration), Actions.after(Actions.fadeIn(buttonFlashDuration)))));
	}
	
	public void flashTitle(Actor actor) {
		actor.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeOut(0.75f), Actions.fadeIn(0.75f))));
	}
	
	public boolean isFinishedActing(Actor actor) {
		return actor.getActions().size <= 0;
	}

	public float getScale() {
		if(Gravitation.isMobileDevice()) {
			return 0.5f;
		}
		return 1;
	}

	@Override
	public void dispose() {
		skin.dispose();
		atlas.dispose();
		skin.dispose();
	}
}
