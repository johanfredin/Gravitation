package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.level.Level;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MultiPlayerDialogue extends Dialogue {

	private Image player1WinsImage, player2WinsImage, drawImage;
	private Player player;
	
	public MultiPlayerDialogue(Game game, Level level, Player player) {
		super(game, level, GameMode.MULTI_PLAYER, player);
		this.player = player;
		player1WinsImage = getImage("player 1 wins", 75, 7.5f);
		player2WinsImage = getImage("player 2 wins", 75, 7.5f);
		//drawImage = getImage("draw", 75, 7.5f);
	}

	@Override
	public void setListener(Actor actor, int ACTION, Level level) {
		
	}

	@Override
	public void addToStageAndSetPositions(GameMode gameMode, float width, float height) {
		
	}

}
