import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PVector;

import javax.sound.sampled.Control;
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
    Textfeld tfNumbersOfPlayers, tfMaxRound,tfGenNum;
    boolean backToMainMenu = true;
    AlmindeligKnap ResLeft, ResRight, backToMain,btnVolUp,btnVolDown;
    int screenWidth, screenHeight;
    GameBoard gb;
    SaveManger sm;

    SettingMenu(PApplet p) {
        this.p = p;
        //sds
        btnVolDown = new AlmindeligKnap(p, 200, 100, 50, 50, "<");
        btnVolUp = new AlmindeligKnap(p, 650, 100, 50, 50, ">");
        ResLeft = new AlmindeligKnap(p, 200, 200, 50, 50, "<");
        ResRight = new AlmindeligKnap(p, 650, 200, 50, 50, ">");
        tfNumbersOfPlayers = new Textfeld(p, 200, 400, 200, 50, "Antal af modspiller");
        tfNumbersOfPlayers.acceptLetters = false;

        tfMaxRound = new Textfeld(p, 200, 500, 200, 50, "Antal Rundter");
        tfMaxRound.acceptLetters = false;

        tfGenNum = new Textfeld(p, 200, 600, 200, 50, "Generation nummer");
        tfGenNum.acceptLetters = false;

        backToMain = new AlmindeligKnap(p, 540, 600, 200, 50, "Back to Menu");
    }

    void drawMenu() {


        p.textSize(16 * size);
        if (visible) {

            p.background(200);

            p.text("mx: " + p.mouseX + " my: " + p.mouseY, p.mouseX, p.mouseY);

            String displayInfo = (int) displayResolution[displayResolutionInt].x + " X " + (int) displayResolution[displayResolutionInt].y;
            p.text(displayInfo,
                    (450 - p.textWidth(displayInfo) / 2) * size, (230) * size);
            btnVolUp.tegnKnap();
            btnVolDown.tegnKnap();
            backToMain.tegnKnap();
            ResLeft.tegnKnap();
            ResRight.tegnKnap();
            tfMaxRound.tegnTextFlet();
            tfNumbersOfPlayers.tegnTextFlet();
            tfGenNum.tegnTextFlet();

            screenResManger();

            String volInfo = volumes[volInt].x +"%";
            p.text(volInfo,
                    (450 - p.textWidth(displayInfo) / 2) * size, (130) * size);
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

    public void reSizeBtn(float s, Knap btn) {
        btn.size = s;

    }

    public void reSizeFT(float s, Textfeld tf) {
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
