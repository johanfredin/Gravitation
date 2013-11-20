package se.fredin.gravitation.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface LevelBase {

	void start();
	
	void restart();
	
	void end(boolean cleared);
	
	void tick(float deltatime);
	
	void render(SpriteBatch renderer, OrthographicCamera camera);
	
}
