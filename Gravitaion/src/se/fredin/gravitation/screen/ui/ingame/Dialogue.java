package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.screen.BaseScreen;
import se.fredin.gravitation.utils.Paths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class Dialogue {

	protected final int RESUME = 1, REPLAY = 2, BACK_TO_MENU = 3;
	protected Image replayImage, backToMenuImage;
	protected OrthographicCamera camera;
	protected TextureAtlas atlas;
	protected Skin skin;
	protected Stage stage;
	protected Game game;
	
	public Dialogue(Game game, Level level, GameMode gameMode) {
		this.game = game;
		this.camera = new OrthographicCamera(gameMode == GameMode.MULTI_PLAYER ? BaseScreen.VIEWPORT_WIDTH * 2 : BaseScreen.VIEWPORT_WIDTH, BaseScreen.VIEWPORT_HEIGHT);
		this.stage = new Stage(camera.viewportWidth, camera.viewportHeight);
		this.atlas = new TextureAtlas(Gdx.files.internal(Paths.MENU_ITEMS));
		this.skin = new Skin(atlas);
		
		replayImage = getImage("replay", 75, 7.5f);
		backToMenuImage = getImage("back to menu", 75, 7.5f);
		
		addToStageAndSetPositions(gameMode, camera.viewportWidth, camera.viewportHeight);
		
		setListener(backToMenuImage, BACK_TO_MENU, level);
		setListener(replayImage, REPLAY, level);
		
	}
	
	public Dialogue(Game game, Level level, GameMode gameMode, Player player) {
		this(game, level, gameMode);
	}
	
	public abstract void setListener(Actor actor, final int ACTION, final Level level);
	
	public abstract void addToStageAndSetPositions(GameMode gameMode, float width, float height);
	
	protected Image getImage(String name, float width, float height) {
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
}
