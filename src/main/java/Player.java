import java.util.ArrayList;

public class Player extends Boat {
    Player(float x, float y, float s, float m, ArrayList a){
        xPos = x;
        yPos = y;
        score = s;
        money = m;
        inventory = a;
    }
}
