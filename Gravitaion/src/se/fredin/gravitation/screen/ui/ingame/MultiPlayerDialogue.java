package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.level.Level;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MultiPlayerDialogue extends Dialogue {

	private Image player1WinsImage, player2WinsImage, drawImage;
	
	public MultiPlayerDialogue(Game game, Level level, OrthographicCamera camera) {
		super(game, level, GameMode.MULTI_PLAYER, camera);
		player1WinsImage = getImage("player 1 wins", 75, 7.5f);
		player2WinsImage = getImage("player 2 wins", 75, 7.5f);
		drawImage = getImage("DRAW", 75, 7.5f);
		
		addToStageAndSetPositions(gameMode, dialogImage.getX() + (dialogImage.getWidth() / 2), dialogImage.getY() + dialogImage.getHeight());
	}

	@Override
	public void addToStageAndSetPositions(GameMode gameMode, float centerX, float height) {
		player1WinsImage.setPosition(centerX - player1WinsImage.getWidth() / 2, height - player1WinsImage.getHeight());
		player2WinsImage.setPosition(centerX - player2WinsImage.getWidth() / 2, height - player2WinsImage.getHeight());
		drawImage.setPosition(centerX - drawImage.getWidth() / 2, height - drawImage.getHeight());
		
		replayImage.setPosition(centerX - replayImage.getWidth() / 2, player1WinsImage.getY() - player1WinsImage.getHeight());
		stage.addActor(replayImage);
		backToMenuImage.setPosition(centerX - backToMenuImage.getWidth() / 2, replayImage.getY() - replayImage.getHeight());
		stage.addActor(backToMenuImage);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
