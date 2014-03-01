package se.fredin.gravitation.entity;

import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class that handles a station that the player needs to pass in single player mode.
 * @author Johan Fredin
 *
 */
public class Station implements Disposable {

	private ShapeRenderer shapeRenderer;
	private Player player;
	private boolean isAlive;
	private boolean isTaken;
	private Rectangle bounds;
	private Sound stationPassedSound;
	private boolean stationPassedSoundPlaying;
	
	/**
	 * Creates a new Station.
	 * @param x The x position of the station.
	 * @param y The y position of the station.
	 * @param width The width of the station.
	 * @param height The height of the station.
	 * @param player The Player object that will interact with the station.
	 * @param stationNr The number of the station.
	 */
	public Station(float x, float y, float width, float height, Player player, byte stationNr) {
		this.player = player;
		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setColor(Color.GREEN);
		this.bounds = new Rectangle(x, y, width, height);
		this.stationPassedSound = Gdx.audio.newSound(Gdx.files.internal(Paths.STATION_PASSED_SOUND));
	}
	
	/**
	 * Renders the station to the screen if it is not already passed.
	 * @param batch The SpriteBatch responsible for rendering the station.
	 */
	public void render(SpriteBatch batch) {
		if(isAlive && !isTaken) {
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
			shapeRenderer.end();
		}
	}
	
	/**
	 * Checks the state of the station.
	 * @param delta The time interval since last render occurred.
	 */
	public void tick(float delta) {
		if(player.getBounds().overlaps(bounds) && isAlive) {
			if(!stationPassedSoundPlaying) {
				stationPassedSound.play();
				stationPassedSoundPlaying = true;
			}
			isAlive = false;
			isTaken = true;
		}
	}
	
	/**
	 * Set the state of this station.
	 * @param isAlive <b>true<b/> if this station should be alive.
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	/**
	 * Check if this station is taken.
	 * @return <b>true<b/> if the station is taken.
	 */
	public boolean isTaken() {
		return isTaken;
	}
	
	/**
	 * Return the boundaries of this station.
	 * @return The boundaries of this station.
	 */
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		player.dispose();
		stationPassedSound.dispose();
	}

	
	
	

}
