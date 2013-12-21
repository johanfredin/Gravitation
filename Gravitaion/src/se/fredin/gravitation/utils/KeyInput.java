package se.fredin.gravitation.utils;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class KeyInput extends InputAdapter {

	private Player player1, player2;

	public KeyInput(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.ESCAPE:
			Gdx.app.exit();
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
			if(Gravitation.multiPlayerMode)
				player2.leftPressed = true;
			break;
		case Keys.D:
			if(Gravitation.multiPlayerMode)
				player2.rightPressed = true;
			break;
		case Keys.W:
			if(Gravitation.multiPlayerMode) {
				player2.getExhaust().start();
				player2.gasPressed = true;
			}
			break;
		case Keys.SPACE:
			if(Gravitation.multiPlayerMode)
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
				if(Gravitation.multiPlayerMode) {
					player2.getExhaust().allowCompletion();
					player2.gasPressed = false;
					player2.setMovement(0, 0);
				}
				break;
			case Keys.D:
				if(Gravitation.multiPlayerMode)
					player2.rightPressed = false;
				break;
			case Keys.A:
				if(Gravitation.multiPlayerMode)
					player2.leftPressed = false;
				break;
			case Keys.SPACE:
				break;
			default:
				return false;
			}
			return true;
		}
	
}
