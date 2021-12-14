package com.jude.tumultbts;

import java.util.ArrayList;

public class NPC extends Player{

    private int textCounter;

    public NPC(int paramX, int paramY, char gender, String pClass, ArrayList<String> dialogue)
    {
        super(paramX, paramY, gender, pClass, dialogue);

        textCounter = 0;
    }
}
