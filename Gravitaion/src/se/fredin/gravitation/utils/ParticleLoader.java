package se.fredin.gravitation.utils;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ParticleLoader {
	
	public static ParticleEmitter getEmitter(String propertiesPath, String texturePath, float highLow, float highHigh) {
		ParticleEmitter emitter = new ParticleEmitter();
		try {
			emitter.load(Gdx.files.internal(propertiesPath).reader(2024));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Texture exhaustTexture = new Texture(Gdx.files.internal(texturePath));
		Sprite exhaustSprite = new Sprite(exhaustTexture);
		emitter.setSprite(exhaustSprite);
		emitter.getScale().setHigh(highLow, highHigh);
		return emitter;
	}
	
	public static ParticleEmitter getEmitter(String propertiesPath, String texturePath, float highLow, float highHigh, float pixPerMeters, float zoomLevel) {
		ParticleEmitter emitter = new ParticleEmitter();
		try {
			emitter.load(Gdx.files.internal(propertiesPath).reader(2024));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Texture exhaustTexture = new Texture(Gdx.files.internal(texturePath));
		Sprite exhaustSprite = new Sprite(exhaustTexture);
		emitter.setSprite(exhaustSprite);
		emitter.getScale().setHigh(highLow, highHigh);
		return emitter;
	}

}
