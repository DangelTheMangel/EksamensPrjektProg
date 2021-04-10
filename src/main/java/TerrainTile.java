import processing.core.PApplet;
import processing.core.PImage;

public class TerrainTile extends Tile {


    public TerrainTile(PApplet p, String C, float x, float y) {

        super(p, C, x, y);

    }

    @Override
    public void drawShopMenu(Player player, float scaleSize) {

    }

    @Override
    public void showShop(float s) {

    }

    @Override
    public void clickShop() {


    }

    @Override
    public void setTileImage() {
        if (Contents.equals("WATER")) {
            PImage tile;
            tile = p.loadImage("w" + (int) p.random(1, 5) + ".png");
            tileImage = tile;
        } else if (Contents.equals("GRASS")) {
            PImage tile;
            if (Math.random() > 0.5) {
                if (Math.random() > 0.5) {
                    tile = p.loadImage("g1.png");
                } else {
                    tile = p.loadImage("g2.png");
                }
            } else if (Math.random() > 0.6) {
                tile = p.loadImage("g3.png");
            } else {
                tile = p.loadImage("g4.png");
            }
            tileImage = tile;

        } else if (Contents.equals("SAND")) {
            PImage tile;
            tile = p.loadImage("s" + (int) p.random(1, 5) + ".png");
            tileImage = tile;
        } else if (Contents.equals("BORDER")) {
            tileImage = p.loadImage("border.png");
        }
    }
}
