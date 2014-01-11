package se.fredin.gravitation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Gravitation";
		cfg.useGL20 = true;
		cfg.width = 1920;
		cfg.height = 1080;
		cfg.vSyncEnabled = false;
		cfg.fullscreen = true;
		
		new LwjglApplication(new Gravitation(), cfg);
	}
}
