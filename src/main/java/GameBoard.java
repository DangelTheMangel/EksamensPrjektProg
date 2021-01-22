import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class GameBoard {
    PApplet p;
    //til bord klassen
    ArrayList<Tile> tileSet = new ArrayList<Tile>();
    ArrayList<Tile> showneTileSet = new ArrayList<Tile>();
    PVector playerPos = new PVector(5,5);

    GameBoard(PApplet p){
        this.p = p;

    }

    void drawBoard(){
        p.clear();
        p.background(200);
        fillUpShownTiles();

        for(int i = 0;i<showneTileSet.size();++i){
            int posX = showneTileSet.get(i).xPos;
            int posY = showneTileSet.get(i).yPos;
            if(i<3){
                posX = 1;
                posY = i+1;
            }else if(i<6){
                posX = 2;
                posY = i-2;
            }else{
                posX = 3;
                posY = i-5;
            }




            showneTileSet.get(i).Display(posX,posY,100);

            if(showneTileSet.get(i).cliked){
                playerPos = new PVector(showneTileSet.get(i).xPos,showneTileSet.get(i).yPos);
                showneTileSet.get(i).cliked = false;
            }
        }
    }

    void fillUpShownTiles(){
        showneTileSet.clear();
        for(int i = 0;i<tileSet.size();++i){
            if(tileSet.get(i).xPos - playerPos.x == 1 ||tileSet.get(i).xPos - playerPos.x == -1 || tileSet.get(i).xPos - playerPos.x == 0){
                if(tileSet.get(i).yPos - playerPos.y == 1 ||tileSet.get(i).yPos - playerPos.y == -1||tileSet.get(i).yPos - playerPos.y == 0){
                    showneTileSet.add(tileSet.get(i));
                }
            }

        }
    }

    void boardmouseClicked(){
        for(int i = 0;i<showneTileSet.size();++i){
            int posX = showneTileSet.get(i).xPos;
            int posY = showneTileSet.get(i).yPos;

            if(i<3){
                posX = 1;
                posY = i+1;
            }else if(i<6){
                posX = 2;
                posY = i-2;
            }else{
                posX = 3;
                posY = i-5;
            }

            showneTileSet.get(i).checkIfCliked(posX,posY,100);
        }
    }
}

