package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.utils.GameMode;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * UI screen for handling level selection as well as match settings be this a multiplayer game.
 * @author Johan Fredin
 *
 */
public class LevelSelect extends MenuBase {

	private ShapeRenderer shapeRenderer;
	private Rectangle scoreSelectionBounds;
	private Rectangle timeSelectionBounds;
	
	private GameMode gameMode;
	private Image levelSelectImage;
	private Image level1Image; 
	private Image level2Image; 
	private Image level3Image; 
	private Image returnToMenuImage;
	private Image timeLimitImage;
	private Image scoreImage;
	private Image lowScoreImage;
	private Image mediumScoreImage;
	private Image highScoreImage;
	private Image unlimitedScoreImage;
	private Image shortTimeImage;
	private Image mediumTimeImage;
	private Image longTimeImage;
	private Image unlimitedTimeImage;
	
	private final byte LEVEL_1 = 1;
	private final byte LEVEL_2 = 2; 
	private final byte LEVEL_3 = 3; 
	private final byte RETURN_TO_MENU = 4;
	private final byte SHORT_TIME = 5;
	private final byte MEDIUM_TIME = 6;
	private final byte LONG_TIME = 7;
	private final byte UNLIMITED_TIME = 8;
	private final byte LOW_SCORE = 9;
	private final byte MEDIUM_SCORE = 10;
	private final byte HIGH_SCORE = 11;
	private final byte UNLIMITED_SCORE = 12;
	
	private boolean level1ImagePressed;
	private boolean level2ImagePressed;
	private boolean level3ImagePressed;
	private boolean returnToMenuPressed;

	/**
	 * Creates a new LevelSelect instance with a game for switching screens and the specified game mode.
	 * @param game The game instance responsible for switching screens.
	 * @param gameMode The selected game mode, multiplayer or single player.
	 */
	public LevelSelect(Game game, GameMode gameMode) {
		super(game);
		this.gameMode = gameMode;
		setupImages();
		
		if(gameMode == GameMode.MULTI_PLAYER) {
			shapeRenderer = new ShapeRenderer();
			scoreSelectionBounds = new Rectangle(mediumScoreImage.getX(), mediumScoreImage.getY(), mediumScoreImage.getWidth(), mediumScoreImage.getHeight());
			timeSelectionBounds = new Rectangle(mediumTimeImage.getX(), mediumTimeImage.getY(), mediumTimeImage.getWidth(), mediumTimeImage.getHeight());
		}
		
		setListener(level1Image, LEVEL_1);
		setListener(level2Image, LEVEL_2);
		setListener(level3Image, LEVEL_3);
		if(gameMode == GameMode.MULTI_PLAYER) {
			setListener(lowScoreImage, LOW_SCORE);
			setListener(mediumScoreImage, MEDIUM_SCORE);
			setListener(highScoreImage, HIGH_SCORE);
			setListener(unlimitedScoreImage, UNLIMITED_SCORE);
			setListener(shortTimeImage, SHORT_TIME);
			setListener(mediumTimeImage, MEDIUM_TIME);
			setListener(longTimeImage, LONG_TIME);
			setListener(unlimitedTimeImage, UNLIMITED_TIME);
		}
		setListener(returnToMenuImage, RETURN_TO_MENU);
	}
	
