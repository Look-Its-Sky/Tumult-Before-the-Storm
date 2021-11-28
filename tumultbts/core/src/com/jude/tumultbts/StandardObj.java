package com.jude.tumultbts;

public class StandardObj {

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
}