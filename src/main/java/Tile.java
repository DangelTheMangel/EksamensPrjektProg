import processing.core.PApplet;

import javax.swing.text.AbstractDocument;

public class Tile {
    PApplet p;
    String Contents; //Contents kan være "Vand", "Land", "Skattekiste", "Havn" osv.
    int xPos;
    int yPos;

    //----------CONSTRUCTOR----------
    Tile(String C,float x, float y){
        C = Contents;
        x = xPos;
        y = yPos;
    }
    //----------METHODS----------
    //Idk, vi skal måske have noget der displayer den. Når man hover over den bliver den mørkere
    void Display(){
        p.rect(xPos,yPos,10,10);

        if(p.mouseX < xPos && p.mouseX >xPos + 10 && p.mouseY < yPos && p.mouseY > yPos + 10) { //ved virkelig ikke om det er rigtigt
            System.out.println("Av!"); //placeholder output, tænker tile bliver mørkere
        }
    }

    //Vi skal have en method der kan gøre så man klikker på den og den udregner movement af spilleren.

    //
}


//sej pseudokode
/*
Felter skal genereres i et for-loop
De skal så have et Content, her taler vi om String Contents på linje 2.
Afhængigt af hvad deres content er, skal de displaye forskellige ting, og have forskellige funktioner.
*/