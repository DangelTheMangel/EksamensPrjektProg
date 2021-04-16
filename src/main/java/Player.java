import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Player<oldpath> extends Boat {

    //This lsite is filled with the players' chosen route
    ArrayList<PVector> path = new ArrayList<>();

    //---------- CONSTRUCTOR :) ----------\\
    public Player(PApplet p, float x, float y,  float m) {

        super(p, x, y,  m);
        path.add(null);

    }
    //----------METHODS----------\\

    //find the best tile to go to based on 2 tiles
    int neighBordPath(ArrayList<Tile> tileSet, int chosen, int extra, int path) {
        int re = Math.min(path, tileSet.get(chosen + extra).path);
        return re;
    }

    //This function gives all tiles from the selected
    // tile a number of how far it is from the selected tile
    // and forges the fastest route to the selected tile and adds the items to the list path
    void findPath(Tile selected, ArrayList<Tile> tileSet) {
        int playerTileNum = 0;
        selected.path = 0;

        boolean pathAdded = false;
        do {
            pathAdded = false;
            for (int i = 0; i < tileSet.size(); ++i) {
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
        for (int i = orginalPlayerPathNumber; i > 0; --i) {


                if (playerTileNum + 1 < tileSet.size() && checkNaboPath(tileSet, playerTileNum, +1)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, +1);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 1;
                }
                if (playerTileNum + 35 < tileSet.size() && checkNaboPath(tileSet, playerTileNum, +35)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, +35);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 35;
                }
                if (playerTileNum + 36 < tileSet.size() && checkNaboPath(tileSet, playerTileNum, +36)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, +36);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 36;
                }
                if (playerTileNum + 37 < tileSet.size() && checkNaboPath(tileSet, playerTileNum, +37)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, +37);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum += 37;
                }
                if (playerTileNum - 35 > 0 && checkNaboPath(tileSet, playerTileNum, -35)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, -35);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 35;
                }
                if (playerTileNum - 36 > 0 && checkNaboPath(tileSet, playerTileNum, -36)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, -36);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 36;
                }
                if (playerTileNum - 37 > 0 && checkNaboPath(tileSet, playerTileNum, -37)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, -37);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 37;
                }
                if (playerTileNum - 1 > 0 && checkNaboPath(tileSet, playerTileNum, -1)) {
                    Tile tile = getNaboTile(tileSet, playerTileNum, -1);
                    PVector pos = new PVector(tile.xPos, tile.yPos);
                    posPath.add(pos);
                    playerTileNum -= 1;
                }
        }

        for (int i = 0; i < tileSet.size(); ++i) {
            tileSet.get(i).path = 10000;
            tileSet.get(i).selectedTile = false;
        }
        path = posPath;

    }

    //This function provides the neighbor to which you choose with the variable extra
    Tile getNaboTile(ArrayList<Tile> tileSet, int choosen, int extra) {
        Tile nabo = tileSet.get(choosen + extra);
        return nabo;

    }

    //this function checks if the neighbor is a tile you can back to
    Boolean checkNaboPath(ArrayList<Tile> tileSet, int choosen, int extra) {
        Tile selected = tileSet.get(choosen);
        Tile nabo = tileSet.get(choosen + extra);

        if (selected.path - 1 == nabo.path && nabo.Contents.equals("WATER")) {
            return true;
        } else {
            return false;
        }
    }

    //This function backs up the players according to what is in the list path
    int movePlayer(int turnCount) {

        if (path.size() > 1) {
            if (turnCount <= 0) {
                path.clear();
                path.add(null);
                return 0;
            }
            xPos = path.get(1).x;
            yPos = path.get(1).y;
            path.remove(1);
            System.out.println(turnCount--);
            return turnCount--;

        } else {
            return turnCount;
        }


    }
}
