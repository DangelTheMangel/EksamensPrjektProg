import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class Cpu extends Boat {


    public Cpu(PApplet p, float x, float y, float s, float m, ArrayList<Item> inventory) {
        super(p, x, y, s, m, inventory);
    }

    void Turn(){
        boolean canSeeShop = false;
        for(int i = 0; i<showneTileSet.size()-1;++i){
            if(showneTileSet.get(i).Contents.equals("SHOP")){
                canSeeShop = true;
            }
            }
        if(canSeeShop && Math.random()<0.5){
            System.out.println("Valgte at shoppe");
            shop();
        }else {
            System.out.println("Valgte at rykke");
            Move();
        }


    }
    void shop(){
        System.out.println("jeg køber det hele");
    }
    void Move(){
        int xMove =(int) p.random(-7,7);
        int yMove =(int) p.random(-7,7);
        float newX = (int) (xPos +xMove);
        float newY = (int) (yPos +yMove);
        boolean haveFoundMove = false;
        if(Math.random() > 0.5){

            for(int i = 0; i<showneTileSet.size()-1;++i){
               if(showneTileSet.get(i).xPos == newX && showneTileSet.get(i).yPos == newY && showneTileSet.get(i).Contents.equals("WATER")){
                   haveFoundMove = true;
                   xPos = newX;
                   yPos = newY;
               }
            }

        }else if(Math.random() > 0.5){
            for(int i = 0; i<showneTileSet.size()-1;++i){
                if(showneTileSet.get(i).xPos == newX && showneTileSet.get(i).yPos == yPos && showneTileSet.get(i).Contents.equals("WATER")){
                    haveFoundMove = true;
                    xPos = newX;

                }
            }
        }else{
            for(int i = 0; i<showneTileSet.size()-1;++i){
                if(showneTileSet.get(i).xPos == xPos && showneTileSet.get(i).yPos == newY && showneTileSet.get(i).Contents.equals("WATER")){
                    haveFoundMove = true;
                    yPos = newY;
                }
            }
        }

        if(haveFoundMove == false){
            Move();
        }

    }
}
