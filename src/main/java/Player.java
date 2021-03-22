
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Player extends Boat {


    public Player(PApplet p, float x, float y, float s, float m) {
        super(p, x, y, s, m);
    }

    ArrayList<PVector> findPath(Tile seleted,Tile start , ArrayList<Tile> tileSet){
        ArrayList<PVector> path = new ArrayList<>();
        ArrayList<Tile> open = new ArrayList<>();
        ArrayList<Tile> closed = new ArrayList<>();
        open.add(start);
        for (int j = 0; j<36;++j) {
            for (int i = 0; i < tileSet.size(); ++i) {
                Tile tile = tileSet.get(i);
             /*   System.out.println("\nx: " + tile.xPos + " y: " + tile.yPos
                        + "\nABSx: " + Math.abs(tile.xPos - start.xPos) + " ABSy: " + Math.abs(tile.yPos - start.yPos));*/
                if (((Math.abs(tile.xPos - seleted.xPos) <= j) && (Math.abs(tile.yPos - seleted.yPos) <= j))) {
                    if (tile.Contents.equals("WATER") ) {
                        if(tile.path > 0){

                        }else{
                            //ændre det til det at den tager den mindste nabo + 1
                            tile.path = j;
                        }

                    } else {
                        tile.path = -1;
                    }


                }
                seleted.path = 0;
            }
        }
        /*
        https://www.youtube.com/watch?v=-L-WgKMFuhE
        * Open //the set of nodes to be evaluated
        * Closed // the set of notes already evaluated
        * add the start node to Open
        *LOOP{
        * current = node in open with the lowest f_cost
        * remove current from open
        * add current to Closed
        * If Current is the target node//path has benn found
        * return
        *
        * foreach neighbour of the current node
        * if neighbour is not traversable or neighbour is in closed
        * skip to next neighbour
        *
        * if new path to neighbour is shorter or neighbour is not Open
        * set f_cost of neighbour
        * set parent of neighbour to current
        * if neighbour is not open
        * add neighbour to open
        * }
        * */
        return path;
    }
    void movePlayer(int x , int y){
       //klikker på vandtiels
        /*xPos = x;
        yPos = y;*/


    }
}
