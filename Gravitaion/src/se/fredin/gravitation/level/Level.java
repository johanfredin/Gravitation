package se.fredin.gravitation.level;

import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.entity.physical.LaunchPad;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	private World world;
	private TiledMap map;
	private Player player;
	private Array<LaunchPad> launchPads;
	private Array<Vector2> spawnPoints;
	private Array<Rectangle> hardBlocks;
	private Array<Vector2> launchPadPositions;
	private Vector2 spawnPoint;
	
	private final float MAP_WIDTH;
	private final float MAP_HEIGHT;
	private final float UNIT_SCALE = 1 / 3.20f;
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8;
	private final int POSITIONITERATIONS = 3;
	
	// For debugging
	private Box2DDebugRenderer box2DRenderer;
	private ShapeRenderer shapeRenderer;
	
	
	public Level(String levelPath, GameScreen gameScreen) {
		// Setup box2d world
		this.world = new World(new Vector2(0, -12.82f), true);
		this.box2DRenderer = new Box2DDebugRenderer();
		this.shapeRenderer = new ShapeRenderer();
		
		// Setup tileMap
		TmxMapLoader mapLoader = new TmxMapLoader();
		this.map = mapLoader.load(Gdx.files.internal(levelPath).path());
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
		int tmpMapWidth = (Integer) map.getProperties().get("width") * (Integer) map.getProperties().get("tilewidth");
		int tmpMapHeight = (Integer) map.getProperties().get("height") * (Integer) map.getProperties().get("tileheight");
		this.MAP_WIDTH = tmpMapWidth * UNIT_SCALE;
		this.MAP_HEIGHT = tmpMapHeight * UNIT_SCALE;
		initLaunchPads();
	
		// Setup player
		this.spawnPoint = new Vector2(spawnPoints.get((int)(Math.random() * spawnPoints.size)));
		this.player = new Player(spawnPoint.x, spawnPoint.y, Paths.SHIP_TEXTUREPATH, this.world, 96, 64);
		this.hardBlocks = getWorldAdaptedBlocks(map);
		
		// Add gamePad support
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			for(Controller controller: Controllers.getControllers()) {
				Gdx.app.log(Gravitation.LOG, "Controller found: " + controller.getName());
				controller.addListener(player.getGamePad());
			}
		} 
	}
	
	private void initLaunchPads() {
		launchPadPositions = new Array<Vector2>();
		spawnPoints = new Array<Vector2>();
		launchPads = new Array<LaunchPad>();
		for(int i = 1; i <= 4; i++) {
			MapProperties spawnProperties = map.getLayers().get("spawn-points").getObjects().get("start" + i).getProperties();
			
			Vector2 launchPadPosition = new Vector2((Float)spawnProperties.get("x") * UNIT_SCALE, (Float)spawnProperties.get("y") * UNIT_SCALE);
			this.launchPadPositions.add(launchPadPosition);
			
			LaunchPad launchPad = new LaunchPad(launchPadPosition.x, launchPadPosition.y, Paths.LANDING_PAD_TEXTUREPATH, world, 180, 25f);
			this.launchPads.add(launchPad);
			this.spawnPoints.add(new Vector2(launchPadPosition.x + launchPad.getSprite().getWidth() / 4, launchPadPosition.y + launchPad.getSprite().getHeight()));
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
		for(LaunchPad launchPad : launchPads) {
			launchPad.render(batch);
		}
		player.render(batch);
		batch.end();
		
		if(Gravitation.DEBUG_MODE) {
			box2DRenderer.render(world, camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.rect(player.getBounds().x, player.getBounds().y, player.getBounds().width, player.getBounds().height);
			for(LaunchPad launchPad : launchPads) {
				shapeRenderer.rect(launchPad.getBounds().x, launchPad.getBounds().y, launchPad.getBounds().width, launchPad.getBounds().height);
			}
			shapeRenderer.end();
		}
		
		moveCamera(player, camera);
		camera.update();
	}
	
	public void tick(float delta) {
		player.tick(delta);
		for(LaunchPad launchPad : launchPads) {
			launchPad.tick(delta);
			launchPad.checkIfTaken(player, delta);
		}
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		player.checkForCollision(hardBlocks, spawnPoints);
	}
	
	public void moveCamera(Player gameObject, OrthographicCamera camera) {
		float centerX = camera.viewportWidth / 2;
		float centerY = camera.viewportHeight / 2;
		
		// SET CAMERA TO PLAYER OR EXPLOSION POINT
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
		for(LaunchPad launchPad : launchPads) {
			launchPad.dispose();
		}
	}
	
}
