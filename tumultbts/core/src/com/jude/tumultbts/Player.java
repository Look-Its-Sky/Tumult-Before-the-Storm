package com.jude.tumultbts;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

public class Player extends StandardObj implements Runnable{

	private int animCounter;
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

	public void addCount()
	{
		animCounter++;
	}

	public void addCount(int num)
	{
		animCounter += num;
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

	public Texture renderPlayer()
	{
		return returnIdleAnim().get(0);
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

	@Override
	public void run()
	{
		//It works now not sure how but it does
		updatePos();
	}
}