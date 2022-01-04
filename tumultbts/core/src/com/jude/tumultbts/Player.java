package com.jude.tumultbts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends StandardObj{

	//Anim Frame Counting
	private int animCounter;
	private int animSpeed;
	private int animSpeedCount;

	//Speed of Player
	private final int speed = 2;

	//Use character to print coords
	private final boolean coord_mode = true;

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
	private boolean isFlipX;
	private boolean isJump;
	private boolean canMove;
	private int jumpCounter;

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
		x = paramX;
		y = paramY;

		animCounter = 0;
		animSpeedCount = 0;

		pClassObj = new StandardChar(gender, pClass);

		state = "idle";

		currentAnim = returnIdleAnim(); //Set default anim
		noAnimChange = true;
		isFlipX = false;
		canMove = true;

		jumpCounter = 0;

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

	//NPC Class only -- I shouldve done this a better way -- This will be redone
	public Player(int paramX, int paramY, char gender, String pClass, ArrayList<String> dialogue)
	{
		x = paramX;
		y = paramY;

		animCounter = 0;
		animSpeedCount = 0;

		pClassObj = new StandardChar(gender, pClass);

		state = "idle";
		currentAnim = returnIdleAnim(); //Set default anim
		noAnimChange = true;
		isFlipX = false;
		jumpCounter = 0;

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
				width = 85;
				height = 85;
				break;

			case "royal_guard":
				width = 75;
				height = 75;
				break;

			case "samurai":
				width = 85;
				height = 85;
				break;

			case "spartan":
				width = 85;
				height = 85;
				break;

			case "viking":
				width = 75;
				height = 75;
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

	//Returns correct texture for current animation
	public Texture renderPlayer()
	{
		int jumpHeight = 2;

		if(isJump)
		{
			jumpCounter++;

			if(jumpCounter >= jumpHeight)
			{
				isJump = false; //Stopping
				jumpCounter = 0;
			}

			if(jumpCounter >= jumpHeight/2) y-= 8; //Falling
			if(jumpCounter <= jumpHeight/2) y += 8; //Jumping
		}

		//Check previous value of state
		String prev_state = state;

		//Check if the character should be idle
		if(!isRight && !isLeft && !isUp && !isDown && canMove) state = "idle";


		switch(state)
		{
			case "idle":
				currentAnim = returnIdleAnim();
				break;

			case "run":
				currentAnim = returnRunAnim();
				break;

			case "nlight":
				currentAnim = returnAttackAnim1();
				break;

			case "slight":
				currentAnim = returnAttackAnim2();
				break;

			case "dlight":
				currentAnim = returnAttackAnim3();
				break;

			default:
				currentAnim = returnIdleAnim();
				System.err.println("Error in Rendering Player");
				break;
		}

		System.out.println(state);

		if(prev_state != state)
		{
			resetAnimCounter();
		}

		else
		{
			//Frame counting stuff
			autoResetAnimCounter();
			if(noAnimChange && shouldFrameRender()) nextFrame();
		}

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

			if(mode == "fighting")
			{
				isUp = false;
				isDown = false;
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

	//Position stuff
	public void updatePos()
	{
		int multiplier = 1;

		if(mode == "fighting") multiplier = 10;

		if(isRight)
		{
			x += speed * multiplier;
			isFlipX = true;
		}

		if(isLeft)
		{
			x -= speed * multiplier;
			isFlipX = false;
		}

		if(isDown && mode == "rpg")
		{
			y -= speed * multiplier;
		}

		if(isUp && mode == "rpg")
		{
			y += speed * multiplier;
		}
	}

	//Input stuff
	public void updateInput()
	{
		Gdx.input.setInputProcessor(new InputAdapter()
		{
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

				if(keycode == Input.Keys.J)
				{
					isLightAttack = true;
				}

				if(keycode == Input.Keys.K)
				{
					isHeavyAttack = true;
				}

				if(keycode == Input.Keys.SPACE && mode == "fighting" && !isJump && false) //UH kinda disabled this because its borked but it makes my game unique🥲 ig... lmao jp itll be fixed
				{
					isJump = true;
				}

				//Set current input
				if(isUp) temp_curr[0] = "up";
				if(isDown) temp_curr[0] = "down";
				if(isRight) temp_curr[1] = "right";
				if(isLeft) temp_curr[1] = "left";

				//Check if the animation shouldnt be changed
				if(temp_curr[0] == temp_prev[0] && temp_curr[1] == temp_prev[1]) noAnimChange = true;

				//Check if the character is running
				if((temp_curr[0] == "up" || temp_curr[0] == "down" || temp_curr[1] == "right" || temp_curr[1] == "left") && canMove) state = "run";

				//Attacks
				if(mode == "fighting")
				{
					if(isDown && isLightAttack && canMove)
					{
						state = "dlight";
						canMove = false;
					}

					if(isLightAttack && !isDown && !isRight && !isLeft && canMove)
					{
						state = "nlight";
						canMove = false;
					}

					if((isLeft || isRight) && isLightAttack && canMove)
					{
						state = "slight";
						canMove = false;
					}
				}

				return true;
			}

			@Override
			public boolean keyUp(int keycode)
			{
				if(keycode == Input.Keys.A) isLeft = false;
				if(keycode == Input.Keys.S) isDown = false;
				if(keycode == Input.Keys.D) isRight = false;
				if(keycode == Input.Keys.W) isUp = false;

				if(keycode == Input.Keys.J) isLightAttack = false;
				if(keycode == Input.Keys.K) isHeavyAttack = false;

				if(keycode == Input.Keys.SPACE && coord_mode) System.out.println("\nX: " + x + "\nY: " + y);

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
				if(animSpeedCount % 20 == 0)
				{
					if (isDebug) System.out.println(animSpeedCount);
					return true;
				}

				else return false;

			case "run":
				if(animSpeedCount %10 == 0)
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

	//Returns original dimensions of the images
	public int returnOrigH()
	{
		return currentAnim.get(animCounter).getHeight();
	}

	public int returnOrigW()
	{
		return currentAnim.get(animCounter).getWidth();
	}

	public boolean returnIsFlipX()
	{
		return isFlipX;
	}
}