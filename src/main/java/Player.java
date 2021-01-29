
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Player extends Boat {


    public Player(PApplet p, float x, float y, float s, float m, ArrayList<Item> inventory) {
        super(p, x, y, s, m, inventory);
    }
}
