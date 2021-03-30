
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.Table;

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

/*
* så man kan se et terning kast-casper
* map generation - Christian
* ✔️mængde af spiller i settings
*tilføj lyd
*Menuer i havnen påvirker i din ryk??? - Christian
*✔️ esc for at få menuen frem
*Når man vælger en tile på kortet vælger spillet automatisk ruten derhen - Christian
* gøre ui pænere - laura/ casper

* */

public class main extends PApplet {
    public static void main(String[] args) {

        PApplet.main("main");
    }


    public PauseMenu pauseMenu;
    public SettingMenu settingMenu;
    public static MainMenu mainMenu;

    public GameBoard gb ;
    public static ArrayList<PImage> waterTiles = new ArrayList<>();
    public static float waterAnimeNumber = 0;
    public static ArrayList<PImage> sandTiles = new ArrayList<>();
    public static float sandAnimeNumber = 0;
    boolean forward = true;
    @Override
    public void settings() {

        size(1280,720);
    }


    @Override
    public void setup() {
//cunt
        for(int i = 1 ;i<15;++i){
            PImage photo = loadImage("w"+i+".png");
            waterTiles.add(photo);
        }
        for(int i = 1 ;i<6;++i){
            PImage photo = loadImage("s"+i+".png");
            sandTiles.add(photo);
        }
        settingMenu = new SettingMenu(this);
        mainMenu = new MainMenu(this);
        pauseMenu = new PauseMenu(this,settingMenu,mainMenu);
        gb = new GameBoard(this,pauseMenu);
        gb.saveManger = new SaveManger(this);
        gb.visible = true;


        gb.saveManger.generateGame(32, gb.tileSet);
        gb.saveManger.gb = gb;






    }
   public  void loadedMap(File selection){
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {
            Table map = null;

            try{
                map = loadTable(selection.getAbsolutePath());
                println("User selected " + selection.getAbsolutePath() + " First tile: " + map.getString(0,0));
                gb.saveManger.loadGame(32,map, gb.tileSet);
            }catch (NullPointerException ignored){
                System.out.println(selection.getAbsolutePath());
            }
        }
    }

    float animeTiles(float animNum, ArrayList<PImage> tiles){
        if(animNum >= tiles.size()-1 ){

            animNum = 0;


        }else{

            animNum += 0.025;


        }
        return animNum;
    }
    @Override
    public void draw() {

        waterAnimeNumber = animeTiles(waterAnimeNumber,waterTiles);
        sandAnimeNumber = animeTiles(sandAnimeNumber,sandTiles);
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
    public void keyPressed() {
        if (key == ESC) {
            key = 0;
            if(gb.pauseMenu.visible){
                gb.pauseMenu.aktiverGameBord();
            }else if(gb.visible){
                gb.aktiverPauseMenu();
            }
        }
    }

    public void keyTyped() {

        settingMenu.menuKeyTyped();
    }


}
