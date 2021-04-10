import processing.core.PApplet;

public class ShopTile extends Tile {
    ShopMenu shopMenu;

    public ShopTile(PApplet p, String C, float x, float y) {
        super(p, C, x, y);
        Contents = "SHOP";
        shopMenu = new ShopMenu(p);

    }

    public void drawShopMenu(Player player, float s) {
        if (shopMenu.visible) {
            shopMenu.drawMenu(player, s);


        }


    }


    public void showShop(float s) {
        shopMenu.reSizeShopMenu(s);
        shopMenu.visible = !shopMenu.visible;

    }

    public void clickShop() {
        shopMenu.shopMenuClicked();
    }

    @Override
    public void setTileImage() {
        tileImage = p.loadImage("shop.png");
    }
}
