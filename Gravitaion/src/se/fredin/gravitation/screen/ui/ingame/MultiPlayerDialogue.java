package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.utils.GameMode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Class that should be used for multiplayer pop up menus.
 * @author Johan Fredin
 *
 */
public class MultiPlayerDialogue extends Dialogue {

	private Image player1WinsImage;
	private Image player2WinsImage;
	private Image drawImage;
	
	/**
	 * Creates a new MultiPlayerDialogue instance.
	 * @param game The game instance used for switching screens.
	 * @param level The current level.
	 * @param gameMode The selected game mode.
	 * @param camera The camera responsible for this menu.
	 */
	public MultiPlayerDialogue(Game game, Level level, OrthographicCamera camera) {
		super(game, level, GameMode.MULTI_PLAYER, camera);
		player1WinsImage = uiHelper.getImage("player 1 wins", dialogImage.getWidth() / 1.33f, dialogImage.getHeight() / 5);
		player2WinsImage = uiHelper.getImage("player 2 wins", dialogImage.getWidth() / 1.33f, dialogImage.getHeight() / 5);
		drawImage = uiHelper.getImage("DRAW", dialogImage.getWidth() / 1.33f, dialogImage.getHeight() / 5);
		
		uiHelper.flashActor(drawImage);
		uiHelper.flashActor(player1WinsImage);
		uiHelper.flashActor(player2WinsImage);
		addToStageAndSetPositions(gameMode, dialogImage.getX() + (dialogImage.getWidth() / 2), dialogImage.getY() + dialogImage.getHeight());
	}

	@Override
	public void addToStageAndSetPositions(GameMode gameMode, float centerX, float height) {
		player1WinsImage.setPosition(centerX - player1WinsImage.getWidth() / 2, height - player1WinsImage.getHeight() - 10);
		player2WinsImage.setPosition(centerX - player2WinsImage.getWidth() / 2, height - player2WinsImage.getHeight() - 10);
		drawImage.setPosition(centerX - drawImage.getWidth() / 2, height - drawImage.getHeight() - 10);
		
		replayImage.setPosition(centerX - replayImage.getWidth() / 2, player1WinsImage.getY() - player1WinsImage.getHeight());
		stage.addActor(replayImage);
		backToMenuImage.setPosition(centerX - backToMenuImage.getWidth() / 2, replayImage.getY() - replayImage.getHeight() - 1);
		stage.addActor(backToMenuImage);
	}
	
	@Override
	public void render(String title) {
		render();
		switch(title) {
		case "Player1":
			if(!stage.getActors().contains(player1WinsImage, true)) {
				stage.addActor(player1WinsImage);
			}
			break;
		case "Player2":
			if(!stage.getActors().contains(player2WinsImage, true)) {
				stage.addActor(player2WinsImage);
			}
			break;
		case "Draw":
			if(!stage.getActors().contains(drawImage, true)) {
				stage.addActor(drawImage);
			}
			break;
		}
	}
}
