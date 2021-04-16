import processing.core.PApplet;
import processing.core.PVector;
import processing.data.Table;

import javax.swing.*;
import java.util.ArrayList;

public class SaveManger {
    //the variables
    PApplet p;
    //quick access to the game board
    GameBoard gb;
    //increment to the genoration of tiles
    public float increment = (float) 0.09;
    //list of possible positions for the npcs
    ArrayList<PVector> cpuPos = new ArrayList<>();

    //---------- CONSTRUCTOR :) ----------\\
    SaveManger(PApplet p) {
        this.p = p;
        for (int i = 0; i < 4; ++i) {
            int x = (int) p.random(0, 33), y = (int) p.random(0, 33);
            PVector pos = new PVector(x, y);
            cpuPos.add(pos);
        }
    }
    //----------METHODS----------\\

    //This function saves all information in the game in a csv file
    void saveGame(int numberOfTiles, ArrayList<Tile> tileSet) {
        ArrayList<Tile> mapTiles = new ArrayList<Tile>();
        for (int i = 0; i < tileSet.size(); ++i) {
            String contents = tileSet.get(i).Contents;
            if (!contents.equalsIgnoreCase("BORDER")) {
                mapTiles.add(tileSet.get(i));
            }
        }
        Table map = new Table();
        map.addColumn();
        for (int i = 0; i < mapTiles.size(); ++i) {
            int xPos = mapTiles.get(i).xPos - 1;
            int yPos = mapTiles.get(i).yPos - 1;
            String contens = mapTiles.get(i).Contents;
            if (contens.equals("SAND")) {
                map.setString(xPos, yPos, "B");
            } else if (contens.equals("WATER")) {
                map.setString(xPos, yPos, "W");
            } else if (contens.equals("SHOP")) {
                map.setString(xPos, yPos, "S");
            } else if (contens.equals("GRASS")) {
                map.setString(xPos, yPos, "G");
            }
        }
        map.setString(0, 32, "Round");
        map.setString(2, 32, "Turn");
        map.setString(4, 32, "Numbers of CPU's");
        map.setInt(1, 32, gb.roundCount);
        map.setInt(3, 32, gb.turnCount);
        map.setInt(5, 32, gb.numbersOfCpus);
        loadBoatToString(map, "Player", 33, gb.player);
        for (int i = 0; i < gb.NPCBoatArrayList.size(); ++i) {
            loadBoatToString(map, "CPU", 34 + i, gb.NPCBoatArrayList.get(i));
        }
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);
        System.out.println(f.getCurrentDirectory());

    }

    //takes the boats and makes adds them to the csv file
    void loadBoatToString(Table map, String titel, int column, Boat boat) {
        map.setString(0, column, titel);
        map.setString(1, column, String.valueOf(gb.player.money));
        map.setInt(2, column, (int) boat.xPos);
        map.setInt(3, column, (int) boat.yPos);
        ArrayList<Item> inventory = boat.inventory;
        for (int i = 0; i < inventory.size(); ++i) {
            String name = inventory.get(i).Name;
            float value = inventory.get(i).value;
            int amount = inventory.get(i).ammount;
            map.setString(4 + (i * 3), column, name);
            map.setFloat(5 + (i * 3), column, value);
            map.setInt(6 + (i * 3), column, amount);
        }
    }

    //This function takes the boats from the csv file and converts it to a boat
    void loadMapToBoat(Table map, String titel, int column, Boat boat) {
        ArrayList<Item> inventory = boat.inventory;
        ArrayList<Item> newInventory = new ArrayList<Item>();
        for (int i = 0; i < inventory.size(); ++i) {
            float value = map.getFloat(5 + (i * 3), column);
            int amount = map.getInt(6 + (i * 3), column);
            String name = map.getString(4 + (i * 3), column);
            Item item = new Item(value, amount, name);
            newInventory.add(item);
        }
        boat.money = map.getFloat(1, column);
        boat.inventory = newInventory;

        gb.roundCount = map.getInt(1, 32);
        gb.turnCount = map.getInt(3, 32);
        boat.xPos = map.getInt(2, column);
        boat.yPos = map.getInt(3, column);


    }

    //This function takes the csv file and adds it to the game
    void loadGame(int NumberoFTiles, Table map, ArrayList<Tile> tileSet) {
        tileSet.clear();

        for (int x = -1; x < NumberoFTiles + 3; ++x) {
            for (int y = -1; y < NumberoFTiles + 3; ++y) {
                Tile t = new TerrainTile(p, "", x, y);
                if ((x <= 0 || y <= 0) || (33 <= x || 33 <= y)) {
                    t.Contents = "BORDER";
                    t.tileImage = p.loadImage("border.png");
                } else {
                    int xPos = x - 1;
                    int yPos = y - 1;
                    String contens = map.getString(xPos, yPos);
                    if (contens.equals("W")) {
                        contens = "WATER";
                    } else if (contens.equals("B")) {
                        contens = "SAND";

                    } else if (contens.equals("S")) {
                        contens = "SHOP";
                        t = new ShopTile(p, contens, xPos + 1, yPos + 1);
                    } else if (contens.equals("G")) {
                        contens = "GRASS";
                        //skriv noget med den vælger billede
                    }
                    t.Contents = contens;
                }
                tileSet.add(t);
            }
        }
        gb.numbersOfCpus = map.getInt(5, 32) - 1;
        System.out.println("numbersOfCpus " + (map.getInt(5, 32)));
        gb.startGame(gb.numbersOfCpus, cpuPos);

        loadMapToBoat(map, "Player", 33, gb.player);
        for (int i = 0; i < gb.NPCBoatArrayList.size() - 2; i++) {

            loadMapToBoat(map, "CPU", 34 + i, gb.NPCBoatArrayList.get(i));
        }


    }

    //adds shops to the map
    void addShopANdPlayLocation(int numberOfTiles, ArrayList<Tile> tileSet, ArrayList<Integer> sandTiles, int numREmaningShops) {
        int ypos = -1, xPos = -1;
        for (int i = 0; i < tileSet.size(); ++i) {
            Tile tile = tileSet.get(i);
            for (int j = 0; j < sandTiles.size(); ++j) {

                if (sandTiles.get(j) == i) {

                    if (numREmaningShops < 0) {
                        addShopANdPlayLocation(numberOfTiles, tileSet, sandTiles, numREmaningShops);
                        break;

                    }
                    if (tile.xPos > 2 && tile.xPos < 30 && tile.yPos > 2 && tile.yPos < 30) {
                        int numbersOfWaterTiles = 0;
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 1);
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 35);
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 36);
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 37);
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 1);
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 35);
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 36);
                        numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 37);
                        if (numberOfTiles > 1) {
                            if (numberOfTiles > 1 &&
                                    numREmaningShops != 0
                                    && (ypos + 2 > tileSet.get(i).yPos && tileSet.get(i).xPos > xPos + 2)) {
                                System.out.println("Shops X:" + tileSet.get(i).xPos + " Y:" + tileSet.get(i).yPos);
                                Tile t = new ShopTile(p, "SHOP", tileSet.get(i).xPos, tileSet.get(i).yPos);
                                tileSet.set(i, t);
                                ypos = tileSet.get(i).yPos;
                                xPos = tileSet.get(i).xPos;
                                numREmaningShops--;
                            } else {
                                cpuPos.add(new PVector(tileSet.get(i).xPos, tileSet.get(i).yPos));
                            }

                        }
                    }

                    break;
                }

            }
        }

        if (numREmaningShops != 0) {
            System.out.println("rs: " + numREmaningShops);
            for (int j = 0; j < tileSet.size(); ++j) {
                if (numREmaningShops == 0) {
                    break;
                }
                int i = (int) p.random(64, tileSet.size());
                int numbersOfWaterTiles = 0;
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 1);
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 35);
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 36);
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i + 37);
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 1);
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 35);
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 36);
                numbersOfWaterTiles += checkIfLoactionHaveWater(tileSet, i - 37);
                if (numbersOfWaterTiles > 1 && !tileSet.get(i).Contents.equals("BORDER")) {
                    Tile t = new ShopTile(p, "SHOP", tileSet.get(i).xPos, tileSet.get(i).yPos);
                    tileSet.set(i, t);
                    numREmaningShops -= 1;
                    System.out.println("rs: " + numREmaningShops);

                } else {
                }
            }
        }
    }

    //checks if j tile is water
    int checkIfLoactionHaveWater(ArrayList<Tile> tileSet, int j) {
        if (j < tileSet.size() && j > 0) {
            if (tileSet.get(j).Contents.equals("WATER")) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    //This feature bothers the game
    void generateGame(int numberOfTiles, ArrayList<Tile> tileSet) {

        ArrayList<Integer> sandTiles = new ArrayList<>();
        ArrayList<PVector> waterTiles = new ArrayList<>();
        int in = 0;
        float xoff = 0.0f; // Start xoff at 0

        for (int x = -1; x < numberOfTiles + 3; ++x) {
            xoff += increment;   // Increment xoff
            float yoff = 0.0f;   // For every xoff, start yoff at 0
            for (int j = -1; j < numberOfTiles + 3; ++j) {
                yoff += increment; // Increment yoff
                float bright = p.noise(xoff, yoff) * 255;
                Tile t = new TerrainTile(p, "", x, j);
                if ((x <= 0 || j <= 0) || (33 <= x || 33 <= j)) {
                    t.Contents = "BORDER";
                } else {
                    if (bright > 99) {
                        waterTiles.add(new PVector(x, j));
                        t.Contents = "WATER";
                    } else if (bright > 85) {
                        sandTiles.add(in);
                        t.Contents = "SAND";
                    } else {
                        t.Contents = "GRASS";
                    }
                }
                tileSet.add(t);
                in++;
            }
        }
        addShopANdPlayLocation(numberOfTiles, tileSet, sandTiles, 3);
        if (gb.cpuPos != null)
            gb.cpuPos.clear();
        gb.cpuPos = waterTiles;
    }
}

