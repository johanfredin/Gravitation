package se.fredin.gravitation.entity.item.handler;

import se.fredin.gravitation.entity.item.Station;
import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public class StationHandler {
	
	private Array<Station> stations;
	
	public StationHandler(TiledMap map, Player player, float unitScale) {
		this.stations = getWorldAdaptedStationStations(map, unitScale, player);
	}
	
	private Array<Station> getWorldAdaptedStationStations(TiledMap map, float unitScale, Player player) {
		Array<RectangleMapObject> objects = map.getLayers().get("stations").getObjects().getByType(RectangleMapObject.class);
		Array<Station> stations = new Array<Station>();
		System.out.println(objects.size);
		for(int i = 0; i < objects.size; i++) {
			Station station = new Station(objects.get(i).getRectangle().x * unitScale, objects.get(i).getRectangle().y * unitScale, 
					objects.get(i).getRectangle().width * unitScale, objects.get(i).getRectangle().height * unitScale, player, i);
			if(i == 0) {
				station.setAlive(true);
			}
			stations.add(station);
		}
		return stations;
	}
	
	public void tick(float delta) {
		for(int i = 0; i < stations.size; i++) {
			stations.get(i).tick(delta);
			if(stations.get(i).isTaken()) {
				if(i == stations.size - 1 && stations.get(i - 1).isTaken()) {
					stations.get(i).setAlive(true);
				} else {
					stations.get(i + 1).setAlive(true);
				}
			}
		}
	}

	public void render(SpriteBatch batch) {
		for(Station station : stations) {
			station.render(batch);
		}
	}
	
	public Array<Station> getStations() {
		return stations;
	}
	
	
	public void dispose() {
		for(Station station : stations) {
			station.dispose();
		}
	}
}
