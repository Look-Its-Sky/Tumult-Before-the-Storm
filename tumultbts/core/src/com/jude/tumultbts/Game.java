package com.jude.tumultbts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.awt.geom.Area;
import java.util.ArrayList;

public class Game extends ApplicationAdapter{
	
	//Garbage Collector Friendly Variables
	private int state;
	private int cutscene, area;
	private int whereIsCursor;
	private int screenHeight, screenWidth;

	private float when; //Where the player is at during the game
	
	private boolean isMenuOpen; //Note: this refers to in game
	private boolean isInGame;
	private boolean isCharacterMade;

	private String sex;
	private String player_class;
	private String mode;

	private ArrayList<Text> temp;
	private ArrayList<int[]> bounds;
	
	//Non-garbage Collector Friendly Variables
	private SpriteBatch batch;
	
	private BitmapFont font, font2;
	
	private Texture background;
	private Texture selected_Character, pirateF, pirateM, royal_guardF, royal_guardM, samuraiF, samuraiM, spartanF, spartanM, vikingF, vikingM;
	private Texture title;

	private Texture area1;
	
	private Player p1;
	private Player p2; //Possibly

	private NPC npc1;
	private boolean isNPCInit;

	//Test Characters
	private ArrayList<Player> test;

	private Text gtemp;
	
	//Constants NOTE: i gotta change these theyre just placeholders
	private final int[] p1DefaultFightingPos = {100,100};
	private final int[] p2DefaultFightingPos = {500,500};

	private final boolean isHeightTest = false;

	/*
	 * Gamestate notes
	 * 
	 * the higher the number represents how far you have progressed in game
	 * 
	 * 
	 * cutscene notes
	 * 
	 * its a variable for cutscenes
	 * defines what cutscene is being displayed
	 * -1 = no cutscene is active 
	 * 
	 * 
	 * area notes
	 * 
	 * area defines the area your character is currently in regardless of how far you have progressed
	 * -1: character select
	 *
	 * the temp arraylist holds all of the text temporarily to get around the function scope issue
	 * this saves memory.... in theory, i hope
	*/

	//**************************************************************My Objects and Variables**************************************************************
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		font2 = new BitmapFont();
		
		background = new Texture("./misc/jungle-background.png");
		title = new Texture("./misc/title_1.png");
		pirateF = new Texture("./Pirate_Female/idle_1.png");
		pirateM = new Texture("./Pirate_Male/idle_1.png");
		royal_guardF = new Texture("./Royal_Guard_Female/idle_1.png");
		royal_guardM = new Texture("./Royal_Guard_Male/idle_1.png");
		samuraiF = new Texture("./Samurai_Female/idle_1.png");
		samuraiM = new Texture("./Samurai_Male/idle_1.png");
		spartanF = new Texture("./Spartan_Female/idle_1.png");
		spartanM = new Texture("./Spartan_Male/idle_1.png");
		vikingF = new Texture("./Viking_Female/idle_1.png");
		vikingM = new Texture("./Viking_Male/idle_1.png");
		area1 = new Texture("./backgrounds/jungle-ruins-preview.png");

		state = -3;
		cutscene = -1;
		area = 0;
		whereIsCursor = 0;

		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		
		isInGame = false;
		isMenuOpen = false;
		isCharacterMade = false;

		temp = new ArrayList<Text>(); //A arraylist for handling all text scene by scene

