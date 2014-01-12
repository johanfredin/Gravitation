package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.Gravitation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class OptionsScreen extends MenuBase {

	private Image mobileDeviceControlsImage;
	private Image titleImage;
	private Image keyboardControlsImage;
	private Image gamePadControlsImage;
	private Image returnToMenuImage;
	
	public OptionsScreen(Game game) {
		super(game);
		initImages();
		setPositions();
		setListener(returnToMenuImage, (byte) 1);
	}

	private void initImages() {
		float titleWidth = 200 * uiHelper.getScale();
		float titleHeight = 20 * uiHelper.getScale();
		titleImage = uiHelper.getImage("controls", titleWidth, titleHeight);
		if(Gravitation.isMobileDevice()) {
			mobileDeviceControlsImage = uiHelper.getImage("mobile", 100, 100);
		} else {
			keyboardControlsImage = uiHelper.getImage("keyboard", 100, 150);
			gamePadControlsImage = uiHelper.getImage("Xbox360 Controller", 100, 150);
		}
		returnToMenuImage = uiHelper.getImage("return to menu", 133.3f * uiHelper.getScale(), 13.3f * uiHelper.getScale());
	}
	
	private void setPositions() {
		float buttonCenterX = camera.position.x - (titleImage.getWidth() / 2);
		float spacingY = 20 * uiHelper.getScale();
		if(Gravitation.isMobileDevice()) {
			mobileDeviceControlsImage.setPosition(buttonCenterX, titleImage.getY() - titleImage.getHeight());
		}
		returnToMenuImage.setPosition(buttonCenterX, Gravitation.isMobileDevice() ? mobileDeviceControlsImage.getY() - mobileDeviceControlsImage.getHeight() : gamePadControlsImage.getY() - gamePadControlsImage.getHeight());
	}
	
	@Override
	protected void setListener(Actor actor, byte ACTION) {
		actor.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MainMenuScreen(game));
				return true;
			}
		});
	}

}
