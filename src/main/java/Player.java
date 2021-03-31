
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Player<oldpath> extends Boat {

    ArrayList<PVector> path = new ArrayList<>();

    public Player(PApplet p, float x, float y, float s, float m) {

        super(p, x, y, s, m);
        path.add(null);

    }

    int neighBordPath(ArrayList<Tile> tileSet, int chosen, int extra, int path) {
        int re = Math.min(path, tileSet.get(chosen+extra).path);
        return re;
    }

    void findPath(Tile selected, ArrayList<Tile> tileSet) {
        int playerTileNum = 0;
        selected.path = 0;

        boolean pathAdded = false;
        do {
            pathAdded = false;
            for (int i=0; i < tileSet.size(); ++i) {
                Tile tile = tileSet.get(i);

                if (!tile.Contents.equals("WATER")) {
                    tile.path = Integer.MAX_VALUE;
                    continue;
                }

                if (selected == tile)
                    continue;

                // If player positioned on tile
                if (tile.xPos == xPos && tile.yPos == yPos)
                    playerTileNum = i;

                int pathNumber = 10000;
                pathNumber = neighBordPath(tileSet, i, +1, pathNumber);
                pathNumber = neighBordPath(tileSet, i, +35, pathNumber);
                pathNumber = neighBordPath(tileSet, i, +36, pathNumber);
                pathNumber = neighBordPath(tileSet, i, +37, pathNumber);
                pathNumber = neighBordPath(tileSet, i, -35, pathNumber);
                pathNumber = neighBordPath(tileSet, i, -36, pathNumber);
                pathNumber = neighBordPath(tileSet, i, -37, pathNumber);
                pathNumber = neighBordPath(tileSet, i, -1, pathNumber);

               // System.out.println("Path number: " + pathNumber);

                if (tile.path > pathNumber + 1) {
                    tile.path = pathNumber + 1;
                    pathAdded = true;
                }
            }
        } while (pathAdded);




        int orginalPlayerPathNumber = tileSet.get(playerTileNum).path;
        ArrayList<PVector> posPath = new ArrayList<>();
        posPath.add(null);
        for(int i = orginalPlayerPathNumber; i > 0 ; --i){

            try {
                if(playerTileNum +1 < tileSet.size() &&checkNaboPath(tileSet, playerTileNum, +1)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, +1);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 1;
                }
                if(playerTileNum +35 < tileSet.size() &&checkNaboPath(tileSet, playerTileNum, +35)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, +35);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 35;
                }
                if(playerTileNum +36 < tileSet.size() &&checkNaboPath(tileSet, playerTileNum, +36)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, +36);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 36;

                }
                if(playerTileNum +37 < tileSet.size() &&checkNaboPath(tileSet, playerTileNum, +37)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, +37);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 37;

                }

                if(playerTileNum -35 > 0 && checkNaboPath(tileSet, playerTileNum, -35)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, -35);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 35;

                }
                if(playerTileNum -36 > 0 &&checkNaboPath(tileSet, playerTileNum, -36)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, -36);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 36;

                }
                if(playerTileNum -37 > 0 &&checkNaboPath(tileSet, playerTileNum, -37)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, -37);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 37;

                }

                if(playerTileNum -1 > 0 &&checkNaboPath(tileSet, playerTileNum, -1)){
                    Tile tile = getNaboTile(tileSet, playerTileNum, -1);
                    PVector pos = new PVector(tile.xPos ,tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 1;

                }
            }
            catch(Exception e) {
                System.out.println(e);
                break;
            }






        }

        for(int i = 0; i <tileSet.size();++i){
            tileSet.get(i).path = 10000;
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
