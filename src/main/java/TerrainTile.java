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
            tile = p.loadImage("w" + 1 + ".png");
            tileImage = tile;
        } else if (Contents.equals("GRASS")) {
            PImage tile;
            tile = p.loadImage("g" + (int) p.random(1, 5) + ".png");
            tileImage = tile;

        } else if (Contents.equals("SAND")) {
            PImage tile;
            tile = p.loadImage("s" + 1 + ".png");
            tileImage = tile;
        } else if (Contents.equals("BORDER")) {
            tileImage = p.loadImage("border.png");
        }
    }
}
