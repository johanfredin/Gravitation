package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;
import se.fredin.gravitation.utils.UiHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

/**
 * Class for making powerups that will affect the players in a good or a bad way.
 * @author Johan fredin
 *
 */
public abstract class Powerup extends AbstractEntity {

	protected Player player1, player2;
	protected TextureAtlas atlas;
	protected Skin skin;
	protected Image powerupExplanationImage;
	private boolean isGoodPowerup;
	private Sound goodPowerupSound;
	private Sound badPowerupSound;
	
	/**
	 * Creates a new Powerup object on a random map position. Gives it a specified width and height and assigns a sprite to it.
	 * @param spawnPoints The different spawnpoints that this powerup will get a random position from.
	 * @param width The width of the powerup.
	 * @param height The height of the powerup.
	 * @param player1 The first Player this powerup will interact with.
	 * @param player2 The second Player this powerup will interact with.
	 * @param powerupExplanationPath The location of the text image that will pop-up once a player has interacted with the powerup.
	 * @param isGoodPowerup <b>true</b> if this powerup will affect the player in a good way.
	 */
	public Powerup(Array<Rectangle> spawnPoints, float width, float height, Player player1, Player player2, String powerupExplanationPath, boolean isGoodPowerup) {
		this(spawnPoints, width, height, Gdx.files.internal(Paths.POWERUP_IMAGE_PATH).path(), player1, player2, powerupExplanationPath, isGoodPowerup);
		this.atlas = new TextureAtlas(Gdx.files.internal(Paths.MENU_ITEMS));
		this.skin = new Skin(atlas);
		this.powerupExplanationImage = UiHelper.getImage(skin, powerupExplanationPath, 40, 4);
		this.powerupExplanationImage.setPosition(getPosition().x - powerupExplanationImage.getWidth() / 2, getPosition().y);
		
		this.player1 = player1;
		this.player2 = player2;
		this.isGoodPowerup = isGoodPowerup;
		this.goodPowerupSound = Gdx.audio.newSound(Gdx.files.internal(Paths.GOOD_POWERUP_SOUND_EFFECT));
		this.badPowerupSound = Gdx.audio.newSound(Gdx.files.internal(Paths.BAD_POWERUP_SOUND_EFFECT));
	}
	
	/**
	 * Creates a new Powerup object on a random map position. Gives it a specified width and height and assigns a sprite to it.
	 * @param spawnPoints The different spawnpoints that this powerup will get a random position from.
	 * @param width The width of the powerup.
	 * @param height The height of the powerup.
	 * @param texturePath The location of the texture that the Sprite will use.
	 * @param player1 The first Player this powerup will interact with.
	 * @param player2 The second Player this powerup will interact with.
	 * @param powerupExplanationPath The location of the text image that will popup once a player has interacted with the powerup.
	 * @param isGoodPowerup <b>true</b> if this powerup will affect the player in a good way.
	 */
	public Powerup(Array<Rectangle> spawnPoints, float width, float height, String texturePath, Player player1, Player player2, String powerupExplanationPath, boolean isGoodPowerup) {
		super(spawnPoints, width, height, texturePath);
		this.atlas = new TextureAtlas(Gdx.files.internal(Paths.MENU_ITEMS));
		this.skin = new Skin(atlas);
		this.powerupExplanationImage = UiHelper.getImage(skin, powerupExplanationPath, 40, 4);
		
		this.player1 = player1;
		this.player2 = player2;
		this.isGoodPowerup = isGoodPowerup;
		this.goodPowerupSound = Gdx.audio.newSound(Gdx.files.internal(Paths.GOOD_POWERUP_SOUND_EFFECT));
		this.badPowerupSound = Gdx.audio.newSound(Gdx.files.internal(Paths.BAD_POWERUP_SOUND_EFFECT));
	}
	
	/**
	 * Will decide how to affect the player once interaction with powerup has taken place.
	 * @param player The player to affect
	 */
	public abstract void affectEntity(Player player);
	
	/**
	 * Will remove the effect of this powerup from the player.
	 * @param player The player to remove this powerup from.
	 */
	public abstract void removePower(Player player);
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		powerupExplanationImage.act(delta);
		if(isAlive) {
			if(player1.getBounds().overlaps(bounds)) {
				this.powerupExplanationImage.setPosition(player1.getPosition().x, player1.getPosition().y);
				affectEntity(player1);
				isAlive = false;
				if(isGoodPowerup) {
					goodPowerupSound.play();
				} else {
					badPowerupSound.play();
				}
				if(UiHelper.isNotActing(powerupExplanationImage)) {
					this.powerupExplanationImage.addAction(Actions.sequence(Actions.sizeTo(80, 8, 2, Interpolation.circle), Actions.sizeTo(0, 0, 2, Interpolation.circle)));
				}
			} if(player2.getBounds().overlaps(bounds)) {
				affectEntity(player2);
				this.powerupExplanationImage.setPosition(player2.getPosition().x, player2.getPosition().y);
				isAlive = false;
				if(isGoodPowerup) {
					goodPowerupSound.play();
				} else {
					badPowerupSound.play();
				}
				if(UiHelper.isNotActing(powerupExplanationImage)) {
					this.powerupExplanationImage.addAction(Actions.sequence(Actions.sizeTo(80, 8, 2, Interpolation.circle), Actions.sizeTo(0, 0, 2, Interpolation.circle)));
				}
			}
		} 
		
		if(player1.isCrashed() && !isAlive) {
			removePower(player1);
			setPosition(spawnPoints.get((int)(Math.random() * spawnPoints.size)).x, spawnPoints.get((int)(Math.random() * spawnPoints.size)).y);
			isAlive = true;
		} if(player2.isCrashed() && !isAlive) {
			removePower(player2);
			setPosition(spawnPoints.get((int)(Math.random() * spawnPoints.size)).x, spawnPoints.get((int)(Math.random() * spawnPoints.size)).y);
			isAlive = true;
		}
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(powerupExplanationImage.getActions().size > 0) {
			powerupExplanationImage.draw(batch, 1);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		atlas.dispose();
		skin.dispose();
		player1.dispose();
		player2.dispose();
		goodPowerupSound.dispose();
		badPowerupSound.dispose();
	}
}
