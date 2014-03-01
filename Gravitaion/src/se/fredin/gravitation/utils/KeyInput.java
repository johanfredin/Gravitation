package se.fredin.gravitation.utils;

import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;

/**
 * Input handler class for keyboard.
 * @author Johan Fredin
 *
 */
public class KeyInput extends InputAdapter {

	private Player player1, player2;
	private Sound pauseSound;
	
	/**
	 * Creates a new instance of KeyInput.
	 * @param player1 the first player to give keyboard input to.
	 * @param player2 the second player to give keyboard input to( 
	 * if this is a single player game, set this to <b>null</b>).
	 */
	public KeyInput(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.pauseSound = Gdx.audio.newSound(Gdx.files.internal(Paths.PAUSE_SOUND_EFFECT));
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.ESCAPE:
			Gdx.app.exit();
			break;
		case Keys.P:
			if(Settings.isPaused) {
				Settings.isPaused = false;
			} else {
				Settings.isPaused = true;
			}
			pauseSound.play();
			break;
		case Keys.LEFT:
			player1.leftPressed = true;
			break;
		case Keys.RIGHT:
			player1.rightPressed = true;
			break;
		case Keys.UP:
			player1.getExhaust().start();
			player1.gasPressed = true;
			break;
		case Keys.CONTROL_RIGHT:
			player1.shoot();
			break;
		case Keys.A:
			if(player2 != null)
				player2.leftPressed = true;
			break;
		case Keys.D:
			if(player2 != null)
				player2.rightPressed = true;
			break;
		case Keys.W:
			if(player2 != null) {
				player2.getExhaust().start();
				player2.gasPressed = true;
			}
			break;
		case Keys.SPACE:
			if(player2 != null)
				player2.shoot();
			break;
		default:
			return false;
		}
		return true;
	}
		
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.UP:
			player1.getExhaust().allowCompletion();
			player1.gasPressed = false;
			player1.setMovement(0, 0);
			break;
		case Keys.RIGHT:
			player1.rightPressed = false;
			break;
		case Keys.LEFT:
			player1.leftPressed = false;
			break;
		case Keys.CONTROL_RIGHT:
			break;
		case Keys.W:
			if(player2 != null) {
				player2.getExhaust().allowCompletion();
				player2.gasPressed = false;
				player2.setMovement(0, 0);
			}
			break;
		case Keys.D:
			if(player2 != null)
				player2.rightPressed = false;
			break;
		case Keys.A:
			if(player2 != null)
				player2.leftPressed = false;
			break;
		case Keys.SPACE:
			break;
		default:
			return false;
		}
		return true;
	}
		
	public void dispose() {
		player1.dispose();
		if(player2 != null) {
			player2.dispose();
		}
		pauseSound.dispose();
	}
	
}
