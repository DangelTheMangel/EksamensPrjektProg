import processing.core.PApplet;

import java.io.File;

public class MainMenu {
    PApplet p;
    GameBoard gb;
    PauseMenu pauseMenu;
    SettingMenu settingMenu;
    Boolean visible = false;
    float scaleSize = 1;
    AlmindeligKnap btnPlay, btnSettings,btnHowToPlay, btnLoadGame,btnCloseGame;
    MainMenu(PApplet p){
        this.p =p;

        btnPlay = new AlmindeligKnap(p,640-160,100,320,50,"Spil");
        btnLoadGame = new AlmindeligKnap(p,640-160,160,320,50,"Indlæs Spil");
        btnSettings = new AlmindeligKnap(p,640-160,220,320,50,"Indstillinger");

        btnHowToPlay = new AlmindeligKnap(p,640-160,280,320,50,"Hvordan man spiller");
        btnCloseGame = new AlmindeligKnap(p,640-160,340,320,50,"luk spil");


    }

    void drawMenu(){
        p.clear();
        p.background(200);
        btnPlay.tegnKnap();
        btnSettings.tegnKnap();
        btnLoadGame.tegnKnap();
        btnHowToPlay.tegnKnap();
        btnCloseGame.tegnKnap();

        if(btnPlay.klikket){
            gb.visible = true;
            visible = false;
            btnPlay.registrerRelease();
        }

        if(btnSettings.klikket){
            settingMenu.backToMainMenu = true;
            settingMenu.visible = true;
            visible = false;
            btnSettings.registrerRelease();
        }

        if(btnLoadGame.klikket){
            System.out.println("Bib bab og andre robotlyde");
            p.selectInput("Select a file to process:", "loadedMap");
            btnLoadGame.registrerRelease();
        }

        if(btnHowToPlay.klikket){

            btnHowToPlay.registrerRelease();
        }

        if(btnCloseGame.klikket){
            p.exit();
            btnCloseGame.registrerRelease();

        }


    }



    void reSizeMainMenu(){
        settingMenu.reSizeBtn(scaleSize,btnPlay);
        settingMenu.reSizeBtn(scaleSize,btnSettings);
        settingMenu.reSizeBtn(scaleSize,btnLoadGame);
        settingMenu.reSizeBtn(scaleSize,btnHowToPlay);
        settingMenu.reSizeBtn(scaleSize,btnCloseGame);
    }

    void menuMouseClick(float mx,float my){
        btnPlay.registrerKlik(mx,my);
        btnSettings.registrerKlik(mx,my);
        btnLoadGame.registrerKlik(mx,my);
        btnHowToPlay.registrerKlik(mx,my);
        btnCloseGame.registrerKlik(mx,my);
    }

    void menuKeyTyped(){

    }

}