	@Override
	public void setListener(Actor actor, final byte ACTION) {
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				buttonPressedSound.play();
				switch(ACTION) {
				case LEVEL_1:	// Start the game!!
					uiHelper.animateActorAndFadeOutScreen(level1Image, whiteCanvasImage, 0.1f, 1.2f);
					level1ImagePressed = true;
					return true;
				case LEVEL_2:
					uiHelper.animateActorAndFadeOutScreen(level2Image, whiteCanvasImage, 0.1f, 1.2f);
					level2ImagePressed = true;
					return true;
				case LEVEL_3:	// Quit the game
					uiHelper.animateActorAndFadeOutScreen(level3Image, whiteCanvasImage, 0.1f, 1.2f);
					level3ImagePressed = true;
					return true;
				case RETURN_TO_MENU:
					uiHelper.animateActor(returnToMenuImage, 0.1f);
					returnToMenuPressed = true;
					return true;
				case LOW_SCORE:
					Settings.defaultScoreLimit = Settings.LOW_SCORE_LIMIT;
					scoreSelectionBounds.set(lowScoreImage.getX(), lowScoreImage.getY(), lowScoreImage.getWidth(), lowScoreImage.getHeight());
					uiHelper.animateActor(lowScoreImage, 0.1f);
					return true;
				case MEDIUM_SCORE:
					Settings.defaultScoreLimit = Settings.MEDIUM_SCORE_LIMIT;
					scoreSelectionBounds.set(mediumScoreImage.getX(), mediumScoreImage.getY(), mediumScoreImage.getWidth(), mediumScoreImage.getHeight());
					uiHelper.animateActor(mediumScoreImage, 0.1f);
					return true;
				case HIGH_SCORE:
					Settings.defaultScoreLimit = Settings.HIGH_SCORE_LIMIT;
					scoreSelectionBounds.set(highScoreImage.getX(), highScoreImage.getY(), highScoreImage.getWidth(), highScoreImage.getHeight());
					uiHelper.animateActor(highScoreImage, 0.1f);
					return true;
				case UNLIMITED_SCORE:
					Settings.isUnlimitedcore = true;
					scoreSelectionBounds.set(unlimitedScoreImage.getX(), unlimitedScoreImage.getY(), unlimitedScoreImage.getWidth(), unlimitedScoreImage.getHeight());
					uiHelper.animateActor(unlimitedScoreImage, 0.1f);
					return true;
				case SHORT_TIME:
					Settings.defaultTimeLimit = Settings.SHORT_TIME_LIMIT;
					timeSelectionBounds.set(shortTimeImage.getX(), shortTimeImage.getY(), shortTimeImage.getWidth(), shortTimeImage.getHeight());
					uiHelper.animateActor(shortTimeImage, 0.1f);
					return true;
				case MEDIUM_TIME:
					Settings.defaultTimeLimit = Settings.MEDIUM_TIME_LIMIT;
					timeSelectionBounds.set(mediumTimeImage.getX(), mediumTimeImage.getY(), mediumTimeImage.getWidth(), mediumTimeImage.getHeight());
					uiHelper.animateActor(mediumTimeImage, 0.1f);
					return true;
				case LONG_TIME:
					Settings.defaultTimeLimit = Settings.LONG_TIME_LIMIT;
					timeSelectionBounds.set(longTimeImage.getX(), longTimeImage.getY(), longTimeImage.getWidth(), longTimeImage.getHeight());
					uiHelper.animateActor(longTimeImage, 0.1f);
					return true;
				case UNLIMITED_TIME:
					Settings.isUnlimitedTime = true;
					timeSelectionBounds.set(unlimitedTimeImage.getX(), unlimitedTimeImage.getY(), unlimitedTimeImage.getWidth(), unlimitedTimeImage.getHeight());
					uiHelper.animateActor(unlimitedTimeImage, 0.1f);
					return true;
				default:
					return false;
				}
			}
		});
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		whiteCanvasImage.draw(batch, 1);
		batch.end();
		
		if(gameMode == GameMode.MULTI_PLAYER) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.rect(scoreSelectionBounds.x, scoreSelectionBounds.y, scoreSelectionBounds.width, scoreSelectionBounds.height);
			shapeRenderer.rect(timeSelectionBounds.x, timeSelectionBounds.y, timeSelectionBounds.width, timeSelectionBounds.height);
			shapeRenderer.end();
		}
		
		tick(delta);
	}
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		
		whiteCanvasImage.act(delta);
		
		if(level1ImagePressed && uiHelper.isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 1));
		} else if(level2ImagePressed && uiHelper.isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 2));
		} else if(level3ImagePressed && uiHelper.isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 3));
		} else if(returnToMenuPressed && uiHelper.isFinishedActing(returnToMenuImage)) {
			stage.clear();
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if(gameMode == GameMode.MULTI_PLAYER) {
			shapeRenderer.dispose();
		}
	}
	
	private void setupImages() {
		float scale = uiHelper.getScale();
		float spacingX = 28 * scale;
		float titleWidth = 200 * scale;
		float titleHeight = 20 * scale;
		float levelImageWidth = 64 * scale;
		float levelImageHeight = 44 * scale;
		
		this.levelSelectImage = uiHelper.getImage("level select", titleWidth, titleHeight);
		levelSelectImage.setPosition(camera.position.x - levelSelectImage.getWidth() / 2, camera.viewportHeight - levelSelectImage.getHeight() - 2);
		stage.addActor(levelSelectImage);
		
		this.level1Image = uiHelper.getImage("level1_img", levelImageWidth, levelImageHeight);
		level1Image.setPosition(spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level1Image);
		
		this.level2Image = uiHelper.getImage("level2_img", levelImageWidth, levelImageHeight);
		level2Image.setPosition(level1Image.getX() + level1Image.getWidth() + spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level2Image);
		
		this.level3Image = uiHelper.getImage("level3_img", levelImageWidth, levelImageHeight);
		level3Image.setPosition(level2Image.getX() + level2Image.getWidth() + spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level3Image);
		
		this.returnToMenuImage = uiHelper.getImage("return to menu", 133.33f * scale, 13.33f * scale);
		returnToMenuImage.setPosition(camera.position.x - returnToMenuImage.getWidth() / 2, 5);
		stage.addActor(returnToMenuImage);
		
		if(gameMode == GameMode.MULTI_PLAYER) {
			float numberImageSize = 7.5f * scale;
			float textImageWidth = 75 * scale;
			float textImageHeight = 7.5f * scale;
			
			this.scoreImage = uiHelper.getImage("score", textImageWidth, textImageHeight);
			scoreImage.setPosition(5, level3Image.getY() - scoreImage.getHeight() - 5);
			stage.addActor(scoreImage);
			
			this.timeLimitImage = uiHelper.getImage("time limit", textImageWidth, textImageHeight);
			timeLimitImage.setPosition(17, scoreImage.getY() - scoreImage.getHeight() - 2);
			stage.addActor(timeLimitImage);
			
			this.lowScoreImage = uiHelper.getImage("5", numberImageSize, numberImageSize);
			lowScoreImage.setPosition(scoreImage.getX() + timeLimitImage.getWidth() + 15, scoreImage.getY());
			stage.addActor(lowScoreImage);
			
			this.mediumScoreImage = uiHelper.getImage("10", numberImageSize * 2, numberImageSize);
			mediumScoreImage.setPosition(lowScoreImage.getX() + lowScoreImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(mediumScoreImage);
			
			this.highScoreImage = uiHelper.getImage("20", numberImageSize * 2, numberImageSize);
			highScoreImage.setPosition(mediumScoreImage.getX() + mediumScoreImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(highScoreImage);
			
			this.unlimitedScoreImage = uiHelper.getImage("unlimited", textImageWidth, textImageHeight);
			unlimitedScoreImage.setPosition(highScoreImage.getX() + highScoreImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(unlimitedScoreImage);
			
			this.shortTimeImage = uiHelper.getImage("5", numberImageSize, numberImageSize);
			shortTimeImage.setPosition(scoreImage.getX() + timeLimitImage.getWidth() + 15, timeLimitImage.getY());
			stage.addActor(shortTimeImage);
			
			this.mediumTimeImage = uiHelper.getImage("10", numberImageSize * 2, numberImageSize);
			mediumTimeImage.setPosition(shortTimeImage.getX() + shortTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(mediumTimeImage);
			
			this.longTimeImage = uiHelper.getImage("20", numberImageSize * 2, numberImageSize);
			longTimeImage.setPosition(mediumTimeImage.getX() + mediumTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(longTimeImage);
			
			this.unlimitedTimeImage = uiHelper.getImage("unlimited", textImageWidth, textImageHeight);
			unlimitedTimeImage.setPosition(longTimeImage.getX() + longTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(unlimitedTimeImage);
		}
		
		whiteCanvasImage.setBounds(0, 0, camera.viewportWidth, camera.viewportHeight);
		
		// add fade in effect
		whiteCanvasImage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeOut(2f)));	
	}
}
