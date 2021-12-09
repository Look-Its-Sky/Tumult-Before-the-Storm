package com.jude.tumultbts.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jude.tumultbts.Game;

public class DesktopLauncher {

	//only supports my school laptop resolution for now

	/*
	 private static final int width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
	 private static final int length = LwjglApplicationConfiguration.getDesktopDisplayMode().length;
	 */
	private static final int width = 1280;
	private static final int length = 720;

	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//Window settings
		config.title = "Tumult: Before the Storm";
		config.resizable = false;
		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.fullscreen = true;
		config.foregroundFPS = 60;

		new LwjglApplication(new Game(), config);
	}
}
