package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LevelSelect extends MenuBase {

	private GameMode gameMode;
	
	private Image levelSelectImage, 
				  level1Image, 
				  level2Image, 
				  level3Image, 
				  returnToMenuImage,
				  timeLimitImage,
				  scoreImage,
				  fiveImage,
				  tenImage,
				  twentyImage,
				  unlimitedImage,
				  fiveTimeImage,
				  tenTimeImage,
				  twentTimeyImage,
				  unlimitedTimeImage;
	
	private final int LEVEL_1 = 1, 
			          LEVEL_2 = 2, 
			          LEVEL_3 = 3, 
			          RETURN_TO_MENU = 4,
			          SHORT_TIME = 5,
			          MEDIUM_TIME = 6,
			          LONG_TIME = 7,
			          UNLIMITED_TIME = 8,
			          LOW_SCORE = 9,
			          MEDIUM_SCORE = 10,
			          HIGH_SCORE = 11,
			          UNLIMITED_SCORE = 12;
					  
	
	private boolean level1ImagePressed;
	private boolean level2ImagePressed;
	private boolean level3ImagePressed;

	private boolean killWhiteImage;
	
	public LevelSelect(Game game, GameMode gameMode) {
		super(game);
		this.gameMode = gameMode;
		setupImages();

		setListener(level1Image, LEVEL_1);
		setListener(level2Image, LEVEL_2);
		setListener(level3Image, LEVEL_3);
		if(gameMode == GameMode.MULTI_PLAYER) {
			setListener(fiveImage, LOW_SCORE);
			setListener(tenImage, MEDIUM_SCORE);
			setListener(twentyImage, HIGH_SCORE);
			setListener(unlimitedImage, UNLIMITED_SCORE);
			setListener(fiveTimeImage, SHORT_TIME);
			setListener(tenTimeImage, MEDIUM_TIME);
			setListener(twentTimeyImage, LONG_TIME);
			setListener(unlimitedTimeImage, UNLIMITED_TIME);
		}
		setListener(returnToMenuImage, RETURN_TO_MENU);

	}
	
	private void setupImages() {
		this.levelSelectImage = new Image(skin.getDrawable("level select"));
		levelSelectImage.setSize(200, 20);
		levelSelectImage.setPosition(camera.position.x - levelSelectImage.getWidth() / 2, camera.viewportHeight - levelSelectImage.getHeight() - 2);
		stage.addActor(levelSelectImage);
		
		float spacingX = 28;
		this.level1Image = new Image(skin.getDrawable("level1_img"));
		level1Image.setSize(64, 44);
		level1Image.setPosition(spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level1Image);
		
		this.level2Image = new Image(skin.getDrawable("level2_img"));
		level2Image.setSize(64, 44);
		level2Image.setPosition(level1Image.getX() + level1Image.getWidth() + spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level2Image);
		
		this.level3Image = new Image(skin.getDrawable("level3_img"));
		level3Image.setSize(64, 44);
		level3Image.setPosition(level2Image.getX() + level2Image.getWidth() + spacingX, levelSelectImage.getY() - level1Image.getHeight() - 5);
		stage.addActor(level3Image);
		
		this.returnToMenuImage = new Image(skin.getDrawable("return to menu"));
		returnToMenuImage.setSize(133.33f, 13.33f);
		returnToMenuImage.setPosition(camera.position.x - returnToMenuImage.getWidth() / 2, 5);
		stage.addActor(returnToMenuImage);
		
		if(gameMode == GameMode.MULTI_PLAYER) {
			scoreImage = new Image(skin.getDrawable("score"));
			scoreImage.setSize(75, 7.5f);
			scoreImage.setPosition(5, level3Image.getY() - scoreImage.getHeight() - 5);
			stage.addActor(scoreImage);
			
			timeLimitImage = new Image(skin.getDrawable("time limit"));
			timeLimitImage.setSize(75, 7.5f);
			timeLimitImage.setPosition(17, scoreImage.getY() - scoreImage.getHeight() - 2);
			stage.addActor(timeLimitImage);
			
			fiveImage = new Image(skin.getDrawable("5"));
			fiveImage.setSize(7.5f, 7.5f);
			fiveImage.setPosition(scoreImage.getX() + timeLimitImage.getWidth() + 15, scoreImage.getY());
			stage.addActor(fiveImage);
			
			tenImage = new Image(skin.getDrawable("10"));
			tenImage.setSize(15, 7.5f);
			tenImage.setPosition(fiveImage.getX() + fiveImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(tenImage);
			
			twentyImage = new Image(skin.getDrawable("20"));
			twentyImage.setSize(15, 7.5f);
			twentyImage.setPosition(tenImage.getX() + tenImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(twentyImage);
			
			unlimitedImage = new Image(skin.getDrawable("unlimited"));
			unlimitedImage.setSize(75, 7.5f);
			unlimitedImage.setPosition(twentyImage.getX() + twentyImage.getWidth() + 5, scoreImage.getY());
			stage.addActor(unlimitedImage);
			
			fiveTimeImage = new Image(skin.getDrawable("5"));
			fiveTimeImage.setSize(7.5f, 7.5f);
			fiveTimeImage.setPosition(scoreImage.getX() + timeLimitImage.getWidth() + 15, timeLimitImage.getY());
			stage.addActor(fiveTimeImage);
			
			tenTimeImage = new Image(skin.getDrawable("10"));
			tenTimeImage.setSize(15, 7.5f);
			tenTimeImage.setPosition(fiveTimeImage.getX() + fiveTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(tenTimeImage);
			
			twentTimeyImage = new Image(skin.getDrawable("20"));
			twentTimeyImage.setSize(15, 7.5f);
			twentTimeyImage.setPosition(tenTimeImage.getX() + tenTimeImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(twentTimeyImage);
			
			unlimitedTimeImage = new Image(skin.getDrawable("unlimited"));
			unlimitedTimeImage.setSize(75f, 7.5f);
			unlimitedTimeImage.setPosition(twentTimeyImage.getX() + twentTimeyImage.getWidth() + 5, timeLimitImage.getY());
			stage.addActor(unlimitedTimeImage);
		}
		
		whiteCanvasImage.setBounds(0, 0, camera.viewportWidth, camera.viewportHeight);
		
		// add fade in effect
		whiteCanvasImage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeOut(4f)));	
	}
	
	@Override
	public void setListener(Actor actor, final int ACTION) {
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// Stop fading in if it hasn't stopped already
				if(whiteCanvasImage.getActions().size > 0) {
					whiteCanvasImage.getActions().clear();
					killWhiteImage = true;
				}
				
				switch(ACTION) {
				case LEVEL_1:	// Start the game!!
					animateActorAndFadeOutScreen(level1Image, 0.1f, 1.2f);
					level1ImagePressed = true;
					killWhiteImage = false;
					return true;
				case LEVEL_2:
					animateActorAndFadeOutScreen(level2Image, 0.1f, 1.2f);
					level2ImagePressed = true;
					killWhiteImage = false;
					return true;
				case LEVEL_3:	// Quit the game
					animateActorAndFadeOutScreen(level3Image, 0.1f, 1.2f);
					level3ImagePressed = true;
					killWhiteImage = false;
					return true;
				case RETURN_TO_MENU:
					game.setScreen(new MainMenuScreen(game));
				case LOW_SCORE:
					Settings.defaultScoreLimit = Settings.LOW_SCORE_LIMIT;
					animateActor(fiveImage, 0.1f);
					return true;
				case MEDIUM_SCORE:
					Settings.defaultScoreLimit = Settings.MEDIUM_SCORE_LIMIT;
					animateActor(tenImage, 0.1f);
					return true;
				case HIGH_SCORE:
					Settings.defaultScoreLimit = Settings.HIGH_SCORE_LIMIT;
					animateActor(twentyImage, 0.1f);
					return true;
				case UNLIMITED_SCORE:
					Settings.isUnlimitedcore = true;
					animateActor(unlimitedImage, 0.1f);
					return true;
				case SHORT_TIME:
					Settings.defaultTimeLimit = Settings.SHORT_TIME_LIMIT;
					animateActor(fiveTimeImage, 0.1f);
					return true;
				case MEDIUM_TIME:
					Settings.defaultTimeLimit = Settings.MEDIUM_TIME_LIMIT;
					setAlpha(tenTimeImage, 0.2f, 1);
					return true;
				case LONG_TIME:
					Settings.defaultTimeLimit = Settings.LONG_TIME_LIMIT;
					animateActor(twentTimeyImage, 0.1f);
					return true;
				case UNLIMITED_TIME:
					Settings.isUnlimitedTime = true;
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
		if(!killWhiteImage) {
			whiteCanvasImage.draw(batch, 1);
		}
		batch.end();
		
		tick(delta);
	}
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		
		whiteCanvasImage.act(delta);
		
		if(level1ImagePressed && whiteCanvasImage.getActions().size <= 0) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 1));
		} else if(level2ImagePressed && whiteCanvasImage.getActions().size <= 0) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 2));
		} else if(level3ImagePressed && whiteCanvasImage.getActions().size <= 0) {
			stage.clear();
			game.setScreen(new GameScreen(game, gameMode, 3));
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
