package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.utils.GameMode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Class used for a single player pop up menus.
 * @author Johan Fredin
 *
 */
public class SinglePlayerDialogue extends Dialogue {
	
	private Image levelCompleteImage;
	
	/**
	 * Creates a new SinglePlayerDialogue instance.
	 * @param game The game instance used for switching screens.
	 * @param level The current level.
	 * @param gameMode The selected game mode.
	 * @param camera The camera responsible for this menu.
	 */
	public SinglePlayerDialogue(Game game, Level level, OrthographicCamera camera) {
		super(game, level, GameMode.SINGLE_PLAYER, camera);// 100, 10
		levelCompleteImage = uiHelper.getImage("level complete", dialogImage.getWidth() / 1.33f, dialogImage.getHeight() / 5);
		uiHelper.flashActor(levelCompleteImage);
		addToStageAndSetPositions(gameMode, dialogImage.getX() + (dialogImage.getWidth() / 2), dialogImage.getY() + dialogImage.getHeight());
	}

	@Override
	public void addToStageAndSetPositions(GameMode gameMode, float centerX, float height) {
		levelCompleteImage.setPosition(centerX - levelCompleteImage.getWidth() / 2, height - levelCompleteImage.getHeight() * 1.33f);
		stage.addActor(levelCompleteImage);
		replayImage.setPosition(centerX - replayImage.getWidth() / 2, levelCompleteImage.getY() - levelCompleteImage.getHeight());
		stage.addActor(replayImage);
		backToMenuImage.setPosition(centerX - backToMenuImage.getWidth() / 2, replayImage.getY() - replayImage.getHeight() - 1);
		stage.addActor(backToMenuImage);
	}
}
