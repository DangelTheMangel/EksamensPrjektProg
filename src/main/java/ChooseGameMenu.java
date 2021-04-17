import processing.core.PApplet;

public class ChooseGameMenu {
    //Menu variables
    PApplet p;
    //This is so the menu can easily access the game board
    GameBoard gb;
    //Quick access to the main menu
    MainMenu mainMenu;

    //About the menu can be seen
    Boolean visible = false;

    //This variable is only used if you have completed a game.
    // Then we would like the users to click on the
    // button that is used to make a new game and not
    // the one that opens the current game. therefore we hide it away
    Boolean needNewGame = false;

    //All menu buttons
    NomalButton btnPlay, btnLoadGame, btnNewGame, btnBackToMenu;

    //---------- CONSTRUCTOR :) ----------\\
    ChooseGameMenu(PApplet p, GameBoard gb, MainMenu mainMenu) {
        this.p = p;
        this.gb = gb;
        this.mainMenu = mainMenu;
        btnPlay = new NomalButton(p, 640 - 160, 100, 320, 50, "Forsæt spil");
        btnLoadGame = new NomalButton(p, 640 - 160, 160, 320, 50, "Indlæs Spil");
        btnNewGame = new NomalButton(p, 640 - 160, 220, 320, 50, "Nyt Spil");//280
        btnBackToMenu = new NomalButton(p, 640 - 160, 280, 320, 50, "Tilbage");


    }

    //----------METHODS----------\\

    //draws the menu
    void drawMenu() {
        p.clear();
        p.image(main.bg,0,0,p.width,p.height);
        if(!needNewGame)
        btnPlay.drawButton();


        btnLoadGame.drawButton();
        btnNewGame.drawButton();
        btnBackToMenu.drawButton();
        if (btnPlay.clicked) {

            gb.scaleSize = mainMenu.scaleSize;
            gb.reSizeGamebord();
            gb.visible = true;
            needNewGame =false;
            mainMenu.visible = false;
            visible = false;
            btnPlay.registrerRelease();
        }

        if (btnLoadGame.clicked) {
            needNewGame =false;
            System.out.println("Bib bab og andre robotlyde");
            p.selectInput("Select a file to process:", "loadedMap");
            btnLoadGame.registrerRelease();
        }

        if (btnNewGame.clicked) {
            needNewGame =false;
            gb.scaleSize = mainMenu.scaleSize;
            gb.reSizeGamebord();
            gb.tileSet.clear();
            gb.saveManger.generateGame(32, gb.tileSet);
            gb.startGame(gb.numbersOfCpus, gb.saveManger.cpuPos);
            gb.visible = true;
            gb.roundCount = 0;
            gb.turnCount = 0;
            mainMenu.visible = false;
            visible = false;
            needNewGame = true;
            btnNewGame.registrerRelease();
        }

        if (btnBackToMenu.clicked) {
            mainMenu.visible = true;
            visible = false;
            btnBackToMenu.registrerRelease();
        }


    }
}
