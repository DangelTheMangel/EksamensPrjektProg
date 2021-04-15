import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PVector;

import javax.sound.sampled.FloatControl;

public class SettingMenu {
    PApplet p;


    Boolean visible = false;
    int displayResolutionInt = 1;
    int lastDisplayResolutionInt = 1;
    public float size = 1;
    PVector[] volumes ={new PVector(0,-80),new PVector(25,-20),new PVector(50,0),new PVector(75,3),new PVector(100,6)};
    int volInt =2;
    PVector[] displayResolution = {new PVector(640, 360, 0.5f), new PVector(1280, 720, 1), new PVector(1600, 900, 1.25f), new PVector(1920, 1080, 1.5f)};
    // Res  = Resolution
    PauseMenu pauseMenu;
    TextField tfNumbersOfPlayers, tfMaxRound,tfGenNum;
    boolean backToMainMenu = true;
    NomalButton ResLeft, ResRight, backToMain,btnVolUp,btnVolDown;
    int screenWidth, screenHeight;
    GameBoard gb;
    SaveManger sm;

    SettingMenu(PApplet p) {
        this.p = p;
        //sds
        btnVolDown = new NomalButton(p, 320, 100, 50, 50, "<");
        btnVolUp = new NomalButton(p, 910, 100, 50, 50, ">");

        ResLeft = new NomalButton(p, 320, 250, 50, 50, "<");
        ResRight = new NomalButton(p, 910, 250, 50, 50, ">");


        tfNumbersOfPlayers = new TextField(p, 200, 450, 200, 50, "Antal af modspiller");
        tfNumbersOfPlayers.acceptLetters = false;
        tfMaxRound = new TextField(p, 540, 450, 200, 50, "Antal Rundter");
        tfMaxRound.acceptLetters = false;
        tfGenNum = new TextField(p, p.width-400, 450, 200, 50, "Generation nummer");
        tfGenNum.acceptLetters = false;
        backToMain = new NomalButton(p, 540, 600, 200, 50, "Back to Menu");
    }

    void drawMenu() {



        if (visible) {

            p.image(main.bg,0,0,p.width,p.height);
            p.textSize(32 * size);
            p.text("Musikvolumen:", 320*size,70*size);
            p.text("Skærmopløsning:", 320*size,230*size);
            p.text("Spille Instillinger:", 320*size,410*size);
            p.textSize(16 * size);


            String displayInfo = (int) displayResolution[displayResolutionInt].x + " X " + (int) displayResolution[displayResolutionInt].y;
            p.text(displayInfo,
                    (p.width/2- p.textWidth(displayInfo) / 2) , (280) * size);
            String volInfo = volumes[volInt].x +"%";
            p.text(volInfo,
                    (p.width/2- p.textWidth(displayInfo) / 2) , (130) * size);
            p.textSize(16 * size);


            btnVolUp.tegnKnap();
            btnVolDown.tegnKnap();
            backToMain.tegnKnap();
            ResLeft.tegnKnap();
            ResRight.tegnKnap();
            tfMaxRound.tegnTextFlet();
            tfNumbersOfPlayers.tegnTextFlet();
            tfGenNum.tegnTextFlet();

            screenResManger();


            if(btnVolUp.klikket){
                if(volInt +1 !=  volumes.length){
                    volInt += 1;
                    changeVolume(volumes[volInt].y);
                }
                btnVolUp.registrerRelease();

            }

            if(btnVolDown.klikket){
                if(volInt -1 !=  -1){
                    volInt -= 1;
                    changeVolume(volumes[volInt].y);
                }
                btnVolDown.registrerRelease();

            }


        }
    }
    void changeVolume(float volu){
        FloatControl vol = (FloatControl) main.bgmusic.getControl(FloatControl.Type.MASTER_GAIN);
        vol.setValue(volu);


    }
    void btnChangeScreen(MainMenu mm, GameBoard gb) {
        if (backToMain.klikket) {

            if (backToMainMenu) {
                mm.reSizeMainMenu();
                mm.visible = true;
            } else {
                pauseMenu.reSizePauseMenu();
                pauseMenu.visible = true;
            }
            visible = false;
            backToMain.registrerRelease();
        }
    }

