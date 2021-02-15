import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PVector;

public class SettingMenu {
    PApplet p;



    Boolean visible = false;
    int displayResolutionInt = 1;
    int lastDisplayResolutionInt = 1;
    public float size = 1;
    PVector[] displayResolution = {new PVector(640,360,0.5f),new PVector(1280,720,1),new PVector(1600,900,1.25f),new PVector(1920,1080,1.5f )};
    // Res  = Resolution
    PauseMenu pauseMenu;
    boolean backToMainMenu = true;
    AlmindeligKnap ResLeft, ResRight, backToMain;
    int screenWidth, screenHeight;
    SettingMenu(PApplet p){
        this.p =p;

        ResLeft = new AlmindeligKnap(p,200,200,50,50,"<");
        ResRight = new AlmindeligKnap(p,650,200,50,50,">");
        backToMain = new AlmindeligKnap(p,540,600,200,50,"Back to Menu");
    }

    void drawMenu(){


        p.textSize(16*size);
        if(visible){

            p.background(200);

            p.text("mx: " + p.mouseX + " my: " + p.mouseY, p.mouseX,p.mouseY);

            String displayInfo = (int)displayResolution[displayResolutionInt].x + " X " + (int)displayResolution[displayResolutionInt].y;
            p.text(displayInfo,
                    (450 - p.textWidth(displayInfo)/2) * size,(230)*size);

            backToMain.tegnKnap();
            ResLeft.tegnKnap();
            ResRight.tegnKnap();
            screenResManger();

        }
    }

    void btnChangeScreen(MainMenu mm,GameBoard gb){
        if(backToMain.klikket){

            if(backToMainMenu ) {
                mm.reSizeMainMenu();
                mm.visible = true;
            }else {
                pauseMenu.reSizePauseMenu();
                pauseMenu.visible = true;
            }
            visible = false;
            backToMain.registrerRelease();
        }
    }
    void screenResManger(){
        if(visible) {
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
        return (javax.swing.JFrame) ( (processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
    }

    void reSizeMenu(float s){
        pauseMenu.gb.scaleSize = s;
        pauseMenu.mainMenu.scaleSize = s;
        reSizeBtn(s,ResLeft);
        reSizeBtn(s,ResRight);
        reSizeBtn(s,backToMain);
    }

    public void reSizeBtn(float s, Knap btn){
        btn.size = s;

    }


    void menuMouseClick(){
        if(visible){

            ResLeft.registrerKlik(p.mouseX,p.mouseY);
            ResRight.registrerKlik(p.mouseX,p.mouseY);
            backToMain.registrerKlik(p.mouseX,p.mouseY);
        }
    }

    void menuKeyTyped(){
        if(visible){

        }
    }
}
