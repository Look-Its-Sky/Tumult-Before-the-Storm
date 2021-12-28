package com.jude.tumultbts;

//Text effects class - i overcomplicated this but lets go with it i learned my lesson
public class Text {

    private float fade, fade2;

    private int x,y;
    private int flash;
    private int counter;

    private boolean flashswitch;
    private boolean isShow;
    private boolean isSet;
    private boolean isFlash;

    private String txt;
    private String temp;

    public Text(String str, int paramX, int paramY)
    {
        fade = 0;
        fade2 = 0;
        flash = 0;
        counter = 0;

        flashswitch = false;
        isShow = true;
        isSet = false;
        isFlash = false;

        txt = str;
        temp = txt;

        x = paramX;
        y = paramY;
    }

    public void flashText(String str)
    {
        boolean debug = false;
        int flashIntensity = 30;

        if(isShow)
        {
            if(flashswitch == false)
            {

                if(counter >= flashIntensity)
                {
                    flashswitch = true;
                    counter = 0;
                }

                else temp = "";
            }

            if(flashswitch == true)
            {

                if(counter >= flashIntensity)
                {
                    flashswitch = false;
                    counter = 0;
                }

                else temp = txt;
            }

            counter ++;
        }

        else
        {
            temp = "";
        }
    }

    public void fadeIn()
    {
        if(isShow)
        {
            if(fade <= 1)
            {
                fade += 0.02;
            }
        }
    }

    public void fadeAndFlash(String str)
    {
        boolean debug = false;

        //Avoid overwriting existing values
        if(!isSet)
        {
            //System.out.println("Not set");
            fade = 0;
            fade2 = 0;
            flash = 0;
            flashswitch = false;

            isSet = true;
        }

        if(isShow)
        {
            if(fade < 1)
            {
                fade += 0.02;

                if(debug) System.out.println("Flash: " +  fade);
            }

            else
            {
                if(debug) System.out.println("FLASHING");
                flashText(str);
            }
        }

        else
        {
            temp = "";
        }
    }

    public int returnFlash()
    {
        return flash;
    }

    public float returnFade()
    {
        return fade;
    }

    public float returnFade2()
    {
        return fade2;
    }

    public String returnText()
    {
        return temp;
    }

    public void resetAnim()
    {
        fade = 0;
        fade2 = 0;
        flash = 0;
        flashswitch = false;
        isSet = false;
    }

    public void show()
    {
        isShow = true;
        temp = txt;
    }

    public void hide()
    {
        isShow = false;
        temp = "";
    }

    public int returnX()
    {
        return x;
    }

    public int returnY()
    {
        return y;
    }
}