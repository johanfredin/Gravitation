package se.fredin.gravitation;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Gravitation";
		cfg.addIcon("data/objects/shipS1.png", FileType.Internal);
		cfg.useGL20 = true;
		cfg.width = 600;
		cfg.height = 480;
		cfg.fullscreen = false;
		
		new LwjglApplication(new Gravitation(), cfg);
	}
}
