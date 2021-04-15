import processing.core.PApplet;

import java.util.ArrayList;

public class ShopMenu {
    PApplet p;
    Boolean visible = false;

    ArrayList<Item> StockInventory = new ArrayList<Item>();
    AlmindeligKnap btnBuyBa, btnBuyRum, btnBuyEye, btnSellBa, btnSellRum, btnSellEye, btnCloseShop;
    float bananPrices, eyepachPrices, rumPrices;

    ShopMenu(PApplet p) {
        this.p = p;
        int xpos = 830, ypos = 150;
        Item Banana = new Item(10, (int) p.random(0, 100), "Banana", "idk");
        Item Rum = new Item(50, (int) p.random(0, 100), "Rum", "idk");
        Item Eyepatch = new Item(30, (int) p.random(0, 100), "Eyepatch", "idk");

        StockInventory.add(Banana);
        StockInventory.add(Rum);
        StockInventory.add(Eyepatch);

        btnBuyEye = new AlmindeligKnap(p, xpos + 20, 270, 160, 50, "Køb en klap");
        btnBuyRum = new AlmindeligKnap(p, xpos + 20, 360, 160, 50, "Køb en rom");
        btnBuyBa = new AlmindeligKnap(p, xpos + 20, 460, 160, 50, "Køb en Banan");

        btnSellEye = new AlmindeligKnap(p, xpos + 400 - 180, 270, 160, 50, "sælg en klap");
        btnSellRum = new AlmindeligKnap(p, xpos + 400 - 180, 360, 160, 50, "slæg en rom");
        btnSellBa = new AlmindeligKnap(p, xpos + 400 - 180, 460, 160, 50, "sælg en Banan");

        btnCloseShop = new AlmindeligKnap(p, xpos + 20, 580, 360, 50, "luk butik");
        bananPrices = determinePrice(StockInventory.get(0));
        rumPrices = determinePrice(StockInventory.get(1));
        eyepachPrices = determinePrice(StockInventory.get(2));


    }

    float determinePrice(Item item) {
        float itemPrices = item.value;
        float extra = p.random(-(itemPrices / 2), (itemPrices / 2));
        item.value = itemPrices + extra;
        return itemPrices + extra;

    }

    void drawMenu(Player player, float s) {
        //880*scaleSize,150*scaleSize
        float xpos = 830 * s, ypos = 150 * s;
        p.fill(209, 166, 96, 255);
        p.rect(s * 830, s * 150, s * 400, s * 500);
        p.fill(0);
        p.textSize(32*s);
        p.text("Shop", xpos + 200 * s - p.textWidth("Shop") / 2, ypos + 50 * s);
        p.textSize(16*s);
        ArrayList<Item> t = StockInventory;

        // p.text("Taske: " +"\n" +t.get(0).Name +t.get(0).ammount +"\n" + t.get(1).Name +t.get(1).ammount +"\n" + t.get(2).Name +t.get(2).ammount,s*850 ,s*150);


        //buy
        p.text(t.get(2).Name + ": " + t.get(2).ammount + " Køb/sælg for: " + eyepachPrices , xpos + 20 * s, ypos + 100 * s);
        btnBuyEye.tegnKnap();
        if (btnBuyEye.klikket) {
            buyItem(StockInventory.get(2), player, btnBuyEye, eyepachPrices);
            btnBuyEye.registrerRelease();
        }

        //sell
        btnSellEye.tegnKnap();
        if (btnSellEye.klikket) {
            SellItem(player.inventory.get(2), player, btnSellEye, 1, eyepachPrices);
            btnSellEye.registrerRelease();
        }

        p.text(t.get(1).Name + ": " + t.get(1).ammount + " Køb/sælg for: " + eyepachPrices, xpos + 20 * s, ypos + 200 * s);
        btnBuyRum.tegnKnap();
        if (btnBuyRum.klikket) {
            buyItem(StockInventory.get(1), player, btnBuyRum, rumPrices);
            btnBuyRum.registrerRelease();
        }

        btnSellRum.tegnKnap();
        if (btnSellRum.klikket) {
            SellItem(player.inventory.get(1), player, btnSellRum, 1, rumPrices);
            btnSellRum.registrerRelease();
        }

        p.text(t.get(0).Name + ": " + t.get(0).ammount+ " Køb/sælg for: " + eyepachPrices, xpos + 20 * s, ypos + 300 * s);
        btnBuyBa.tegnKnap();
        if (btnBuyBa.klikket) {
            buyItem(StockInventory.get(0), player, btnBuyBa, bananPrices);
            btnBuyBa.registrerRelease();
        }

        btnSellBa.tegnKnap();
        if (btnSellBa.klikket) {
            SellItem(player.inventory.get(0), player, btnSellBa, 1, bananPrices);
            btnSellBa.registrerRelease();
        }

        btnCloseShop.tegnKnap();
        if (btnCloseShop.klikket) {

            visible = false;
            btnCloseShop.registrerRelease();
        }


    }

    void reSizeShopMenu(float s) {
        btnBuyBa.size = s;
        btnBuyRum.size = s;
        btnBuyEye.size = s;
        btnSellBa.size = s;
        btnSellRum.size = s;
        btnSellEye.size = s;
        btnCloseShop.size = s;

    }

    void btbBuyItem(AlmindeligKnap btn, Player player, Item item) {

    }

    void shopMenuClicked() {
        btnBuyBa.registrerKlik(p.mouseX, p.mouseY);
        btnBuyEye.registrerKlik(p.mouseX, p.mouseY);
        btnBuyRum.registrerKlik(p.mouseX, p.mouseY);

        btnSellBa.registrerKlik(p.mouseX, p.mouseY);
        btnSellEye.registrerKlik(p.mouseX, p.mouseY);
        btnSellRum.registrerKlik(p.mouseX, p.mouseY);

        btnCloseShop.registrerKlik(p.mouseX, p.mouseY);
    }

    void buyItem(Item item, Player player, AlmindeligKnap btn, float itemPrices) {
        if (player.money - itemPrices >= 0 && item.ammount - 1 >= 0 && btn.klikket) {
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

    void SellItem(Item item, Player player, AlmindeligKnap btn, int amount, float itemPrices) {

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



