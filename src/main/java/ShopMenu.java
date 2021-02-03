import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class ShopMenu {
    PApplet p;
    Boolean visible = false;
    ArrayList StockInventory = new ArrayList<Item>();
    AlmindeligKnap btnBuyBa,btnBuyRum,btnBuyEye;
    ShopMenu(PApplet p){
        this.p = p;
        Item Banana = new Item(10,(int) p.random(0,100),"Banana","idk");
        Item Rum = new Item(50,(int) p.random(0,100),"Rum","idk");
        Item Eyepatch = new Item(30,(int) p.random(0,100),"Eyepatch","idk");

        StockInventory.add(new Item(10,0,"Banana","idk"));
        StockInventory.add(Rum);
        StockInventory.add(Eyepatch);
        btnBuyBa = new AlmindeligKnap(p,630,210,340,50,"Køb en Banan");
        btnBuyEye =new AlmindeligKnap(p,630,270,340,50,"Køb en klap");
        btnBuyRum = new AlmindeligKnap(p,630,330,340,50,"Køb en rom");


    }

    void drawMenu(Player player){
        p.fill(200);
        p.rect(610,100,400,500);
        p.fill(0);
        p.textSize(32);
        p.text("Shop",810 - p.textWidth("Shop")/2,150);
        p.textSize(10);
        ArrayList<Item> t = StockInventory ;

        p.text("Taske: " +"\n" +t.get(0).Name +t.get(0).ammount +"\n" + t.get(1).Name +t.get(1).ammount +"\n" + t.get(2).Name +t.get(2).ammount,850 ,150);
        btnBuyBa.tegnKnap();
        btnBuyEye.tegnKnap();
        btnBuyRum.tegnKnap();

        if(btnBuyRum.klikket){
            System.out.println("sdasds");
            btnBuyRum.registrerRelease();
        }
        if(btnBuyBa.klikket){
            System.out.println("sdasds");
            btnBuyBa.registrerRelease();
        }
        if(btnBuyEye.klikket){
            System.out.println("sdasds");
            btnBuyEye.registrerRelease();
        }



    }

    void shopMenuClicked(){
        btnBuyBa.registrerKlik(p.mouseX,p.mouseY);
        btnBuyEye.registrerKlik(p.mouseX,p.mouseY);
        btnBuyRum.registrerKlik(p.mouseX,p.mouseY);

    }

    void buyItem(Item item){

    }


}
