import processing.core.PApplet;

public class chooseGameMenu {
    PApplet p;
    GameBoard gb;
    MainMenu mainMenu;
    Boolean visible = false;
    AlmindeligKnap btnPlay,btnLoadGame , btnNewGame, btnBackToMenu;
    chooseGameMenu(PApplet p, GameBoard gb, MainMenu mainMenu){
        this.p = p;
        this.gb = gb;
        this.mainMenu = mainMenu;
        btnPlay = new AlmindeligKnap(p,640-160,100,320,50,"Forsæt spil");
        btnLoadGame = new AlmindeligKnap(p,640-160,160,320,50,"Indlæs Spil");
        btnNewGame = new AlmindeligKnap(p,640-160,220,320,50,"Nyt Spil");//280
        btnBackToMenu = new AlmindeligKnap(p,640-160,280,320,50,"Tilbage");


    }

    void drawMenu(){
        p.clear();
        p.background(200);
        btnPlay.tegnKnap();
        btnLoadGame.tegnKnap();
        btnNewGame.tegnKnap();
        btnBackToMenu.tegnKnap();
        if (btnPlay.klikket) {
             /*   gb.visible = true;
                visible = false;
                btnPlay.registrerRelease();*/

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
            gb.tileSet.clear();
            gb.saveManger.generateGame(32, gb.tileSet);
            gb.startGame(gb.numbersOfCpus);
            gb.visible = true;
            mainMenu.visible = false;
            visible = false;
            btnNewGame.registrerRelease();
        }

        if (btnBackToMenu.klikket) {
            mainMenu.visible = true;
            visible = false;
            btnBackToMenu.registrerRelease();
        }


    }
}
