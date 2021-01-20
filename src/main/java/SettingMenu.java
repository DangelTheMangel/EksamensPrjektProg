import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PVector;

public class SettingMenu {
    PApplet p;
    Boolean visible = true;
    int displayResolutionInt = 1;
    int lastDisplayResolutionInt = 1;
    public float size = 1;
    PVector[] displayResolution = {new PVector(640,360,0.5f),new PVector(1280,720,1),new PVector(1600,900,1.25f),new PVector(1920,1080,1.5f )};
    // Res  = Resolution
    AlmindeligKnap ResLeft, ResRight, btnBack;
    int screenWidth, screenHeight;
    SettingMenu(PApplet p){
        this.p =p;

        ResLeft = new AlmindeligKnap(p,200,200,50,50,"<");
        ResRight = new AlmindeligKnap(p,650,200,50,50,">");
    }

    void drawMenu(){
        //det skal udrenges ikke det her lort;

        p.textSize(16*size);
        if(visible){
            p.clear();
            p.background(200);
            p.pushMatrix();
            p.text("mx: " + p.mouseX + " my: " + p.mouseY, p.mouseX,p.mouseY);

            String displayInfo = (int)displayResolution[displayResolutionInt].x + " X " + (int)displayResolution[displayResolutionInt].y;
            p.text(displayInfo,
                    (450 - p.textWidth(displayInfo)/2) * size,(230)*size);


            ResLeft.tegnKnap();
            ResRight.tegnKnap();
            screenResManger();
            p.popMatrix();
        }
    }
    void screenResManger(){

        if(ResLeft.erKlikket()) {
            lastDisplayResolutionInt = displayResolutionInt;
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

                size =  displayResolution[displayResolutionInt].z;

           // RestSettings();
            reSizeMenu(size);
            System.out.println("size: " + size);
            ResLeft.registrerRelease();
        }

        if(ResRight.erKlikket()) {
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

                size =  displayResolution[displayResolutionInt].z;


           // RestSettings();
            reSizeMenu(size);
            System.out.println("size: " + size);
            ResRight.registrerRelease();

        }

    }
    
    public static final javax.swing.JFrame getJFrame(final PSurface surface) {
        return (javax.swing.JFrame) ( (processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
    }

    void reSizeMenu(float s){
        reSizeBtn(s,ResLeft);
        reSizeBtn(s,ResRight);
    }

    public void reSizeBtn(float s, Knap btn){
        btn.size = s;



    }


    void menuMouseClick(){
        if(visible){
            System.out.println(" my: " + p.mouseX  +" my: " +p.mouseY);
            ResLeft.registrerKlik(p.mouseX,p.mouseY);
            ResRight.registrerKlik(p.mouseX,p.mouseY);
        }
    }

    void menuKeyTyped(){
        if(visible){

        }
    }
}
