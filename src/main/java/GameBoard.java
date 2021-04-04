
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class GameBoard {
    PApplet p;

    //til bord klassen
    ArrayList<Tile> tileSet = new ArrayList<Tile>();
    //ArrayList<Tile> showneTileSet = new ArrayList<Tile>();
    PImage playerPic;
    AlmindeligKnap btnMenu, btnMap;
    public PauseMenu pauseMenu;
    SettingMenu settingMenu;
    SaveManger saveManger;
    public Player player;
    boolean drawmap = false;
    DevConsole devConsole;
  //  ArrayList<Turn> turnList = new ArrayList<Turn>();
    ArrayList<Cpu>  cpuArrayList = new ArrayList<Cpu>();
  //  Cpu cpu;
    int maxRounds = 500;
    int numbersOfCpus = 3;
    int roundCount = 1;
    int turnCount ;
    float scaleSize = 1;
    Boolean visible = false;
    Boolean turnEnded = false;
    ArrayList<PVector> cpuPos = new ArrayList<>();
    PImage bg;
    GameBoard(PApplet p, PauseMenu pauseMenu){

        this.p = p;
        bg = p.loadImage("bgofgame.png");

        saveManger = new SaveManger(p);
        saveManger.gb = this;
        saveManger.generateGame(32, tileSet);


        this.pauseMenu = pauseMenu;
        startGame(numbersOfCpus,cpuPos);
        btnMenu = new AlmindeligKnap(p,p.width-60,0,60,60,"-\n-\n-");
        btnMap = new AlmindeligKnap(p,270,650,450,50,"map");
        pauseMenu.gb = this;
        pauseMenu.mainMenu.gb = this;
        pauseMenu.mainMenu.chooseGameMenu.gb = this;

        settingMenu = pauseMenu.settingMenu;
        settingMenu.gb = this;
        settingMenu.tfNumbersOfPlayers.indput = ""+ numbersOfCpus;
        settingMenu.tfMaxRound.indput = ""+ maxRounds;
        devConsole = new DevConsole(p,this);


    }

    void startGame(int antalCPU,ArrayList<PVector> cpuPos){

        int playerPos = (int)p.random(0,cpuPos.size());
        player = new Player(p,cpuPos.get(playerPos).x,cpuPos.get(playerPos).y,0,1000);
        cpuPos.remove(playerPos);
        player.generateInventory();
        player.boatPic = p.loadImage("Skibet32.png");
        //turnList.add(new Turn(p,player));
        turnCount = (int) p.random(0,7);

        for(int i = 0; i < antalCPU; ++i){
            int cpurPos = (int)p.random(0,cpuPos.size());
            int x = (int) cpuPos.get(cpurPos).x,y= (int) cpuPos.get(cpurPos).y;
            cpuPos.remove(cpurPos);
            System.out.println("x: "+ x + " y: " + y);
            cpuArrayList.add(new Cpu(p,x,y,0,100));

        }

        for(int i = 0; i < cpuArrayList.size(); ++i){

            cpuArrayList.get(i).generateInventory();
            cpuArrayList.get(i).boatPic = p.loadImage("Skibet32.png");
           // turnList.add(new Turn(p,cpuArrayList.get(i)));
        }


    }

    void drawBoard(){

        if(visible && !devConsole.visibale){
        //    System.out.println("cpu: " + cpu.inventory.get(0) + "player: " + player.inventory.get(0));
        p.clear();
        //p.background(200);
        p.image(bg,0,0,p.width,p.height);
        //ui
        p.textSize(16*scaleSize);
        p.fill(200);
        p.rect(0,0,p.width,60*scaleSize);
        String info = "Round: " + roundCount + "/" + maxRounds +"\nTurn: " + turnCount;
        p.fill(0);
        p.text(info,(p.width/2-p.textWidth(info)/2),30*scaleSize);
        ArrayList<Item> t = player.inventory;
        p.fill(200);

        p.fill(0);
        p.text(     "\nTaske: " +"\nPenge"+player.money
                        +"\n" +t.get(0).Name +t.get(0).ammount
                        +"\n" + t.get(1).Name +t.get(1).ammount
                        +"\n" + t.get(2).Name +t.get(2).ammount
                        +"\n -----------------\n"

                ,880*scaleSize,150*scaleSize);
        player.fillUpShownTiles(tileSet);
        for(int j = 0;j < cpuArrayList.size();++j){
            cpuArrayList.get(j).fillUpShownTiles(tileSet);
        }
        /*
        * +"\n Round: " + roundCount
                        +"\n Turn: " + turnCount
                        +"\n -----------------\n"
                        +"\nCpu-Taske: "
                        +"\nPenge"+ cpuArrayList.get(0).money
                        +"\n" +cpuArrayList.get(0).inventory.get(0).Name +cpuArrayList.get(0).inventory.get(0).ammount
                        +"\n" + cpuArrayList.get(0).inventory.get(1).Name +cpuArrayList.get(0).inventory.get(1).ammount
                        +"\n" + cpuArrayList.get(0).inventory.get(2).Name +cpuArrayList.get(0).inventory.get(2).ammount
        * */


        //burger menu kanp
        btnMenu.tegnKnap();
        btnMap.tegnKnap();

        //brætet
        if(btnMenu.klikket){
            aktiverPauseMenu();
        }

        if(btnMap.klikket){
            drawmap = !drawmap;
            btnMap.registrerRelease();
        }

        ArrayList<Tile> showneTileSet = player.showneTileSet;
        if(!drawmap){
        for(int i = 0;i<showneTileSet.size();++i){
            p.pushMatrix();
            float posX = showneTileSet.get(i).xPos;
            float posY = showneTileSet.get(i).yPos;

            if(i<5){

                //225*scaleSize
                posX = 2.5f;
                posY = i+1.5f;
            }else if(i<10) {
                posX = 3.5f;
                posY = i-3.5f;
            }else if(i<15){
                posX = 4.5f;
                posY = i-8.5f;
            }else if(i<20){
                posX = 5.5f;
                posY = i-13.5f;
            }else{
                posX = 6.5f;
                posY = i-18.5f;
            }
            //drawing bord
            showneTileSet.get(i).Display(posX,posY,(int) (85*scaleSize), true,scaleSize);
            showneTileSet.get(i).drawShopMenu(player,scaleSize);
            showneTileSet.get(i).checkIfMouseOver(posX,posY, (int) (85*scaleSize));

            //drawing boats
            drawShips(player,i,posX,posY);
            for(int j = 0;j < cpuArrayList.size();++j){
                drawShips(cpuArrayList.get(j),i,posX,posY);
            }
            p.popMatrix();


            //
            if(showneTileSet.get(i).cliked){
                if(showneTileSet.get(i).Contents.equals("SHOP")){
                    //klikker på shop
                    player.findPath(showneTileSet.get(i), tileSet);
                    System.out.println("ShopTime");
                    showneTileSet.get(i).showShop(scaleSize);

                } else if(!showneTileSet.get(i).Contents.equals("BORDER")){


                    //klikker på vandtiels
                   /* player.xPos =showneTileSet.get(i).xPos;
                    player.yPos =showneTileSet.get(i).yPos;
                    */
                 
                    showneTileSet.get(i).selectedTile = true;
                    if(showneTileSet.get(i).selectedTile ) {
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
            if(turnCount <=0){
                turnEnded = true;

            }


        }
        }else {

                for(int i = 0;i<tileSet.size();++i){
                    p.pushMatrix();
                    p.translate(225*scaleSize, 140*scaleSize);
                    Tile tile = tileSet.get(i);
                    tile.Display(tile.xPos,tile.yPos,(int) (12*scaleSize) , false,scaleSize);
                    p.fill(201, 0, 0);
                    if(tile.xPos == player.xPos && tile.yPos == player.yPos){

                        float s = (int) (12*scaleSize);
                        p.rect( s*tile.xPos, s*tile.yPos,s,s);
                    }
                    p.popMatrix();



                }

        }
        if(turnEnded){
            for(int j = 0;j < cpuArrayList.size();++j){
                cpuArrayList.get(j).Turn();
            }

            ++roundCount;
            turnCount = (int) p.random(0,6);
            Rul();
            turnEnded = false;

        }
        }else if(devConsole.visibale){
            devConsole.display(scaleSize);
        }
    }
    void reSizeGamebord(){
        settingMenu.reSizeBtn(scaleSize,btnMenu);
        settingMenu.reSizeBtn(scaleSize,btnMap);

    }

    void drawShips(Boat boat, int i, float posX, float posY){
        ArrayList<Tile> showneTileSet = player.showneTileSet;
        if(boat.xPos == showneTileSet.get(i).xPos && boat.yPos == showneTileSet.get(i).yPos){
            //p.ellipse(100*posX + 50,100*posY+ 50,100,100);
            boat.displayBoat(posX,posY, (int) (85*scaleSize));
        }
    }

    void aktiverPauseMenu(){
        System.out.println("fuck!");
        pauseMenu.visible = true;
        visible = false;
        btnMenu.registrerRelease();
    }
    void akitverDevconsole(){
        System.out.println("½");
        devConsole.visibale = !devConsole.visibale;

    }



    void boardmouseClicked(){
        ArrayList<Tile> showneTileSet = player.showneTileSet;
        devConsole.mouseClick();

        btnMenu.registrerKlik(p.mouseX,p.mouseY);
        btnMap.registrerKlik(p.mouseX,p.mouseY);
        if(!drawmap)
        if(turnEnded == false){
           for(int i = 0;i<showneTileSet.size();++i){
            float posX = showneTileSet.get(i).xPos;
            float posY = showneTileSet.get(i).yPos;

               if(i<5){

                   //225*scaleSize
                   posX = 2.5f;
                   posY = i+1.5f;
               }else if(i<10) {
                   posX = 3.5f;
                   posY = i-3.5f;
               }else if(i<15){
                   posX = 4.5f;
                   posY = i-8.5f;
               }else if(i<20){
                   posX = 5.5f;
                   posY = i-13.5f;
               }else{
                   posX = 6.5f;
                   posY = i-18.5f;
               }/**/

            showneTileSet.get(i).clickShop();
            showneTileSet.get(i).checkIfCliked((int)posX,(int)posY,(int) (100*scaleSize));

        }
    }
    }

    void Rul(){
        float terningTal = p.random(1,6);
        p.text("Du rullede" + terningTal,p.width/2,p.height/2);
    }

    void keyTyped(){
        if(visible){
            devConsole.keybordTyped();
        }
    }

}

