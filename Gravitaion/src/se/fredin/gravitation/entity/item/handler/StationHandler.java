package se.fredin.gravitation.entity.item.handler;

import se.fredin.gravitation.entity.item.BouncingBulletPowerup;
import se.fredin.gravitation.entity.item.FasterBulletPowerup;
import se.fredin.gravitation.entity.item.FasterPlayerPowerup;
import se.fredin.gravitation.entity.item.Powerup;
import se.fredin.gravitation.entity.item.ReversedStearingPowerup;
import se.fredin.gravitation.entity.item.SlowerBulletPowerup;
import se.fredin.gravitation.entity.item.SlowerPlayerPowerup;
import se.fredin.gravitation.entity.item.Station;
import se.fredin.gravitation.entity.physical.Player;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class StationHandler {
	
	private Array<Rectangle> locations;
	private Array<Station> stations;
	
	public StationHandler(TiledMap map, Player player, float unitScale) {
		this.locations = getWorldAdaptedStationLocations(map, unitScale);
		this.stations = getStations(player);
	}
	
	private Array<Rectangle> getWorldAdaptedStationLocations(TiledMap map, float unitScale) {
		Array<RectangleMapObject> rectangleMapObjects = map.getLayers().get("stations").getObjects().getByType(RectangleMapObject.class);
		this.locations = new Array<Rectangle>();
		System.out.println(rectangleMapObjects.size);
		for(int i = 1; i <= rectangleMapObjects.size; i++) {
			System.out.println(rectangleMapObjects.get(i).getName());
		}
//		for(RectangleMapObject rect : rectangleMapObjects) {
//			rect.getRectangle().set(rect.getRectangle().x * unitScale, rect.getRectangle().y * unitScale, 
//				 rect.getRectangle().width * unitScale, rect.getRectangle().height * unitScale);
//			locations.add(rect.getRectangle());
//		}
		return locations;
	}
	
	private Array<Station> getStations(Player player) {
		Array<Station> stations = new Array<Station>();
		return stations;
	}

}
