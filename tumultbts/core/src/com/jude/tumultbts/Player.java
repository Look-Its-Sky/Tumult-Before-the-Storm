package com.jude.tumultbts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

public class Player extends StandardObj implements InputProcessor{

	//Anim Frame Counting
	private int animCounter;
	private int animSpeed;
	private int animSpeedCount;

	//Speed of Player
	private final int speed = 2;

	//Debug mode
	private final boolean isDebug = false;

	//Player actions
	private boolean isVisible;
	private boolean isUp;
	private boolean isDown;
	private boolean isLeft;
	private boolean isRight;
	private boolean isNoDirection;
	private boolean isLightAttack;
	private boolean isHeavyAttack;
	private boolean isDodge;

	//Deprecated - what is the player doing
	private String state;

	//Determines whether or not if the player is in RPG or fighting mode
	private String mode;

	//What animation should be playing
	private ArrayList<Texture> currentAnim;

	//Input stuff

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

		state = "idle";
		currentAnim = returnIdleAnim(); //Set default anim

		//Get input
		updateInput();

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

	//Manually reset anim
	public void resetAnimCounter()
	{
		animCounter = 0;
		if(isDebug) System.out.println("Anim Reset");
	}

	//What is the animcounter on
	public int returnAnimCounter()
	{
		return animCounter;
	}

	//Go to next frame
	private void nextFrame()
	{
		animCounter++;
	}

	//Go back a frame
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

	//Deprecated - Set the state of the player
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

	//Returns correct texture for current animation -- doesnt work yet
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

	//Mode setting
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

	//Position stuff
	public void updatePos()
	{
		if(isRight)
		{
			x += speed;
		}

		if(isLeft)
		{
			x -= speed;
		}

		if(isDown)
		{
			y -= speed;
		}

		if(isUp)
		{
			y += speed;
		}
	}

	//Input stuff
	public void updateInput()
	{
		//First Attempt
		/*
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

		//Second Attempt
		/*
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
		 */

		//Third attempt
		/*
		Gdx.input.setInputProcessor(new InputAdapter()
		{
			@Override
			public boolean keyTyped(char key) {
				if(key == 'w')
				{
					isUp = true;
					isDown = false;
				}

				if(key == 'a')
				{
					isLeft = true;
					isRight = false;
				}

				if(key == 'd')
				{
					isRight = true;
					isLeft = false;
				}

				if(key == 's')
				{
					isDown = true;
					isUp = false;
				}

				return true;
			}

			@Override
			public boolean keyUp(char key)
			{
				if (key == 'a') isLeft = false;
				if (key == 's') isDown = false;
				if (key == 'd') isRight = false;
				if (key == 'w') isUp = false;

				return true;
			}
		});

		 */
	}


	//Helps to ease the speed of the frames switching
	private boolean shouldFrameRender()
	{
		//So we dont overrun the int limit and crash which would be very bad
		if (animSpeedCount + 1 > 2147483647) animSpeedCount = 0;
		animSpeedCount++;

		if (animSpeedCount % animSpeed == 1)
		{
			if (isDebug) System.out.println(animSpeedCount);
			return true;
		}

		else
		{
			return false;
		}
	}


	@Override
	public boolean keyDown(int keycode)
	{
		if(keycode == Input.Keys.W)
		{
			isUp = true;
			isDown = false;
		}

		if(keycode == Input.Keys.A)
		{
			isLeft = true;
			isRight = false;
		}

		if(keycode == Input.Keys.D)
		{
			isRight = true;
			isLeft = false;
		}

		if(keycode == Input.Keys.S)
		{
			isDown = true;
			isUp = false;
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (keycode == Input.Keys.A) isLeft = false;
		if (keycode == Input.Keys.S) isDown = false;
		if (keycode == Input.Keys.D) isRight = false;
		if (keycode == Input.Keys.W) isUp = false;

		return true;
	}

	@Override
	public boolean keyTyped(char key)
	{
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		return false;
	}
}