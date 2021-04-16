import processing.core.PApplet;

public class ShopTile extends Tile {
    //shop menu the tile
    ShopMenu shopMenu;

    //---------- CONSTRUCTOR :) ----------\\
    public ShopTile(PApplet p, String C, float x, float y) {
        super(p, C, x, y);
        Contents = "SHOP";
        shopMenu = new ShopMenu(p);
    }
    //----------METHODS----------\\

    //This draws the shop menu
    public void drawShopMenu(Player player, float s) {
        if (shopMenu.visible) {
            shopMenu.drawMenu(player, s);
        }
    }

    //resider the shop and make the shop menu visble/hide it away
    public void showShop(float s) {
        shopMenu.reSizeShopMenu(s);
        shopMenu.visible = !shopMenu.visible;
    }

    //this function do so you can click on the shop
    public void clickShop() {
        shopMenu.shopMenuClicked();
    }

    //this function change the image on the tile
    @Override
    public void setTileImage() {
        tileImage = p.loadImage("shop.png");
    }
}
