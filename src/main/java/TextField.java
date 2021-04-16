import processing.core.PApplet;

public class TextField {

    //button "positions" and "size".
    float positionX, positionY, sizeX, sizeY;
    //button size and position on the screen
    float realPositionX, realPositionY, realSizeX, realSizeY;
    //mouse x and y postion
    float mouseX, mouseY;
    //The size. This size is multiplied by "positions" and "size"
    // to get the positions and size of the screen.
    // that way the game can font window size without destroying anything
    float size = 1;
    //this string is the tilt
    String textfieldTitle;
    //this is the input
    String input = "";
    //boolean if the TextField is clicked
    boolean clicked = false;
    //whether it uses only spoken or not
    boolean acceptLetters = true;

    PApplet p;

    //---------- CONSTRUCTOR :) ----------\\
    TextField(PApplet papp, int posX, int posY, int sizeX, int sizeY, String text) {
        p = papp;
        positionX = posX;
        positionY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.textfieldTitle = text;
        realSizeY = sizeY;
        realSizeX = sizeX;

        realPositionY = posY;
        realPositionX = posX;
    }
    //----------METHODS----------\\

    //This function checks if the mouse is over the feild and is clicked change it clicked to true
    void registerClick(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (mouseX > positionX &&
                mouseX < positionX + sizeX &&
                mouseY > positionY &&
                mouseY < positionY + sizeY) {
            clicked = !clicked;
        } else {
            clicked = false;
        }


    }

    //drawing the field
    void drawField() {

        positionX = realPositionX * size;
        positionY = realPositionY * size;
        sizeX = realSizeX * size;
        sizeY = realSizeY * size;

        p.stroke(1, 46, 74, 100);
        if (clicked) {
            p.fill(227, 225, 252, 250);
        } else {
            p.fill(200);
        }


        p.rect(positionX, positionY, sizeX, sizeY);

        p.fill(0);
        p.textSize(16 * size);
        p.text(input, positionX + (sizeX / 16), positionY + (sizeY / 2));
        p.text(textfieldTitle, positionX, positionY);
    }

    //this function lets you change the input
    void keyinput(char key) {

        if (clicked) {
            if (key == p.BACKSPACE && input.length() > 0) {

                input = input.substring(0, input.length() - 1);
            } else {
                if (acceptLetters || (key >= '0' && key <= '9'))
                    input = input + key;
            }

        }
        PApplet.println(input);
    }


}
