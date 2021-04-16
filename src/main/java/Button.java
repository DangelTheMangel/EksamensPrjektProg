import processing.core.PApplet;

public abstract class Button {
    //button variables
    PApplet p;
    //button "positions" and "size".
    float positionX, positionY, sizeX, sizeY;
    //button size and position on the screen
    float realPositionX, realPositionY, realSizeX, realSizeY;

    //The size. This size is multiplied by "positions" and "size"
    // to get the positions and size of the screen.
    // that way the game can font window size without destroying anything
    float size = 1;

    //mouse x and y postion
    float mouseX, mouseY;

    //The string with infoamtion inside the button
    String text;

    //boolean if the button is clicked
    boolean clicked = false;

    //---------- CONSTRUCTOR :) ----------\\
    Button(PApplet papp, int posX, int posY, int sizeX, int sizeY, String text) {
        p = papp;
        positionX = posX;
        positionY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = text;
        realSizeY = sizeY;
        realSizeX = sizeX;

        realPositionY = posY;
        realPositionX = posX;

    }

    //----------METHODS----------\\


    //draws the button
    void drawButton() {
        positionX = realPositionX * size;
        positionY = realPositionY * size;
        sizeX = realSizeX * size;
        sizeY = realSizeY * size;


        p.stroke(1, 46, 74, 100);
        if (clicked) {
            p.fill(255);
        } else {
            p.fill(200, 200, 200, 100);
        }

        p.rect(positionX, positionY, sizeX, sizeY);
        p.fill(0);
        p.textSize(16 * size);
        p.text(text, positionX + (sizeX / 16), positionY + (sizeY / 2));
    }
    


    //return if the button is clicked
    boolean isClicked() {
        return clicked;
    }

    //The one who puts about the click is true
    abstract void registerClick(float mouseX, float mouseY);
}
