package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.utils.GameMode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Class that is responsible for the main menu
 * @author johan
 *
 */
public class MainMenuScreen extends MenuBase {
	
	private Button singlePlayerButton, optionsButton, multiPlayerButton, quitButton;
	private boolean singlePlayerButtonClicked;
	private boolean multiPlayerButtonClicked;
	private boolean quitButtonClicked;
	private Image titleImage;
	private final byte SINGLE_PLAYER = 1; 
	private final byte OPTIONS = 2; 
	private final byte MULTIPLAYER = 3; 
	private final byte QUIT = 4;
	protected boolean optionsButtonPressed;		
	
	public MainMenuScreen(Game game) {
		super(game);
		initButtonsAndImages();
		setListener(singlePlayerButton, SINGLE_PLAYER);
		if(!Gravitation.isMobileDevice()) {
			setListener(multiPlayerButton, MULTIPLAYER);
		}
		setListener(optionsButton, OPTIONS);
		setListener(quitButton, QUIT);
		setPositionsAndSizes(camera.viewportWidth, camera.viewportHeight);	
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		whiteCanvasImage.draw(batch, 1);
		batch.end();
		
		tick(delta);
		
	}
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		
		whiteCanvasImage.act(delta);
		
		if(singlePlayerButtonClicked && uiHelper.isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new LevelSelect(game, GameMode.SINGLE_PLAYER));
		} else if(multiPlayerButtonClicked && uiHelper.isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new LevelSelect(game, GameMode.MULTI_PLAYER));
		} else if(optionsButtonPressed && uiHelper.isFinishedActing(optionsButton)) {
			stage.clear();
			game.setScreen(new OptionsScreen(game));
		} else if(quitButtonClicked && uiHelper.isFinishedActing(whiteCanvasImage)) {
			Gdx.app.exit();
		}

	}
	
	private void initButtonsAndImages() {
		singlePlayerButton = uiHelper.getButton("single player");
		if(!Gravitation.isMobileDevice()) {
			multiPlayerButton = uiHelper.getButton("multiplayer");
		}
		optionsButton = uiHelper.getButton("options");
		quitButton = uiHelper.getButton("quit");
		
		titleImage = uiHelper.getImage("TITLE-SMALL", 300 * uiHelper.getScale(), 30 * uiHelper.getScale());
		titleImage.setSize(300 * uiHelper.getScale(), 30 * uiHelper.getScale());
		titleImage.setPosition(camera.position.x - (titleImage.getWidth() / 2), camera.viewportHeight - titleImage.getHeight() - 2);
		stage.addActor(titleImage);
		
		// add fade in effect
		whiteCanvasImage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeOut(2f)));	
	}
	
	private void setPositionsAndSizes(float width, float height) {
		float buttonWidth = 133.33f * uiHelper.getScale();
		float buttonHeight = 13.33f * uiHelper.getScale();
		float spacingY = 20 * uiHelper.getScale();
		
		whiteCanvasImage.setBounds(0, 0, width, height);
		
		singlePlayerButton.setSize(buttonWidth, buttonHeight);
		float buttonCenterX = camera.position.x - (singlePlayerButton.getWidth() / 2);
		singlePlayerButton.setBounds(buttonCenterX, height - titleImage.getHeight() - spacingY * 1.25f, buttonWidth, buttonHeight);
		if(!Gravitation.isMobileDevice()){
			multiPlayerButton.setBounds(buttonCenterX, singlePlayerButton.getY() - spacingY, buttonWidth, buttonHeight);
		}
		optionsButton.setBounds(buttonCenterX, !Gravitation.isMobileDevice() ? multiPlayerButton.getY() - spacingY : singlePlayerButton.getY() - spacingY, buttonWidth, buttonHeight);
		quitButton.setBounds(buttonCenterX, optionsButton.getY() - spacingY, buttonWidth, buttonHeight);
	}
	
	@Override
	public void setListener(Actor actor, final byte ACTION) {
		stage.addActor(actor);		// add button to the scene as an actor
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// Stop fading in if it hasn't stopped already
				if(whiteCanvasImage.getActions().size > 0) {
					whiteCanvasImage.getActions().clear();
				}
				buttonPressedSound.play();
				switch(ACTION) {
				case SINGLE_PLAYER:	// Start a single player game!!
					uiHelper.animateActorAndFadeOutScreen(singlePlayerButton, whiteCanvasImage, 0.1f, 1.2f);
					singlePlayerButtonClicked = true;
					disableButtons();
					return true;
				case OPTIONS: // Options
					uiHelper.animateActorAndFadeOutScreen(optionsButton, whiteCanvasImage, 0.1f, 1.2f);
					optionsButtonPressed = true;
					disableButtons();
					return true;
				case MULTIPLAYER: // Start a multiplayer game
					uiHelper.animateActorAndFadeOutScreen(multiPlayerButton, whiteCanvasImage, 0.1f, 1.2f);
					multiPlayerButtonClicked = true;
					disableButtons();
					return true;
				case QUIT:	// Quit the game
					uiHelper.animateActorAndFadeOutScreen(quitButton, whiteCanvasImage, 0.1f, 1.2f);
					quitButtonClicked = true;
					disableButtons();
					return true;
				default:
					return false;
				}
			}
		});
	}
	
	private void disableButtons() {
		singlePlayerButton.setTouchable(Touchable.disabled);
		if(!Gravitation.isMobileDevice()) {
			multiPlayerButton.setTouchable(Touchable.disabled);
		}
		optionsButton.setTouchable(Touchable.disabled);
		quitButton.setTouchable(Touchable.disabled);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
