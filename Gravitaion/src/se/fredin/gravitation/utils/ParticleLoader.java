package se.fredin.gravitation.utils;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Helper class for getting particle effects.
 * @author Johan Fredin
 *
 */
public class ParticleLoader {
	
	/**
	 * Gets a new ParticleEmitter instance with a specified minimum and maximum height scale.
	 * @param propertiesPath The path to the emitter properties file.
	 * @param texturePath The path to the emitter texture.
	 * @param highMin The high min scale of the emitter.
	 * @param highMax The hight max scale of the emitter.
	 * @return A new ParticleEmitter instance.
	 */
	public static ParticleEmitter getEmitter(String propertiesPath, String texturePath, float highMin, float highMax) {
		ParticleEmitter emitter = new ParticleEmitter();
		try {
			emitter.load(Gdx.files.internal(propertiesPath).reader(2024));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Texture exhaustTexture = new Texture(Gdx.files.internal(texturePath));
		Sprite exhaustSprite = new Sprite(exhaustTexture);
		Array<Sprite> sprites = new Array<Sprite>();
		sprites.add(exhaustSprite);
		emitter.setSprites(sprites);
		emitter.getXScale().setHigh(highMin, highMax);
		emitter.getYScale().setHigh(highMin, highMax);
		return emitter;
	}
}
