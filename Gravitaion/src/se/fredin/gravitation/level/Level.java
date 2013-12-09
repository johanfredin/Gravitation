package se.fredin.gravitation.level;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.entity.LaunchPad;
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
import com.badlogic.gdx.math.Rectangle;
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
	private LaunchPad launchPad;
	private Array<Rectangle> hardBlocks;
	private Vector2 launchPadPosition;
	private Vector2 spawnPoint;
	
	private final float MAP_WIDTH;
	private final float MAP_HEIGHT;
	private final float UNIT_SCALE = 1 / 3.2f;
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8;
	private final int POSITIONITERATIONS = 3;
	
	public Level(String levelPath, GameScreen gameScreen) {
		// Setup box2d world
		this.world = new World(new Vector2(0, -9.82f), true);
		this.box2DRenderer = new Box2DDebugRenderer();
		
		// Setup tileMap
		TmxMapLoader mapLoader = new TmxMapLoader();
		this.map = mapLoader.load(Gdx.files.internal(levelPath).path());
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
		int tmpMapWidth = (Integer) map.getProperties().get("width") * (Integer) map.getProperties().get("tilewidth");
		int tmpMapHeight = (Integer) map.getProperties().get("height") * (Integer) map.getProperties().get("tileheight");
		this.MAP_WIDTH = tmpMapWidth * UNIT_SCALE;
		this.MAP_HEIGHT = tmpMapHeight * UNIT_SCALE;
		MapProperties spawnProperties = map.getLayers().get("spawn-points").getObjects().get("start").getProperties();
		
		// Setup launch pad
		this.launchPadPosition = new Vector2((Float)spawnProperties.get("x"), (Float)spawnProperties.get("y"));
		this.launchPad = new LaunchPad(launchPadPosition.x, launchPadPosition.y, Paths.LANDING_PAD_TEXTUREPATH, world, 180, 25f);
		
		// Setup player
		this.spawnPoint = new Vector2(launchPadPosition.x + launchPad.getSprite().getWidth() / 4, launchPadPosition.y + launchPad.getSprite().getHeight());
		this.player = new Player(spawnPoint.x, spawnPoint.y, Paths.SHIP_TEXTUREPATH, this.world, 96, 64);
		this.hardBlocks = getWorldAdaptedBlocks(map);
		
		// Add gamePad support
		for(Controller controller: Controllers.getControllers()) {
			Gdx.app.log(Gravitation.LOG, "Controller found: " + controller.getName());
			controller.addListener(player.getGamePad());
		}
	}
	
	
	private Array<Rectangle> getWorldAdaptedBlocks(TiledMap map) {
		Array<RectangleMapObject> rectangleMapObjects = map.getLayers().get("collision").getObjects().getByType(RectangleMapObject.class);
		this.hardBlocks = new Array<Rectangle>();
		for(RectangleMapObject rect : rectangleMapObjects) {
			rect.getRectangle().set(rect.getRectangle().x * UNIT_SCALE, rect.getRectangle().y * UNIT_SCALE, 
				 rect.getRectangle().width * UNIT_SCALE, rect.getRectangle().height * UNIT_SCALE);
			hardBlocks.add(rect.getRectangle());
		}
		return hardBlocks;
	}

	@Override
	public void start() {}

	@Override
	public void restart() {}

	@Override
	public void end(boolean cleared) {}
	
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mapRenderer.setView(camera);
		mapRenderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.render(batch);
		launchPad.render(batch);
		batch.end();
		
		if(Gravitation.DEBUG_MODE) {
			box2DRenderer.render(world, camera.combined);
		}
		
		camera.update();
		moveCamera(player, camera);
	}
	
	public void tick(float delta) {
		player.tick(delta);
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		player.checkForCollision(hardBlocks, spawnPoint);
	}
	
	
	public void moveCamera(Player gameObject, OrthographicCamera camera) {
		float centerX = camera.viewportWidth / 2;
		float centerY = camera.viewportHeight / 2;
		
		if(player.isCrashed()) {
			float explosionX = gameObject.getExplosion().getX();
			float explosionY = gameObject.getExplosion().getY();
			camera.position.set(explosionX, explosionY, 0);
		} else {
			camera.position.set(gameObject.getBodyPosition().x, gameObject.getBodyPosition().y, 0);
		}
		
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
		launchPad.dispose();
	}
	
}
