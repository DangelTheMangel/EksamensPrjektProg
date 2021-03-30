import processing.core.PApplet;

public abstract class Knap {
    //variabler
    float positionX, positionY, sizeX, sizeY;
    float realPositionX, realPositionY, realSizeX, realSizeY;
    float size =1;
    float mouseX, mouseY;

    String text;
    boolean klikket = false;
  

    PApplet p;

    Knap(PApplet papp, int posX, int posY, int sizeX, int sizeY, String text ) {
        p = papp;
        positionX = posX;
        positionY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = text;
        realSizeY =sizeY ;
        realSizeX =sizeX;

        realPositionY =posY;
        realPositionX =posX;

    }

    void klik() {
        if (p.mousePressed &&
                mouseX > positionX &&
                mouseX < positionX + sizeX &&
                mouseY > positionY &&
                mouseY < positionY + sizeY) {
        }
    }

    void setTekst(String tekst) {
        p.fill(0);

        p.text(tekst, positionX +(sizeX/16), positionY + (sizeY/2));

    }

    void tegnKnap() {
        positionX = realPositionX * size;
        positionY = realPositionY * size;
        sizeX = realSizeX*size;
        sizeY = realSizeY*size;


        p.stroke(1, 46, 74, 100);
        if(klikket){
            p.fill(255);
        }else{
            p.fill(200,200,200,100);
        }

        p.rect(positionX, positionY, sizeX, sizeY);
        setTekst(text);
    }

    boolean erKlikket() {
        return klikket;
    }

    abstract void registrerKlik(float mouseX, float mouseY);
}
