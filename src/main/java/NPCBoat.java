import processing.core.PApplet;

import java.util.ArrayList;

public class NPCBoat extends Boat {

    //---------- CONSTRUCTOR :) ----------\\
    public NPCBoat(PApplet p, float x, float y, float m) {
        super(p, x, y, m);
    }

    //----------METHODS----------\\

    //This function controls the NPC turn.
    void Turn() {
       ///cpu og player deler åbenbart items så hvad du gøre ved dene ene sker der også hos den anden
        boolean canSeeShop = false;
        ArrayList<ShopTile> shopTileset = new ArrayList<ShopTile>();

        for (int i = 0; i < showneTileSet.size() - 1; ++i) {
            if (showneTileSet.get(i).Contents.equals("SHOP")) {
                shopTileset.add((ShopTile) showneTileSet.get(i));
                canSeeShop = true;
            }
        }
        if (canSeeShop && Math.random() < 0.5) {
            System.out.println("Valgte at shoppe");
            int random = (int) p.random(0, shopTileset.size());
            shop(shopTileset.get(random));
        } else {
            System.out.println("Valgte at rykke");
            Move(0);
        }


    }

    //This feature allows opponents to choose to buy or sell items
    void shop(ShopTile shopTile) {

        //finds the shop menu
        ShopMenu shop = shopTile.shopMenu;
        //the size of the shop items
        int s = shop.StockInventory.size();

        //booleans that tell you if you have bought or sold something
        boolean boughtSomthing = false;
        boolean soultSomthing = false;

        //chooses with 50% whether to buy something
        if (Math.random() < 0.5) {
            Item item = shop.StockInventory.get((int) p.random(0, s));
            int amount = (int) (money/item.value);
            amount = (int) p.random(amount/2,amount);
            buy(item, amount);
            boughtSomthing = true;
        }

        //chooses with 50% whether to sell something
        if (Math.random() < 0.5) {
            Item item = shop.StockInventory.get((int) p.random(0, s));
            int amount = item.ammount;
            amount = (int) p.random(amount/2,amount);
            sell(item, amount);
            soultSomthing = true;
        }

        //show nothing is bought or sold try again
        if (!boughtSomthing || !soultSomthing) {
            shop(shopTile);
        }
    }

    //This function finds its selected item and sells x amount of it
    void sell(Item item, int amount) {
        if (money - item.value >= 0 && item.ammount - amount >= 0) {
            for (int i = 0; i < inventory.size(); ++i) {
                System.out.println("itemName: " + item.Name + "|iName: " + inventory.get(i).Name + "|");
                if (inventory.get(i).Name.equals(item.Name)) {

                    inventory.get(i).ammount -= amount;
                    item.ammount += amount;
                    money = money + item.value;
                    break;

                }
            }

        } else if (amount > 1) {
            System.out.println("amount: " + amount);
            sell(item, amount - 1);
        } else if (amount < 1) {
            System.out.println("amount: " + amount);
            buy(item, amount - 1);

        }
    }

    //This function finds its selected item and buys x amount of it
    void buy(Item item, int amount) {
        if (money - item.value >= 0 && item.ammount - amount >= 0) {
            for (int i = 0; i < inventory.size(); ++i) {
                System.out.println("itemName: " + item.Name + "|iName: " + inventory.get(i).Name + "|");
                if (inventory.get(i).Name.equals(item.Name)) {


                    inventory.get(i).ammount += amount;
                    item.ammount -= amount;
                    money = money - item.value;
                    break;

                }
            }
        } else if (amount > 1) {
            System.out.println("amount: " + amount);
            buy(item, amount - 1);
        }
    }

    //This function moves the opponents to a random place
    void Move(int trys) {
        int xMove = (int) p.random(-3, 3);
        int yMove = (int) p.random(-3, 3);
        float newX = (int) (xPos + xMove);
        float newY = (int) (yPos + yMove);
        boolean haveFoundMove = false;
        if (Math.random() > 0.5) {

            for (int i = 0; i < showneTileSet.size() - 1; ++i) {
                if (showneTileSet.get(i).xPos == newX && showneTileSet.get(i).yPos == newY && showneTileSet.get(i).Contents.equals("WATER")) {
                    haveFoundMove = true;
                    xPos = newX;
                    yPos = newY;
                }
            }

        } else if (Math.random() > 0.5) {
            for (int i = 0; i < showneTileSet.size() - 1; ++i) {
                if (showneTileSet.get(i).xPos == newX && showneTileSet.get(i).yPos == yPos && showneTileSet.get(i).Contents.equals("WATER")) {
                    haveFoundMove = true;
                    xPos = newX;

                }
            }
        } else {
            for (int i = 0; i < showneTileSet.size() - 1; ++i) {
                if (showneTileSet.get(i).xPos == xPos && showneTileSet.get(i).yPos == newY && showneTileSet.get(i).Contents.equals("WATER")) {
                    haveFoundMove = true;
                    yPos = newY;
                }
            }
        }

        if (haveFoundMove == false&& trys<10) {
            Move(trys+1);
        }

    }
}
