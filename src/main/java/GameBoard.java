import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class GameBoard {
    PApplet p;

    boolean showTileImage = true;
    //til bord klassen
    boolean won = false;
    ArrayList<Tile> tileSet = new ArrayList<Tile>();
    //ArrayList<Tile> showneTileSet = new ArrayList<Tile>();
    NomalButton btnMenu, btnMap, btnAcceptRul, btnBackToMenu;
    public PauseMenu pauseMenu;
    SettingMenu settingMenu;
    SaveManger saveManger;
    public Player player;
    Boat theWinner;
    boolean playerWon;
    boolean drawmap = false;
    DevConsole devConsole;

    ArrayList<CpuBoat> cpuBoatArrayList = new ArrayList<CpuBoat>();
    //  Cpu cpu;
    int maxRounds = 300;
    int numbersOfCpus = 3;
    int roundCount = 0;
    int turnCount;
    float scaleSize = 1;
    Boolean rul = true;
    Boolean visible = false;
    Boolean turnEnded = false;
    ArrayList<PVector> cpuPos = new ArrayList<>();
    PImage bg;
    int rulleAnimation = 0;

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
        settingMenu.tfNumbersOfPlayers.indput = "" + numbersOfCpus;
        settingMenu.tfMaxRound.indput = "" + maxRounds;
        devConsole = new DevConsole(p, this);
        settingMenu.sm = saveManger;
        settingMenu.tfGenNum.indput = "" + saveManger.increment * 100;

    }


    void startGame(int antalCPU, ArrayList<PVector> cpuPos) {

        int playerPos = (int) p.random(0, cpuPos.size());
        player = new Player(p, cpuPos.get(playerPos).x, cpuPos.get(playerPos).y, 0, 500);
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
            cpuBoatArrayList.add(new CpuBoat(p, x, y, 0, 500));

        }

        for (int i = 0; i < cpuBoatArrayList.size(); ++i) {

            cpuBoatArrayList.get(i).generateInventory();
            cpuBoatArrayList.get(i).boatPic = p.loadImage("piratbad.png");
            // turnList.add(new Turn(p,cpuArrayList.get(i)));
        }


    }

    void drawBoard() {

        if (visible && !devConsole.visibale && maxRounds > roundCount) {

            p.clear();

            if (bg != null) {
                p.image(bg, 0, 0, p.width, p.height);
            } else {
                p.background(200);
                bg = p.loadImage("bgofgame.png");
            }

            //ui
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
            player.fillUpShownTiles(tileSet);

            for (int j = 0; j < cpuBoatArrayList.size(); ++j) {
                cpuBoatArrayList.get(j).fillUpShownTiles(tileSet);

                //dette kan være med til program går unga
                if (rulleAnimation > 0) {
                    //disable input eller noget
                    Rul();
                    rulleAnimation--;
                }
            }

            //burger menu kanp
            btnMenu.tegnKnap();
            btnMap.tegnKnap();

            //brætet
            if (btnMenu.klikket) {
                aktiverPauseMenu();
            }

            if (btnMap.klikket) {
                drawmap = !drawmap;
                btnMap.registrerRelease();
            }

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
                    //drawing bord
                    if (showTileImage && showneTileSet.get(i).tileImage == null) {
                        showneTileSet.get(i).setTileImage();
                    }
                    showneTileSet.get(i).Display(posX, posY, (int) (85 * scaleSize), showTileImage, scaleSize);
                    showneTileSet.get(i).drawShopMenu(player, scaleSize);
                    showneTileSet.get(i).checkIfMouseOver(posX, posY, (int) (85 * scaleSize));

                    //drawing boats
                    drawShips(player, i, posX, posY);
                    for (int j = 0; j < cpuBoatArrayList.size(); ++j) {
                        drawShips(cpuBoatArrayList.get(j), i, posX, posY);
                    }
                    p.popMatrix();


                    //
                    if (showneTileSet.get(i).cliked) {
                        if (showneTileSet.get(i).Contents.equals("SHOP")) {
                            //klikker på shop
                            player.findPath(showneTileSet.get(i), tileSet);
                            System.out.println("ShopTime");
                            showneTileSet.get(i).showShop(scaleSize);


                        } else if (!showneTileSet.get(i).Contents.equals("BORDER")) {


                            //klikker på vandtiels
                   /* player.xPos =showneTileSet.get(i).xPos;
                    player.yPos =showneTileSet.get(i).yPos;
                    */

                            showneTileSet.get(i).selectedTile = true;
                            if (showneTileSet.get(i).selectedTile) {
                                player.findPath(showneTileSet.get(i), tileSet);
                            }


                   /* --turnCount;
                    if(turnCount <=0){
                        turnEnded = true;
                    }*/
                        }

                        showneTileSet.get(i).cliked = false;
                    }
                    turnCount = player.movePlayer(turnCount);
                    if (turnCount <= 0 && !rul) {
                        turnEnded = true;

                    }


                }
            } else {

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

            if (turnEnded) {
                p.textSize(60 * scaleSize);
                p.fill(200, 200, 200, 200);
                p.rect(0, p.height / 2 - 60 * scaleSize, p.width, 80 * scaleSize);
                p.fill(0);
                p.text("VENTER PÅ MODSPILLERNES TUR", p.width / 2 - p.textWidth("VENTER PÅ MODSPILLERNES TUR") / 2, p.height / 2);

                for (int j = 0; j < cpuBoatArrayList.size(); ++j) {
                    cpuBoatArrayList.get(j).Turn();
                    System.out.println("cpu number: " + j);
                }
                if (!rul)
                    ++roundCount;
                turnEnded = false;
                rul = true;
            }
            Rul();


        } else if (devConsole.visibale) {
            devConsole.display(scaleSize);
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
            btnBackToMenu.tegnKnap();

            if (btnBackToMenu.klikket) {
                visible = false;

                main.mainMenu.visible = true;
                saveManger.increment += p.random(-0.02f, 0.02f);

                main.mainMenu.chooseGameMenu.needNytSpil = true;
                btnBackToMenu.registrerRelease();
            }
        }
    }

    void reSizeGamebord() {
        settingMenu.reSizeBtn(scaleSize, btnMenu);
        settingMenu.reSizeBtn(scaleSize, btnMap);
        settingMenu.reSizeBtn(scaleSize,btnAcceptRul);
        settingMenu.reSizeBtn(scaleSize,btnBackToMenu);
        settingMenu.reSizeFT(scaleSize, devConsole.textField);

    }

    void drawShips(Boat boat, int i, float posX, float posY) {
        ArrayList<Tile> showneTileSet = player.showneTileSet;
        if (boat.xPos == showneTileSet.get(i).xPos && boat.yPos == showneTileSet.get(i).yPos) {

            boat.displayBoat(posX, posY, (int) (85 * scaleSize));
        }
    }

    void aktiverPauseMenu() {
        System.out.println("fuck!");
        pauseMenu.visible = true;
        visible = false;
        btnMenu.registrerRelease();
    }

    void akitverDevconsole() {
        System.out.println("½");
        devConsole.visibale = !devConsole.visibale;

    }


    void boardmouseClicked() {
        ArrayList<Tile> showneTileSet = player.showneTileSet;
        devConsole.mouseClick();
        if (!rul) {
            btnMenu.registrerKlik(p.mouseX, p.mouseY);
            btnMap.registrerKlik(p.mouseX, p.mouseY);
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
            btnAcceptRul.registrerKlik(p.mouseX, p.mouseY);
        }
        if (maxRounds <= roundCount) {
            btnBackToMenu.registrerKlik(p.mouseX, p.mouseY);
        }
    }

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
            btnAcceptRul.tegnKnap();
            if (btnAcceptRul.klikket) {
                rul = false;
                turnCount = terningTal;
                btnAcceptRul.registrerRelease();
            }
        }


    }

    void keyTyped() {
        if (visible) {
            devConsole.keybordTyped();
        }
    }

    void chooseWinner() {
        Boat winner = cpuBoatArrayList.get(0);
        for (int i = 0; i < cpuBoatArrayList.size(); ++i) {
            Boat boat = cpuBoatArrayList.get(i);
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

