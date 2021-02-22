import processing.core.PApplet;
import processing.core.PVector;
import processing.data.Table;

import java.util.ArrayList;

public class SaveManger {
    PApplet p;
    GameBoard gb;
    SaveManger(PApplet p){
        this.p = p;
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

        loadBoatToString(map,"Player",33,gb.player);
        loadBoatToString(map,"CPU",34,gb.cpu);
        map.setString(0,32,"Round");
        map.setString(2,32,"Turn");
        map.setInt(1,32,gb.roundCount);
        map.setInt(3,32,gb.turnCount);
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
            Item item = new Item(map.getFloat(5+(i*3),column),map.getInt(6+(i*3),column),map.getString(4+(i*3),column),"");
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
                System.out.println(x + " x " + y + "tile contens: " + t.Contents);
                tileSet.add(t);}
        }
        gb.startGame();

        loadMapToBoat(map, "Player" ,33, gb.player);
        loadMapToBoat(map, "CPU" ,34, gb.cpu);


    }



    void generateGame(int numberOfTiles, ArrayList<Tile> tileSet){
        /*void GenerateMap(int NumberoFTiles){
        PVector[] shopLoc = {new PVector(16,16),new PVector(0,0),new PVector(0,0) };//new PVector(15,17),new PVector(17,17)};
       /*
        * */
        PVector[] shopLoc = {new PVector(16,16),new PVector(1,1),new PVector(1,1) };//new PVector(15,17),new PVector(17,17)};
        /*for(int i=0; i<3;++i){


            int px = (int) p.random(1,33);
            int py = (int) p.random(1,33);
            shopLoc[i] = new PVector(px,py);
        }*/


        for(int x = -1;x<numberOfTiles+3;++x) {
            for (int j = -1; j < numberOfTiles+3; ++j){
                Tile t = new TerrainTile(p,"",x ,j );
                if((x <= 0 || j<= 0)||(33 <= x || 33<= j)){
                    t.Contents = "BORDER";
                }else{
                    if(Math.random() > 0.3){
                        t.Contents ="WATER";
                    }else{
                        t.Contents ="SAND";
                    }
                }
                for(int e=0; e<3;++e){
                    if(x == shopLoc[e].x&& j== shopLoc[e].y){
                        System.out.println("x: "+ x + "=="+ "Shop: " +shopLoc[e].x + "x: "+ j + "=="+ "Shop: " +shopLoc[e].y );
                        t = new ShopTile(p,"SHOP",x ,j);
                        System.out.println("SHOP:  "+x + " x " + j);
                    }
                }

                System.out.println(x + " x " + j + "tile contens: " + t.Contents);
              tileSet.add(t);}
        }

    }
    }

