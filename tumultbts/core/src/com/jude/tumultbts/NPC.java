package com.jude.tumultbts;

import java.util.ArrayList;
import java.util.Random;


public class NPC extends Player
{

    private int textCounter;
    private ArrayList<String> totalDialogue;
    private Random rand;

    public NPC(int paramX, int paramY, char gender, String pClass, ArrayList<String> dialogue)
    {
        super(paramX, paramY, gender, pClass, dialogue);

        textCounter = 0;

        totalDialogue = dialogue;

        rand = new Random();
    }

    public String returnDialogue(int in)
    {
        try
        {
            return totalDialogue.get(in);
        }

        catch (Exception e)
        {
            System.err.println(e);

            return "ERROR";
        }
    }

    public String returnRandomDialogue()
    {
        return totalDialogue.get(rand.nextInt(totalDialogue.size() + 1));
    }
}
