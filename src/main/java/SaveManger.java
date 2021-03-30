import processing.core.PApplet;
import processing.core.PVector;
import processing.data.Table;

import java.util.ArrayList;
import java.util.Random;

public class SaveManger {
    PApplet p;
    GameBoard gb;
    ArrayList<PVector> cpuPos = new ArrayList<>();
    SaveManger(PApplet p){
        this.p = p;
        for (int i = 0; i<4; ++i ){
            int x = (int) p.random(0,33),y= (int) p.random(0,33);
            PVector pos = new PVector(x,y);
            cpuPos.add(pos);
        }
    }

    void saveGame(int numberOfTiles, ArrayList<Tile> tileSet){


        ArrayList<Tile> mapTiles = new ArrayList<Tile>();
        for(int i = 0; i<tileSet.size();++i){
            String contents = tileSet.get(i).Contents;
            if(!contents.equalsIgnoreCase("BORDER")) {
                mapTiles.add(tileSet.get(i));
            }
        }
        Table map = new Table();
        map.addColumn();

        for(int i = 0; i<mapTiles.size();++i){
         int xPos = mapTiles.get(i).xPos -1;
         int yPos = mapTiles.get(i).yPos -1;
         String contens = mapTiles.get(i).Contents;
         if(contens.equals("SAND")){
             map.setString(xPos,yPos,"B");
         } else if(contens.equals("WATER")){
             map.setString(xPos,yPos,"W");
         } else if(contens.equals("SHOP")){
             map.setString(xPos,yPos,"S");

         }

        }
        map.setString(0,32,"Round");
        map.setString(2,32,"Turn");
        map.setString(4,32,"Numbers of CPU's");
        map.setInt(1,32,gb.roundCount);
        map.setInt(3,32,gb.turnCount);
        map.setInt(5,32,gb.numbersOfCpus);
        loadBoatToString(map,"Player",33,gb.player);

        for(int i = 0; i< gb.cpuArrayList.size();++i){
            loadBoatToString(map,"CPU",34+i,gb.cpuArrayList.get(i));
        }


        p.saveTable(map,"F:\\new.csv");
        System.out.println("Done!");
    }

    void loadBoatToString(Table map, String titel , int column, Boat boat){
        map.setString(0,column,titel);
        map.setString(1,column, String.valueOf(gb.player.money));
        map.setInt(2,column,(int)boat.xPos);
        map.setInt(3,column,(int)boat.yPos);
        ArrayList<Item> inventory = boat.inventory;
        for(int i = 0 ; i < inventory.size();++i){
            String name = inventory.get(i).Name;
            float value = inventory.get(i).value;
            int amount = inventory.get(i).ammount;
            map.setString(4+(i*3),column, name);
            map.setFloat(5+(i*3),column, value);
            map.setInt(6+(i*3),column, amount);

            System.out.println("Name: "+map.getString(2+i,column) +  " Value: "+map.getString(3+i,column)+
                    " Amount: "+map.getString(4+i,column)
                    + "\n row:" +(4+(i*3))
                    + "\n row:" +(5+(i*3))
                    + "\n row:" +(6+(i*3)));
        }

    }

    void loadMapToBoat(Table map, String titel , int column, Boat boat){

        ArrayList<Item> inventory = boat.inventory;
        ArrayList<Item> newInventory = new ArrayList<Item>();
        for(int i = 0 ; i < inventory.size();++i) {
            //map.getFloat(5+(i*3),column),map.getInt(6+(i*3),column),map.getString(4+(i*3),column),""
            float value = map.getFloat(5+(i*3),column);
            int amount =  map.getInt(6+(i*3),column);
            String name =map.getString(4+(i*3),column);
            Item item = new Item(value,amount,name,"");
            newInventory.add(item);
        }
        boat.money = map.getFloat(1,column);
        boat.inventory = newInventory;

        gb.roundCount = map.getInt(1,32);
        gb.turnCount = map.getInt(3,32);
        boat.xPos = map.getInt(2,column);
        boat.yPos = map.getInt(3,column);
        System.out.println("Boat " + titel + "have been loaded");


    }


    void loadGame(int NumberoFTiles, Table map,ArrayList<Tile> tileSet){
        tileSet.clear();

        for(int x = -1;x<NumberoFTiles+3;++x) {
            for (int y = -1; y < NumberoFTiles+3; ++y){
                Tile t = new TerrainTile(p,"",x ,y );
                if((x <= 0 || y<= 0)||(33 <= x || 33<= y)){
                    t.Contents = "BORDER";
                }else{
                    int xPos = x-1;
                    int yPos = y-1;
                    String contens = map.getString(xPos,yPos);
                    if(contens.equals("W")){
                        contens = "WATER";
                    }else if(contens.equals("B")){
                        contens = "SAND";

                    }else if(contens.equals("S")){
                        contens = "SHOP";
                        t = new ShopTile(p,contens,xPos+1,yPos+1);
                    }
                    t.Contents = contens;

                }
                System.out.println(x + " x " + y + "tile contens: " + t.Contents+"\n" );
                tileSet.add(t);

            }
        }
        gb.numbersOfCpus = map.getInt(5,32) -1;
        System.out.println("numbersOfCpus " + (map.getInt(5,32) ));
        gb.startGame(gb.numbersOfCpus, cpuPos);

        loadMapToBoat(map, "Player" ,33, gb.player);
        for(int i= 0; i <gb.cpuArrayList.size()-2;i++){
            System.out.println("int i " + i + " column " +( 34 +i));
            loadMapToBoat(map, "CPU" ,34 +i, gb.cpuArrayList.get(i));
        }



    }

