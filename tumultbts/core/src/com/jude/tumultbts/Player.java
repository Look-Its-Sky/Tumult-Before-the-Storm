package com.jude.tumultbts;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

public class Player extends StandardObj implements Runnable{

	private int animCounter;
	private int animSpeed;
	private int animSpeedCount;

	private final boolean isDebug = false;

	private Keyboard keyboard;

	private String state;
	private String mode;

	private Thread t;

	public Player(int paramX, int paramY, char gender, String pClass)
	{
		width = 50;
		height = 50;

		x = paramX;
		y = paramY;

		animCounter = 0;
		animSpeedCount = 0;
		animSpeed = 25;

		pClassObj = new StandardChar(gender, pClass);
		keyboard = new Keyboard();

		state = "idle";

		t = new Thread(this);
		t.start();

		//TODO: Set stats
		switch(pClass)
		{
			case "pirate":
				break;

			case "royal_guard":
				break;

			case "samurai":
				break;

			case "spartan":
				break;

			case "viking":
				break;
		}
	}


	//I could have made this 100x more efficient i swear im learning from my mistakes lmao

	public ArrayList<Texture> returnIdleAnim()
	{
		return pClassObj.returnIdleAnim();
	}

	public ArrayList<Texture> returnRunAnim()
	{
		return pClassObj.returnRunAnim();
	}

	public ArrayList<Texture> returnAttackAnim1()
	{
		return pClassObj.returnAttackAnim1();
	}

	public ArrayList<Texture> returnAttackAnim2()
	{
		return pClassObj.returnAttackAnim2();
	}

	public ArrayList<Texture> returnAttackAnim3()
	{
		return pClassObj.returnAttackAnim3();
	}

	public ArrayList<Texture> returnDashAnim()
	{
		return pClassObj.returnDashAnim();
	}

	public ArrayList<Texture> returnHitAnim()
	{
		return pClassObj.returnHitAnim();
	}


	//Everything related to counting frames

	public void resetAnimCounter()
	{
		animCounter = 0;
	}

	public int returnAnimCounter()
	{
		return animCounter;
	}

	//Set the state of the player
	public void setState(String s)
	{
		switch(s)
		{
			case "sheavy":

			case "dheavy":

			case "nheavy":

			case "slight":
			
			case "nlight":
			
			case "dlight":
			
			case "idle":
				state = s;
				break;
				
			default:
				System.err.println("ERROR: Defining State");
		}
	}

	//Returns correct texture for idle animation
	public Texture renderPlayer()
	{
		if(!shouldFrameRender()) return returnIdleAnim().get(animCounter);

		if(animCounter + 1 >= returnIdleAnim().size())
		{
			if(isDebug) System.out.println("Anim Reset");
			animCounter = 0;
		}

		animCounter++;
		return returnIdleAnim().get(animCounter);
	}

	//Should be fine for a fixed number of players and npcs
	public void enableInput()
	{
		if(t == null || !t.isAlive()) t.start();
	}

	public void disableInput()
	{
		if(t.isAlive()) t.stop();
	}

	public void setMode(String m)
	{
		if(m != "fighting" || m != "rpg")
		{
			System.err.println("Error Setting Mode: Invalid mode");
		}

		else
		{
			mode = m;
		}
	}

	public void toggleRPG()
	{
		mode = "rpg";
	}

	public void toggleFight()
	{
		mode = "fight";
	}

	public void updatePos()
	{
		switch (keyboard.checkForInput(mode))
		{
			case "left":
				x -= 5;
				break;

			case "right":
				x += 5;
				break;

			case "up":
				if (mode == "rpg") y += 5;
				break;

			case "down":
				if (mode == "rpg") y -= 5;
				break;

			default:
				break;
		}
	}

	//Helps to ease the speed how the frames switching
	private boolean shouldFrameRender()
	{
		//So we dont over run the int limit and crash which would be very bad
		if(animSpeedCount + 1 > 2147483647) animSpeedCount = 0;

		animSpeedCount++;

		if(animSpeedCount %animSpeed == 1)
		{
			if(isDebug) System.out.println(animSpeedCount);
			return true;
		}

		else
		{
			return false;
		}

	}

	@Override
	public void run()
	{
		//It works now not sure how but it does
		updatePos();
	}
}