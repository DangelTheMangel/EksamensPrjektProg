import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;

public class Boat {
    PApplet p;
    float xPos, yPos;
    float score, money;
    PImage boatPic;
    ArrayList<Item> inventory;
    ArrayList<Tile> showneTileSet = new ArrayList<Tile>();

    //---------- DEFAULT CONSTRUCTOR :) ----------
    public Boat(PApplet p,float x, float y, float s, float m ){
        this.p = p;
        this.inventory = inventory;
        xPos = x;
        yPos = y;
        score = s;
        money = m;
       // boatPic = p.loadImage("Skibet32.png");
    }



    void generateInventory(){
        ArrayList StockInventory = new ArrayList<Item>();
        Item Banana = new Item(10,0,"Banana","idk");
        Item Rum = new Item(5,0,"Rum","idk");
        Item Eyepatch = new Item(3,0,"Eyepatch","idk");
        StockInventory.add(Banana);
        StockInventory.add(Rum);
        StockInventory.add(Eyepatch);


        inventory = StockInventory;

    }

    //----------METHODS----------
    void displayBoat(int pX, int pY,int s){

        p.image(boatPic,s*pX,s*pY,s,s);
    }

    void fillUpShownTiles(ArrayList<Tile> tileSet){
        showneTileSet.clear();


        for(int i = 0;i<tileSet.size();++i){
            if(tileSet.get(i).xPos - xPos > -3 && tileSet.get(i).xPos - xPos < 3){

                if(tileSet.get(i).yPos - yPos > -3 && tileSet.get(i).yPos - yPos < 3){

                    showneTileSet.add(tileSet.get(i));
                }
            }

        }
    }
}
