package se.fredin.gravitation.entity.item.handler;

import se.fredin.gravitation.entity.item.Station;
import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
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
			Rectangle rect = objects.get(i).getRectangle();
			switch(i) {
			case 2: case 4: case 5:
				stations.add(new Station(rect.x * unitScale + rect.width * unitScale, 
										 rect.y * unitScale + (rect.height / 2 * unitScale), 
										 rect.width, rect.height, player));
				break;
			default:
				stations.add(new Station(rect.x * unitScale + rect.width * (unitScale * rect.width / 2), 
						 rect.y, rect.width, rect.height, player));
				break;
			}
			
		}
		return stations;
	}
	
	public void tick(float delta) {
		for(Station station : stations) {
			station.tick(delta);
		}
	}

	public void render(SpriteBatch batch) {
		for(Station station : stations) {
			station.render(batch);
		}
	}
	
	public void dispose() {
		for(Station station : stations) {
			station.dispose();
		}
	}
}