    void screenResManger() {
        if (visible) {
            if (ResLeft.erKlikket()) {
                lastDisplayResolutionInt = displayResolutionInt;
                displayResolutionInt--;
                if (displayResolutionInt < 0)
                    displayResolutionInt = displayResolution.length - 1;

                if (displayResolutionInt == displayResolution.length - 1) {
                    screenWidth = getJFrame(p.getSurface()).getX();
                    screenHeight = getJFrame(p.getSurface()).getY();
                    p.frame.setLocation(0, 0);
                    p.frame.setSize(p.displayWidth, p.displayHeight);
                } else {
                    p.frame.setLocation(screenWidth, screenHeight);
                    p.frame.setSize((int) displayResolution[displayResolutionInt].x, (int) displayResolution[displayResolutionInt].y);
                }

                size = displayResolution[displayResolutionInt].z;

                // RestSettings();
                reSizeMenu(size);
                System.out.println("size: " + size);
                ResLeft.registrerRelease();
            }

            if (ResRight.erKlikket()) {
                lastDisplayResolutionInt = displayResolutionInt;
                displayResolutionInt++;
                if (displayResolutionInt == displayResolution.length)
                    displayResolutionInt = 0;

                if (displayResolutionInt == displayResolution.length - 1) {
                    screenWidth = getJFrame(p.getSurface()).getX();
                    screenHeight = getJFrame(p.getSurface()).getY();
                    p.frame.setLocation(0, 0);
                    p.frame.setSize(p.displayWidth, p.displayHeight);
                } else {
                    p.frame.setLocation(screenWidth, screenHeight);
                    p.frame.setSize((int) displayResolution[displayResolutionInt].x, (int) displayResolution[displayResolutionInt].y);
                }

                size = displayResolution[displayResolutionInt].z;


                // RestSettings();
                reSizeMenu(size);
                System.out.println("size: " + size);
                ResRight.registrerRelease();

            }


        }
    }

    public static final javax.swing.JFrame getJFrame(final PSurface surface) {
        return (javax.swing.JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
    }

    void reSizeMenu(float s) {
        pauseMenu.gb.scaleSize = s;
        pauseMenu.mainMenu.scaleSize = s;
        reSizeBtn(s, ResLeft);
        reSizeBtn(s, ResRight);
        reSizeBtn(s, backToMain);
        reSizeFT(s, tfNumbersOfPlayers);
        reSizeFT(s, tfMaxRound);
        reSizeFT(s,tfGenNum);
        reSizeBtn(s,btnVolDown);
        reSizeBtn(s,btnVolUp);

    }

    public void reSizeBtn(float s, Button btn) {
        btn.size = s;

    }

    public void reSizeFT(float s, TextField tf) {
        tf.size = s;

    }


    void menuMouseClick() {
        if (visible) {
            tfGenNum.KlikTjek(p.mouseX, p.mouseY);
            ResLeft.registrerKlik(p.mouseX, p.mouseY);
            ResRight.registrerKlik(p.mouseX, p.mouseY);
            backToMain.registrerKlik(p.mouseX, p.mouseY);
            tfNumbersOfPlayers.KlikTjek(p.mouseX, p.mouseY);
            tfMaxRound.KlikTjek(p.mouseX, p.mouseY);
            btnVolDown.registrerKlik(p.mouseX, p.mouseY);
            btnVolUp.registrerKlik(p.mouseX, p.mouseY);
        }
    }

    void menuKeyTyped() {
        if (visible) {
            tfGenNum.keyindput(p.key);
            tfNumbersOfPlayers.keyindput(p.key);
            tfMaxRound.keyindput(p.key);

            if (tfGenNum.indput.length() > 0) {
                sm.increment = Float.valueOf(tfGenNum.indput)/100;
            }
            if (tfNumbersOfPlayers.indput.length() > 0) {
                gb.numbersOfCpus = Integer.valueOf(tfNumbersOfPlayers.indput);
            }
            if (tfMaxRound.indput.length() > 0) {
                gb.maxRounds = Integer.valueOf(tfMaxRound.indput);
            }

        }
    }
}
