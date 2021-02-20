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

    void saveGame(int NumberoFTiles, ArrayList<Tile> tileSet){
        ArrayList<Tile> mapTiles = new ArrayList<Tile>();
        for(int i = 0; i<tileSet.size();++i){
            String contens = tileSet.get(i).Contents;
            if(!contens.equalsIgnoreCase("BORDER")) {
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
        p.saveTable(map,"F:\\new.csv");
        System.out.println("Done!");
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

                    }
                    t.Contents = contens;
                }
                System.out.println(x + " x " + y + "tile contens: " + t.Contents);
                tileSet.add(t);}
        }
        gb.startGame();

    }



    void generateGame(int NumberoFTiles, ArrayList<Tile> tileSet){
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

        for(int x = -1;x<NumberoFTiles+3;++x) {
            for (int j = -1; j < NumberoFTiles+3; ++j){
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

