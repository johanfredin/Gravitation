package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.level.Level;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PauseDialogue extends Dialogue {
	
	private Image pauseImage, resumeImage;

	public PauseDialogue(Game game, Level level, GameMode gameMode, OrthographicCamera camera) {
		super(game, level, gameMode, camera);
		pauseImage = getImage("PAUSE", 75, 7.5f);
		stage.addActor(pauseImage);
		setListener(resumeImage, RESUME, level);
		
		addToStageAndSetPositions(gameMode, dialogImage.getX() + (dialogImage.getWidth() / 2), dialogImage.getY() + dialogImage.getHeight());
	}

	@Override
	public void addToStageAndSetPositions(GameMode gameMode, float centerX, float height) {
		pauseImage.setPosition(centerX - pauseImage.getWidth() / 2, height - pauseImage.getHeight());
		stage.addActor(pauseImage);
		replayImage.setPosition(centerX - replayImage.getWidth() / 2, pauseImage.getY() - pauseImage.getHeight());
		stage.addActor(replayImage);
		backToMenuImage.setPosition(centerX - backToMenuImage.getWidth() / 2, replayImage.getY() - replayImage.getHeight());
		stage.addActor(backToMenuImage);
	}
}
