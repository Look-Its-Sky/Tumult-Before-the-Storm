package com.jude.tumultbts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
	
	//Garbage Collector Friendly Variables
	private int state;
	private int cutscene, area;
	private int whereIsCursor;
	
	private boolean isMenuOpen; //Note: this refers to in game
	private boolean isInGame;
	private boolean isCharacterMade;

	private String sex;
	private String player_class;

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
	private Player npc;

	private Text gtemp;

	private Thread t1;
	
	//Constants NOTE: i gotta change these theyre just placeholders
	private final int[] p1DefaultFightingPos = {100,100};
	private final int[] p2DefaultFightingPos = {500,500};

	/*
	Fade isnt saving because the objects go out of scope before fade can be added to make a visible text

	Some solutions:
	Make an arrayList to handle temporaries and memory manage myself ie: set clean the arraylist after every "scene" -- the option im going to do
	Make global variables for EVERY text and use so much ram itll put Google Chrome to shame
	Completely rewrite everything -- Probably the best option but i dont have the time or patience
	 */
	
	
	
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
		
		isInGame = false;
		isMenuOpen = false;
		isCharacterMade = false;

		temp = new ArrayList<Text>(); //A arraylist for handling all text scene by scene
	}

	//**************************************************************Graphics**************************************************************
	@Override
	public void render()
	{
		ScreenUtils.clear(1, 0, 0, 1);
		
		batch.begin();
		
		switch(state)
		{
			//Add splash screen
			case -5:
				break;

			//Start menu
			case -4:
				batch.draw(background, 0, 0);
				loadMenu();
				break;
		
			case -3:
				batch.draw(background, 0, 0);
				begin();
				break;
		
			//Choose gender
			case -2:
				batch.draw(background, 0, 0);
				chooseSex();
				break;
			
			//Choose class
			case -1:
				batch.draw(background, 0, 0);
				chooseClass();		
				break;

			//Load into world
			case 0:
				//loadFromSave();
				initiatePlayer(p1DefaultFightingPos[0], p1DefaultFightingPos[1]); //Change this eventually its just for testing
				loadWorld();
				renderPlayer();
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
			temp.add(new Text("You are dreaming", 250, 410));
			temp.add(new Text("Press ENTER to Continue", 230, 390));
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
			temp.add(new Text("In this dream you are a", 230, 380));
			temp.add(new Text("(M)ale", 270, 330));
			temp.add(new Text("or", 270, 280));
			temp.add(new Text("(F)emale", 270, 230));
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
			temp.add(new Text("You dreamt as yourself being a", 225, 450));
			temp.add(new Text("(P)irate", 80, 250));
			temp.add(new Text("(R)oyal Guard", 150, 250));
			temp.add(new Text("(S)amurai", 260, 250));
			temp.add(new Text("Sparta(n)", 380, 250));
			temp.add(new Text("(V)iking", 480, 250));
		}

		//Draw character select
		for(int i = 0; i < temp.size(); i++)
		{
			startFade(temp.get(i));
		}
		
		//Female characters
		if(sex == "f")
		{
			batch.draw(pirateF, 50, 250);
			batch.draw(royal_guardF, 150, 250);
			batch.draw(samuraiF, 250, 250);
			batch.draw(spartanF, 360, 250);
			batch.draw(vikingF, 460, 250);
		}
		
		//Male characters
		if(sex == "m")
		{
			batch.draw(pirateM, 50, 250);
			batch.draw(royal_guardM, 150, 250);
			batch.draw(samuraiM, 250, 250);
			batch.draw(spartanM, 360, 250);
			batch.draw(vikingM, 460, 250);
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
		batch.draw(area1, 0, 0);
		p1.toggleRPG();

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

			//Title obv -- FIX THIS LATER
			//batch.draw(title, 0, 350);

			//Buttons not so obv
		}
		
		//Menu when opened anywhere else in game
		else
		{
			
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
			p1.enableInput();
			isCharacterMade = true;

		}
	}

	//For later use whenever I get fighting mode to work
	public void initiateNPC()
	{

	}

	public void renderPlayer()
	{
		batch.draw(p1.renderPlayer(), p1.returnX(), p1.returnY());
	}

}