package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.GameMode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenuScreen extends MenuBase {
	
	private Button singlePlayerButton, multiPlayerButton, quitButton;
	private boolean singlePlayerButtonClicked;
	private boolean multiPlayerButtonClicked;
	private boolean quitButtonClicked;
	private Image titleImage;
	private final byte NEW_GAME = 1, MULTIPLAYER = 2, QUIT = 3;		// button actions
	
	public MainMenuScreen(Game game) {
		super(game);
		initButtonsAndImages();
		setListener(singlePlayerButton, NEW_GAME);
		if(notAndroid()) {
			setListener(multiPlayerButton, MULTIPLAYER);
		}
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
		
		if(singlePlayerButtonClicked && whiteCanvasImage.getActions().size <= 0) {
			stage.clear();
			game.setScreen(new LevelSelect(game, GameMode.SINGLE_PLAYER));
		} else if(multiPlayerButtonClicked && whiteCanvasImage.getActions().size <= 0 && notAndroid()) {
			stage.clear();
			game.setScreen(new LevelSelect(game, GameMode.MULTI_PLAYER));
		} else if(quitButtonClicked) {
			Gdx.app.exit();
		}

	}
	
	private void initButtonsAndImages() {
		singlePlayerButton = new Button(skin.getDrawable("single player"));
		if(notAndroid()) {
			multiPlayerButton = new Button(skin.getDrawable("multiplayer"));
		}
		quitButton = new Button(skin.getDrawable("quit"));
		
		titleImage = new Image(skin.getDrawable("TITLE-SMALL"));
		titleImage.setSize(300, 30);
		titleImage.setPosition(camera.position.x - (titleImage.getWidth() / 2), camera.viewportHeight - titleImage.getHeight() - 2);
		stage.addActor(titleImage);
		
		// add fade in effect
		whiteCanvasImage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeOut(4f)));	
	}
	
	private void setPositionsAndSizes(float width, float height) {
		float buttonWidth = 133.33f;
		float buttonHeight = 13.33f;
		float spacingY = 20;
		
		whiteCanvasImage.setBounds(0, 0, width, height);
		
		singlePlayerButton.setSize(buttonWidth, buttonHeight);
		float buttonCenterX = camera.position.x - (singlePlayerButton.getWidth() / 2);
		singlePlayerButton.setBounds(buttonCenterX, height - titleImage.getHeight() - spacingY * 1.5f, buttonWidth, buttonHeight);
		if(notAndroid()){
			multiPlayerButton.setBounds(buttonCenterX, singlePlayerButton.getY() - spacingY, buttonWidth, buttonHeight);
		}
		quitButton.setBounds(buttonCenterX, notAndroid() ? multiPlayerButton.getY() - spacingY : singlePlayerButton.getY() - spacingY, buttonWidth, buttonHeight);
	}
	
	@Override
	public void setListener(Actor actor, final int ACTION) {
		stage.addActor(actor);		// add button to the scene as an actor
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// Stop fading in if it hasn't stopped already
				if(whiteCanvasImage.getActions().size > 0) {
					whiteCanvasImage.getActions().clear();
				}
				
				switch(ACTION) {
				case NEW_GAME:	// Start the game!!
					animateActorAndFadeOutScreen(singlePlayerButton, 0.1f, 1.2f);
					singlePlayerButtonClicked = true;
					disableButtons();
					return true;
				case MULTIPLAYER:
					animateActorAndFadeOutScreen(multiPlayerButton, 0.1f, 1.2f);
					multiPlayerButtonClicked = true;
					disableButtons();
					return true;
				case QUIT:	// Quit the game
					animateActorAndFadeOutScreen(quitButton, 0.1f, 1.2f);
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
		if(notAndroid()) {
			multiPlayerButton.setTouchable(Touchable.disabled);
		}
		quitButton.setTouchable(Touchable.disabled);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
