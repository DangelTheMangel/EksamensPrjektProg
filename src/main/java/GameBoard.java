import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class GameBoard {
    PApplet p;

    //til bord klassen
    ArrayList<Tile> tileSet = new ArrayList<Tile>();
    ArrayList<Tile> showneTileSet = new ArrayList<Tile>();

    Player player = new Player(16,16,0,0);
    Boolean visible = false;
    GameBoard(PApplet p){
        this.p = p;

    }

    void drawBoard(){
        if(visible){
        p.clear();
        p.background(200);
        fillUpShownTiles();

        for(int i = 0;i<showneTileSet.size();++i){
            int posX = showneTileSet.get(i).xPos;
            int posY = showneTileSet.get(i).yPos;

            if(i<5){
                posX = 1;
                posY = i+1;
            }else if(i<10) {
                posX = 2;
                posY = i-4;
            }else if(i<15){
                posX = 3;
                posY = i-9;
            }else if(i<20){
                posX = 4;
                posY = i-14;
            }else{
                posX = 5;
                posY = i-19;
            }/**/



            showneTileSet.get(i).Display(posX,posY,100);
            showneTileSet.get(i).checkIfMouseOver(posX,posY,100);

            if(player.xPos == showneTileSet.get(i).xPos && player.yPos == showneTileSet.get(i).yPos){
                p.ellipse(100*posX + 50,100*posY+ 50,100,100);
            }

            if(showneTileSet.get(i).cliked){
                player.xPos =showneTileSet.get(i).xPos;
                player.yPos =showneTileSet.get(i).yPos;
                //playerPos = new PVector(showneTileSet.get(i).xPos,showneTileSet.get(i).yPos);
                showneTileSet.get(i).cliked = false;
            }
        }}
    }

    void fillUpShownTiles(){
        showneTileSet.clear();
        PVector playerPos = new PVector(player.xPos,player.yPos);

        for(int i = 0;i<tileSet.size();++i){
            if(tileSet.get(i).xPos - playerPos.x > -3 && tileSet.get(i).xPos - playerPos.x < 3){

                if(tileSet.get(i).yPos - playerPos.y > -3 && tileSet.get(i).yPos - playerPos.y < 3){

                    showneTileSet.add(tileSet.get(i));
                }
            }

        }
    }

    void boardmouseClicked(){
       if(visible){
           for(int i = 0;i<showneTileSet.size();++i){
            int posX = showneTileSet.get(i).xPos;
            int posY = showneTileSet.get(i).yPos;

               if(i<5){
                   posX = 1;
                   posY = i+1;
               }else if(i<10) {
                   posX = 2;
                   posY = i-4;
               }else if(i<15){
                   posX = 3;
                   posY = i-9;
               }else if(i<20){
                   posX = 4;
                   posY = i-14;
               }else{
                   posX = 5;
                   posY = i-19;
               }/**/

            showneTileSet.get(i).checkIfCliked(posX,posY,100);
        }
    }
    }
}

