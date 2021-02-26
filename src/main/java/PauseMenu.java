import processing.core.PApplet;

public class PauseMenu {
    PApplet p;
    Boolean visible = false;
    GameBoard gb;
    MainMenu mainMenu;
    SettingMenu settingMenu;
    AlmindeligKnap btnResume, btnSettings,btnExitToMain, btnSave;
    PauseMenu(PApplet p,SettingMenu settingMenu,MainMenu mainMenu){
        this.p = p;
        this.mainMenu = mainMenu;
        this.settingMenu = settingMenu;


        mainMenu.settingMenu = settingMenu;
        mainMenu.pauseMenu = this;
        settingMenu.pauseMenu = this;

        btnResume = new AlmindeligKnap(p,640-160,100,320,50,"Forsæt");
        btnSettings = new AlmindeligKnap(p,640-160,160,320,50,"Indstillinger");
        btnSave = new AlmindeligKnap(p,640-160,220,320,50,"Gem Spil");
        btnExitToMain = new AlmindeligKnap(p,640-160,280,320,50,"Tilbage Til Menu");


    }

    void drawMenu(){
    p.clear();
    p.background(200);
    btnResume.tegnKnap();
    btnSettings.tegnKnap();
    btnExitToMain.tegnKnap();
    btnSave.tegnKnap();

    if(btnResume.klikket){
        aktiverGameBord();
    }

    if(btnSettings.klikket){
        settingMenu.backToMainMenu = false;
        settingMenu.visible = true;
        visible = false;
        btnSettings.registrerRelease();
    }

    if(btnExitToMain.klikket){
        btnExitToMain.registrerRelease();
        mainMenu.visible = true;
        visible = false;
    }

    if(btnSave.klikket){
        gb.saveManger.saveGame(32,gb.tileSet);
        btnSave.registrerRelease();
    }



    }

    void reSizePauseMenu(){

        float size =settingMenu.displayResolution[settingMenu.displayResolutionInt].z;
        settingMenu.reSizeBtn(size,btnResume);
        settingMenu.reSizeBtn(size,btnSettings);
        settingMenu.reSizeBtn(size,btnExitToMain);
        settingMenu.reSizeBtn(size,btnSave);
    }

    void clickMenu(float mx,float my){
        btnResume.registrerKlik(mx,my);
        btnSettings.registrerKlik(mx,my);
        btnExitToMain.registrerKlik(mx,my);
        btnSave.registrerKlik(mx,my);
    }

    void aktiverGameBord(){
        gb.visible = true;
        gb.reSizeGamebord();
        visible = false;
        btnResume.registrerRelease();
    }
}
