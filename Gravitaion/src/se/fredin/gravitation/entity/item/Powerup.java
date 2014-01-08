package se.fredin.gravitation.entity.item;

import se.fredin.gravitation.entity.AbstractEntity;
import se.fredin.gravitation.entity.physical.Player;
import se.fredin.gravitation.utils.Paths;

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

public abstract class Powerup extends AbstractEntity {

	protected Player player1, player2;
	protected TextureAtlas atlas;
	protected Skin skin;
	protected Image powerupExplanationImage;
	private boolean isGoodPowerup;
	private Sound goodPowerupSound;
	private Sound badPowerupSound;
	
	public Powerup(Array<Rectangle> spawnPoints, float width, float height, String texturePath, Player player1, Player player2, String powerupExplanationPath, boolean isGoodPowerup) {
		super(spawnPoints, width, height, texturePath);
		this.atlas = new TextureAtlas(Gdx.files.internal(Paths.MENU_ITEMS));
		this.skin = new Skin(atlas);
		this.powerupExplanationImage = getImage(powerupExplanationPath, 40, 4);
		this.powerupExplanationImage.setPosition(getPosition().x - powerupExplanationImage.getWidth() / 2, getPosition().y);
		
		this.player1 = player1;
		this.player2 = player2;
		this.isGoodPowerup = isGoodPowerup;
		this.goodPowerupSound = Gdx.audio.newSound(Gdx.files.internal(Paths.GOOD_POWERUP_SOUND_EFFECT));
		this.badPowerupSound = Gdx.audio.newSound(Gdx.files.internal(Paths.BAD_POWERUP_SOUND_EFFECT));
	}
	
	public abstract void affectEntity(Player player);
	
	public abstract void removePower(Player player);
	
	protected Image getImage(String name, float width, float height) {
		Image image = new Image(skin.getDrawable(name));
		image.setSize(width, height);
		return image;
	}
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		powerupExplanationImage.act(delta);
		if(isAlive) {
			if(player1.getBounds().overlaps(bounds)) {
				affectEntity(player1);
				isAlive = false;
				if(isGoodPowerup) {
					goodPowerupSound.play();
				} else {
					badPowerupSound.play();
				}
				this.powerupExplanationImage.addAction(Actions.sequence(Actions.sizeTo(80, 8, 2, Interpolation.circle), Actions.sizeTo(0, 0, 2, Interpolation.circle)));
			} if(player2.getBounds().overlaps(bounds)) {
				affectEntity(player2);
				isAlive = false;
				if(isGoodPowerup) {
					goodPowerupSound.play();
				} else {
					badPowerupSound.play();
				}
				this.powerupExplanationImage.addAction(Actions.sequence(Actions.sizeTo(80, 8, 2, Interpolation.circle), Actions.sizeTo(0, 0, 2, Interpolation.circle)));
			}
		} else {
			if(player1.isCrashed()) {
				removePower(player1);
				setPosition(spawnPoints.get((int)(Math.random() * spawnPoints.size)).x, spawnPoints.get((int)(Math.random() * spawnPoints.size)).y);
				isAlive = true;
			} if(player2.isCrashed()) {
				removePower(player2);
				setPosition(spawnPoints.get((int)(Math.random() * spawnPoints.size)).x, spawnPoints.get((int)(Math.random() * spawnPoints.size)).y);
				isAlive = true;
			}
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
