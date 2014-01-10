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
	
	private final int LEVEL_1 = 1;
	private final int LEVEL_2 = 2; 
	private final int LEVEL_3 = 3; 
	private final int RETURN_TO_MENU = 4;
	private final int SHORT_TIME = 5;
	private final int MEDIUM_TIME = 6;
	private final int LONG_TIME = 7;
	private final int UNLIMITED_TIME = 8;
	private final int LOW_SCORE = 9;
	private final int MEDIUM_SCORE = 10;
	private final int HIGH_SCORE = 11;
	private final int UNLIMITED_SCORE = 12;
	
	private boolean level1ImagePressed;
	private boolean level2ImagePressed;
	private boolean level3ImagePressed;
	private boolean returnToMenuPressed;

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
	
	private void setupImages() {
		this.levelSelectImage = new Image(skin.getDrawable("level select"));
		levelSelectImage.setSize(200 * getScale(), 20 * getScale());
		levelSelectImage.setPosition(camera.position.x - levelSelectImage.getWidth() / 2, camera.viewportHeight - levelSelectImage.getHeight() - 2);
		stage.addActor(levelSelectImage);
		
		float spacingX = 28 * getScale();
		this.level1Image = new Image(skin.getDrawable("level1_img"));
		level1Image.setSize(64 * getScale(), 44 * getScale());
		level1Image.setPosition(spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level1Image);
		
		this.level2Image = new Image(skin.getDrawable("level2_img"));
		level2Image.setSize(64 * getScale(), 44 * getScale());
		level2Image.setPosition(level1Image.getX() + level1Image.getWidth() + spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level2Image);
		
		this.level3Image = new Image(skin.getDrawable("level3_img"));
		level3Image.setSize(64 * getScale(), 44 * getScale());
		level3Image.setPosition(level2Image.getX() + level2Image.getWidth() + spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level3Image);
		
		this.returnToMenuImage = new Image(skin.getDrawable("return to menu"));
		returnToMenuImage.setSize(133.33f * getScale(), 13.33f * getScale());
		returnToMenuImage.setPosition(camera.position.x - returnToMenuImage.getWidth() / 2, 5);
		stage.addActor(returnToMenuImage);
		
		if(gameMode == GameMode.MULTI_PLAYER) {
			this.scoreImage = new Image(skin.getDrawable("score"));
			scoreImage.setSize(75, 7.5f);
			scoreImage.setPosition(5, level3Image.getY() - scoreImage.getHeight() - 5);
			stage.addActor(scoreImage);
			
			this.timeLimitImage = new Image(skin.getDrawable("time limit"));
			timeLimitImage.setSize(75, 7.5f);
			timeLimitImage.setPosition(17, scoreImage.getY() - scoreImage.getHeight() - 2);
			stage.addActor(timeLimitImage);
			
			this.lowScoreImage = new Image(skin.getDrawable("5"));
			lowScoreImage.setSize(7.5f, 7.5f);
			lowScoreImage.setPosition(scoreImage.getX() + timeLimitImage.getWidth() + 15, scoreImage.getY());
			stage.addActor(lowScoreImage);
			
			this.mediumScoreImage = new Image(skin.getDrawable("10"));
			mediumScoreImage.setSize(15, 7.5f);
			mediumScoreImage.setPosition(lowScoreImage.getX() + lowScoreImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(mediumScoreImage);
			
			this.highScoreImage = new Image(skin.getDrawable("20"));
			highScoreImage.setSize(15, 7.5f);
			highScoreImage.setPosition(mediumScoreImage.getX() + mediumScoreImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(highScoreImage);
			
			this.unlimitedScoreImage = new Image(skin.getDrawable("unlimited"));
			unlimitedScoreImage.setSize(75, 7.5f);
			unlimitedScoreImage.setPosition(highScoreImage.getX() + highScoreImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(unlimitedScoreImage);
			
			this.shortTimeImage = new Image(skin.getDrawable("5"));
			shortTimeImage.setSize(7.5f, 7.5f);
			shortTimeImage.setPosition(scoreImage.getX() + timeLimitImage.getWidth() + 15, timeLimitImage.getY());
			stage.addActor(shortTimeImage);
			
			this.mediumTimeImage = new Image(skin.getDrawable("10"));
			mediumTimeImage.setSize(15, 7.5f);
			mediumTimeImage.setPosition(shortTimeImage.getX() + shortTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(mediumTimeImage);
			
			this.longTimeImage = new Image(skin.getDrawable("20"));
			longTimeImage.setSize(15, 7.5f);
			longTimeImage.setPosition(mediumTimeImage.getX() + mediumTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(longTimeImage);
			
			this.unlimitedTimeImage = new Image(skin.getDrawable("unlimited"));
			unlimitedTimeImage.setSize(75f, 7.5f);
			unlimitedTimeImage.setPosition(longTimeImage.getX() + longTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(unlimitedTimeImage);
		}
		
		whiteCanvasImage.setBounds(0, 0, camera.viewportWidth, camera.viewportHeight);
		
		// add fade in effect
		whiteCanvasImage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeOut(2f)));	
	}
	
	@Override
	public void setListener(Actor actor, final int ACTION) {
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				buttonPressedSound.play();
				switch(ACTION) {
				case LEVEL_1:	// Start the game!!
					animateActorAndFadeOutScreen(level1Image, whiteCanvasImage, 0.1f, 1.2f);
					level1ImagePressed = true;
					return true;
				case LEVEL_2:
					animateActorAndFadeOutScreen(level2Image, whiteCanvasImage, 0.1f, 1.2f);
					level2ImagePressed = true;
					return true;
				case LEVEL_3:	// Quit the game
					animateActorAndFadeOutScreen(level3Image, whiteCanvasImage, 0.1f, 1.2f);
					level3ImagePressed = true;
					return true;
				case RETURN_TO_MENU:
					animateActor(returnToMenuImage, 0.1f);
					returnToMenuPressed = true;
					return true;
				case LOW_SCORE:
					Settings.defaultScoreLimit = Settings.LOW_SCORE_LIMIT;
					scoreSelectionBounds.set(lowScoreImage.getX(), lowScoreImage.getY(), lowScoreImage.getWidth(), lowScoreImage.getHeight());
					animateActor(lowScoreImage, 0.1f);
					return true;
				case MEDIUM_SCORE:
					Settings.defaultScoreLimit = Settings.MEDIUM_SCORE_LIMIT;
					scoreSelectionBounds.set(mediumScoreImage.getX(), mediumScoreImage.getY(), mediumScoreImage.getWidth(), mediumScoreImage.getHeight());
					animateActor(mediumScoreImage, 0.1f);
					return true;
				case HIGH_SCORE:
					Settings.defaultScoreLimit = Settings.HIGH_SCORE_LIMIT;
					scoreSelectionBounds.set(highScoreImage.getX(), highScoreImage.getY(), highScoreImage.getWidth(), highScoreImage.getHeight());
					animateActor(highScoreImage, 0.1f);
					return true;
				case UNLIMITED_SCORE:
					Settings.isUnlimitedcore = true;
					scoreSelectionBounds.set(unlimitedScoreImage.getX(), unlimitedScoreImage.getY(), unlimitedScoreImage.getWidth(), unlimitedScoreImage.getHeight());
					animateActor(unlimitedScoreImage, 0.1f);
					return true;
				case SHORT_TIME:
					Settings.defaultTimeLimit = Settings.SHORT_TIME_LIMIT;
					timeSelectionBounds.set(shortTimeImage.getX(), shortTimeImage.getY(), shortTimeImage.getWidth(), shortTimeImage.getHeight());
					animateActor(shortTimeImage, 0.1f);
					return true;
				case MEDIUM_TIME:
					Settings.defaultTimeLimit = Settings.MEDIUM_TIME_LIMIT;
					timeSelectionBounds.set(mediumTimeImage.getX(), mediumTimeImage.getY(), mediumTimeImage.getWidth(), mediumTimeImage.getHeight());
					animateActor(mediumTimeImage, 0.1f);
					return true;
				case LONG_TIME:
					Settings.defaultTimeLimit = Settings.LONG_TIME_LIMIT;
					timeSelectionBounds.set(longTimeImage.getX(), longTimeImage.getY(), longTimeImage.getWidth(), longTimeImage.getHeight());
					animateActor(longTimeImage, 0.1f);
					return true;
				case UNLIMITED_TIME:
					Settings.isUnlimitedTime = true;
					timeSelectionBounds.set(unlimitedTimeImage.getX(), unlimitedTimeImage.getY(), unlimitedTimeImage.getWidth(), unlimitedTimeImage.getHeight());
					animateActor(unlimitedTimeImage, 0.1f);
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
		
		if(level1ImagePressed && isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 1));
		} else if(level2ImagePressed && isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 2));
		} else if(level3ImagePressed && isFinishedActing(whiteCanvasImage)) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 3));
		} else if(returnToMenuPressed && isFinishedActing(returnToMenuImage)) {
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

}
