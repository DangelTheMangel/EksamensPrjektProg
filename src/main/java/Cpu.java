import processing.core.PApplet;

import java.util.ArrayList;

public class Cpu extends Boat {


    public Cpu(PApplet p, float x, float y, float s, float m, ArrayList<Item> inventory) {
        super(p, x, y, s, m, inventory);
    }

    void Move(){
        float movementsLeft = 10;
        float f = p.random(-10,10);
        xPos = xPos + f;
        movementsLeft-= f;
        yPos = yPos + p.random(-movementsLeft,movementsLeft);


    }
}
