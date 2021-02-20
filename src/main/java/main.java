
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import processing.core.PApplet;
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
