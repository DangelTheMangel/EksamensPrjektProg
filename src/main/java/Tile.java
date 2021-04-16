import processing.core.PApplet;
import processing.core.PImage;

public abstract class Tile {
    PApplet p;

    //This variable determines how large the line of tiles should be
    float strong = 0;
    //This variable will be used for path finding
    int path = 10000;
    //the image of the image
    PImage tileImage;
    //This boolean tells you if the tile is pressed
    Boolean selectedTile = false;
    boolean cliked = false;
    //This string tells what the tilen contains
    String Contents;
    //the positions
    int xPos;
    int yPos;

    //----------CONSTRUCTOR----------
    public Tile(PApplet p, String C, float x, float y) {
        Contents = C;
        this.p = p;
        xPos = (int) x;
        yPos = (int) y;
    }
    //----------METHODS----------\\

    //This function checks tile clicked
    void checkIfCliked(int pX, int pY, int s) {
        int positionX = 0 + s * pX;
        int positionY = 0 + s * pY;
        if (p.mouseX > positionX &&
                p.mouseX < positionX + s &&
                p.mouseY > positionY &&
                p.mouseY < positionY + s) {
            cliked = true;

        }
    }

    //check show the mouse is over it
    void checkIfMouseOver(float pX, float pY, int s) {
        int positionX = (int) (0 + s * pX);
        int positionY = (int) (0 + s * pY);
        if (p.mouseX > positionX &&
                p.mouseX < positionX + s &&
                p.mouseY > positionY &&
                p.mouseY < positionY + s) {
            p.fill(200, 200, 200, 200);
            p.strokeWeight(10);
            p.stroke(strong * 255, strong * 190, 0);
            p.rect(0 + s * pX, 0 + s * pY, s, s);

        }
    }

    //draws it
    void Display(float pX, float pY, int s, Boolean pic, float strokeSize) {

        PImage tilephoto = null;
        if (Contents.equals("WATER")) {
            p.fill(60, 100, 200);

        } else if (Contents.equals("SAND")) {
            p.fill(190, 181, 115);

        } else {

            if (Contents.equals("GRASS")) {
                p.fill(84, 201, 71);
            } else if (Contents.equals("SHOP")) {
                p.fill(201, 0, 191);
            } else {
                p.fill(200);
            }
        }
        if (tileImage != null) {
            if (pic)
                tilephoto = tileImage;
        }

        p.strokeWeight(strokeSize * 2);
        p.stroke((strong * 255), strong * 190, 0);
        p.rect(s * pX, s * pY, s, s);
        if (tilephoto != null && pic) {
            p.image(tilephoto, 0 + s * pX, 0 + s * pY, s, s);
            p.fill(0);
            String tileInfo = xPos + "x" + yPos;
            p.text(tileInfo, s * pX + s / 2 - p.textWidth(tileInfo) / 2, s * pY + s / 2);
        }


        if (selectedTile) {
            p.fill(200, 200, 200, 200);
            p.strokeWeight(10);
            p.stroke(strong * 255, strong * 190, 0);
            p.rect(0 + s * pX, 0 + s * pY, s, s);

        }
    }

    //used for shoptiles
    public abstract void drawShopMenu(Player player, float scaleSize);
    public abstract void showShop(float s);
    public abstract void clickShop();
    public abstract void setTileImage();
}
