import processing.core.PApplet;

import java.util.ArrayList;

public class Cpu extends Boat {


    public Cpu(PApplet p, float x, float y, float s, float m) {
        super(p, x, y, s, m);
    }

    void Turn(){
        ///cpu og player deler åbenbart items så hvad du gøre ved dene ene sker der også hos den anden 
        boolean canSeeShop = false;
        ArrayList<ShopTile> shopTileset = new ArrayList<ShopTile>();

        for(int i = 0; i<showneTileSet.size()-1;++i){
            if(showneTileSet.get(i).Contents.equals("SHOP")){
                shopTileset.add((ShopTile) showneTileSet.get(i));
                canSeeShop = true;
            }
            }
        if(canSeeShop && Math.random()<0.5){
            System.out.println("Valgte at shoppe");
            int random = (int) p.random(0,shopTileset.size());
            shop(shopTileset.get( random));
        }else {
            System.out.println("Valgte at rykke");
            Move();
        }


    }
    void shop(ShopTile shopTile){ //buy();
        ShopMenu shop = shopTile.shopMenu;
        int s = shop.StockInventory.size();
        boolean boughtSomthing = false;
        boolean soultSomthing = false;
        if(Math.random() < 0.5){
            System.out.println("køber lortet");
        buy((Item) shop.StockInventory.get((int) p.random(0,s)),(int) p.random(1,100));
        boughtSomthing = true;
        }

        if(Math.random() < 0.5){
            System.out.println("sælger lortet");
            sell(inventory.get((int) p.random(0,inventory.size())),(int) p.random(1,100));
            soultSomthing = true;
        }


        if(!boughtSomthing || !soultSomthing){
            shop(shopTile);
        }
    }

    void sell(Item item, int amount){
        if(money - item.value>= 0 && item.ammount-amount >= 0 ){
            for(int i = 0; i<inventory.size();++i){
                System.out.println("itemName: " + item.Name + "|iName: " + inventory.get(i).Name+"|");
                if(inventory.get(i).Name.equals(item.Name)){

                    inventory.get(i).ammount -= amount;
                    item.ammount += amount;
                    money = money + item.value;
                    break;

                }
            }

        }else if(amount > 1){
            System.out.println("amount: " + amount);
        sell(item,amount-1);
        } else if(amount < 1){
            System.out.println("amount: " + amount);
            buy(item,amount-1);

        }
    }

    void buy( Item item, int amount){
        if(money - item.value>= 0 && item.ammount-amount >= 0 ){
            for(int i = 0; i<inventory.size();++i){
                System.out.println("itemName: " + item.Name + "|iName: " + inventory.get(i).Name+"|");
                if(inventory.get(i).Name.equals(item.Name)){



                    inventory.get(i).ammount += amount;
                    item.ammount -= amount;
                    money = money - item.value;
                    break;

                }
            }}else if(amount > 1){
            System.out.println("amount: " + amount);
            buy(item,amount-1);
        }
    }

    void Move(){
        int xMove =(int) p.random(-7,7);
        int yMove =(int) p.random(-7,7);
        float newX = (int) (xPos +xMove);
        float newY = (int) (yPos +yMove);
        boolean haveFoundMove = false;
        if(Math.random() > 0.5){

            for(int i = 0; i<showneTileSet.size()-1;++i){
               if(showneTileSet.get(i).xPos == newX && showneTileSet.get(i).yPos == newY && showneTileSet.get(i).Contents.equals("WATER")){
                   haveFoundMove = true;
                   xPos = newX;
                   yPos = newY;
               }
            }

        }else if(Math.random() > 0.5){
            for(int i = 0; i<showneTileSet.size()-1;++i){
                if(showneTileSet.get(i).xPos == newX && showneTileSet.get(i).yPos == yPos && showneTileSet.get(i).Contents.equals("WATER")){
                    haveFoundMove = true;
                    xPos = newX;

                }
            }
        }else{
            for(int i = 0; i<showneTileSet.size()-1;++i){
                if(showneTileSet.get(i).xPos == xPos && showneTileSet.get(i).yPos == newY && showneTileSet.get(i).Contents.equals("WATER")){
                    haveFoundMove = true;
                    yPos = newY;
                }
            }
        }

        if(haveFoundMove == false){
            Move();
        }

    }
}
