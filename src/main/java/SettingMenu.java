import processing.core.PApplet;
import processing.core.PVector;

public class SettingMenu {
    PApplet p;
    Boolean visible = true;
    PVector[] displayResolution = {new PVector(480,234),new PVector(640,360),new PVector(1280,720),new PVector(1600,900),new PVector(1920,1080)};


    SettingMenu(PApplet p){
        this.p =p;
    }

    void drawMenu(){
        p.clear();

    }

    void menuMouseClick(){

    }

    void menuKeyTyped(){

    }
}
