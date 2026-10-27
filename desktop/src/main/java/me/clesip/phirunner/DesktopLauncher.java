package me.clesip.phirunner;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.clesip.phirunner.PhiRunner;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(PhiRunner.WIDTH, PhiRunner.HEIGHT);
		config.setForegroundFPS(800);
		config.setTitle("Phigros Runner");
		new Lwjgl3Application(new PhiRunner(), config);
	}
}
