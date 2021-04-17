import processing.core.PApplet;

public class PauseMenu {

    //menu variables
    PApplet p;
    //Boolean that tells about the menu can be seen
    Boolean visible = false;
    //quick access to the other menus
    GameBoard gb;
    MainMenu mainMenu;
    SettingMenu settingMenu;
    //all the buttons
    NomalButton btnResume, btnSettings, btnExitToMain, btnSave;

    //---------- CONSTRUCTOR :) ----------\\
    PauseMenu(PApplet p, SettingMenu settingMenu, MainMenu mainMenu) {
        this.p = p;
        this.mainMenu = mainMenu;
        this.settingMenu = settingMenu;


        mainMenu.settingMenu = settingMenu;
        mainMenu.pauseMenu = this;
        settingMenu.pauseMenu = this;

        btnResume = new NomalButton(p, 640 - 160, 100, 320, 50, "Forsæt");
        btnSettings = new NomalButton(p, 640 - 160, 160, 320, 50, "Indstillinger");
        btnSave = new NomalButton(p, 640 - 160, 220, 320, 50, "Gem Spil");
        btnExitToMain = new NomalButton(p, 640 - 160, 280, 320, 50, "Tilbage Til Menu");


    }
    //----------METHODS----------\\

    //This function draws the menu
    void drawMenu() {
        p.clear();
        p.image(main.bg,0,0,p.width,p.height);
        btnResume.drawButton();
        btnSettings.drawButton();
        btnExitToMain.drawButton();
        btnSave.drawButton();

        if (btnResume.clicked) {
            aktiverGameBord();
        }

        if (btnSettings.clicked) {
            settingMenu.backToMainMenu = false;
            settingMenu.visible = true;
            visible = false;
            btnSettings.registrerRelease();
        }

        if (btnExitToMain.clicked) {
            btnExitToMain.registrerRelease();
            mainMenu.chooseGameMenu.needNewGame = false;
            mainMenu.visible = true;
            visible = false;
        }

        if (btnSave.clicked) {
            gb.saveManger.saveGame(32, gb.tileSet);
            btnSave.registrerRelease();
        }


    }

    //This function for all buttons and look to fit with the screen size
    void reSizePauseMenu() {

        float size = settingMenu.displayResolution[settingMenu.displayResolutionInt].z;
        settingMenu.reSizeBtn(size, btnResume);
        settingMenu.reSizeBtn(size, btnSettings);
        settingMenu.reSizeBtn(size, btnExitToMain);
        settingMenu.reSizeBtn(size, btnSave);
    }

    //This function allows you to click on the menu
    void clickMenu(float mx, float my) {
        btnResume.registerClick(mx, my);
        btnSettings.registerClick(mx, my);
        btnExitToMain.registerClick(mx, my);
        btnSave.registerClick(mx, my);
    }

    //This function activates the game board
    void aktiverGameBord() {
        gb.visible = true;
        gb.reSizeGamebord();
        visible = false;
        btnResume.registrerRelease();
    }
}
