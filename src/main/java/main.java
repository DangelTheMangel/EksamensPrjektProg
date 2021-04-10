import processing.core.PApplet;
import processing.data.Table;

import java.io.File;

/*
 * så man kan se et terning kast-casper
 * ✔️map generation - Christian
 * ✔️mængde af spiller i settings
 *tilføj lyd
 *✔️Menuer i havnen påvirker i din ryk??? - Christian
 *✔️ esc for at få menuen frem
 *✔️Når man vælger en tile på kortet vælger spillet automatisk ruten derhen - Christian
 * gøre ui pænere - laura/ casper

 * */

public class main extends PApplet {
    public static void main(String[] args) {

        PApplet.main("main");
    }


    public PauseMenu pauseMenu;
    public SettingMenu settingMenu;
    public static MainMenu mainMenu;
    public GameBoard gb;


    @Override
    public void settings() {

        size(1280, 720);
    }


    @Override
    public void setup() {

        settingMenu = new SettingMenu(this);
        mainMenu = new MainMenu(this);
        pauseMenu = new PauseMenu(this, settingMenu, mainMenu);
        gb = new GameBoard(this, pauseMenu);
        gb.visible = true;
        textSize(12);
        text("Loading", width / 2 - textWidth("Loading") / 2, height / 2);


    }

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

    public void keyTyped() {

        settingMenu.menuKeyTyped();
        gb.keyTyped();
    }


}
