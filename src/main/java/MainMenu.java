import processing.core.PApplet;
import processing.core.PImage;

public class MainMenu {
    //hello mand tjek resize
    PApplet p;
    GameBoard gb;
    PauseMenu pauseMenu;
    chooseGameMenu chooseGameMenu;
    SettingMenu settingMenu;
    Boolean visible = true;
    Boolean howToPlay = false;
    float scaleSize = 1;
    PImage bg,howTo;
    AlmindeligKnap btnPlay, btnSettings, btnHowToPlay, btnLoadGame, btnCloseGame,backToMain;

    MainMenu(PApplet p) {
        this.p = p;

        btnPlay = new AlmindeligKnap(p, 640 - 160, 260, 150, 50, "Spil");
        btnLoadGame = new AlmindeligKnap(p, 640 + 170 - 160, 260, 150, 50, "Indlæs Spil");
        btnSettings = new AlmindeligKnap(p, 640 - 160, 340, 320, 50, "Indstillinger");
        btnHowToPlay = new AlmindeligKnap(p, 640 - 160, 420, 320, 50, "Hvordan man spiller");
        btnCloseGame = new AlmindeligKnap(p, 640 - 160, 500, 320, 50, "luk spil");
        chooseGameMenu = new chooseGameMenu(p, gb, this);
        backToMain = new AlmindeligKnap(p, 540, 600, 200, 50, "Back to Menu");
        bg = p.loadImage("startside.png");
        howTo= p.loadImage("w4.png");
    }

    void drawMenu() {
        if (chooseGameMenu.visible&& !howToPlay) {
            chooseGameMenu.drawMenu();
        }

        if (!chooseGameMenu.visible && !howToPlay) {
            p.clear();
            p.image(bg,0,0,p.width,p.height);
            btnPlay.tegnKnap();
            btnSettings.tegnKnap();
            btnLoadGame.tegnKnap();
            btnHowToPlay.tegnKnap();
            btnCloseGame.tegnKnap();

            if (btnPlay.klikket) {


                chooseGameMenu.visible = true;

                btnPlay.registrerRelease();
            }

            if (btnSettings.klikket) {
                settingMenu.backToMainMenu = true;
                settingMenu.visible = true;
                visible = false;
                btnSettings.registrerRelease();
            }

            if (btnLoadGame.klikket) {

                p.selectInput("Select a file to process:", "loadedMap");
                btnLoadGame.registrerRelease();
            }

            if (btnHowToPlay.klikket) {
                howToPlay = true;
                btnHowToPlay.registrerRelease();
            }

            if (btnCloseGame.klikket) {
                p.exit();
                btnCloseGame.registrerRelease();

            }

        }else if(howToPlay) {
            p.image(howTo,0,0,p.width,p.height);
            backToMain.tegnKnap();
            if (backToMain.klikket){
                howToPlay = false;
                backToMain.registrerRelease();
            }
        }


    }


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
        settingMenu.reSizeBtn(scaleSize,backToMain);
    }

    void menuMouseClick(float mx, float my) {
        if (!chooseGameMenu.visible&& !howToPlay) {

            btnPlay.registrerKlik(mx, my);;
            btnSettings.registrerKlik(mx, my);
            btnLoadGame.registrerKlik(mx, my);
            btnHowToPlay.registrerKlik(mx, my);
            btnCloseGame.registrerKlik(mx, my);
        } else if(!howToPlay) {

                chooseGameMenu.btnPlay.registrerKlik(mx, my);
            chooseGameMenu.btnLoadGame.registrerKlik(mx, my);
            chooseGameMenu.btnBackToMenu.registrerKlik(mx, my);
            chooseGameMenu.btnNewGame.registrerKlik(mx, my);
        }else if(howToPlay){
            backToMain.registrerKlik(mx,my);
        }

    }

    void menuKeyTyped() {

    }

}
