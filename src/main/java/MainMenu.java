import processing.core.PApplet;
import processing.core.PImage;

public class MainMenu {
    //main menu variables
    PApplet p;
    //quick access to all the menus and the like
    GameBoard gb;
    PauseMenu pauseMenu;
    ChooseGameMenu chooseGameMenu;
    SettingMenu settingMenu;
    //boolean which determines whether the main menu can be viewed
    Boolean visible = true;
    //This variable is the one that determines whether the howTopla image should be viewed
    Boolean howToPlay = false;
    //This variable helps to scale all ui elements with the screen size
    float scaleSize = 1;
    //the image of the background and description of how to play
    PImage bg, howTo;
    //all the buttons
    NomalButton btnPlay, btnSettings, btnHowToPlay, btnLoadGame, btnCloseGame, backToMain;

    //---------- CONSTRUCTOR :) ----------\\
    MainMenu(PApplet p) {
        this.p = p;

        btnPlay = new NomalButton(p, 640 - 160, 260, 150, 50, "Spil");
        btnLoadGame = new NomalButton(p, 640 + 170 - 160, 260, 150, 50, "Indlæs Spil");
        btnSettings = new NomalButton(p, 640 - 160, 340, 320, 50, "Indstillinger");
        btnHowToPlay = new NomalButton(p, 640 - 160, 420, 320, 50, "Hvordan man spiller");
        btnCloseGame = new NomalButton(p, 640 - 160, 500, 320, 50, "luk spil");
        chooseGameMenu = new ChooseGameMenu(p, gb, this);
        backToMain = new NomalButton(p, 540, 600, 200, 50, "Back to Menu");
        bg = p.loadImage("startside.png");
        howTo = p.loadImage("how.png");
    }
    //----------METHODS----------\\

    //This function draws the menu
    void drawMenu() {
        if (chooseGameMenu.visible && !howToPlay) {
            chooseGameMenu.drawMenu();
        }

        if (!chooseGameMenu.visible && !howToPlay) {
            p.clear();
            p.image(bg, 0, 0, p.width, p.height);
            btnPlay.drawButton();
            btnSettings.drawButton();
            btnLoadGame.drawButton();
            btnHowToPlay.drawButton();
            btnCloseGame.drawButton();

            if (btnPlay.clicked) {


                chooseGameMenu.visible = true;

                btnPlay.registrerRelease();
            }

            if (btnSettings.clicked) {
                settingMenu.backToMainMenu = true;
                settingMenu.visible = true;
                visible = false;
                btnSettings.registrerRelease();
            }

            if (btnLoadGame.clicked) {

                p.selectInput("Select a file to process:", "loadedMap");
                btnLoadGame.registrerRelease();
            }

            if (btnHowToPlay.clicked) {
                howToPlay = true;
                btnHowToPlay.registrerRelease();
            }

            if (btnCloseGame.clicked) {
                p.exit();
                btnCloseGame.registrerRelease();

            }

        } else if (howToPlay) {
            p.image(howTo, 0, 0, p.width, p.height);
            backToMain.drawButton();
            if (backToMain.clicked) {
                howToPlay = false;
                backToMain.registrerRelease();
            }
        }


    }

    //This function for all buttons and look to fit with the screen size
    void reSizeMainMenu() {

        settingMenu.reSizeBtn(scaleSize, btnPlay);
        settingMenu.reSizeBtn(scaleSize, btnSettings);
        settingMenu.reSizeBtn(scaleSize, btnLoadGame);
        settingMenu.reSizeBtn(scaleSize, btnHowToPlay);
        settingMenu.reSizeBtn(scaleSize, btnCloseGame);

        settingMenu.reSizeBtn(scaleSize, chooseGameMenu.btnBackToMenu);
        settingMenu.reSizeBtn(scaleSize, chooseGameMenu.btnLoadGame);
        settingMenu.reSizeBtn(scaleSize, chooseGameMenu.btnNewGame);
        settingMenu.reSizeBtn(scaleSize, chooseGameMenu.btnPlay);
        settingMenu.reSizeBtn(scaleSize, backToMain);
    }

    //This function allows you to click on the menu
    void menuMouseClick(float mx, float my) {
        if (!chooseGameMenu.visible && !howToPlay) {

            btnPlay.registerClick(mx, my);

            btnSettings.registerClick(mx, my);
            btnLoadGame.registerClick(mx, my);
            btnHowToPlay.registerClick(mx, my);
            btnCloseGame.registerClick(mx, my);
        } else if (!howToPlay) {

            chooseGameMenu.btnPlay.registerClick(mx, my);
            chooseGameMenu.btnLoadGame.registerClick(mx, my);
            chooseGameMenu.btnBackToMenu.registerClick(mx, my);
            chooseGameMenu.btnNewGame.registerClick(mx, my);
        } else if (howToPlay) {
            backToMain.registerClick(mx, my);
        }

    }
}
