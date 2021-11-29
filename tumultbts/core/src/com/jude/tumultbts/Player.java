package com.jude.tumultbts;

import com.badlogic.gdx.graphics.Texture;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player extends StandardObj implements Runnable{

	private int animCounter;
	private Keyboard input;
	private String state;
	private String mode;
	private Thread t;
	private String currentInput;

	public Player(int paramX, int paramY, char gender, String pClass)
	{
		width = 50;
		height = 50;

		x = paramX;
		y = paramY;

		animCounter = 0;

		input = new Keyboard();
		t = new Thread(input);
		pClassObj = new StandardChar(gender, pClass);

		state = "idle";


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

	public void setState(String s)
	{
		//Error checking here potentially - its not needed unless ur dumb - watch me break it - update: i broke it -- UPDATE to update: i fixed it
		state = s;
	}

	public Texture renderPlayer()
	{
		switch(input.returnCurrentInput())
		{
			case "left":

			case "right":
				state = "run";

			default:
				break;
		}

		switch(state)
		{
			case "idle":
				return pClassObj.returnIdleAnim().get(animCounter);

			case "hit":
				return pClassObj.returnHitAnim().get(animCounter);

			case "run":
				return pClassObj.returnRunAnim().get(animCounter);

			case "dash":
				return pClassObj.returnDashAnim().get(animCounter);

			case "AttackAnim1":
				return pClassObj.returnAttackAnim1().get(animCounter);

			case "AttackAnim2":
				return pClassObj.returnAttackAnim2().get(animCounter);

			case "AttackAnim3":
				return pClassObj.returnAttackAnim3().get(animCounter);

			default:
				System.err.println("ERROR: No Animation Loaded");
				return new Texture(""); //Return no image in case of the developer screwing up
		}


	}

	//Should be fine for a fixed number of players and npcs
	public void enableInput()
	{
		t.start();
	}

	public void disableInput()
	{
		t.stop();
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
		input.toggleRPG();
	}

	public void toggleFight()
	{
		mode = "fight";
		input.toggleFight();
	}

	//My special thread for inputs hopefully my idea doesnt go to garbage
	//NOTE: this should handle anims only keyboard handles inputs
	//UPDATE: it went to garbage ill fix it later for now i implemented a work around
	//TODO: MAYBE add support for this thread to handle animations
	//NOTE: this could use more resources than I intend to
	public void run()
	{

	}
}