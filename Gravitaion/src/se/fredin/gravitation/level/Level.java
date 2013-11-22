package se.fredin.gravitation.level;

import se.fredin.gravitation.entity.Player;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Level implements LevelBase, Disposable {

	private OrthogonalTiledMapRenderer mapRenderer;
	private Box2DDebugRenderer box2DRenderer;
	private World world;
	private TiledMap map;
	private Player player;
	private int MAP_WIDTH;
	private int MAP_HEIGHT;
	private Array<RectangleMapObject> hardBlocks;
	private Vector2 spawnPoint;
	public static final String CONTROLLER = "Controller";
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERARIONS = 8;
	private final int POSITIONITERATIONS = 3;
	
	public Level(String levelPath, GameScreen gameScreen) {
		this.world = new World(new Vector2(0, -9.81f), true);
		this.box2DRenderer = new Box2DDebugRenderer();
		
		TmxMapLoader mapLoader = new TmxMapLoader();
		map = mapLoader.load(Gdx.files.internal(levelPath).path());
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		MAP_WIDTH = (Integer) map.getProperties().get("width") * (Integer) map.getProperties().get("tilewidth");
		MAP_HEIGHT = (Integer) map.getProperties().get("height") * (Integer) map.getProperties().get("tileheight");
		MapProperties spawnProperties = map.getLayers().get("spawn-points").getObjects().get("start").getProperties();
		spawnPoint = new Vector2((Float)spawnProperties.get("x"), (Float)spawnProperties.get("y"));
		player = new Player(spawnPoint, Paths.SHIP_TEXTUREPATH, this.world);
		hardBlocks = map.getLayers().get("collision").getObjects().getByType(RectangleMapObject.class);
		
		for(Controller controller: Controllers.getControllers()) {
			Gdx.app.log(CONTROLLER, "Controller found: " + controller.getName());
			controller.addListener(player.getGamePad());
		}
	}
	
	@Override
	public void start() {
	}

	@Override
	public void restart() {
	}

	@Override
	public void end(boolean cleared) {
	}
	
	private void checkForCollision() {
		for(RectangleMapObject rect : hardBlocks) {
			if(player.getBounds().overlaps(rect.getRectangle())) {
				player.setPosition(spawnPoint.x, spawnPoint.y);
				player.setMovement(0, 0);
			}
		}
	}

	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		batch.begin();
		player.render(batch);
		batch.end();
		
		box2DRenderer.render(world, camera.combined);
		
		moveCamera(player, camera);
	}
	
	public void tick(float delta) {
		player.tick(delta);
		world.step(TIMESTEP, VELOCITYITERARIONS, POSITIONITERATIONS);
		checkForCollision();
	}
	
	
	public void moveCamera(Player gameObject, OrthographicCamera camera) {
		float centerX = camera.viewportWidth / 2;
		float centerY = camera.viewportHeight / 2;
		
		camera.position.set(gameObject.getPosition().x, gameObject.getPosition().y, 0);
				
		if(camera.position.x - centerX <= 0) 
			camera.position.x = centerX;
		if(camera.position.x + centerX >= MAP_WIDTH)
			camera.position.x = MAP_WIDTH - centerX;
		if(camera.position.y - centerY <= 0)
			camera.position.y = centerY;
		if(camera.position.y + centerY >= MAP_HEIGHT)
			camera.position.y = MAP_HEIGHT - centerY;
	}
	
	@Override
	public void dispose() {
		map.dispose();
		mapRenderer.dispose();
		player.dispose();
	}
	
}
