import processing.core.PApplet;

import javax.swing.text.AbstractDocument;

public class Tile {
    PApplet p;
    String Contents; //Contents kan være "Vand", "Land", "Skattekiste", "Havn" osv.
    int xPos;
    int yPos;
    boolean cliked = false;
    float darken; //gør felter mørkere når man hover

    //----------CONSTRUCTOR----------
    Tile(PApplet p,String C,float x, float y){
        Contents =C ;
        this.p = p;
        xPos = (int) x;
        yPos = (int)y;
    }

    void checkIfCliked(int pX, int pY,int s){
        int positionX =0+ s*pX;
        int positionY =0+ s*pY;
        if (p.mouseX > positionX &&
                p.mouseX < positionX + s &&
                p.mouseY > positionY &&
                p.mouseY < positionY + s) {
            cliked = true;
        }
    }

    //----------METHODS----------
    //Idk, vi skal måske have noget der displayer den. Når man hover over den bliver den mørkere
    void Display(int pX, int pY,int s){
        p.fill(200);
        p.rect(0+ s*pX,0+ s*pY,s,s);
        p.fill(0);
        p.text(xPos +"x"+ yPos,0+ s*pX,0+ s*pY);
        if(p.mouseX < xPos && p.mouseX >xPos + s && p.mouseY < yPos && p.mouseY > yPos + s) { //ved virkelig ikke om det er rigtigt
            System.out.println("Av!"); //placeholder output, tænker tile bliver mørkere
            darken = 1.5f;
        }
        else{darken = 1;}

        if(Contents=="WATER"){
            p.fill(60*darken, 100*darken, 200*darken);
        }else if(Contents=="SAND") {
            p.fill(190*darken, 181*darken, 115*darken);
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