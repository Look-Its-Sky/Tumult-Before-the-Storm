package com.jude.tumultbts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class StandardObj {

	protected String playerClass;

	protected int hp, def, atk, dex, spd;
	protected int x, y;
	protected int width, height;

	protected StandardChar pClassObj;

	//**************************************************************Position**************************************************************
	
	public void changePos(int paramX, int paramY)
	{
		x += paramX;
		y += paramY;
	}
	
	public int returnX()
	{
		return x;
	}
	
	public int returnY()
	{
		return y;
	}

	public void Y(int y)
	{
		this.y = y;
	}

	public void X(int x)
	{
		this.x = x;
	}

	public void pos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//**************************************************************Dimensions**************************************************************
	
	public int returnH()
	{
		return height;
	}
	
	public int returnW()
	{
		return width;
	}
	
	//**************************************************************Player Class**************************************************************
	
	public void setClass(String c)
	{
		playerClass = c;
	}

	//**************************************************************Misc**************************************************************
}