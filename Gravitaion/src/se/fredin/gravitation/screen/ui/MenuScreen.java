package se.fredin.gravitation.screen.ui;

import se.fredin.gravitation.screen.BaseScreen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public abstract class MenuScreen extends BaseScreen implements Disposable {
	
	protected Stage stage;
	protected OrthographicCamera camera;
	
	
	
	public void render() {
		
	}
	
	public void tick(float deltaTime) {
		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

}
