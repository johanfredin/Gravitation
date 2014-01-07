package se.fredin.gravitation.screen.ui.ingame;

import se.fredin.gravitation.GameMode;
import se.fredin.gravitation.level.Level;
import se.fredin.gravitation.screen.GameScreen;
import se.fredin.gravitation.screen.ui.MainMenuScreen;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class Dialogue {

	protected final int REPLAY = 1, BACK_TO_MENU = 2;
	protected Image dialogImage, replayImage, backToMenuImage, whiteRectImage;
	protected TextureAtlas atlas;
	protected Skin skin;
	protected Stage stage;
	protected Game game;
	protected GameMode gameMode;
	private boolean isReplayPressed;
	
	public Dialogue(Game game, Level level, GameMode gameMode, OrthographicCamera camera) {
		this.game = game;
		this.gameMode = gameMode;
		this.atlas = new TextureAtlas(Gdx.files.internal(Paths.MENU_ITEMS));
		this.skin = new Skin(atlas);
		this.stage = new Stage(camera.viewportWidth, camera.viewportHeight);
		
		this.dialogImage = getImage("SQUARE", stage.getWidth() / 2, stage.getHeight() / 2.33f);
		this.whiteRectImage = getImage("whiterect", stage.getWidth(), stage.getHeight());
		whiteRectImage.addAction(Actions.fadeOut(0f));
		
		dialogImage.setPosition(camera.position.x - dialogImage.getWidth() / 2, camera.position.y - dialogImage.getHeight() / 2);
		stage.addActor(dialogImage);
		
		replayImage = getImage("replay", dialogImage.getWidth() / 1.66f, dialogImage.getHeight() / 7);
		backToMenuImage = getImage("return to menu", dialogImage.getWidth() / 1.66f, dialogImage.getHeight() / 7);
		
		setListener(backToMenuImage, BACK_TO_MENU, level);
		setListener(replayImage, REPLAY, level);
		
	}
	
	protected void flashTitle(Actor actor) {
		actor.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeOut(0.75f), Actions.fadeIn(0.75f))));
	}
	
	public abstract void addToStageAndSetPositions(GameMode gameMode, float centerX, float height);
	
	public Stage getStage() {
		return stage;
	}
	
	private void setListener(Actor actor, final int ACTION, final Level level) {
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				switch(ACTION) {
				case REPLAY:
					whiteRectImage.addAction(Actions.fadeIn(1.33f));
					isReplayPressed = true;
					return true;
				case BACK_TO_MENU:
					game.setScreen(new MainMenuScreen(game));
					return true;
				}
				return false;
			}
		});
	}
	
	protected Image getImage(String name, float width, float height) {
		Image image = new Image(skin.getDrawable(name));
		image.setSize(width, height);
		return image;
	}

	public void tick(float delta) {
		stage.act(delta);
		whiteRectImage.act(delta);
		if(isReplayPressed && whiteRectImage.getActions().size <= 0) {
			game.setScreen(new GameScreen(game, gameMode, Settings.currentLevel));
		}
	}
	
	public void render() {
		stage.draw();
		stage.getSpriteBatch().begin();
		whiteRectImage.draw(stage.getSpriteBatch(), 1);
		stage.getSpriteBatch().end();
	}
	
	public void render(String title) {
	}
	
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		game.dispose();
	}
}
