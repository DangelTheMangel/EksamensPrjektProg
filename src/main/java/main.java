
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.Table;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class main extends PApplet {
    public static void main(String[] args) {

        PApplet.main("main");
    }
    //background music
    public static Clip bgmusic;
    //all the menus
    public PauseMenu pauseMenu;
    public SettingMenu settingMenu;
    public static MainMenu mainMenu;
    //The play the board
    public GameBoard gb;
    //background image
    public static PImage bg;

    @Override
    public void settings() {
        //sets the size of the window
        size(1280, 720);
    }


    //sets up the game by loading music, pictures and constructing all the classes
    @Override
    public void setup() {
        bg = loadImage("Tegnebraet 2.png");
        settingMenu = new SettingMenu(this);
        mainMenu = new MainMenu(this);
        pauseMenu = new PauseMenu(this, settingMenu, mainMenu);
        gb = new GameBoard(this, pauseMenu);

        textSize(12);
        text("Loading", width / 2 - textWidth("Loading") / 2, height / 2);
        try {
            File file = new File("Music/bg.wav");
            Scanner scanner = new Scanner(System.in);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            bgmusic = AudioSystem.getClip();
            bgmusic.open(audioInputStream);
            bgmusic.loop(Clip.LOOP_CONTINUOUSLY);




        } catch (Exception e) {
            e.printStackTrace();
        }





    }



    //loaded mappt from csv file vilel put it in savemanger but it pde could not figure out
    public void loadedMap(File selection) {
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {
            Table map = null;

            try {
                map = loadTable(selection.getAbsolutePath());
                println("User selected " + selection.getAbsolutePath() + " First tile: " + map.getString(0, 0));
                gb.saveManger.loadGame(32, map, gb.tileSet);
            } catch (NullPointerException ignored) {
                System.out.println(selection.getAbsolutePath());
            }
        }
    }

    //This function is suitable for anything on the window
    @Override
    public void draw() {

        clear();
        background(200);

        if (settingMenu.visible) {
            settingMenu.drawMenu();
        }
        if (gb.visible) {
            gb.drawBoard();
        }
        if (pauseMenu.visible) {
            pauseMenu.drawMenu();
        }

        if (mainMenu.visible) {
            mainMenu.drawMenu();

        }


    }

    //Do this function when the mouse is clicked
    @Override
    public void mouseClicked() {

        if (settingMenu.visible) {

            settingMenu.menuMouseClick();
            settingMenu.btnChangeScreen(mainMenu, gb);
        }
        if (gb.visible) {
            gb.boardmouseClicked();
        }

        if (mainMenu.visible) {
            mainMenu.menuMouseClick(mouseX, mouseY);
        }

        if (pauseMenu.visible) {
            pauseMenu.clickMenu(mouseX, mouseY);
        }

    }

    //This function runs when the keyboard is clicked
    @Override
    public void keyPressed() {

        if (key == ESC) {
            key = 0;
            if (gb.pauseMenu.visible) {
                gb.pauseMenu.aktiverGameBord();
            } else if (gb.visible) {
                gb.aktiverPauseMenu();
            }

        }

        if (keyCode == 0) {
            gb.akitverDevconsole();
        }

    }

    //This function runs when the keyboard is clicked
    public void keyTyped() {

        settingMenu.menuKeyTyped();
        gb.keyTyped();
    }


}
