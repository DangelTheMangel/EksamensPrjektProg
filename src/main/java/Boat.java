import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Boat {
    //All the boat's variables
    PApplet p;
    //The positions
    float xPos, yPos;
    //players money
    float  money;
    //boats sprite / appearance
    PImage boatPic;
   /*list of what boat have in its inventory of
   items and been item tells how much it has of that item*/
    ArrayList<Item> inventory;
    //The list that the boat can see
    ArrayList<Tile> showneTileSet = new ArrayList<Tile>();

    //---------- DEFAULT CONSTRUCTOR :) ----------\\
    public Boat(PApplet p, float x, float y, float m) {
        this.p = p;
        this.inventory = inventory;
        xPos = x;
        yPos = y;
        money = m;
    }
    //----------METHODS----------\\

    //This feature makes the boat inventory and fills it up with all the game items
    void generateInventory() {
        ArrayList StockInventory = new ArrayList<Item>();
        Item Banana = new Item(10, 0, "Banana");
        Item Rum = new Item(5, 0, "Rum");
        Item Eyepatch = new Item(3, 0, "Eyepatch");
        StockInventory.add(Banana);
        StockInventory.add(Rum);
        StockInventory.add(Eyepatch);
        inventory = StockInventory;
    }

    //This feature draws the boat
    void displayBoat(float pX, float pY, int s) {
        if (boatPic != null)
            p.image(boatPic, s * pX, s * pY, s, s);


        for (int i = 0; i < showneTileSet.size(); ++i) {
            Tile tile = showneTileSet.get(i);
            if (xPos == tile.xPos && yPos == tile.yPos) {
                if (!tile.Contents.equals("WATER")) {
                    moveBoatToWater();
                }

            }
        }
    }

    //Since the boat can not show it is on land and the boat is
    // placed randomly on the map then make this
    // function that the host boat moves to a water tile
    void moveBoatToWater() {
        boolean didFindWaterTile = false;

        //checks if there is a water tile in the shovn tiles
        for (int i = 0; i < showneTileSet.size(); ++i) {
            Tile tile = showneTileSet.get(i);

            //show there is back there and stop  the looped
            if (tile.Contents.equals("WATER")) {
                xPos = tile.yPos;
                yPos = tile.yPos;
                didFindWaterTile = true;
                break;
            }


        }

        //if no back water is found at random and check again
        if (!didFindWaterTile) {
            int ran = (int) p.random(0, showneTileSet.size());
            xPos = showneTileSet.get(ran).xPos;
            yPos = showneTileSet.get(ran).yPos;
            moveBoatToWater();
        }

    }

    //This feature fills the list with tiles that the boat can see
    void fillUpShownTiles(ArrayList<Tile> tileSet) {
        showneTileSet.clear();


        for (int i = 0; i < tileSet.size(); ++i) {
            if (tileSet.get(i).xPos - xPos > -3 && tileSet.get(i).xPos - xPos < 3) {

                if (tileSet.get(i).yPos - yPos > -3 && tileSet.get(i).yPos - yPos < 3) {

                    showneTileSet.add(tileSet.get(i));
                }
            }

        }
    }

    //This feature provides a float with players' scores. by taking number of money and items nomal pric * with number
    float calRating(){
        float price = 0;
        price += money;
        for(int i = 0; i<inventory.size();++i){

            price+=inventory.get(i).value * inventory.get(i).ammount;
        }
        return price;

    }
}
