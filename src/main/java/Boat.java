import processing.core.PApplet;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;

public class Boat {
    PApplet p;
    float xPos, yPos;
    float score, money;
    ArrayList<Item> inventory;

    //---------- DEFAULT CONSTRUCTOR :) ----------
    public Boat(PApplet p,float x, float y, float s, float m){
        this.p = p;
        xPos = x;
        yPos = y;
        score = s;
        money = m;
       // boatPic = p.loadImage("Skibet32.png");
    }



    //----------METHODS----------
    void displayBoat(int pX, int pY,int s){
        p.image(boatPic,s*pX,s*pY,s,s);
    }
}
