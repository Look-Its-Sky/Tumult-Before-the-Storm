package com.jude.tumultbts.desktop;

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
		new LwjglApplication(new Game(), config);
		
		//Window settings
		config.title = "Tumult: Before the Storm";
		config.resizable = false;
	}
}
