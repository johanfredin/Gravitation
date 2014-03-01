package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.Gravitation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Class responsible for the options menu.
 * @author johan
 *
 */
public class OptionsScreen extends MenuBase {

	private Image mobileDeviceControlsImage;
	private Image titleImage;
	private Image keyboardControlsImage;
	private Image gamePadControlsImage;
	private Image returnToMenuImage;
	
	/**
	 * Creates a new Options instance.
	 * @param game The game instance responsible for switching screens.
	 */
	public OptionsScreen(Game game) {
		super(game);
		initImages();
		setPositionsAndSizes(camera.viewportWidth, camera.viewportHeight);	
		setListener(returnToMenuImage, (byte) 1);
	}

	@Override
	protected void setListener(Actor actor, byte ACTION) {
		actor.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				buttonPressedSound.play();
				game.setScreen(new MainMenuScreen(game));
				return true;
			}
		});
	}

	private void initImages() {
		float titleWidth = 200 * uiHelper.getScale();
		float titleHeight = 20 * uiHelper.getScale();
		titleImage = uiHelper.getImage("controls2", titleWidth, titleHeight);
		if(Gravitation.isMobileDevice()) {
			mobileDeviceControlsImage = uiHelper.getImage("androidcontrols");
		} else {
			keyboardControlsImage = uiHelper.getImage("keys");
			gamePadControlsImage = uiHelper.getImage("controller");
		}
		returnToMenuImage = uiHelper.getImage("return to menu", 133.3f * uiHelper.getScale(), 13.3f * uiHelper.getScale());
	}
	
	private void setPositionsAndSizes(float width, float height) {
		float buttonCenterX = (width / 2);
		float imageWidth = 133.33f * uiHelper.getScale();
		float imageHeight = 55.33f * uiHelper.getScale();
		float spacingY = 20 * uiHelper.getScale();
		
		titleImage.setPosition(buttonCenterX - titleImage.getWidth() / 2, height - titleImage.getHeight());
		stage.addActor(titleImage);
		if(Gravitation.isMobileDevice()) {
			mobileDeviceControlsImage.setSize(imageWidth, imageHeight);
			mobileDeviceControlsImage.setPosition(buttonCenterX - mobileDeviceControlsImage.getWidth() / 2, titleImage.getY() - titleImage.getHeight() - spacingY * 3);
			stage.addActor(mobileDeviceControlsImage);
		} else {
			keyboardControlsImage.setBounds(10, titleImage.getY() - titleImage.getHeight() * 3.5f, imageWidth / 1.4f, imageHeight);
			gamePadControlsImage.setBounds(keyboardControlsImage.getX() + keyboardControlsImage.getWidth() + spacingY, keyboardControlsImage.getY(), imageWidth * 1.33f, imageHeight);
			stage.addActor(keyboardControlsImage);
			stage.addActor(gamePadControlsImage);
		}
		returnToMenuImage.setPosition(width / 2 - returnToMenuImage.getWidth() / 2, Gravitation.isMobileDevice() ? mobileDeviceControlsImage.getY() - mobileDeviceControlsImage.getHeight() : gamePadControlsImage.getY() - spacingY * 1.33f);
		stage.addActor(returnToMenuImage);
	}
}