    void addShopANdPlayLocation(int numberOfTiles, ArrayList<Tile> tileSet, ArrayList<Integer> sandTiles,int numREmaningShops){
        ArrayList<Tile> possiableLocations = new ArrayList<>();
        int extra = 3;
        int ypos = 0 , xPos = 0;
        for(int i = 0; i<tileSet.size();++i){
            Tile tile = tileSet.get(i);
            for (int j = 0; j < sandTiles.size();++j){

                if(sandTiles.get(j) == i){
                    // kode stas
                    if(numREmaningShops < 0){
                        addShopANdPlayLocation(numberOfTiles,tileSet,sandTiles,numREmaningShops);
                        break;

                    }
                    if(tile.xPos > 2 && tile.xPos <30 && tile.yPos > 2 && tile.yPos <30 ){
                        int numbersOfWaterTiles = 0;
                        if(tileSet.get(i-1).Contents.equals("WATER") && tileSet.get(i-2).Contents.equals("WATER")){
                            numbersOfWaterTiles++;
                        }
                        if(tileSet.get(i+1).Contents.equals("WATER") && tileSet.get(i+2).Contents.equals("WATER")){
                            numbersOfWaterTiles++;
                        }
                        if(tileSet.get(i+numberOfTiles+extra).Contents.equals("WATER") && tileSet.get(i+(numberOfTiles+extra)*2).Contents.equals("WATER")){
                            numbersOfWaterTiles++;
                        }
                        if(tileSet.get(i-(numberOfTiles+extra)).Contents.equals("WATER") && tileSet.get(i-(numberOfTiles+extra)*2).Contents.equals("WATER")){
                            numbersOfWaterTiles++;
                        }

                        if(numberOfTiles > 1){
                            if(p.random(0,1) > 0.5 && numREmaningShops > 0 && ypos< tileSet.get(i).yPos && xPos != tileSet.get(i).xPos ){
                                System.out.println("Shops X:" + tileSet.get(i).xPos + " Y:" + tileSet.get(i).yPos);
                                Tile t = new ShopTile(p,"SHOP",tileSet.get(i).xPos ,tileSet.get(i).yPos);
                                tileSet.set(i,t);
                                ypos = tileSet.get(i).yPos;
                                xPos = tileSet.get(i).xPos;
                                numREmaningShops --;
                            }else {
                                cpuPos.add(new PVector(tileSet.get(i).xPos,tileSet.get(i).yPos));
                            }

                        }
                    }

                    break;
                }

            }
        }

    }

    void generateGame(int numberOfTiles, ArrayList<Tile> tileSet){

        ArrayList<Integer> sandTiles = new ArrayList<>();
        int in = 0;

        float increment = (float) 0.09;
        int antalSand = 0;
        int antalVand = 0;
        float xoff =  0.0f; // Start xoff at 0

        for(int x = -1;x<numberOfTiles+3;++x) {
            xoff += increment;   // Increment xoff
            float yoff = 0.0f;   // For every xoff, start yoff at 0
            for (int j = -1; j < numberOfTiles+3; ++j){
                yoff += increment; // Increment yoff

                // Calculate noise and scale by 255
                float bright = p.noise(xoff, yoff) * 255;

                Tile t = new TerrainTile(p,"",x ,j );
                if((x <= 0 || j<= 0)||(33 <= x || 33<= j)){
                    t.Contents = "BORDER";
                }else{
                    if(bright > 100){
                        antalVand +=1;
                        t.Contents ="WATER";
                    }else{
                        antalSand +=1;
                        sandTiles.add(in);
                       t.Contents ="SAND";
                        //t.Contents ="WATER";

                    }
                }
                /*for(int e=0; e<3;++e){
                    if(x == shopLoc[e].x&& j== shopLoc[e].y){
                        System.out.println("x: "+ x + "=="+ "Shop: " +shopLoc[e].x + "x: "+ j + "=="+ "Shop: " +shopLoc[e].y );
                        t = new ShopTile(p,"SHOP",x ,j);
                        System.out.println("SHOP:  "+x + " x " + j);
                    }
                }*/

                System.out.println(x + " x " + j + "tile contens: " + t.Contents);
              tileSet.add(t);
                in++;
            }
        }

        System.out.println("vand: " + antalVand + " Sand: " + antalSand +"\n " + in);

        addShopANdPlayLocation(numberOfTiles,tileSet,sandTiles,3);
        /*if(antalVand< antalSand){
            tileSet.clear();
            generateGame(numberOfTiles,  tileSet);
        }*/

    }
    }

