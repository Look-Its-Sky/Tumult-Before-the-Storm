package com.jude.tumultbts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/*
NOTE:
THIS CLASS IS SO BLOATED AND TERRIBLY DONE
IT SHOULD BE REWRITTEN
I ABSOLUTELY HATE IT
ILL WRITE MY OWN UNIVERSAL KEYBOARD CLASS ONE DAY
 */

public class Keyboard extends Thread
{
	private int[] currentKeys;
	private String currentInput;
	private String mode;

	//Constants
	private final int UP = Input.Keys.W;
	private final int LEFT = Input.Keys.A;
	private final int DOWN = Input.Keys.S;
	private final int RIGHT = Input.Keys.D;
	private final int j = Input.Keys.J;
	private final int k = Input.Keys.K;

	private final String nlight = String.valueOf(j);
	private final String slightl = String.valueOf(LEFT + " " + j);
	private final String slightr = String.valueOf(RIGHT + " " + j);
	private final String dlight = String.valueOf(DOWN + " " + j);

	private final String nheavy = String.valueOf(k);
	private final String sheavyl = String.valueOf(LEFT + " " + k);
	private final String sheavyr = String.valueOf(RIGHT + " " + k);
 	private final String dheavy = String.valueOf(DOWN + " " + k);

	public void toggleRPG()
	{
		mode = "rpg";
	}

	public void toggleFight()
	{
		mode = "fighting";
	}

	public Keyboard()
	{
		currentKeys = new int[1];
		currentInput = "";
	}

	public String returnCurrentInput()
	{
		return currentInput;
	}

	//Just so my thread looks cleaner im messing around with it a lot
	private void checkForInput()
	{
		if (mode == "fight")
		{
			//Handle fighting inputs and combos
            //TODO: inputs/combos
		}

		if (mode == "rpg")
		{
			if (Gdx.input.isKeyPressed(Input.Keys.A))
			{
				currentInput = "left";
			}

			if (Gdx.input.isKeyPressed(Input.Keys.D))
			{
				currentInput = "right";
			}

			if (Gdx.input.isKeyPressed(Input.Keys.W))
			{
				currentInput = "up";
			}

			if (Gdx.input.isKeyPressed(Input.Keys.S))
			{
				currentInput = "down";
			}
		}
	}


	public void run()
	{
		checkForInput();
	}
}