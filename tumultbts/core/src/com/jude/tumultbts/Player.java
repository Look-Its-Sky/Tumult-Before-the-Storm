package com.jude.tumultbts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import com.badlogic.gdx.InputAdapter;

public class Player extends StandardObj{

	//Anim Frame Counting
	private int animCounter;
	private int animTimer;
	private int currentRecFrames;

	//Speed of Player
	private int speed = 2;

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
	private boolean isLightAttack;
	private boolean isHeavyAttack;
	private boolean isDodge;
	private boolean isFlipX;
	private boolean isJump;

	private boolean doIlisten;

	//What is the player doing
	private String state;
	private String prev_state;
	private String[] temp_curr, temp_prev;

	//Determines whether or not if the player is in RPG or fighting mode
	private String mode;

	//What animation should be playing
	private ArrayList<Texture> currentAnim;

	public Player(int paramX, int paramY, char gender, String pClass)
	{
		doIlisten = true;

		x = paramX;
		y = paramY;

		animCounter = 0;
		animTimer = 0;
		currentRecFrames = 0;

		pClassObj = new StandardChar(gender, pClass);

		state = "idle";

		currentAnim = returnIdleAnim(); //Set default anim
		isFlipX = false;

		//Get input
		//updateInput();

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
		doIlisten = false;

		x = paramX;
		y = paramY;

		animCounter = 0;
		animTimer = 0;

		pClassObj = new StandardChar(gender, pClass);

		state = "idle";
		currentAnim = returnIdleAnim(); //Set default anim
		isFlipX = false;

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
		if(doIlisten) updateInputMakeShift();

		if(currentRecFrames <= 0)
		{
			int ori;

			if(isFlipX) ori = 1;
			else ori = -1;

			switch(state) {
				case "idle":
					currentAnim = returnIdleAnim();
					speed = 2;
					break;

				case "run":
					currentAnim = returnRunAnim();
					speed = 2;
					break;

				case "nlight":
					currentAnim = returnAttackAnim1();
					currentRecFrames = 10;
					speed = 1;
					break;

				case "slight":
					currentAnim = returnAttackAnim2();
					currentRecFrames = 10;

					x += speed * 40 * ori;
					speed = 1;

					break;

				case "dlight":
					currentAnim = returnAttackAnim3();
					currentRecFrames = 10;
					speed = 1;
					break;

				case "nheavy":
					currentAnim = returnAttackAnim1();

				default:
					currentAnim = returnIdleAnim();
					System.err.println("Error in Rendering Player");
					break;
			}
		}

		//Frame counting stuff

		if(currentRecFrames >= 0) currentRecFrames --;

		//TODO: FIX COMPLETE ANIMATIONS

		if(state  == "idle" || state == "run")
		{
			autoResetAnimCounter();
			if(shouldFrameRender() && currentRecFrames <= 0) nextFrame();

			return currentAnim.get(animCounter);
		}

		else
		{
			return currentAnim.get(currentAnim.size() - 2);
		}
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
		/*
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

				//Check if the character is running
				if(mode == "rpg" && (temp_curr[0] == "up" || temp_curr[0] == "down" || temp_curr[1] == "right" || temp_curr[1] == "left"))
				{
					state = "run";
				}

				else if(mode == "fighting" && (temp_curr[1] == "right" || temp_curr[1] == "left"))
				{
					state = "run";
				}


				//Attacks
				if(mode == "fighting")
				{
					if(isDown && isLightAttack)
					{
						state = "dlight";
					}

					if(isLightAttack && !isDown && !isRight && !isLeft)
					{
						state = "nlight";
					}

					if((isLeft || isRight) && isLightAttack)
					{
						state = "slight";
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

		 */
	}

	private void updateInputMakeShift()
	{
		prev_state = state;

		//Check if the character should be idle
		if(!isRight && !isLeft && !isUp && !isDown && !isLightAttack && !isHeavyAttack) state = "idle";

		//Movement

		if(Gdx.input.isKeyPressed(Input.Keys.A)) isLeft = true;
		else isLeft = false;

		if(Gdx.input.isKeyPressed(Input.Keys.D)) isRight = true;
		else isRight = false;

		if(Gdx.input.isKeyPressed(Input.Keys.W)) isUp = true;
		else isUp = false;

		if(Gdx.input.isKeyPressed(Input.Keys.S)) isDown = true;
		else isDown = false;

		//Attacks

		if(Gdx.input.isKeyPressed(Input.Keys.J)) isLightAttack = true;
		else isLightAttack = false;

		if(Gdx.input.isKeyPressed(Input.Keys.K)) isHeavyAttack = true;
		else isHeavyAttack = false;

		//Determine state

		//When you didnt pay attention in principals of comp sci and dont know how advanced boolean equations work :|
		if(isLeft || isRight || isUp || isDown)
		{
			if(!isLightAttack && !isHeavyAttack && mode == "rpg" && currentRecFrames <= 0) state = "run";
		}

		if(isLeft || isRight)
		{
			if(mode == "fighting" && !isLightAttack && !isHeavyAttack && currentRecFrames <= 0) state = "run";
		}

		if(!isLeft && !isRight && !isUp && !isDown && !isLightAttack && !isHeavyAttack && mode == "fighting" && currentRecFrames <= 0)
		{
			state = "idle";
		}

		if((isRight || isLeft) && isLightAttack && !isHeavyAttack && !isDown && mode == "fighting" && currentRecFrames <= 0)
		{
			state = "slight";
		}


		if(!isRight && !isLeft && !isHeavyAttack && !isDown && isLightAttack && mode == "fighting" && currentRecFrames <= 0)
		{
			state = "nlight";
		}

		if(isDown && isLightAttack && !isHeavyAttack && !isUp && !isRight && !isLeft && mode == "fighting" && currentRecFrames <= 0)
		{
			state = "dlight";
		}

		//WHOOOO HEAVY ATTACKS

		if(!isUp && !isDown && !isLeft && !isRight && isHeavyAttack && !isLightAttack)
		{
			state = "nheavy";
		}

		if(!isUp && !isDown & !isLightAttack && isHeavyAttack)
		{
			if(isRight || isLeft) state = "sheavy";
		}

		if(!isUp && !isLeft && !isRight && !isLightAttack && isHeavyAttack && isDown)
		{
			state = "dheavy";
		}
	}

	//Should the character be locked until anim is done
	private boolean isLock()
	{
		if(state != "idle" || state != "run")
		{
			if(animCounter >= currentAnim.size() && mode == "fighting")
			{
				animCounter = 0;

				return false;
			}

			else return true;
		}

		else return false;
	}

	//Helps to ease the speed of the frames switching
	private boolean shouldFrameRender()
	{
		animTimer++;

		switch(state)
		{
			case "idle":

				if(animTimer >= 20)
				{
					animTimer = 0;
					return true;
				}

				else return false;

			case "run":

				if(animTimer >= 10)
				{
					animTimer = 0;
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
		return currentAnim.get(0).getHeight();
	}

	public int returnOrigW()
	{
		return currentAnim.get(0).getWidth();
	}

	public boolean returnIsFlipX()
	{
		return isFlipX;
	}
}