import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PVector;

public class SettingMenu {
    PApplet p;
    Boolean visible = true;
    int displayResolutionInt = 0;
    PVector[] displayResolution = {new PVector(480,234),new PVector(640,360),new PVector(1280,720),new PVector(1600,900),new PVector(1920,1080)};
    // Res  = Resolution
    AlmindeligKnap ResLeft, ResRight, btnBack;
    int screenWidth, screenHeight;
    SettingMenu(PApplet p){
        this.p =p;

        ResLeft = new AlmindeligKnap(p,200,200,50,50,"<");
        ResRight = new AlmindeligKnap(p,650,200,50,50,">");
    }

    void drawMenu(){
        if(visible){
            p.clear();
            p.background(200);
            String displayInfo = (int)displayResolution[displayResolutionInt].x + " X " + (int)displayResolution[displayResolutionInt].y;
            p.text(displayInfo,
                    450 - p.textWidth(displayInfo)/2,230);


            ResLeft.tegnKnap();
            ResRight.tegnKnap();
            screenResManger();
        }
    }
    void screenResManger(){

        if(ResLeft.erKlikket()) {
            displayResolutionInt--;
            if (displayResolutionInt<0)
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
           // RestSettings();
            ResLeft.registrerRelease();
        }

        if(ResRight.erKlikket()) {
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
           // RestSettings();
            System.out.println("abe");
            ResRight.registrerRelease();

        }

    }
    
    public static final javax.swing.JFrame getJFrame(final PSurface surface) {
        return (javax.swing.JFrame) ( (processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
    }
    


    void menuMouseClick(){
        if(visible){
            ResLeft.registrerKlik(p.mouseX,p.mouseY);
            ResRight.registrerKlik(p.mouseX,p.mouseY);
        }
    }

    void menuKeyTyped(){
        if(visible){

        }
    }
}
