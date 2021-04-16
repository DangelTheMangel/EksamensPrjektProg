import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class GameBoard {
    //all the variables on the playing board
    PApplet p;
    //this boolean determines whether to use alcohol on the board or not
    boolean showTileImage = true;
    //these are all tiles on the map
    ArrayList<Tile> tileSet = new ArrayList<Tile>();
    //all the buttons
    NomalButton btnMenu, btnMap, btnAcceptRul, btnBackToMenu;
    //pause menu
    public PauseMenu pauseMenu;
    //quick access to settings
    SettingMenu settingMenu;
    //this class is responsible for creating the folder and saving the game
    SaveManger saveManger;
    //the players
    public Player player;

    //this boat will be the winners eventually
    Boat theWinner;
    //this variable tells if the players have won
    boolean playerWon;
    //This boolean tells you whether the folder should be drawn or the tileset
    boolean drawmap = false;
    //Devconsolen
    DevConsole devConsole;
    //the list of all opponents
    ArrayList<NPCBoat> NPCBoatArrayList = new ArrayList<NPCBoat>();
    //max rounds
    int maxRounds = 300;
    //number of opponents
    int numbersOfCpus = 3;
    //round you are reached
    int roundCount = 0;
    //the players turns
    int turnCount;
    //This variable helps to scale all ui elements with the screen size
    float scaleSize = 1;
    //This boolean determines if rolling is in progress
    Boolean rul = true;
    //Whether the game board is visible
    Boolean visible = false;
    //This boolean tells if the trip is over
    Boolean turnEnded = false;
    //This list has good cpu positions for the start
    ArrayList<PVector> cpuPos = new ArrayList<>();
    //the background to the game board
    PImage bg;
    //This variable determines the roll animation
    int rulleAnimation = 0;

    //---------- CONSTRUCTOR :) ----------\\
    GameBoard(PApplet p, PauseMenu pauseMenu) {

        this.p = p;
        saveManger = new SaveManger(p);
        saveManger.gb = this;
        saveManger.generateGame(32, tileSet);


        this.pauseMenu = pauseMenu;
        startGame(numbersOfCpus, cpuPos);
        btnMenu = new NomalButton(p, p.width - 60, 0, 60, 60, "-\n-\n-");
        btnMap = new NomalButton(p, 270, 650, 450, 50, "map");
        btnAcceptRul = new NomalButton(p, 270, 650, 450, 50, "Rul");
        btnBackToMenu = new NomalButton(p, 270, 650, 450, 50, "Tilbage til menu");
        pauseMenu.gb = this;
        pauseMenu.mainMenu.gb = this;
        pauseMenu.mainMenu.chooseGameMenu.gb = this;

        settingMenu = pauseMenu.settingMenu;
        settingMenu.gb = this;
        settingMenu.tfNumbersOfPlayers.input = "" + numbersOfCpus;
        settingMenu.tfMaxRound.input = "" + maxRounds;
        devConsole = new DevConsole(p, this);
        settingMenu.sm = saveManger;
        settingMenu.tfGenNum.input = "" + saveManger.increment * 100;

    }
    //----------METHODS----------\\

    //This function starts the game by making all the boats
    void startGame(int antalCPU, ArrayList<PVector> cpuPos) {

        int playerPos = (int) p.random(0, cpuPos.size());
        player = new Player(p, cpuPos.get(playerPos).x, cpuPos.get(playerPos).y, 500);
        cpuPos.remove(playerPos);
        player.generateInventory();
        player.boatPic = p.loadImage("Skibet32.png");
        //turnList.add(new Turn(p,player));
        // turnCount = (int) p.random(0, 7);
        turnCount = 1;
        for (int i = 0; i < antalCPU; ++i) {
            int cpurPos = (int) p.random(0, cpuPos.size());
            int x = (int) cpuPos.get(cpurPos).x, y = (int) cpuPos.get(cpurPos).y;
            cpuPos.remove(cpurPos);
            System.out.println("x: " + x + " y: " + y);
            NPCBoatArrayList.add(new NPCBoat(p, x, y, 500));

        }

        for (int i = 0; i < NPCBoatArrayList.size(); ++i) {

            NPCBoatArrayList.get(i).generateInventory();
            NPCBoatArrayList.get(i).boatPic = p.loadImage("piratbad.png");
            // turnList.add(new Turn(p,cpuArrayList.get(i)));
        }


    }

    //This function the drawing board
    void drawBoard() {

        if (visible && !devConsole.visible && maxRounds > roundCount) {

            p.clear();
            //show background image is not there so it loads it
            if (bg != null) {
                p.image(bg, 0, 0, p.width, p.height);
            } else {
                p.background(200);
                bg = p.loadImage("bgofgame.png");
            }

            //Drawing the ui
            p.textSize(16 * scaleSize);
            p.fill(200);
            p.rect(0, 0, p.width, 60 * scaleSize);
            String info = "Round: " + roundCount + "/" + maxRounds + "\nTurn: " + turnCount;
            p.fill(0);
            p.text(info, (p.width / 2 - p.textWidth(info) / 2), 30 * scaleSize);
            ArrayList<Item> t = player.inventory;
            p.fill(200);

            p.fill(0);
            p.text("\nTaske: " + "\nPenge" + player.money
                            + "\n" + t.get(0).Name + t.get(0).ammount
                            + "\n" + t.get(1).Name + t.get(1).ammount
                            + "\n" + t.get(2).Name + t.get(2).ammount
                            + "\n -----------------\n"

                    , 880 * scaleSize, 150 * scaleSize);

            //fills the players list of tiles they can see
            player.fillUpShownTiles(tileSet);
            //fills the list up of tiles the npcs can see
            for (int j = 0; j < NPCBoatArrayList.size(); ++j) {
                NPCBoatArrayList.get(j).fillUpShownTiles(tileSet);
                if (rulleAnimation > 0) {
                    Rul();
                    rulleAnimation--;
                }
            }

            //draws the buttons
            btnMenu.drawButton();
            btnMap.drawButton();

            //perform the functions of the buttons when clicked
            if (btnMenu.clicked) {
                aktiverPauseMenu();
            }
            if (btnMap.clicked) {
                drawmap = !drawmap;
                btnMap.registrerRelease();
            }
            //draws the tiles that players can see
            ArrayList<Tile> showneTileSet = player.showneTileSet;
            if (!drawmap) {
                for (int i = 0; i < showneTileSet.size(); ++i) {
                    p.pushMatrix();
                    float posX = showneTileSet.get(i).xPos;
                    float posY = showneTileSet.get(i).yPos;

                    if (i < 5) {

                        //225*scaleSize
                        posX = 2.5f;
                        posY = i + 1.5f;
                    } else if (i < 10) {
                        posX = 3.5f;
                        posY = i - 3.5f;
                    } else if (i < 15) {
                        posX = 4.5f;
                        posY = i - 8.5f;
                    } else if (i < 20) {
                        posX = 5.5f;
                        posY = i - 13.5f;
                    } else {
                        posX = 6.5f;
                        posY = i - 18.5f;
                    }
                    //show tilen does not have a picture it adds it here
                    if (showTileImage && showneTileSet.get(i).tileImage == null) {
                        showneTileSet.get(i).setTileImage();
                    }

                    showneTileSet.get(i).Display(posX, posY, (int) (85 * scaleSize), showTileImage, scaleSize);
                    showneTileSet.get(i).drawShopMenu(player, scaleSize);
                    showneTileSet.get(i).checkIfMouseOver(posX, posY, (int) (85 * scaleSize));

                    //drawing the npc boats
                    drawShips(player, i, posX, posY);
                    for (int j = 0; j < NPCBoatArrayList.size(); ++j) {
                        drawShips(NPCBoatArrayList.get(j), i, posX, posY);
                    }
                    p.popMatrix();

                    //show the shop is clicked then open the menu
                    if (showneTileSet.get(i).cliked) {
                        if (showneTileSet.get(i).Contents.equals("SHOP")) {
                            //klikker på shop
                            player.findPath(showneTileSet.get(i), tileSet);
                            System.out.println("ShopTime");
                            showneTileSet.get(i).showShop(scaleSize);


                        } else if (!showneTileSet.get(i).Contents.equals("BORDER")) {
                            showneTileSet.get(i).selectedTile = true;
                            if (showneTileSet.get(i).selectedTile) {
                                player.findPath(showneTileSet.get(i), tileSet);
                            }
                        }
                        showneTileSet.get(i).cliked = false;
                    }
                    turnCount = player.movePlayer(turnCount);

                    if (turnCount <= 0 && !rul) {
                        turnEnded = true;

                    }


                }
            } else {
                //draws the map of the map
                for (int i = 0; i < tileSet.size(); ++i) {
                    p.pushMatrix();
                    p.translate(225 * scaleSize, 140 * scaleSize);
                    Tile tile = tileSet.get(i);
                    tile.Display(tile.xPos, tile.yPos, (int) (12 * scaleSize), false, scaleSize);
                    p.fill(201, 0, 0);
                    if (tile.xPos == player.xPos && tile.yPos == player.yPos) {

                        float s = (int) (12 * scaleSize);
                        p.rect(s * tile.xPos, s * tile.yPos, s, s);
                    }
                    p.popMatrix();
                }

            }
            //when the players' turn is over it goes through all the npcs turn and allows the players to roll the dice
            if (turnEnded) {
                p.textSize(60 * scaleSize);
                p.fill(200, 200, 200, 200);
                p.rect(0, p.height / 2 - 60 * scaleSize, p.width, 80 * scaleSize);
                p.fill(0);
                p.text("VENTER PÅ MODSPILLERNES TUR", p.width / 2 - p.textWidth("VENTER " +
                        "PÅ MODSPILLERNES TUR") / 2, p.height / 2);

                for (int j = 0; j < NPCBoatArrayList.size(); ++j) {
                    NPCBoatArrayList.get(j).Turn();
                    System.out.println("cpu number: " + j);
                }
                if (!rul)
                    ++roundCount;
                turnEnded = false;
                rul = true;
            }
            Rul();

        //draws the dev console if it is visible
        } else if (devConsole.visible) {
            devConsole.display(scaleSize);
        //checks if the game is over and shows it checks who won
        } else if (maxRounds <= roundCount) {
            chooseWinner();
            p.textSize(14 * scaleSize);
            if (playerWon) {
                String s = "Du vandt!";
                p.text(s, p.width / 2 - p.textWidth(s) / 2, p.height / 2);
            } else {
                String s = "Du Tabte!";
                p.text(s, p.width / 2 - p.textWidth(s) / 2, p.height / 2);
            }
            btnBackToMenu.drawButton();

            if (btnBackToMenu.clicked) {
                visible = false;

                main.mainMenu.visible = true;
                saveManger.increment += p.random(-0.02f, 0.02f);

                main.mainMenu.chooseGameMenu.needNewGame = true;
                btnBackToMenu.registrerRelease();
            }
        }
    }

    //This function for all buttons and look to fit with the screen size
    void reSizeGamebord() {
        settingMenu.reSizeBtn(scaleSize, btnMenu);
        settingMenu.reSizeBtn(scaleSize, btnMap);
        settingMenu.reSizeBtn(scaleSize, btnAcceptRul);
        settingMenu.reSizeBtn(scaleSize, btnBackToMenu);
        settingMenu.reSizeFT(scaleSize, devConsole.textField);

    }

    //This function draws a boat
    void drawShips(Boat boat, int i, float posX, float posY) {
        ArrayList<Tile> showneTileSet = player.showneTileSet;
        if (boat.xPos == showneTileSet.get(i).xPos && boat.yPos == showneTileSet.get(i).yPos) {

            boat.displayBoat(posX, posY, (int) (85 * scaleSize));
        }
    }

    //This function activates the pause menu
    void aktiverPauseMenu() {
        pauseMenu.visible = true;
        visible = false;
        btnMenu.registrerRelease();
    }

    //This function activates the dev console
    void akitverDevconsole() {
        devConsole.visible = !devConsole.visible;

    }

    //This function allows you to click on the board
    void boardmouseClicked() {
        ArrayList<Tile> showneTileSet = player.showneTileSet;
        devConsole.mouseClick();
        if (!rul) {
            btnMenu.registerClick(p.mouseX, p.mouseY);
            btnMap.registerClick(p.mouseX, p.mouseY);
            if (!drawmap)
                if (turnEnded == false) {
                    for (int i = 0; i < showneTileSet.size(); ++i) {
                        float posX = showneTileSet.get(i).xPos;
                        float posY = showneTileSet.get(i).yPos;

                        if (i < 5) {

                            //225*scaleSize
                            posX = 2.5f;
                            posY = i + 1.5f;
                        } else if (i < 10) {
                            posX = 3.5f;
                            posY = i - 3.5f;
                        } else if (i < 15) {
                            posX = 4.5f;
                            posY = i - 8.5f;
                        } else if (i < 20) {
                            posX = 5.5f;
                            posY = i - 13.5f;
                        } else {
                            posX = 6.5f;
                            posY = i - 18.5f;
                        }/**/

                        showneTileSet.get(i).clickShop();
                        showneTileSet.get(i).checkIfCliked((int) posX, (int) posY, (int) (100 * scaleSize));

                    }
                }
        } else {
            btnAcceptRul.registerClick(p.mouseX, p.mouseY);
        }
        if (maxRounds <= roundCount) {
            btnBackToMenu.registerClick(p.mouseX, p.mouseY);
        }
    }

    //This feature rolls the dice
    void Rul() {
        if (rul) {
            int terningTal = (int) (p.random(1, 6));
            System.out.println(terningTal);
            PImage img2 = p.loadImage("terning0.png");
            PImage img = p.loadImage("terning" + p.str(terningTal) + ".png");
            p.fill(200, 200, 200, 200);
            p.rect(0, 60 * scaleSize, p.width, p.height);
            p.image(img2, p.width / 2 - 100 * scaleSize, p.height / 2 - 100 * scaleSize, 200 * scaleSize, 200 * scaleSize);
            p.image(img, p.width / 2 - 100 * scaleSize, p.height / 2 - 100 * scaleSize, 200 * scaleSize, 200 * scaleSize);
            btnAcceptRul.drawButton();
            if (btnAcceptRul.clicked) {
                rul = false;
                turnCount = terningTal;
                btnAcceptRul.registrerRelease();
            }
        }


    }

    //This feature allows the dev console text field to detect what you are clicking on
    void keyTyped() {
        if (visible) {
            devConsole.keybordTyped();
        }
    }

    //Your feature finds the winners of the game
    void chooseWinner() {
        Boat winner = NPCBoatArrayList.get(0);
        for (int i = 0; i < NPCBoatArrayList.size(); ++i) {
            Boat boat = NPCBoatArrayList.get(i);
            if (winner != boat) {
                if (winner.calRating() < boat.calRating()) {
                    winner = boat;
                    i = 0;
                }
            }
        }


        if (winner.calRating() < player.calRating()) {
            theWinner = player;
            playerWon = true;
        } else {
            theWinner = winner;
            playerWon = false;
        }
    }

}

