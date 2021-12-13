package com.jude.tumultbts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import com.badlogic.gdx.InputAdapter;

public class Player extends StandardObj{

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

	//What is the player doing
	private String state;
	private String[] temp_curr, temp_prev;

	//Determines whether or not if the player is in RPG or fighting mode
	private String mode;

	//What animation should be playing
	private ArrayList<Texture> currentAnim;
	private boolean noAnimChange;

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
		noAnimChange = true;

		//Get input
		updateInput();

		//Set States
		setStats(pClass);

		/*
		Temp Movement
		Verticle inputs are always the first slot
		Horizontal inputs are always the second slot
		*/
		temp_prev = new String[2];
		temp_curr = new String[2];
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

	//Stats
	private void setStats(String pClass)
	{
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

	//Returns correct texture for current animation -- doesnt work yet
	public Texture renderPlayer()
	{
		//Check if the character should be idle
		if(!isRight && !isLeft && !isUp && !isDown) state = "idle";

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

		//Frame counting stuff
		autoResetAnimCounter();
		if(noAnimChange && shouldFrameRender()) nextFrame();

		return currentAnim.get(animCounter);
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

		//Third attempt - Works
		Gdx.input.setInputProcessor(new InputAdapter()
		{
			//First Attempt of the third attempt
			/*
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

			//Second Attempt of the Third Attempt
			@Override
			public boolean keyDown(int keycode)
			{
				//Set previous inputs

				if(isUp) temp_prev[0] = "up";
				else if(isDown) temp_prev[0] = "down";

				if(isRight) temp_prev[1] = "right";
				else if(isLeft) temp_prev[1] = "left";

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

				//Set current input
				if(isUp) temp_curr[0] = "up";
				if(isDown) temp_curr[0] = "down";
				if(isRight) temp_curr[1] = "right";
				if(isLeft) temp_curr[1] = "left";

				//Check if the animation shouldnt be changed
				if(temp_curr[0] == temp_prev[0] && temp_curr[1] == temp_prev[1]) noAnimChange = true;

				//Check if the character is running
				if(temp_curr[0] == "up" || temp_curr[0] == "down" || temp_curr[1] == "right" || temp_curr[1] == "left") state = "run";

				return true;
			}

			@Override
			public boolean keyUp(int keycode)
			{
				if (keycode == Input.Keys.A)
				{
					isLeft = false;
				}

				if (keycode == Input.Keys.S)
				{
					isDown = false;
				}

				if (keycode == Input.Keys.D)
				{
					isRight = false;
				}

				if (keycode == Input.Keys.W)
				{
					isUp = false;
				}

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
		});
	}


	//Helps to ease the speed of the frames switching
	private boolean shouldFrameRender()
	{
		//So we dont overrun the int limit and crash which would be very bad
		if (animSpeedCount + 1 > 2147483647) animSpeedCount = 0;
		animSpeedCount++;

		switch(state)
		{
			case "idle":
				if(animSpeedCount % animSpeed == 0)
				{
					if (isDebug) System.out.println(animSpeedCount);
					return true;
				}

				else return false;

			case "run":
				if(animSpeedCount % animSpeed/4 == 0)
				{
					if (isDebug) System.out.println(animSpeedCount);
					return true;
				}

				else return false;

			default:
				if(isDebug) System.err.println("Error in Determining Frame");
				return false;
		}
	}

}