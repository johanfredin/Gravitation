package se.fredin.gravitation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Gravitation";
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 720;
		cfg.fullscreen = true;
		
		new LwjglApplication(new Gravitation(), cfg);
	}
}
