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

	private int writer;

	//Attacks and key stuff
	//Represented as ASCII int
	public enum keys
	{
		LEFT(Input.Keys.A),
		RIGHT(Input.Keys.D),
		UP(Input.Keys.W),
		DOWN(Input.Keys.S),
		SPACE(Input.Keys.SPACE),
		j(Input.Keys.J),
		k(Input.Keys.K);


		private final int label;

		private keys(int label)
		{
			this.label = label;
		}

		//This should be the same concept as toString() in atk enum
		public int toInt()
		{
			switch(this)
			{
				case LEFT:
					return Input.Keys.A;

				case RIGHT:
					return Input.Keys.D;

				case UP:
					return Input.Keys.W;

				case DOWN:
					return Input.Keys.S;

				default:
					return 999; //rip juice- its also an error so something fucked up
			}
		}

	}




	//Translates ASCII ints into actual attacks in the form of Strings
	public enum atk
	{
		nlight(String.valueOf(keys.j)),
		slightl(String.valueOf(keys.LEFT) + " " + String.valueOf(keys.j)),
		slightr(String.valueOf(keys.LEFT) + " " + String.valueOf(keys.j)),
		dlight(String.valueOf(keys.DOWN) + " " + String.valueOf(keys.j)),

		nheavy(String.valueOf(keys.k)),
		sheavyl(String.valueOf(keys.LEFT) + " " + String.valueOf(keys.k)),
		sheavyr(String.valueOf(keys.LEFT) + " " + String.valueOf(keys.k)),
		dheavy(String.valueOf(keys.DOWN) + " " + String.valueOf(keys.k));

		private final String label;


		private atk(String label)
		{
			this.label = label;
		}



		//Checks what enum itself is and returns a string to accurately represent itself
		public String toString()
		{

			switch(this)
			{
				case nlight:
					return String.valueOf(keys.j);

				case slightl:

				case slightr:
					System.out.println("slight");
					return String.valueOf(keys.LEFT) + " " + String.valueOf(keys.j);

				case dlight:
					return String.valueOf(keys.DOWN) + " " + String.valueOf(keys.j);

				case nheavy:
					return String.valueOf(keys.k);

				case sheavyl:

				case sheavyr:
					return String.valueOf(keys.LEFT) + " " + String.valueOf(keys.k);

				case dheavy:
					return String.valueOf(keys.DOWN) + " " + String.valueOf(keys.k);

				default:
					return "";

			}
		}
	}


	public void toggleRPG()
	{
		mode = "rpg";
	}

	public void toggleFight()
	{
		mode = "fighting";
	}

	//Misc stuff also represented as strings
	public enum misc
	{
		jump(String.valueOf(keys.SPACE));

		private final String label;

		private misc(String label)
		{
			this.label = label;
		}
	}



	public Keyboard()
	{
		currentKeys = new int[1];
		currentInput = "";
		writer = 0;
	}

	//For fighting i hope
	private void handleInput()
	{
		if(writer + 1 > 1) writer = 0;

		if(Gdx.input.isKeyPressed(keys.LEFT.toInt()))
		{
			currentKeys[writer] = keys.LEFT.toInt();
			writer++;
		}

		else if(Gdx.input.isKeyPressed(keys.RIGHT.toInt()))
		{
			currentKeys[writer] = keys.RIGHT.toInt();
		}
	}

	public String returnCurrentInput()
	{
		System.out.println(currentInput);
		return currentInput;
	}

	//Makeshift until i get this thread running again

	public void run()
	{
		if(mode == "fight")
		{
			handleInput();
		}

		if(mode == "rpg")
		{

			if(Gdx.input.isKeyPressed(Input.Keys.A))
			{
				currentInput = "left";
			}

			if(Gdx.input.isKeyPressed(Input.Keys.D))
			{
				currentInput = "right";
			}

			if(Gdx.input.isKeyPressed(Input.Keys.W))
			{
				currentInput = "up";
			}

			if(Gdx.input.isKeyPressed(Input.Keys.S))
			{
				currentInput = "down";
			}
		}

	}
}