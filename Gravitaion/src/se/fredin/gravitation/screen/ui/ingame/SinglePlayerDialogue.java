package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.level.Level;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SinglePlayerDialogue extends Dialogue {
	
	private Image levelCompleteImage;
	
	public SinglePlayerDialogue(Game game, Level level) {
		super(game, level, GameMode.SINGLE_PLAYER);
		levelCompleteImage = getImage("level complete", 75, 7.5f);
	}

	@Override
	public void setListener(Actor actor, int ACTION, Level level) {
		
	}

	@Override
	public void addToStageAndSetPositions(GameMode gameMode, float width, float height) {
		float center = width / 2;
		levelCompleteImage.setPosition(center - levelCompleteImage.getWidth() / 2, height - levelCompleteImage.getHeight());
		stage.addActor(levelCompleteImage);
		replayImage.setPosition(center - replayImage.getWidth() / 2, levelCompleteImage.getY() - levelCompleteImage.getHeight());
		stage.addActor(replayImage);
		backToMenuImage.setPosition(center - backToMenuImage.getWidth() / 2, replayImage.getY() - replayImage.getHeight());
		stage.addActor(backToMenuImage);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
