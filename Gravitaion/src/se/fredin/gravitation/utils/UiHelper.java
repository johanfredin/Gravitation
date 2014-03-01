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

/**
 * Helper class for handling UI screens.
 * @author Johan Fredin
 *
 */
public class UiHelper implements Disposable {
	
	private Skin skin;
	private TextureAtlas atlas;
	private Stage stage;
	
	/**
	 * Creates a new instance of the UiHelper.
	 * @param stage The stage the helper will work on.
	 * @param packFileLocation The location of the pack file used for the TextureAtlas.
	 */
	public UiHelper(Stage stage, String packFileLocation) {
		this.atlas = new TextureAtlas(Gdx.files.internal(packFileLocation));
		this.stage = stage;
		this.skin = new Skin(atlas);
	}
	
	/**
	 * Get the specified image from the Skin.
	 * @param nameOfImage Name of the image in the pack file.
	 * @return The Image from the Skin.
	 */
	public Image getImage(String nameOfImage) {
		return new Image(skin.getDrawable(nameOfImage));
	}
	
	/**
	 * Get the specified button from the Skin.
	 * @param nameOfButton Name of the button in the pack file.
	 * @return The Button from the Skin.
	 */
	public Button getButton(String nameOfButton) {
		return new Button(skin.getDrawable(nameOfButton));
	}
	
	/**
	 * Gets a new Image with specified width and height.
	 * @param nameOfImage The name of the image in the pack file.
	 * @param width The width of the image.
	 * @param height The height of the image.
	 * @return A new Image with specified width and height.
	 */
	public Image getImage(String nameOfImage, float width, float height) {
		Image image = new Image(skin.getDrawable(nameOfImage));
		image.setSize(width, height);
		return image;
	}
	
	/**
	 * Gets a new Image with specified width and height.
	 * @param nameOfImage The name of the image in the pack file.
	 * @param skin the skin holding the image.
	 * @param width the width of the image.
	 * @param height the height of the image.
	 * @return a new Image with specified width and height.
	 */
	public static Image getImage(Skin skin, String nameOfImage, float width, float height) {
		Image image = new Image(skin.getDrawable(nameOfImage));
		image.setSize(width, height);
		return image;
	}
	
	/**
	 * Gets a new Image with a specified width and height at a specified position.
	 * @param nameOfImage The name of the image in the pack file.
	 * @param xPos The x position of the image.
	 * @param yPos The y position of the image.
	 * @param width The width of the image.
	 * @param height The height of the image.
	 * @return A new image with specified width and height at a specified position.
	 */
	public Image getImage(String nameOfImage, float xPos, float yPos, float width, float height) {
		Image image = new Image(skin.getDrawable(nameOfImage));
		image.setBounds(xPos, yPos, width, height);
		return image;
	}
	
	/**
	 * Gets a new Image with a specified width and height at a specified position.
	 * @param nameOfImage The name of the image in the pack file.
	 * @param xPos The x position of the image.
	 * @param yPos The y position of the image.
	 * @param width The width of the image.
	 * @param height The height of the image.
	 * @param addToStage <b>true</b> will add the image to the stage.
	 * @return A new image with specified widht and height at a specified position.
	 */
	public Image getImage(Skin skin, String nameOfImage, float xPos, float yPos, float width, float height, boolean addToStage) {
		Image image = new Image(skin.getDrawable(nameOfImage));
		image.setBounds(xPos, yPos, width, height);
		if(addToStage) {
			stage.addActor(image);
		}
		return image;
	}
	
	/**
	 * Flashes the actor touched and fades out the screen.
	 * @param actorToAnimate The actor to be affected by interaction.
	 * @param actorToFadeOut The actor used to fade out the screen.
	 * @param buttonFlashDuration The duration the pressed button will flash.
	 * @param fadeInDuration The duration for the fade effect used after button flash is finished.
	 */
	public void animateActorAndFadeOutScreen(Actor actorToAnimate, Actor actorToFadeOut, float buttonFlashDuration, float fadeInDuration) {
		actorToAnimate.addAction(Actions.repeat(3, Actions.sequence(Actions.fadeOut(buttonFlashDuration), Actions.after(Actions.fadeIn(buttonFlashDuration)))));
		actorToFadeOut.addAction(Actions.sequence(Actions.delay(buttonFlashDuration * 3), Actions.fadeIn(fadeInDuration)));
	}
	
	/**
	 * Flashes the actor touched 3 times.
	 * @param actor The actor to be affected by interaction.
	 * @param buttonFlashDuration The duration the pressed button will flash.
	 */
	public void animateActor(Actor actor, float buttonFlashDuration) {
		actor.addAction(Actions.repeat(3, Actions.sequence(Actions.fadeOut(buttonFlashDuration), Actions.after(Actions.fadeIn(buttonFlashDuration)))));
	}
	
	/**
	 * Creates a fade in out effect that repeats forever and fades in and out the actor at a fixed value of .75 seconds.
	 * @param actor The actor to be affected.
	 */
	public void flashActor(Actor actor) {
		actor.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeOut(0.75f), Actions.fadeIn(0.75f))));
	}
	
	/**
	 * Creates a fade in out effect with specified time that repeats forever.
	 * @param actor The actor to be affected.
	 * @param fadeTime The time fade in and out will be used.
	 */
	public void flashActor(Actor actor, float fadeTime) {
		actor.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeOut(fadeTime), Actions.fadeIn(fadeTime))));
	}
	
	/**
	 * Checks if this actor is finished with its actions.
	 * @param actor The actor to check.
	 * @return <b>true</b> if this actor is done with its actions.
	 */
	public boolean isFinishedActing(Actor actor) {
		return actor.getActions().size <= 0;
	}
	
	/**
	 * Checks if this actor is finished with its actions.
	 * @param actor The actor to check.
	 * @return <b>true</b> if this actor is done with its actions.
	 */
	public static boolean isNotActing(Actor actor) {
		return actor.getActions().size <= 0;
	}

	/**
	 * Get a specified scale for mobile and desktop applications.
	 * @return 0.5 scale if this is a mobile device, 1 otherwise.
	 */
	public float getScale() {
		if(Gravitation.isMobileDevice()) {
			return 0.5f;
		}
		return 1;
	}
	
	/**
	 * Get a specific scale weather game is on a mobile device or not.
	 * @param scale The scale to use if game is being played on a mobile device.
	 * @return The scale if mobile device, 1 otherwise.
	 */
	public float getScale(float scale) {
		if(Gravitation.isMobileDevice()) {
			return scale;
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