		if(isHeightTest)
		{
			//Height test
			char sex = 'm';

			test = new ArrayList<Player>();

			test.add(new Player(100,100, sex, "pirate"));
			test.add(new Player(100,100, sex, "royal_guard"));
			test.add(new Player(100,100, sex, "samurai"));
			test.add(new Player(100,100, sex, "spartan"));
			test.add(new Player(100,100, sex, "viking"));
		}
	}

	//**************************************************************Graphics**************************************************************
	@Override
	public void render()
	{
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		if(state > -5 && state  < 0) batch.draw(background, 0, 0, screenWidth, screenHeight);

		switch(state)
		{
			//Add splash screen
			case -5:
				break;

			//Start menu
			case -4:
				loadMenu();
				break;
		
			case -3:
				begin();
				break;
		
			//Choose gender
			case -2:
				chooseSex();
				break;
			
			//Choose class
			case -1:
				chooseClass();		
				break;

			//Load into world
			case 0:
				//loadFromSave();
				loadWorld();
				initiatePlayer(100, 100); //Change this eventually its just for testing
				break;
				
			default:
				System.out.println("somethings wrong in the state switch (render)");
				break;
		}
		
		batch.end();
		
	}

	
	//**************************************************************Clean up**************************************************************
	@Override
	public void dispose()
	{
		batch.dispose();
		title.dispose();
		background.dispose();
		font.dispose();
		font2.dispose();
		pirateF.dispose();
		pirateM.dispose();
		royal_guardF.dispose();
		royal_guardM.dispose();
		samuraiF.dispose();
		samuraiM.dispose();
		spartanF.dispose();
		spartanM.dispose();
		vikingF.dispose();
		vikingM.dispose();
	}
	
	
	//**************************************************************Game Scenes**************************************************************

	//-3 Works!
	public void begin()
	{
		if(temp.isEmpty())
		{
			temp.add(new Text("You are dreaming", screenWidth/2 - 50, screenHeight/2 + 105));
			temp.add(new Text("Press ENTER to Continue", screenWidth/2 - 75, screenHeight/2 + 75));
		}

		startFade(temp.get(0));
		fadeAndFlash(temp.get(1));

		if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
		{
			temp.clear();
			state++;
		}
	}

	//-2 Works!
	public void chooseSex()
	{
		if (temp.isEmpty())
		{
			temp.add(new Text("In this dream you are a", screenWidth/2 - 63, screenHeight/2 + 100));
			temp.add(new Text("(M)ale", screenWidth/2 - 20, screenHeight/2 + 70));
			temp.add(new Text("or", screenWidth/2 - 15, screenHeight/2 + 40));
			temp.add(new Text("(F)emale", screenWidth/2 - 20, screenHeight/2 + 10));
		}

		for(int i = 0; i < temp.size(); i++)
		{
			startFade(temp.get(i));
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.F)) sex = "f";
		if(Gdx.input.isKeyPressed(Input.Keys.M)) sex = "m";
		if(sex != null)
		{
			temp.clear();
			state++;
		}
	}
	
	//-1 Works!
	public void chooseClass()
	{

		if(temp.isEmpty())
		{
			temp.add(new Text("You dreamt yourself being a", screenWidth/2 - 75, screenHeight/2 + 100));
			temp.add(new Text("(P)irate", screenWidth/2 - 200, screenHeight/2 - 30));
			temp.add(new Text("(R)oyal Guard", screenWidth/2 - 115, screenHeight/2 - 30));
			temp.add(new Text("(S)amurai", screenWidth/2 - 10, screenHeight/2 - 30));
			temp.add(new Text("Sparta(n)", screenWidth/2 + 90, screenHeight/2 - 30));
			temp.add(new Text("(V)iking", screenWidth/2 + 190, screenHeight/2 - 30));
		}

		//Draw character select
		for(int i = 0; i < temp.size(); i++)
		{
			startFade(temp.get(i));
		}
		
		//Female characters
		if(sex == "f")
		{
			batch.draw(pirateF, screenWidth/2 - 220, screenHeight/2 - 20);
			batch.draw(royal_guardF, screenWidth/2 - 110, screenHeight/2 - 20);
			batch.draw(samuraiF, screenWidth/2 - 10, screenHeight/2 - 20);
			batch.draw(spartanF, screenWidth/2 + 70, screenHeight/2 - 20);
			batch.draw(vikingF, screenWidth/2 + 170, screenHeight/2 - 20);
		}
		
		//Male characters
		if(sex == "m")
		{
			batch.draw(pirateM, screenWidth/2 - 220, screenHeight/2 - 20);
			batch.draw(royal_guardM, screenWidth/2 - 110, screenHeight/2 - 20);
			batch.draw(samuraiM, screenWidth/2 - 25, screenHeight/2 - 20);
			batch.draw(spartanM, screenWidth/2 + 70, screenHeight/2 - 20);
			batch.draw(vikingM, screenWidth/2 + 180, screenHeight/2 - 20);
		}
		
		//Take input for class selection
		if(Gdx.input.isKeyPressed(Input.Keys.P))
		{
			player_class = "pirate";
		}
		
		else if(Gdx.input.isKeyPressed(Input.Keys.R))
		{
			player_class = "royal_guard";
		}
		
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			player_class = "samurai";
		}
		
		else if(Gdx.input.isKeyPressed(Input.Keys.N))
		{
			player_class = "spartan";
		}
		
		else if(Gdx.input.isKeyPressed(Input.Keys.V))
		{
			player_class = "viking";
		}
		
		if(player_class != null)
		{
			temp.clear();
			state++;
			area = 0;
		}
	}

	//0
	public void loadWorld()
	{
		switch(area)
		{
			//Starting area.... only thing available in the demo
			case 0:
				loadStartingArea();
				break;

			default:
				System.out.println("HEY HEY THERES NO AREA LOADED YK \n LIKE THATS A REALLY BIG ISSUE");
				break;
		}
	}

	//**************************************************************Area Rendering**************************************************************

	public void loadStartingArea()
	{
		batch.draw(area1, 0, 0, screenWidth, screenHeight);

		mode = "rpg";

		if(when == 0)
		{
			if(!isNPCInit) npc1 = new NPC()
		}

		//Set bounds
		//TODO: need boundary code for the starting area
	}

	//**************************************************************Save States**************************************************************
	
	//Strong maybe but it would be cool
	public int loadFromSave()
	{
		return 1;
	}

	public int writeToSave()
	{
		return 1;
	}
	
	//**************************************************************Text Anim**************************************************************
	
	//Note all this code below is kinda bad and should be redone -- edit: i somehow made it worse lmao

	//Some simple fade code
	public void startFade(Text text)
	{
		text.fadeIn();
		font.draw(batch, text.returnText(), text.returnX(), text.returnY());
		font.setColor(255, 255, 255, text.returnFade());
	}

	//The menu does not work yet
	public void loadMenu()
	{
		//Initial start menu
		if(state == -4)
		{
			//Background obv
			batch.draw(background, 0 ,0);

			//TODO: Title obv -- FIX THIS LATER
			//batch.draw(title, 0, 350);

			//Buttons not so obv
			//TODO: Make buttons for start menu
		}
		
		//Menu when opened anywhere else in game
		else
		{
			//TODO: Make in-game menu assets
		}
	}

	public void fadeAndFlash(Text text)
	{
		Text temp = new Text(text.returnText(), text.returnX(), text.returnY());
		temp.fadeAndFlash(text.returnText());
		font.draw(batch, temp.returnText(), temp.returnX(), temp.returnY());
	}
	
	//**************************************************************Characters**************************************************************
	
	public void initiatePlayer(int paramX, int paramY)
	{
		if(!isCharacterMade)
		{
			char temp = sex.charAt(0); //Convert the string to char cause i was dumb
			p1 = new Player(paramX, paramY, temp, player_class);
			isCharacterMade = true;
		}

			//TODO: Add Fighting mode to drawing
			p1.updatePos();
			batch.draw(p1.renderPlayer(), p1.returnX(), p1.returnY(), p1.returnW(), p1.returnH(), 0, 0, p1.returnOrigW(), p1.returnOrigH(), p1.returnIsFlipX(), false);

			//Height Test
			if(isHeightTest)
			{
				for(int i = 0; i < test.size(); i++)
				{
					batch.draw(test.get(i).renderPlayer(), test.get(i).returnX() + 50 * i, test.get(i).returnY(), test.get(i).returnW(), test.get(i).returnH());
				}
			}

			//Set Mode
			if(mode == "rpg") p1.toggleRPG();
			else if(mode ==  "fight") p1.toggleFight();
			else System.err.println("ERROR: No Valid Mode Set");

	}

	//For later use whenever I get fighting mode to work
	public void initiateNPC()
	{

	}

	//**************************************************************Misc**************************************************************
}