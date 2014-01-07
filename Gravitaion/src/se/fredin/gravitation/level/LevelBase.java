package se.fredin.gravitation.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface LevelBase {

	void tick(float deltatime);
	
	void render(SpriteBatch renderer, OrthographicCamera camera1, OrthographicCamera camera2);
	
	void render(SpriteBatch renderer, OrthographicCamera camera);
}
