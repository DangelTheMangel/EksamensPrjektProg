import processing.core.PApplet;

import java.util.ArrayList;

public class ShopTile extends Tile {
    ShopMenu shopMenu;

    public ShopTile(PApplet p, String C, float x, float y) {
        super(p, C, x, y);
        Contents = "SHOP";
        shopMenu = new ShopMenu(p);
    }

    public void drawShopMenu(Player player){
        if(shopMenu.visible){
           shopMenu.drawMenu(player);


        }


    }

    public void showShop(){
        shopMenu.visible = !shopMenu.visible;

    }

    public void clickShop(){
        shopMenu.shopMenuClicked();
    }
}
