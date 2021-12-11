package com.jude.tumultbts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/*
NOTE:
THIS CLASS IS SO BLOATED AND TERRIBLY DONE
IT SHOULD BE REWRITTEN
I ABSOLUTELY HATE IT
ILL WRITE MY OWN UNIVERSAL KEYBOARD CLASS ONE DAY
 */

public class Keyboard
{
	private String currentInput;

	//Keyboard Constants
	private final int UP = Input.Keys.W;
	private final int LEFT = Input.Keys.A;
	private final int DOWN = Input.Keys.S;
	private final int RIGHT = Input.Keys.D;
	private final int j = Input.Keys.J;
	private final int k = Input.Keys.K;

	//Light Attacks
	private final String nlight = String.valueOf(j);
	private final String slightl = String.valueOf(LEFT + " " + j);
	private final String slightr = String.valueOf(RIGHT + " " + j);
	private final String dlight = String.valueOf(DOWN + " " + j);

	//Heavy Attacks
	private final String nheavy = String.valueOf(k);
	private final String sheavyl = String.valueOf(LEFT + " " + k);
	private final String sheavyr = String.valueOf(RIGHT + " " + k);
	private final String dheavy = String.valueOf(DOWN + " " + k);

	public Keyboard()
	{
		currentInput = "";
	}

	public String returnCurrentInput()
	{
		return currentInput;
	}

	//Just so my thread looks cleaner im messing around with it a lot
	@Deprecated
	public String checkForInput(String mode)
	{
		currentInput = "";

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
				return "left";
			}

			if (Gdx.input.isKeyPressed(Input.Keys.D))
			{
				currentInput = "right";
				return "right";
			}

			if (Gdx.input.isKeyPressed(Input.Keys.W))
			{
				currentInput = "up";
				return "up";
			}

			if (Gdx.input.isKeyPressed(Input.Keys.S))
			{
				currentInput = "down";
				return "down";
			}
		}

		return "";
	}

	public String returnInput()
	{
		return currentInput;
	}
}