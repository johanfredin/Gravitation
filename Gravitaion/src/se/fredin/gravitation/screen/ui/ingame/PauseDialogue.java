package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.level.Level;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PauseDialogue extends Dialogue {
	
	private Image pauseImage, resumeImage;

	public PauseDialogue(Game game, Level level, GameMode gameMode) {
		super(game, level, gameMode);
		pauseImage = getImage("pause", 75, 7.5f);
		stage.addActor(pauseImage);
		setListener(resumeImage, RESUME, level);
	}

	@Override
	public void setListener(Actor actor, int ACTION, Level level) {
		
	}

	@Override
	public void addToStageAndSetPositions(GameMode gameMode, float width, float height) {

	}
}
