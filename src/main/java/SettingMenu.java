import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PVector;

import javax.sound.sampled.FloatControl;

public class SettingMenu {
    //the variables
    PApplet p;
    //menu variables
    Boolean visible = false;
    //determines how far you have come in the display resutlion lists
    int displayResolutionInt = 1;
    //last displayResolutionInt
    int lastDisplayResolutionInt = 1;
    //This variable helps to scale all ui elements with the screen size
    public float size = 1;
    //the list of sounds volume and what to type on the screen
    PVector[] volumes ={new PVector(0,-80),new PVector(25,-20),new PVector(50,0),new PVector(75,3),new PVector(100,6)};
    //this variable tells where on the volume list one has reached
    int volInt =2;
    //the list of the different screen sizes
    PVector[] displayResolution = {new PVector(640, 360, 0.5f), new PVector(1280, 720, 1), new PVector(1600, 900, 1.25f), new PVector(1920, 1080, 1.5f)};
    //quick access to the pause menu
    PauseMenu pauseMenu;
    //all text fields
    TextField tfNumbersOfPlayers, tfMaxRound,tfGenNum;
    //This one licks if everything has been resized
    boolean backToMainMenu = true;
    //All the buttons
    NomalButton ResLeft, ResRight, backToMain,btnVolUp,btnVolDown;
    //the size of the screen
    int screenWidth, screenHeight;
    //quick access to gameboard
    GameBoard gb;
    //quick access to savemanger
    SaveManger sm;

    //---------- CONSTRUCTOR :) ----------\\
    SettingMenu(PApplet p) {
        this.p = p;
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
    //----------METHODS----------\\

    //This function draws the menu
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


            btnVolUp.drawButton();
            btnVolDown.drawButton();
            backToMain.drawButton();
            ResLeft.drawButton();
            ResRight.drawButton();
            tfMaxRound.drawField();
            tfNumbersOfPlayers.drawField();
            tfGenNum.drawField();

            screenResManger();


            if(btnVolUp.clicked){
                if(volInt +1 !=  volumes.length){
                    volInt += 1;
                    changeVolume(volumes[volInt].y);
                }
                btnVolUp.registrerRelease();

            }

            if(btnVolDown.clicked){
                if(volInt -1 !=  -1){
                    volInt -= 1;
                    changeVolume(volumes[volInt].y);
                }
                btnVolDown.registrerRelease();

            }


        }
    }

    //This feature changes the volume of the game
    void changeVolume(float volu){
        FloatControl vol = (FloatControl) main.bgmusic.getControl(FloatControl.Type.MASTER_GAIN);
        vol.setValue(volu);


    }

    //this function resizes the screen
    void btnChangeScreen(MainMenu mm, GameBoard gb) {
        if (backToMain.clicked) {

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

    //this function resizes the screen
    void screenResManger() {
        if (visible) {
            if (ResLeft.isClicked()) {
                lastDisplayResolutionInt = displayResolutionInt;
                displayResolutionInt--;
                if (displayResolutionInt < 0)
                    displayResolutionInt = displayResolution.length - 1;

                if (displayResolutionInt == displayResolution.length - 1) {
                    screenWidth = getFrame(p.getSurface()).getX();
                    screenHeight = getFrame(p.getSurface()).getY();
                    p.frame.setLocation(0, 0);
                    p.frame.setSize(p.displayWidth, p.displayHeight);
                } else {
                    p.frame.setLocation(screenWidth, screenHeight);
                    p.frame.setSize((int) displayResolution[displayResolutionInt].x, (int) displayResolution[displayResolutionInt].y);
                }

                size = displayResolution[displayResolutionInt].z;

                reSizeMenu(size);
                System.out.println("size: " + size);
                ResLeft.registrerRelease();
            }

            if (ResRight.isClicked()) {
                lastDisplayResolutionInt = displayResolutionInt;
                displayResolutionInt++;
                if (displayResolutionInt == displayResolution.length)
                    displayResolutionInt = 0;

                if (displayResolutionInt == displayResolution.length - 1) {
                    screenWidth = getFrame(p.getSurface()).getX();
                    screenHeight = getFrame(p.getSurface()).getY();
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

    //this feature adjusts the size of your screen
    public static final javax.swing.JFrame getFrame(final PSurface surface) {
        return (javax.swing.JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
    }

    //This function for all buttons and look to fit with the screen size
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

    //this function resizes the buttons
    public void reSizeBtn(float s, Button btn) {
        btn.size = s;

    }
    //this function resizes the textfeild
    public void reSizeFT(float s, TextField tf) {
        tf.size = s;

    }

    //This function allows you to click on the menu
    void menuMouseClick() {
        if (visible) {
            tfGenNum.registerClick(p.mouseX, p.mouseY);
            ResLeft.registerClick(p.mouseX, p.mouseY);
            ResRight.registerClick(p.mouseX, p.mouseY);
            backToMain.registerClick(p.mouseX, p.mouseY);
            tfNumbersOfPlayers.registerClick(p.mouseX, p.mouseY);
            tfMaxRound.registerClick(p.mouseX, p.mouseY);
            btnVolDown.registerClick(p.mouseX, p.mouseY);
            btnVolUp.registerClick(p.mouseX, p.mouseY);
        }
    }
    //this feature lets you type in the console
    void menuKeyTyped() {
        if (visible) {
            tfGenNum.keyinput(p.key);
            tfNumbersOfPlayers.keyinput(p.key);
            tfMaxRound.keyinput(p.key);

            if (tfGenNum.input.length() > 0) {
                sm.increment = Float.valueOf(tfGenNum.input)/100;
            }
            if (tfNumbersOfPlayers.input.length() > 0) {
                gb.numbersOfCpus = Integer.valueOf(tfNumbersOfPlayers.input);
            }
            if (tfMaxRound.input.length() > 0) {
                gb.maxRounds = Integer.valueOf(tfMaxRound.input);
            }

        }
    }
}
