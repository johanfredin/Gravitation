package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.screen.ui.MainMenuScreen;
import se.fredin.gravitation.utils.GameMode;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;
import se.fredin.gravitation.utils.UiHelper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

/**
 * The main UI handler class for making in game pop up menus
 * @author johan
 *
 */
public abstract class Dialogue implements Disposable {

	protected final byte REPLAY = 1, BACK_TO_MENU = 2;
	protected Image dialogImage, replayImage, backToMenuImage, whiteRectImage;
	protected UiHelper uiHelper;
	protected Stage stage;
	protected Game game;
	protected GameMode gameMode;
	protected Sound buttonPressedSound;
	private boolean isReplayPressed;
	protected boolean isReturnToMenuPressed;
	
	/**
	 * Creates a new Dialogue instance.
	 * @param game the game instance used for switching screens
	 * @param level the current level
	 * @param gameMode the selected game mode
	 * @param camera the camera responsible for this menu
	 */
	public Dialogue(Game game, Level level, GameMode gameMode, OrthographicCamera camera) {
		this.game = game;
		this.gameMode = gameMode;
		this.stage = new Stage(camera.viewportWidth, camera.viewportHeight, false);
		this.uiHelper = new UiHelper(stage, Paths.MENU_ITEMS);
		
		this.dialogImage = uiHelper.getImage("SQUARE", stage.getWidth() / 2, stage.getHeight() / 2.33f);
		this.whiteRectImage = uiHelper.getImage("whiterect", stage.getWidth(), stage.getHeight());
		whiteRectImage.addAction(Actions.fadeOut(0f));
		
		this.buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal(Paths.MENU_SELECT_SOUND_EFFECT));
		
		dialogImage.setPosition(camera.position.x - dialogImage.getWidth() / 2, camera.position.y - dialogImage.getHeight() / 2);
		stage.addActor(dialogImage);
		
		replayImage = uiHelper.getImage("replay", dialogImage.getWidth() / 1.66f, dialogImage.getHeight() / 7);
		backToMenuImage = uiHelper.getImage("return to menu", dialogImage.getWidth() / 1.66f, dialogImage.getHeight() / 7);
		
		setListener(backToMenuImage, BACK_TO_MENU, level);
		setListener(replayImage, REPLAY, level);
		
	}
	
	/**
	 * Puts the stage actors at the specified positions
	 * @param gameMode the current game mode
	 * @param centerX the center x position of the menu
	 * @param height the height of the menu
	 */
	public abstract void addToStageAndSetPositions(GameMode gameMode, float centerX, float height);
	
	/**
	 * Get the stage of this menu
	 * @return the stage of this menu
	 */
	public Stage getStage() {
		return stage;
	}
	
	private void setListener(Actor actor, final byte ACTION, final Level level) {
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				buttonPressedSound.play();
				
				switch(ACTION) {
				case REPLAY:
					uiHelper.animateActorAndFadeOutScreen(replayImage, whiteRectImage, 0.1f, 1.2f);
					isReplayPressed = true;
					return true;
				case BACK_TO_MENU:
					uiHelper.animateActorAndFadeOutScreen(backToMenuImage, whiteRectImage, 0.1f, 1.2f);
					isReturnToMenuPressed = true;
					return true;
				}
				return false;
			}
		});
	}
	
	/**
	 * Updates this menu
	 * @param delta the time interval between updates
	 */
	public void tick(float delta) {
		stage.act(delta);
		whiteRectImage.act(delta);
		if(isReplayPressed && uiHelper.isFinishedActing(whiteRectImage)) {
			game.setScreen(new GameScreen(game, gameMode, Settings.currentLevel));
		} else if(isReturnToMenuPressed && uiHelper.isFinishedActing(whiteRectImage)) {
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	/**
	 * Renders the menu to the screen
	 */
	public void render() {
		stage.draw();
		stage.getSpriteBatch().begin();
		whiteRectImage.draw(stage.getSpriteBatch(), 1);
		stage.getSpriteBatch().end();
	}
	
	/**
	 * Renders the menu to the screen
	 * @param title the title image to be displayed on this menu
	 */
	public void render(String title) {}
	
	@Override
	public void dispose() {
		stage.dispose();
		uiHelper.dispose();
		game.dispose();
		buttonPressedSound.dispose();
	}
}
