package com.jude.tumultbts.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jude.tumultbts.Game;

public class DesktopLauncher {
	
	private static final int width = 500;
	private static final int height = 500;
	
	public static void main (String[] arg)
	{
		//YES FINALLY IT WORKS.... imma have to bring my laptop to school to present
		
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
