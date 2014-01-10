package se.fredin.gravitation.entity.handler;

import se.fredin.gravitation.entity.Station;
import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Handler class for all the stations in the game. Responsible for drawing them and updating them
 * @author johan
 *
 */
public class StationHandler implements Disposable {
	
	private Array<Station> stations;
	private boolean lastStationPassed;
	
	/**
	 * Creates the stations
	 * @param map - the TiledMap to give the stations to
	 * @param player - the player to interact with the powerups
	 * @param unitScale - the unitscale of the map
	 */
	public StationHandler(TiledMap map, Player player, float unitScale) {
		this.stations = getWorldAdaptedStationStations(map, unitScale, player);
	}
	
	/*
	 * Gets the station position from the map, scales them to the appropriate unitscale and puts them into a Rectangle array.
	 */
	private Array<Station> getWorldAdaptedStationStations(TiledMap map, float unitScale, Player player) {
		Array<RectangleMapObject> objects = map.getLayers().get("stations").getObjects().getByType(RectangleMapObject.class);
		Array<Station> stations = new Array<Station>();
		for(byte i = 0; i < objects.size; i++) {
			Station station = new Station(objects.get(i).getRectangle().x * unitScale, objects.get(i).getRectangle().y * unitScale, 
					objects.get(i).getRectangle().width * unitScale, objects.get(i).getRectangle().height * unitScale, player, i);
			if(i == 0) {
				station.setAlive(true);
			}
			stations.add(station);
		}
		return stations;
	}
	
	/**
	 * Updates the stations. Lights up the next station once the player has passed one.
	 * 
	 * @param delta
	 */
	public void tick(float delta) {
		for(int i = 0; i < stations.size; i++) {
			stations.get(i).tick(delta);
			if(stations.get(i).isTaken()) {
				if(i == stations.size - 1 && stations.get(i - 1).isTaken()) {
					stations.get(i).setAlive(true);
				} else if(stations.get(stations.size - 1).isTaken()) {
					lastStationPassed = true;
				} else {
					stations.get(i + 1).setAlive(true);
				}
			}
		}
	}

	/**
	 * Draws the stations to the screen
	 * @param batch - the SpriteBatch responsible for drawing to the screen.
	 */
	public void render(SpriteBatch batch) {
		for(Station station : stations) {
			station.render(batch);
		}
	}
	
	/**
	 * Check if the last station has been passed
	 * @return - <b>true</b> if the last station has been passed
	 */
	public boolean isLastStationPassed() {
		return lastStationPassed;
	}
	
	@Override
	public void dispose() {
		for(Station station : stations) {
			station.dispose();
		}
	}
}
