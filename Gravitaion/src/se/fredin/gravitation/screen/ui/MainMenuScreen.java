package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.screen.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MainMenuScreen extends MenuBase {
	
	private Label singlePlayerButton, multiPlayerButton, quitButton;
	private boolean singlePlayerButtonClicked;
	private boolean multiPlayerButtonClicked;
	private boolean quitButtonClicked;
	private final byte NEW_GAME = 1, OPTIONS = 2, QUIT = 3;		// button actions
	
	public MainMenuScreen(Game game) {
		super(game);
		initButtonsAndImages();
		setListener(singlePlayerButton, NEW_GAME);
		setListener(multiPlayerButton, OPTIONS);
		setListener(quitButton, QUIT);
		setPositionsAndSizes(camera.viewportWidth, camera.viewportHeight);	
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		checkIfPressed();
	}	
	
	public void checkIfPressed() {
		if(singlePlayerButtonClicked) {
			game.setScreen(new GameScreen(game, GameMode.SINGLE_PLAYER));
		} else if(multiPlayerButtonClicked) {
			game.setScreen(new GameScreen(game, GameMode.MULTI_PLAYER));
		}
		else if(quitButtonClicked) {
			Gdx.app.exit();
		}
	}
	
	private void initButtonsAndImages() {
		LabelStyle ls = new LabelStyle(new BitmapFont(), Color.RED);
		singlePlayerButton = new Label("Singleplayer", ls);
		multiPlayerButton = new Label("MultiPlayer", ls);
		quitButton = new Label("Quit", ls);
	}
	
	private void setPositionsAndSizes(float width, float height) {
		float buttonCenterX = width / 3.33f;
		float buttonWidth = 100;
		float buttonHeight = 30;
		float spacingY = 20;
		singlePlayerButton.setBounds(buttonCenterX, height - spacingY * 3, buttonWidth, buttonHeight);
		multiPlayerButton.setBounds(buttonCenterX, singlePlayerButton.getY() - spacingY, buttonWidth, buttonHeight);
		quitButton.setBounds(buttonCenterX, multiPlayerButton.getY() - spacingY, buttonWidth, buttonHeight);
	}
	
	private void setListener(Label label, final int ACTION) {
		stage.addActor(label);		// add button to the scene as an actor
		label.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				switch(ACTION) {
				case NEW_GAME:	// Start the game!!
					singlePlayerButtonClicked = true;
					disableButtons();
					return true;
				case OPTIONS:
					multiPlayerButtonClicked = true;
					disableButtons();
					return true;
				case QUIT:	// Quit the game
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
		multiPlayerButton.setTouchable(Touchable.disabled);
		quitButton.setTouchable(Touchable.disabled);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
