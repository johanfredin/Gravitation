package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.screen.ui.MainMenuScreen;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class InGameMenu extends Dialogue {

	private final int RESUME = 1, REPLAY = 2, BACK_TO_MENU = 3;
	private Image resumeImage, replayImage, backToMenuImage, drawImage,
				  player1WinsImage, player2WinsImage, levelCompleteImage, pauseImage;
	
	private boolean isPauseMenu;
	private OrthographicCamera camera;
	private TextureAtlas atlas;
	private Skin skin;
	private Stage stage;
	private Game game;
	
	public InGameMenu(Game game, Level level, GameMode gameMode, Player player) {
		this.game = game;
		camera = new OrthographicCamera(gameMode == GameMode.MULTI_PLAYER ? BaseScreen.VIEWPORT_WIDTH * 2 : BaseScreen.VIEWPORT_WIDTH, BaseScreen.VIEWPORT_HEIGHT);
		stage = new Stage(camera.viewportWidth, camera.viewportHeight);
		atlas = new TextureAtlas(Gdx.files.internal(Paths.MENU_ITEMS));
		skin = new Skin(atlas);
		
		resumeImage = getImage("resume", 75, 7.5f);
		replayImage = getImage("replay", 75, 7.5f);
		backToMenuImage = getImage("back to menu", 75, 7.5f);
		switch(gameMode) {
		case SINGLE_PLAYER:
			levelCompleteImage = getImage("level complete", 75, 7.5f);
			break;
		case MULTI_PLAYER:
			player1WinsImage = getImage("player 1 wins", 75, 7.5f);
			player2WinsImage = getImage("player 2 wins", 75, 7.5f);
			//drawImage = getImage("draw", 75, 7.5f);
			break;
		}
		
		if(isPauseMenu) {
			//pauseImage = getImage("pause", 75, 7.5f);
		}
		
		addToStageAndSetPositions(gameMode, camera.viewportWidth, camera.viewportHeight, player);
		setListener(backToMenuImage, BACK_TO_MENU, level);
		setListener(replayImage, REPLAY, level);
		setListener(resumeImage, RESUME, level);
	}
	
	
	
	private void addToStageAndSetPositions(GameMode gameMode, float width, float height, Player player) {
		float center = width / 2;
		switch(gameMode) {
		case SINGLE_PLAYER:
			if(!isPauseMenu) {
				levelCompleteImage.setPosition(center - levelCompleteImage.getWidth() / 2, height - levelCompleteImage.getHeight());
				stage.addActor(levelCompleteImage);
			}
			break;
		case MULTI_PLAYER:
			if(!isPauseMenu) {
				switch(player.getPlayerNum()) {
				case 1:
					player1WinsImage.setPosition(center - player1WinsImage.getWidth() / 2, height - player1WinsImage.getHeight());
					stage.addActor(player1WinsImage);
					break;
				case 2:
					player2WinsImage.setPosition(center - player2WinsImage.getWidth() / 2, height - player2WinsImage.getHeight());
					stage.addActor(player2WinsImage);
					break;
				default:	// draw
					//drawImage.setPosition(center - drawImage.getWidth() / 2, height - drawImage.getHeight());
					//stage.addActor(drawImage);
					break;
				}
			}
		}
		
		if(isPauseMenu) {
			pauseImage.setPosition(center - pauseImage.getWidth() / 2, height - pauseImage.getHeight());
			stage.addActor(pauseImage);
			resumeImage.setPosition(center - resumeImage.getWidth() / 2, pauseImage.getY() - pauseImage.getHeight());
			stage.addActor(resumeImage);
		}
		
		replayImage.setPosition(center - replayImage.getWidth() / 2, levelCompleteImage.getY() - levelCompleteImage.getHeight());
		stage.addActor(replayImage);
		backToMenuImage.setPosition(center - backToMenuImage.getWidth() / 2, resumeImage.getY() - resumeImage.getHeight());
		stage.addActor(backToMenuImage);
		
	}

	private Image getImage(String name, float width, float height) {
		Image image = new Image(skin.getDrawable(name));
		image.setSize(width, height);
		return image;
	}

	public void tick(float delta) {
		stage.act(delta);
	}
	
	public void render(float delta) {
		stage.draw();
		tick(delta);
	}
	
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}
	
	public void setListener(Actor actor, final int ACTION, final Level level) {
		stage.addActor(actor);		// add button to the scene as an actor
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				switch(ACTION) {
				case RESUME:
					// Resume paused level
					return true;
				case REPLAY:
					level.restart();
					return true;
				case BACK_TO_MENU:
					game.setScreen(new MainMenuScreen(game));
					return true;
				}
				return false;
			}
		});
			
		
	}

}
