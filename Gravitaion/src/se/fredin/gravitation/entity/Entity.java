package se.fredin.gravitation.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public interface Entity extends Disposable {
	
	void render(SpriteBatch batch);
	
	void tick(float delta);
	
	Vector2 getBodyPosition();
	
	Rectangle getBounds();
	
	@Override
	public void dispose();

}
