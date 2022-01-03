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
	
	//Garbage Collector Friendly Variables somewhat
	private int state;
	private int area;
	private int whereIsCursor;
	private int screenHeight, screenWidth;

	private float when; //Where the player is at during the game
	
	private boolean isMenuOpen; //Note: this refers to in game
	private boolean isInGame;
	private boolean isCharacterMade;
	private boolean isCharacterFightingReady;

	private String sex;
	private String player_class;
	private String mode;

	private ArrayList<Text> temp;
	private Text fade_temp;
	private ArrayList<int[]> bounds;
	
	//Non-garbage Collector Friendly Variables somewhat
	private SpriteBatch batch;
	
	private BitmapFont font, font2;
	
	private Texture background;
	private Texture selected_Character, pirateF, pirateM, royal_guardF, royal_guardM, samuraiF, samuraiM, spartanF, spartanM, vikingF, vikingM;
	private Texture title;

	private Texture area1;
	private Texture forest_fight;

	private Texture textbox;
	
	private Player p1;
	private Player p2; //Possibly

	private String player_orientation;

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
	 * Gamestate is what state the game is in (duh)
	 * Either fighting or rpg for the most part
	 *
	 * area notes
	 * 
	 * area defines the area your character is currently in regardless of how far you have progressed
	 * -1: character select
	 *
	 * the temp arraylist holds all of the text temporarily to get around the function scope issue
	 * this saves memory.... in theory, i hope
	 *
	 *
	 * TODO: Make ArrayList for all NPCs rather than individual objects (duh what was i thinking prob tired)
	*/

	//**************************************************************My Objects and Variables**************************************************************
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		//font2 = new BitmapFont();

		background = new Texture(Gdx.files.internal("misc/jungle-background.png"));
		title = new Texture(Gdx.files.internal("misc/title_1.png"));
		pirateF = new Texture(Gdx.files.internal("./Pirate_Female/idle_1.png"));
		pirateM = new Texture(Gdx.files.internal("./Pirate_Male/idle_1.png"));
		royal_guardF = new Texture(Gdx.files.internal("./Royal_Guard_Female/idle_1.png"));
		royal_guardM = new Texture(Gdx.files.internal("./Royal_Guard_Male/idle_1.png"));
		samuraiF = new Texture(Gdx.files.internal("./Samurai_Female/idle_1.png"));
		samuraiM = new Texture(Gdx.files.internal("./Samurai_Male/idle_1.png"));
		spartanF = new Texture(Gdx.files.internal("./Spartan_Female/idle_1.png"));
		spartanM = new Texture(Gdx.files.internal("./Spartan_Male/idle_1.png"));
		vikingF = new Texture(Gdx.files.internal("./Viking_Female/idle_1.png"));
		vikingM = new Texture(Gdx.files.internal("./Viking_Male/idle_1.png"));
		area1 = new Texture(Gdx.files.internal("./backgrounds/jungle-ruins-preview.png"));
		textbox = new Texture(Gdx.files.internal("./ui/panel_Example1.png"));

		forest_fight = new Texture(Gdx.files.internal("./stages/forest_fight.png"));

		state = -3;
		area = 0;
		whereIsCursor = 0;
		when = 0;

		mode = "rpg";

		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		
		isInGame = false;
		isMenuOpen = false;
		isCharacterMade = false;
		isCharacterFightingReady = false;

		temp = new ArrayList<Text>(); //A arraylist for handling all text scene by scene
		player_orientation = "left";

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

				if(mode == "rpg")
				{
					loadWorld();
					initiateRPGNPC();
					initiatePlayer(100, 100); //Change this eventually its just for testing
				}

				if(mode == "fighting")
				{
					loadStage(0); //Change Later
					initiateFightingNPC();
					initiatePlayer(134, 322);

				}
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

	//0 Works!
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
			if(!isNPCInit)
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add("What! I thought you died years ago. How peculiar.... Anyways\n the boss would not like it if I let you slide by.");

				npc1 = new NPC(383,505, 'f', "pirate", temp);
			}
		}
	}

	public void loadStage(int stage)
	{
		switch(stage)
		{
			case 0:

				/*
				134 322 Spawn for p1
				322 is the floor
				 */

				batch.draw(forest_fight, 0, 0, screenWidth, screenHeight);

				//Character Positions

				if(!isCharacterFightingReady)
				{
					p1.setMode("fighting");
					p1.pos(-150,24);
					isCharacterFightingReady = true;
				}

				break;
		}
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

	private void fadeAndFlash(Text text)
	{
		boolean debug = false;

		if(fade_temp == null)
		{
			fade_temp = new Text(text.returnText(), text.returnX(), text.returnY());

			if(debug) System.out.println("Caught fade_temp as null");
		}

		fade_temp.fadeAndFlash(text.returnText());
		font.draw(batch, fade_temp.returnText(), fade_temp.returnX(), fade_temp.returnY());
	}
	
	//**************************************************************Characters**************************************************************
	
	private void initiatePlayer(int paramX, int paramY)
	{

		/*
		paramX/paramY - the beginning position of the character
		 */

		int multiplier = 1;

		if(!isCharacterMade)
		{
			char temp = sex.charAt(0); //Convert the string to char cause i was dumb
			p1 = new Player(paramX, paramY, temp, player_class);
			isCharacterMade = true;
		}

		//Fighting mode multiplier
		if(mode == "fighting") multiplier = 5;

		p1.updatePos();

		batch.draw(p1.renderPlayer(), p1.returnX(), p1.returnY(), p1.returnW() * multiplier, p1.returnH() * multiplier, 0, 0, p1.returnOrigW(), p1.returnOrigH(), p1.returnIsFlipX(), false);

		if(p1.returnIsFlipX()) player_orientation = "right";
		else player_orientation = "left";

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
		else if(mode ==  "fighting") p1.toggleFight();
		else System.err.println("ERROR: No Valid Mode Set");

		//Player interaction with NPCs
		if(interact(p1, npc1) && mode == "rpg")
		{
			//Display text box
			textBox(npc1.returnDialogue(0));

			if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
			{
				when++;
				mode = "fighting";
			}
		}
	}

	//For later use whenever I get fighting mode to work
	private void initiateFightingNPC()
	{
		//Clear all npcs
		npc1 = null;
	}

	private void initiateRPGNPC()
	{
		boolean debug = false;

		if(Gdx.input.isKeyPressed(Input.Keys.F) && debug) System.out.println("\nPlayer X:" + p1.returnX() + "\nNPC X: " + npc1.returnX() + "\nPlayer Y: " + p1.returnY() + "\nNPC Y: " + npc1.returnY());

		//Set bounds
		//TODO: need boundary code for the starting area

		//Render all NPCs
		batch.draw(npc1.renderPlayer(), npc1.returnX(), npc1.returnY(), npc1.returnW(), npc1.returnH());
	}

	//**************************************************************Misc**************************************************************

	//Checks if characters can interact with NPC
	private boolean interact(Player p, NPC n)
	{
		int boundForgiveness = 60;
		int xOri = 1;
		int yOri = 1;

		boolean debug = false;

		/*
		Change where the hitbox is in front of the character based on how the character is facing
		Based on how the character is drawn NOT how the coords are represented
		 */
		if((player_orientation == "right" && xOri == -1) || (player_orientation == "left" && xOri == 1)) xOri *= -1 ;
		if((player_orientation == "up" && xOri == -1) || (player_orientation == "up" && yOri == 1)) yOri *= -1 ;

		//Debug
		if(debug)
		{
			System.out.println("\nNPC X: " + npc1.returnX() + "\nNPC Y: " + npc1.returnY() + "\nNPC W: " + npc1.returnW() + "\nNPC H: " + npc1.returnH() + "\nNPC oW: " + npc1.returnOrigW() + "\nNPC oH: " + npc1.returnOrigH());
			System.out.println("\nPlayer X: " + p1.returnX() + "\nPlayer Y: " + p1.returnY() + "\nPlayer W: " + p1.returnW() + "\nPlayer H: " + p1.returnH() + "\nPlayer oW: " + p1.returnOrigW() + "\nPlayer oH: " + p1.returnOrigH());

			System.out.println("\nCollision: ");

			//Replace collision with a better collision method
			if(p1.returnX() + boundForgiveness * xOri > npc1.returnX() && npc1.returnX() + npc1.returnW() > p1.returnX() + boundForgiveness * xOri) System.out.println("\nX Works");
			if(p1.returnY() + boundForgiveness * yOri > npc1.returnY() && npc1.returnY() + npc1.returnH() > p1.returnY() + boundForgiveness * yOri) System.out.println("\nY Works");
		}

		//Some very primitive collision
		if(mode == "rpg")
		{
			if(p1.returnX() + boundForgiveness * xOri > npc1.returnX() && npc1.returnX() + npc1.returnW() > p1.returnX() + boundForgiveness * xOri)
			{
				if(p1.returnY() + boundForgiveness * yOri > npc1.returnY() && npc1.returnY() + npc1.returnH() > p1.returnY() + boundForgiveness *yOri)
				{
					return true;
				}
			}
		}

		//No collision
		return false;
	}

	//Makeshift Static Text Box
	private void textBox(String s)
	{
		font.setColor( 0, 0, 0, 1);

		/*
		Make a textbox thats size changes for the amount of letters inside of it
		so the letters can fit inside the text box
		but for now a box that hits the middle of the screen should be

		y = independent
		x = screenWidth/2 - widthOfBox/2
		width = independent
		height = independent

		*/

		batch.draw(textbox, screenWidth/2 - 250, 0, 500, 250);
		font.draw(batch, s,  screenWidth/2 - 200, 170);

		//TODO: Add support for strings being spread across multiple text boxes
		font.draw(batch, "Press ENTER to Accept Challenge", screenWidth/2 - 95, 100); //TODO: Make this fadeandflash
	}
}