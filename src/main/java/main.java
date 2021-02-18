
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import processing.core.PApplet;
import processing.core.PVector;

import javax.print.DocFlavor;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class main extends PApplet {
    public static void main(String[] args) {

        PApplet.main("main");


    }


    public PauseMenu pauseMenu;
    public SettingMenu settingMenu;
    public static MainMenu mainMenu;
    public GameBoard gb ;
    @Override
    public void settings() {

        size(1280,720);
    }


    @Override
    public void setup() {


        settingMenu = new SettingMenu(this);
        mainMenu = new MainMenu(this);
        pauseMenu = new PauseMenu(this,settingMenu,mainMenu);
        gb = new GameBoard(this,pauseMenu);
        gb.visible = true;
        gb.player.boatPic = loadImage("Skibet32.png");
        gb.cpu.boatPic = loadImage("Skibet32.png");



        GenerateMap(32);


    }
   public  void loadedMap(File selection){
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {
            
            println("User selected " + selection.getAbsolutePath());
        }
    }
    void GenerateMap(int NumberoFTiles){
        PVector[] shopLoc = {new PVector(16,16),new PVector(0,0),new PVector(0,0) };//new PVector(15,17),new PVector(17,17)};
       /* for(int i=0; i<3;++i){
            int px = (int) random(1,33);
            int py = (int) random(1,33);
            shopLoc[i] = new PVector(px,py);
        }*/

        for(int x = -1;x<NumberoFTiles+3;++x) {
            for (int j = -1; j < NumberoFTiles+3; ++j){
                Tile t = new TerrainTile(this,"",x ,j );
                if((x <= 0 || j<= 0)||(33 <= x || 33<= j)){
                    t.Contents = "BORDER";
                }else{
                    if(Math.random() > 0.3){
                        t.Contents ="WATER";
                    }else{
                        t.Contents ="SAND";
                    }
                }
                for(int e=0; e<3;++e){
                    if(x == shopLoc[e].x&& j== shopLoc[e].y){
                        System.out.println("x: "+ x + "=="+ "Shop: " +shopLoc[e].x + "x: "+ j + "=="+ "Shop: " +shopLoc[e].y );
                        t = new ShopTile(this,"SHOP",x ,j);
                        System.out.println("SHOP:  "+x + " x " + j);
                    }
                }

                System.out.println(x + " x " + j + "tile contens: " + t.Contents);
                gb.tileSet.add(t);}
        }

    }

    @Override
    public void draw() {
        clear();
        background(200);
        if(settingMenu.visible){
            settingMenu.drawMenu();
        }
        if(gb.visible){
            gb.drawBoard();
        }
        if(pauseMenu.visible){
            pauseMenu.drawMenu();
        }

        if(mainMenu.visible){
            mainMenu.drawMenu();

        }



    }



    @Override
    public void mouseClicked() {

        if(settingMenu.visible){

            settingMenu.menuMouseClick();
            settingMenu.btnChangeScreen(mainMenu,gb);
        }
        if(gb.visible){
            gb.boardmouseClicked();
        }

        if(mainMenu.visible){
            mainMenu.menuMouseClick(mouseX,mouseY);
        }

        if(pauseMenu.visible){
            pauseMenu.clickMenu(mouseX,mouseY);
        }

    }

    @Override
    public void keyTyped() {

        //settingMenu.menuKeyTyped();
    }
}
