import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class ShopMenu {
    PApplet p;
    Boolean visible = false;
    ArrayList StockInventory = new ArrayList<Item>();
    AlmindeligKnap btnBuyBa,btnBuyRum,btnBuyEye, btnSellBa, btnSellRum,btnSellEye,btnCloseShop;
    ShopMenu(PApplet p){
        this.p = p;
        Item Banana = new Item(10,(int) p.random(0,100),"Banana","idk");
        Item Rum = new Item(50,(int) p.random(0,100),"Rum","idk");
        Item Eyepatch = new Item(30,(int) p.random(0,100),"Eyepatch","idk");

        StockInventory.add(Banana);
        StockInventory.add(Rum);
        StockInventory.add(Eyepatch);
        btnBuyBa = new AlmindeligKnap(p,630,210,160,50,"Køb en Banan");
        btnBuyEye =new AlmindeligKnap(p,630,270,160,50,"Køb en klap");
        btnBuyRum = new AlmindeligKnap(p,630,330,160,50,"Køb en rom");

        btnSellBa = new AlmindeligKnap(p,830,210,160,50,"sælg en Banan");
        btnSellEye =new AlmindeligKnap(p,830,270,160,50,"sælg en klap");
        btnSellRum = new AlmindeligKnap(p,830,330,160,50,"slæg en rom");

        btnCloseShop = new AlmindeligKnap(p,630,500,360,50,"luk butik");


    }

    void drawMenu(Player player,float s){
        p.fill(200);
        p.rect(s*610,s*100,s*400,s*500);
        p.fill(0);
        p.textSize(32);
        p.text("Shop",s*810 - p.textWidth("Shop")/2,s*150);
        p.textSize(10);
        ArrayList<Item> t = StockInventory ;

        p.text("Taske: " +"\n" +t.get(0).Name +t.get(0).ammount +"\n" + t.get(1).Name +t.get(1).ammount +"\n" + t.get(2).Name +t.get(2).ammount,s*850 ,s*150);


        //buy
        btnBuyEye.tegnKnap();
        if(btnBuyEye.klikket){
            buyItem((Item) StockInventory.get(2),player,btnBuyEye);
            btnBuyEye.registrerRelease();
        }

        btnBuyRum.tegnKnap();
        if(btnBuyRum.klikket){
            buyItem((Item) StockInventory.get(1),player,btnBuyRum);
            btnBuyRum.registrerRelease();
        }

        btnBuyBa.tegnKnap();
        if(btnBuyBa.klikket){
            buyItem((Item) StockInventory.get(0),player,btnBuyBa);
            btnBuyBa.registrerRelease();
        }



        //sell
        btnSellEye.tegnKnap();
        if(btnSellEye.klikket){
            SellItem((Item) StockInventory.get(2),player,btnSellEye);
            btnSellEye.registrerRelease();
        }

        btnSellRum.tegnKnap();
        if(btnSellRum.klikket){
            SellItem((Item) StockInventory.get(1),player,btnSellRum);
            btnSellRum.registrerRelease();
        }

        btnSellBa.tegnKnap();
        if(btnSellBa.klikket){
            SellItem((Item) StockInventory.get(0),player,btnSellBa);
            btnSellBa.registrerRelease();
        }

        btnCloseShop.tegnKnap();
        if(btnCloseShop.klikket){
            visible = false;
            btnCloseShop.registrerRelease();
        }




    }
    void reSizeShopMenu(float s){
        btnBuyBa.size = s;
        btnBuyRum.size = s;
        btnBuyEye.size = s;
        btnSellBa.size = s;
        btnSellRum.size = s;
        btnSellEye.size = s;
        btnCloseShop.size = s;

    }
    void btbBuyItem(AlmindeligKnap btn, Player player ,Item item){

    }
    void shopMenuClicked(){
        btnBuyBa.registrerKlik(p.mouseX,p.mouseY);
        btnBuyEye.registrerKlik(p.mouseX,p.mouseY);
        btnBuyRum.registrerKlik(p.mouseX,p.mouseY);

        btnSellBa.registrerKlik(p.mouseX,p.mouseY);
        btnSellEye.registrerKlik(p.mouseX,p.mouseY);
        btnSellRum.registrerKlik(p.mouseX,p.mouseY);

        btnCloseShop.registrerKlik(p.mouseX,p.mouseY);
    }

    void buyItem(Item item ,Player player , AlmindeligKnap btn ){
        if(player.money - item.value>= 0 && item.ammount-1 >= 0 && btn.klikket){
            for(int i = 0; i<player.inventory.size();++i){
                System.out.println("itemName: " + item.Name + "|iName: " + player.inventory.get(i).Name+"|");
                if(player.inventory.get(i).Name.equals(item.Name)){



                    ++player.inventory.get(i).ammount;
                    --item.ammount;
                    player.money = player.money - item.value;
                    btn.registrerRelease();
                    break;

                }
            }
        }
    }

    void SellItem(Item item ,Player player , AlmindeligKnap btn){
        for(int i = 0; i<player.inventory.size();++i){
            System.out.println("itemName: " + item.Name + "|iName: " + player.inventory.get(i).Name+"|");
            if(player.inventory.get(i).Name.equals(item.Name)){
                if(player.inventory.get(i).ammount- 1> 0) {
                    player.money += item.ammount- 1;

                    --player.inventory.get(i).ammount;
                    ++item.ammount;



                    btn.registrerRelease();
                    break;
                }
            }
        }


    }


}
