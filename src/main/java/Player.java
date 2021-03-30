
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Player<oldpath> extends Boat {

    ArrayList<PVector> path = new ArrayList<>();

    public Player(PApplet p, float x, float y, float s, float m) {

        super(p, x, y, s, m);
        path.add(null);

    }

    void findPath(Tile seleted,Tile start , ArrayList<Tile> tileSet, int antalTiles){

        ArrayList<Tile> open = new ArrayList<>();
        ArrayList<Tile> closed = new ArrayList<>();
        open.add(start);
        int playerTileNum = 0;
        antalTiles += 3;
        for (int j = 0; j<tileSet.size();++j) {
            for (int i = 0; i < tileSet.size(); ++i) {
                Tile tile = tileSet.get(i);
                if(tile.xPos == xPos && tile.yPos == yPos){
                    playerTileNum = i;
                }

                if (((Math.abs(tile.xPos - seleted.xPos) <= j) && (Math.abs(tile.yPos - seleted.yPos) <= j))) {
                    if (tile.Contents.equals("WATER") ) {
                        if(tile.path > 0){

                        }else{
                            //ændre det til det at den tager den mindste nabo + 1
                            int pathNumber = Integer.MAX_VALUE;
                            pathNumber = Math.min(tileSet.get(i-1).path,pathNumber) ;
                            pathNumber = Math.min(tileSet.get(i+1).path,pathNumber) ;
                            pathNumber = Math.min(tileSet.get(i-antalTiles).path,pathNumber) ;
                            pathNumber = Math.min(tileSet.get(i+antalTiles).path,pathNumber) ;
                            pathNumber = Math.min(tileSet.get(i-antalTiles-1).path,pathNumber) ;
                            pathNumber = Math.min(tileSet.get(i+antalTiles+1).path,pathNumber) ;
                            pathNumber = Math.min(tileSet.get(i-antalTiles-2).path,pathNumber) ;
                            pathNumber = Math.min(tileSet.get(i+antalTiles+2).path,pathNumber) ;
                            System.out.println(pathNumber);
                            tile.path = j;
                        }

                    } else {
                        tile.path = -1;
                    }


                }
                seleted.path = 0;
            }


        }

        int orginalPlayerPathNumber = tileSet.get(playerTileNum).path;
        ArrayList<PVector> posPath = new ArrayList<>();
        posPath.add(null);
        for(int i = orginalPlayerPathNumber; i > 0 ; --i){

            if(checkNaboPath(tileSet, playerTileNum, +1)){
                Tile tile = getNaboTile(tileSet, playerTileNum, +1);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum += 1;
            }
            if(checkNaboPath(tileSet, playerTileNum, +35)){
                Tile tile = getNaboTile(tileSet, playerTileNum, +35);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum += 35;
            }
            if(checkNaboPath(tileSet, playerTileNum, +36)){
                Tile tile = getNaboTile(tileSet, playerTileNum, +36);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum += 36;

            }
            if(checkNaboPath(tileSet, playerTileNum, +37)){
                Tile tile = getNaboTile(tileSet, playerTileNum, +37);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum += 37;

            }

            if(checkNaboPath(tileSet, playerTileNum, -35)){
                Tile tile = getNaboTile(tileSet, playerTileNum, -35);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum -= 35;

            }
            if(checkNaboPath(tileSet, playerTileNum, -36)){
                Tile tile = getNaboTile(tileSet, playerTileNum, -36);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum -= 36;

            }
            if(checkNaboPath(tileSet, playerTileNum, -37)){
                Tile tile = getNaboTile(tileSet, playerTileNum, -37);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum -= 37;

            }

            if(checkNaboPath(tileSet, playerTileNum, -1)){
                Tile tile = getNaboTile(tileSet, playerTileNum, -1);
                PVector pos = new PVector(tile.xPos ,tile.yPos);
                posPath.add(pos);
                playerTileNum -= 1;

            }


            System.out.println("\n");


        }
        System.out.println("___________________________________________________________________________ path size: " + posPath.size());
        for(int i = 0 ; i < posPath.size() ; ++i){
            if(posPath.get(i) != null){
                System.out.println("i: "+i + " x:" +posPath.get(i).x + " y: " + posPath.get(i).y);
            }

        }
        for(int i = 0; i <tileSet.size();++i){
            tileSet.get(i).path = -1;
            tileSet.get(i).selectedTile = false;
        }
        path = posPath ;

    }

    Tile getNaboTile(ArrayList<Tile> tileSet, int choosen, int extra){
        Tile nabo = tileSet.get(choosen + extra);
        return nabo;

    }
    Boolean checkNaboPath(ArrayList<Tile> tileSet, int choosen, int extra){
        Tile selected = tileSet.get(choosen);
        Tile nabo = tileSet.get(choosen + extra);

        if(selected.path -1  == nabo.path && nabo.Contents.equals("WATER")){
            System.out.println("selected: " + selected.xPos + " x " + selected.yPos + " nabo: " + nabo.xPos + " x " + nabo.yPos);
            return true;
        }else {
            return false;
        }
    }



    int movePlayer(int turnCount){

        if(path.size() > 1) {
            if(turnCount <=0){
                path.clear();
                path.add(null);
                return 0;
            }
            xPos = path.get(1).x;
            yPos = path.get(1).y;
            path.remove(1);
            System.out.println(turnCount--);
            return turnCount--;

        }else {
            return turnCount;
        }


    }
}
