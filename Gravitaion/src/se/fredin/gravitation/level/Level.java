package se.fredin.gravitation.level;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.Gravitation;
import se.fredin.gravitation.entity.Bullet;
import se.fredin.gravitation.entity.handler.LaunchPadHandler;
import se.fredin.gravitation.entity.handler.PowerupHandler;
import se.fredin.gravitation.entity.physical.LaunchPad;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.screen.ui.ingame.Dialogue;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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

public abstract class Level implements LevelBase, Disposable {

	protected OrthogonalTiledMapRenderer mapRenderer;
	protected World world;
	protected TiledMap map;
	protected Player player1, player2;
	protected PowerupHandler itemHandler;
	protected Dialogue inGameMenu;
	protected GameScreen gameScreen;
	
	protected Array<Rectangle> hardBlocks;
	protected LaunchPadHandler launchPadHandler;
	protected Vector2 spawnPoint;
	protected GameMode gameMode;
	protected boolean controlsGivenToStage;
	protected boolean multiPlayerMatchEnded;
	
	protected final float MAP_WIDTH;
	protected final float MAP_HEIGHT;
	protected final float UNIT_SCALE = 1 / 3.20f;
	protected final float TIMESTEP = 1 / 60f;
	protected final int VELOCITYITERATIONS = 8;
	protected final int POSITIONITERATIONS = 3;
	
	// For debugging
	protected Box2DDebugRenderer box2DRenderer;
	protected ShapeRenderer shapeRenderer;
	
	
	public Level(String levelPath, GameScreen gameScreen, GameMode gameMode) {
		this.gameMode = gameMode;
		this.gameScreen = gameScreen;
		
		// Setup box2d world
		this.world = new World(new Vector2(0, -9.82f), true);
		this.box2DRenderer = new Box2DDebugRenderer();
		this.shapeRenderer = new ShapeRenderer();
		this.shapeRenderer.setColor(Color.RED);
		
		// Setup tileMap
		TmxMapLoader mapLoader = new TmxMapLoader();
		this.map = mapLoader.load(Gdx.files.internal(levelPath).path());
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
		int tmpMapWidth = (Integer) map.getProperties().get("width") * (Integer) map.getProperties().get("tilewidth");
		int tmpMapHeight = (Integer) map.getProperties().get("height") * (Integer) map.getProperties().get("tileheight");
		this.MAP_WIDTH = tmpMapWidth * UNIT_SCALE;
		this.MAP_HEIGHT = tmpMapHeight * UNIT_SCALE;
		
		// Setup launchpads
		this.launchPadHandler = new LaunchPadHandler(map, UNIT_SCALE, world);
		
		this.hardBlocks = getWorldAdaptedBlocks(map);
	}
	
	protected void addGamepadSupport() {
		// Add gamePad support
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			for(int i = 0; i < Controllers.getControllers().size; i++) {
				if(i == 0) {
					Controllers.getControllers().get(i).addListener(player1.getGamePad());
					if(gameMode == GameMode.SINGLE_PLAYER) {
						return;
					}
				} else if(i == 1) {
					Controllers.getControllers().get(i).addListener(player2.getGamePad());
				}
			}
		} 
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mapRenderer.setView(camera);
		mapRenderer.render();
	}
	
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera, OrthographicCamera camera2) {	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderHalf(camera, batch, 0, true);
		renderHalf(camera2, batch, multiPlayerMatchEnded ? 0 : Gdx.graphics.getWidth() / 2, false);
		return;
	}
	
	private void renderHalf(OrthographicCamera camera, SpriteBatch batch, int cameraXPos, boolean leftSide) {
		if(!multiPlayerMatchEnded) {
			mapRenderer.setView(camera);
			mapRenderer.render();
			
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			launchPadHandler.render(batch);
			player1.render(batch);
			player2.render(batch);
			itemHandler.render(batch);
			batch.end();
	
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.rect(leftSide ? camera.position.x - camera.viewportWidth / 2 : camera.position.x + camera.viewportWidth / 2, 0, 2, Gdx.graphics.getHeight());
			shapeRenderer.end();
			
			if(Gravitation.DEBUG_MODE) {
				debugRender(camera);
			}
		}
		
		moveCamera(camera, (leftSide ? player1 : player2), cameraXPos, MAP_WIDTH, MAP_HEIGHT);
		camera.update();
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
	
	protected void debugRender(OrthographicCamera camera) {
		box2DRenderer.render(world, camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.rect(player1.getBounds().x, player1.getBounds().y, player1.getBounds().width, player1.getBounds().height);
		for(LaunchPad launchPad : launchPadHandler.getLaunchPads()) {
			shapeRenderer.rect(launchPad.getBounds().x, launchPad.getBounds().y, launchPad.getBounds().width, launchPad.getBounds().height);
		} for(Bullet bullet : player1.getBullets()) {
			shapeRenderer.rect(bullet.getBounds().x, bullet.getBounds().y, bullet.getBounds().width, bullet.getBounds().height);
		} for(Rectangle collisionRect : hardBlocks) {
			shapeRenderer.rect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
		}
		shapeRenderer.end();
	}
	
	protected void moveCamera(OrthographicCamera camera, Player player, int xPos, float mapWidth, float mapHeight) {
		Gdx.gl.glViewport(xPos, 0, gameMode == GameMode.MULTI_PLAYER  && !multiPlayerMatchEnded ? Gdx.graphics.getWidth() / 2 : Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		float centerX = camera.viewportWidth / 2;
		float centerY = camera.viewportHeight / 2;
		// SET CAMERA TO PLAYER OR EXPLOSION POINT
		if(player.isCrashed()) {
			float explosionX = player.getExplosion().getX();
			float explosionY = player.getExplosion().getY();
			camera.position.set(explosionX, explosionY, 0);
		} else {
			camera.position.set(player.getPosition().x, player.getPosition().y, 0);
		}
		
		if(camera.position.x - centerX <= 0) 
			camera.position.x = centerX;
		if(camera.position.x + centerX >= mapWidth)
			camera.position.x = mapWidth - centerX;
		if(camera.position.y - centerY <= 0)
			camera.position.y = centerY;
		if(camera.position.y + centerY >= mapHeight)
			camera.position.y = mapHeight - centerY;
	}
	
	
	@Override
	public void dispose() {
		map.dispose();
		mapRenderer.dispose();
		player1.dispose();
		player2.dispose();
		launchPadHandler.dispose();
		itemHandler.dispose();
	}

	
}
