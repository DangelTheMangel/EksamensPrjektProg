import processing.core.PApplet;

import java.util.ArrayList;

public class ShopMenu {
    //the variables
    PApplet p;
    //menu variables
    Boolean visible = false;
    //list of standard inventory
    ArrayList<Item> StockInventory = new ArrayList<Item>();
    //all the buttons
    NomalButton btnBuyBa, btnBuyRum, btnBuyEye, btnSellBa, btnSellRum, btnSellEye, btnCloseShop;
    //the shop's prices
    float bananPrices, eyepachPrices, rumPrices;

    //---------- CONSTRUCTOR :) ----------\\
    ShopMenu(PApplet p) {
        this.p = p;
        int xpos = 830, ypos = 150;
        Item Banana = new Item(10, (int) p.random(0, 100), "Banana");
        Item Rum = new Item(50, (int) p.random(0, 100), "Rum");
        Item Eyepatch = new Item(30, (int) p.random(0, 100), "Eyepatch");

        StockInventory.add(Banana);
        StockInventory.add(Rum);
        StockInventory.add(Eyepatch);

        btnBuyEye = new NomalButton(p, xpos + 20, 270, 160, 50, "Køb en klap");
        btnBuyRum = new NomalButton(p, xpos + 20, 360, 160, 50, "Køb en rom");
        btnBuyBa = new NomalButton(p, xpos + 20, 460, 160, 50, "Køb en Banan");

        btnSellEye = new NomalButton(p, xpos + 400 - 180, 270, 160, 50, "sælg en klap");
        btnSellRum = new NomalButton(p, xpos + 400 - 180, 360, 160, 50, "slæg en rom");
        btnSellBa = new NomalButton(p, xpos + 400 - 180, 460, 160, 50, "sælg en Banan");

        btnCloseShop = new NomalButton(p, xpos + 20, 580, 360, 50, "luk butik");
        bananPrices = determinePrice(StockInventory.get(0));
        rumPrices = determinePrice(StockInventory.get(1));
        eyepachPrices = determinePrice(StockInventory.get(2));
    }
    //----------METHODS----------\\

    //this function selects the price of the shop's items
    float determinePrice(Item item) {
        float itemPrices = item.value;
        float extra = p.random(-(itemPrices / 2), (itemPrices / 2));
        item.value = itemPrices + extra;
        return itemPrices + extra;

    }

    //This function draws the menu
    void drawMenu(Player player, float s) {
        float xpos = 830 * s, ypos = 150 * s;
        p.fill(209, 166, 96, 255);
        p.rect(s * 830, s * 150, s * 400, s * 500);
        p.fill(0);
        p.textSize(32 * s);
        p.text("Shop", xpos + 200 * s - p.textWidth("Shop") / 2, ypos + 50 * s);
        p.textSize(16 * s);
        ArrayList<Item> t = StockInventory;

        //buy
        p.text(t.get(2).Name + ": " + t.get(2).ammount + " Køb/sælg for: " + eyepachPrices, xpos + 20 * s, ypos + 100 * s);
        btnBuyEye.drawButton();
        if (btnBuyEye.clicked) {
            buyItem(StockInventory.get(2), player, btnBuyEye, eyepachPrices);
            btnBuyEye.registrerRelease();
        }

        //sell
        btnSellEye.drawButton();
        if (btnSellEye.clicked) {
            SellItem(player.inventory.get(2), player, btnSellEye, 1, eyepachPrices);
            btnSellEye.registrerRelease();
        }


        p.text(t.get(1).Name + ": " + t.get(1).ammount + " Køb/sælg for: " + rumPrices, xpos + 20 * s, ypos + 200 * s);
        btnBuyRum.drawButton();
        if (btnBuyRum.clicked) {
            buyItem(StockInventory.get(1), player, btnBuyRum, rumPrices);
            btnBuyRum.registrerRelease();
        }

        btnSellRum.drawButton();
        if (btnSellRum.clicked) {
            SellItem(player.inventory.get(1), player, btnSellRum, 1, rumPrices);
            btnSellRum.registrerRelease();
        }

        p.text(t.get(0).Name + ": " + t.get(0).ammount + " Køb/sælg for: " + bananPrices, xpos + 20 * s, ypos + 300 * s);
        btnBuyBa.drawButton();
        if (btnBuyBa.clicked) {
            buyItem(StockInventory.get(0), player, btnBuyBa, bananPrices);
            btnBuyBa.registrerRelease();
        }

        btnSellBa.drawButton();
        if (btnSellBa.clicked) {
            SellItem(player.inventory.get(0), player, btnSellBa, 1, bananPrices);
            btnSellBa.registrerRelease();
        }

        btnCloseShop.drawButton();
        if (btnCloseShop.clicked) {

            visible = false;
            btnCloseShop.registrerRelease();
        }


    }

    //This function for all buttons and look to fit with the screen size
    void reSizeShopMenu(float s) {
        btnBuyBa.size = s;
        btnBuyRum.size = s;
        btnBuyEye.size = s;
        btnSellBa.size = s;
        btnSellRum.size = s;
        btnSellEye.size = s;
        btnCloseShop.size = s;

    }

    //This function allows you to click on the menu
    void shopMenuClicked() {
        btnBuyBa.registerClick(p.mouseX, p.mouseY);
        btnBuyEye.registerClick(p.mouseX, p.mouseY);
        btnBuyRum.registerClick(p.mouseX, p.mouseY);

        btnSellBa.registerClick(p.mouseX, p.mouseY);
        btnSellEye.registerClick(p.mouseX, p.mouseY);
        btnSellRum.registerClick(p.mouseX, p.mouseY);

        btnCloseShop.registerClick(p.mouseX, p.mouseY);
    }

    //This function allows you to purchase items
    void buyItem(Item item, Player player, NomalButton btn, float itemPrices) {
        if (player.money - itemPrices >= 0 && item.ammount - 1 >= 0 && btn.clicked) {
            for (int i = 0; i < player.inventory.size(); ++i) {
                System.out.println("itemName: " + item.Name + "|iName: " + player.inventory.get(i).Name + "|");
                if (player.inventory.get(i).Name.equals(item.Name)) {


                    ++player.inventory.get(i).ammount;
                    --item.ammount;
                    player.money = player.money - itemPrices;
                    btn.registrerRelease();
                    break;

                }
            }
        }
    }

    //This function allows you to sell items
    void SellItem(Item item, Player player, NomalButton btn, int amount, float itemPrices) {

        int sellAmount = item.ammount - amount;
        if (sellAmount >= 0) {
            for (int i = 0; i < StockInventory.size(); ++i) {
                System.out.println("itemName: " + item.Name + "|iName: " + player.inventory.get(i).Name + "|");
                if (StockInventory.get(i).Name.equals(item.Name)) {

                    item.ammount -= amount;
                    StockInventory.get(i).ammount += amount;
                    player.money = player.money + itemPrices;
                    btn.registrerRelease();
                    break;

                }
            }

        }
    }


}



