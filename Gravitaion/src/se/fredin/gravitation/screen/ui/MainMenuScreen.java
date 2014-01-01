package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.screen.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MainMenuScreen extends MenuBase {
	
	private Label playButton, optionsButton, quitButton;
	private boolean labelPressed = false;
	private boolean playButtonClicked;
	private boolean myCowButtonClicked;
	private boolean quitButtonClicked;
	private final byte NEW_GAME = 1, OPTIONS = 2, QUIT = 3;		// button actions
	
	public MainMenuScreen(Game game) {
		super(game);
		initButtonsAndImages();
		setListener(playButton, NEW_GAME);
		setListener(optionsButton, OPTIONS);
		setListener(quitButton, QUIT);
		setPositionsAndSizes(camera.viewportWidth, camera.viewportHeight);	
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		checkIfPressed(playButtonClicked);
	}	
	
	public void checkIfPressed(boolean isPressed) {
		if(isPressed) {
			System.out.println("pressed");
			game.setScreen(new GameScreen(game));
		}
	}
	
	private void initButtonsAndImages() {
		playButton = new Label("New Game", new LabelStyle(new BitmapFont(), Color.RED));
		optionsButton = new Label("Options", new LabelStyle(new BitmapFont(), Color.RED));
		quitButton = new Label("Quit", new LabelStyle(new BitmapFont(), Color.RED));
	}
	
	private void setPositionsAndSizes(float width, float height) {
		float buttonCenterX = width / 3.33f;
		float buttonWidth = 100;
		float buttonHeight = 50;
		float spacingY = 20;
		playButton.setBounds(buttonCenterX, height - spacingY * 3, buttonWidth, buttonHeight);
		optionsButton.setBounds(buttonCenterX, playButton.getY() - spacingY, buttonWidth, buttonHeight);
		quitButton.setBounds(buttonCenterX, optionsButton.getY() - spacingY, buttonWidth, buttonHeight);
	}
	
	private void setListener(Label label, final int ACTION) {
		stage.addActor(label);		// add button to the scene as an actor
		label.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				switch(ACTION) {
				case NEW_GAME:	// Start the game!!
					playButtonClicked = true;
					disableButtons();
					return true;
				case OPTIONS:
					myCowButtonClicked = true;
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
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
