import processing.core.PApplet;
import processing.core.PImage;

public abstract class Tile {
    float strong = 0;
    int path = 10000;
    PImage tileImage ;


    Boolean selectedTile = false;
    PApplet p;
    String Contents; //Contents kan være "Vand", "Land", "Skattekiste", "Havn" osv.
    int xPos;
    int yPos;
    boolean cliked = false;
    float darken; //gør felter mørkere når man hover

    //----------CONSTRUCTOR----------


    public Tile(PApplet p,String C,float x, float y) {
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

    void checkIfMouseOver(float pX, float pY, int s){
        int positionX = (int) (0+ s*pX);
        int positionY = (int) (0+ s*pY);
        if (p.mouseX > positionX &&
                p.mouseX < positionX + s &&
                p.mouseY > positionY &&
                p.mouseY < positionY + s) {
            p.fill(200,200,200,200);
            p.strokeWeight(10);
            p.stroke(strong*255,strong*190,0);
            p.rect(0+ s*pX,0+ s*pY,s,s);

        }
    }

    //----------METHODS----------
    //Idk, vi skal måske have noget der displayer den. Når man hover over den bliver den mørkere
    void Display(float pX, float pY, int s, Boolean pic,float strokeSize){

        PImage tilephoto = null;
        if(Contents.equals("WATER")){
            p.fill(60, 100, 200);
            if(pic)
             tilephoto = main.waterTiles.get((int) main.waterAnimeNumber);
        }else if(Contents.equals("SAND")) {
            p.fill(190, 181, 115);
            if(pic)
            tilephoto = main.sandTiles.get((int) main.sandAnimeNumber);
        }else {
            if(tileImage != null){
                if(pic)
                tilephoto = tileImage;
            }
            if(Contents.equals("GRASS")){
                p.fill(84, 201, 71);
            }else if(Contents.equals("SHOP")){
                p.fill(201, 0, 191);
            }else {
                p.fill(200);
            }
        }

        p.strokeWeight(strokeSize*2);
        p.stroke((strong*255),strong*190,0);
        p.rect( s*pX, s*pY,s,s);
        if (tilephoto != null&& pic){
           p.image(tilephoto,0+ s*pX,0+ s*pY,s,s) ;
            p.fill(0);
            String tileInfo = xPos +"x"+ yPos  ;
            p.text( tileInfo , s*pX+ s/2- p.textWidth(tileInfo)/2, s*pY + s/2);
        }


        if(selectedTile){
            p.fill(200,200,200,200);
            p.strokeWeight(10);
            p.stroke(strong*255,strong*190,0);
            p.rect(0+ s*pX,0+ s*pY,s,s);

        }



       /* if(Contents=="WATER"){
            p.fill(60*darken, 100*darken, 200*darken);
        }else if(Contents=="SAND") {
            p.fill(190*darken, 181*darken, 115*darken);
        }*/


    }

    public abstract void drawShopMenu(Player player, float scaleSize);
    public abstract  void showShop(float s);
    public abstract void clickShop();

    //Vi skal have en method der kan gøre så man klikker på den og den udregner movement af spilleren.

    //
}


//sej pseudokode
/*
Felter skal genereres i et for-loop
De skal så have et Content, her taler vi om String Contents på linje 2.
Afhængigt af hvad deres content er, skal de displaye forskellige ting, og have forskellige funktioner.
*/