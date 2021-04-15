import processing.core.PApplet;

public class chooseGameMenu {
    PApplet p;
    GameBoard gb;
    MainMenu mainMenu;
    Boolean visible = false;
    Boolean needNytSpil = false;
    AlmindeligKnap btnPlay, btnLoadGame, btnNewGame, btnBackToMenu;

    chooseGameMenu(PApplet p, GameBoard gb, MainMenu mainMenu) {
        this.p = p;
        this.gb = gb;
        this.mainMenu = mainMenu;
        btnPlay = new AlmindeligKnap(p, 640 - 160, 100, 320, 50, "Forsæt spil");
        btnLoadGame = new AlmindeligKnap(p, 640 - 160, 160, 320, 50, "Indlæs Spil");
        btnNewGame = new AlmindeligKnap(p, 640 - 160, 220, 320, 50, "Nyt Spil");//280
        btnBackToMenu = new AlmindeligKnap(p, 640 - 160, 280, 320, 50, "Tilbage");


    }

    void drawMenu() {
        p.clear();
        p.image(main.bg,0,0,p.width,p.height);
        btnPlay.tegnKnap();


        btnLoadGame.tegnKnap();
        btnNewGame.tegnKnap();
        btnBackToMenu.tegnKnap();
        if (btnPlay.klikket) {
             /*   gb.visible = true;
                visible = false;
                btnPlay.registrerRelease();*/
            gb.scaleSize = mainMenu.scaleSize;
            gb.reSizeGamebord();
            gb.visible = true;
            mainMenu.visible = false;
            visible = false;
            btnPlay.registrerRelease();
        }

        if (btnLoadGame.klikket) {
            System.out.println("Bib bab og andre robotlyde");
            p.selectInput("Select a file to process:", "loadedMap");
            btnLoadGame.registrerRelease();
        }

        if (btnNewGame.klikket) {
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
            needNytSpil = true;
            btnNewGame.registrerRelease();
        }

        if (btnBackToMenu.klikket) {
            mainMenu.visible = true;
            visible = false;
            btnBackToMenu.registrerRelease();
        }


    }
}
