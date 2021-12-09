package com.jude.tumultbts;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.*;

public class Player extends StandardObj implements InputProcessor{

	private int animCounter;
	private int animSpeed;
	private int animSpeedCount;

	private final int speed = 2;

	private final boolean isDebug = false;

	private boolean isVisible;
	private boolean isUp;
	private boolean isDown;
	private boolean isLeft;
	private boolean isRight;

	private Keyboard keyboard;

	private String state;
	private String mode;

	private Thread t;

	private ArrayList<Texture> currentAnim;

	public Player(int paramX, int paramY, char gender, String pClass)
	{
		width = 50;
		height = 50;

		x = paramX;
		y = paramY;

		animCounter = 0;
		animSpeedCount = 0;
		animSpeed = 20;

		pClassObj = new StandardChar(gender, pClass);
		keyboard = new Keyboard();

		state = "idle";
		currentAnim = returnIdleAnim(); //Set default anim

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
		if(isDebug) System.out.println("Anim Reset");
	}

	public int returnAnimCounter()
	{
		return animCounter;
	}

	private void nextFrame()
	{
		animCounter++;
	}

	private void prevFrame()
	{
		animCounter--;
	}

	//Reset Counter if there are no more frames to be rendered
	private void autoResetAnimCounter()
	{
		if(animCounter + 1 >= currentAnim.size())
		{
			if(isDebug) System.out.println("Anim Reset");
			animCounter = 0;
		}
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

	//Returns correct texture for current animation
	public Texture renderPlayer()
	{
		switch(state)
		{
			case "idle":
				currentAnim = returnIdleAnim();
				break;

			case "run":
				currentAnim = returnRunAnim();
				break;

			default:
				currentAnim = returnIdleAnim();
				System.err.println("Error in Rendering Player");
				break;
		}

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
		/*
		First attempt
		switch (keyboard.checkForInput(mode))
		{
			case "left":
				state = "run";
				x -= speed;
				break;

			case "right":
				state = "run";
				x += speed;
				break;

			case "up":
				if (mode == "rpg")
				{
					state = "run";
					y += speed;
				}
				break;

			case "down":
				if (mode == "rpg")
				{
					state = "run";
					y -= speed;
				}
				break;

			default:
				state = "idle";
				break;
		}
		*/

		//Second attempt

		isDown = false;
		isUp = false;
		isLeft = false;
		isRight = false;

		//Check to see inputs
		if(keyboard.checkForInput(mode) == "left")
		{
			state = "run";
			isLeft = true;
			isRight = false;
		}

		if(keyboard.checkForInput(mode) == "right")
		{
			state = "run";
			isRight = true;
			isLeft = false;
		}

		if(keyboard.checkForInput(mode) == "up")
		{
			state = "run";
			isUp = true;
			isDown = false;
		}

		if(keyboard.checkForInput(mode) == "down")
		{
			state = "run";
			isDown = true;
			isUp = false;
		}

		if(keyboard.checkForInput(mode) == "")
		{
			state = "idle	";
			isDown = false;
			isUp = false;
			isLeft = false;
			isRight = false;
		}

		//Actually implementing the actions
		if(isUp) y += speed;
		if(isDown) y -= speed;
		if(isLeft) x -= speed;
		if(isRight) x += speed;
	}

	//Helps to ease the speed of the frames switching
	private boolean shouldFrameRender()
	{
		//So we dont overrun the int limit and crash which would be very bad
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




}