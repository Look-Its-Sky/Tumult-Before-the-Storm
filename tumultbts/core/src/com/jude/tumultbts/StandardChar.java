package com.jude.tumultbts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

//Mostly used to handle images
public class StandardChar {
	//************************************************************LAZY STUFF**************************************************************
	
	protected String path;
	
	protected String au = "attackUnarmed_";
	protected String au2 = "attackUnarmed2_";
	protected String au3 = "attackUnarmed3_";
	
	protected String aw1 = "attackWeapon1_";
	protected String aw2 = "attackWeapon2_";
	protected String aw3 = "attackWeapon3_";
	
	protected String idle = "idle_";
	protected String dash = "dash_";
	protected String dead = "dead_1";
	protected String hit = "hit_1";
	protected String jump = "graveBurst_1";
	protected String run = "run_";

	//**************************************************************Stats**************************************************************
	
	protected int hp, def, atk, dex, spd;
	protected int x, y;
	protected int width, height;
	protected int playerClass;
	
	//**************************************************************Anim Arrays**************************************************************
	
	protected ArrayList attackAnim1;
	protected ArrayList attackAnim2;
	protected ArrayList attackAnim3;

	protected ArrayList dashAnim;
	protected ArrayList hitAnim; //idea! since theres no second pic for "hit" i could use dead as a second hit
	protected ArrayList idleAnim;
	protected ArrayList runAnim;
	
	//**************************************************************Set Images to Selected Class**************************************************************

	public StandardChar(char gender, String c)
	{
		//Set types
		attackAnim1 = new ArrayList<Texture>();
		attackAnim2 = new ArrayList<Texture>();
		attackAnim3 = new ArrayList<Texture>();
		
		dashAnim = new ArrayList<Texture>();
		hitAnim = new ArrayList<Texture>();
		idleAnim = new ArrayList<Texture>();
		runAnim = new ArrayList<Texture>();
		
		//Determine path
		switch(c)
		{
			case "pirate":
				
				if(gender == 'f')
				{
					path = "./Pirate_Female/";
				}
				
				else if(gender == 'm')
				{
					path = "./Pirate_Male/";
				}
				
				break;
				
			case "royal_guard":
				
				if(gender == 'f')
				{
					path = "./Royal_Guard_Female/";
				}
				
				else if(gender == 'm')
				{
					path = "./Royal_Guard_Male/";
				}
				
				break;
				
			case "samurai":
				
				if(gender == 'f')
				{
					path = "./Samurai_Female/";
				}
				
				else if(gender == 'm')
				{
					path = "./Samurai_Male/";
				}
				
				break;
				
			case "spartan":
				
				if(gender == 'f')
				{
					path = "./Spartan_Female/";
				}
				
				else if(gender == 'm')
				{
					path = "./Spartan_Male/";
				}
				
				break;
				
			case "viking":
				
				if(gender == 'f')
				{
					path = "./Viking_Female/";
				}
				
				else if(gender == 'm')
				{
					path = "./Viking_Male/";
				}
				
				break;
				
			default:
				System.err.println("ISSUE IN CHARACTER OBJECT AT CHARACTER SELECT");
		}
		
		loadChar();
	}
	
	
	//**************************************************************Load Character**************************************************************
	private void loadChar()
	{

		//Unarmed attack anims

		//Load attackAnim1
		for (int i = 1; i < 4; i++)
		{
			attackAnim1.add(new Texture(Gdx.files.internal(path + au + i + ".png")));
		}

		//Load attackAnim2
		for (int i = 1; i < 4; i++)
		{
			attackAnim2.add(new Texture(Gdx.files.internal(path + au2 + i + ".png")));
		}

		//Load attackAnim3
		for (int i = 1; i < 4; i++)
		{
			attackAnim3.add(new Texture(Gdx.files.internal(path + au3 + i + ".png")));
		}

		//Misc Anims

		//Dash Anim
		dashAnim.add(new Texture(Gdx.files.internal(path + dash + 1 + ".png")));
		dashAnim.add(new Texture(Gdx.files.internal(path + dash + 2 + ".png")));

		//Hit Anim
		hitAnim.add(new Texture(Gdx.files.internal(path + hit + ".png")));
		hitAnim.add(new Texture(Gdx.files.internal(path + dead + ".png")));
		hitAnim.add(new Texture(Gdx.files.internal(path + jump + ".png")));

		//Run Anim
		for (int i = 1; i < 5; i++)
		{
			runAnim.add(new Texture(Gdx.files.internal(path + run + i + ".png")));
		}

		//Idle Anim
		for (int i = 1; i < 4; i++)
		{
			idleAnim.add(new Texture(Gdx.files.internal(path + idle + i + ".png")));
		}
		
	}
	
	//**************************************************************Getters**************************************************************
	
	public ArrayList<Texture> returnAttackAnim1()
	{
		return attackAnim1;
	}
	
	public ArrayList<Texture> returnAttackAnim2()
	{
		return attackAnim2;
	}
	
	public ArrayList<Texture> returnAttackAnim3()
	{
		return attackAnim3;
	}
	
	public ArrayList<Texture> returnDashAnim()
	{
		return dashAnim;
	}
	
	public ArrayList<Texture> returnHitAnim()
	{
		return hitAnim;
	}
	
	public ArrayList<Texture> returnIdleAnim()
	{
		return idleAnim;
	}
	
	public ArrayList<Texture> returnRunAnim()
	{
		return runAnim;
	}
}
