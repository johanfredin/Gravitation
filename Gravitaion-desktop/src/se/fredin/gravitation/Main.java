package se.fredin.gravitation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Gravitation";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 560;
		cfg.fullscreen = false;
		
		new LwjglApplication(new Gravitation(), cfg);
	}
}
