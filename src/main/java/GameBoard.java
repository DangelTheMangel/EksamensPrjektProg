
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class GameBoard {
    PApplet p;

    //til bord klassen
    ArrayList<Tile> tileSet = new ArrayList<Tile>();
   // ArrayList<Tile> showneTileSet = new ArrayList<Tile>();
    PImage playerPic;
    AlmindeligKnap btnMenu;
    public PauseMenu pauseMenu;
    SettingMenu settingMenu;
    SaveManger saveManger;
    public Player player;
    ArrayList<Cpu>  cpuArrayList = new ArrayList<Cpu>();
  //  Cpu cpu;
    int numbersOfCpus = 3;
    int roundCount = 1;
    int turnCount ;
    float scaleSize = 1;
    Boolean visible = false;
    Boolean turnEnded = false;
    GameBoard(PApplet p, PauseMenu pauseMenu){

        this.p = p;
        this.pauseMenu = pauseMenu;
        startGame(numbersOfCpus);
        btnMenu = new AlmindeligKnap(p,50,50,50,50,"-\n-\n-");
        pauseMenu.gb = this;
        pauseMenu.mainMenu.gb = this;
        pauseMenu.mainMenu.chooseGameMenu.gb = this;
        settingMenu = pauseMenu.settingMenu;



    }

    void startGame(int antalCPU){
        player = new Player(p,16,16,0,1000);
        for(int i = 0; i < antalCPU; ++i){
            int x = (int) p.random(0,33),y= (int) p.random(0,33);
            System.out.println("x: "+ x + " y: " + y);
            cpuArrayList.add(new Cpu(p,x,y,0,100));

        }
        for(int i = 0; i < cpuArrayList.size(); ++i){

            cpuArrayList.get(i).generateInventory();
            cpuArrayList.get(i).boatPic = p.loadImage("Skibet32.png");
        }

        player.generateInventory();
        player.boatPic = p.loadImage("Skibet32.png");
        turnCount = (int) p.random(0,7);
    }

    void drawBoard(){

        if(visible){
        //    System.out.println("cpu: " + cpu.inventory.get(0) + "player: " + player.inventory.get(0));
        p.clear();
        p.background(200);
        //ui
        ArrayList<Item> t = player.inventory;
        p.fill(200);
        p.rect(1020*scaleSize,100*scaleSize,400*scaleSize,500*scaleSize);
        p.fill(0);
        p.text(     "\nTaske: " +"\nPenge"+player.money
                        +"\n" +t.get(0).Name +t.get(0).ammount
                        +"\n" + t.get(1).Name +t.get(1).ammount
                        +"\n" + t.get(2).Name +t.get(2).ammount
                        +"\n -----------------\n"
                        +"\n Round: " + roundCount
                        +"\n Turn: " + turnCount
                        +"\n -----------------\n"
                        +"\nCpu-Taske: "
                        +"\nPenge"+ cpuArrayList.get(0).money
                        +"\n" +cpuArrayList.get(0).inventory.get(0).Name +cpuArrayList.get(0).inventory.get(0).ammount
                        +"\n" + cpuArrayList.get(0).inventory.get(1).Name +cpuArrayList.get(0).inventory.get(1).ammount
                        +"\n" + cpuArrayList.get(0).inventory.get(2).Name +cpuArrayList.get(0).inventory.get(2).ammount
                ,1030*scaleSize,120*scaleSize);
        player.fillUpShownTiles(tileSet);
        for(int j = 0;j < cpuArrayList.size();++j){
            cpuArrayList.get(j).fillUpShownTiles(tileSet);
        }



        //burger menu kanp
        btnMenu.tegnKnap();

        //brætet
        if(btnMenu.klikket){
            aktiverPauseMenu();
        }

        ArrayList<Tile> showneTileSet = player.showneTileSet;
        for(int i = 0;i<showneTileSet.size();++i){
            int posX = showneTileSet.get(i).xPos;
            int posY = showneTileSet.get(i).yPos;

            if(i<5){
                posX = 1;
                posY = i+1;
            }else if(i<10) {
                posX = 2;
                posY = i-4;
            }else if(i<15){
                posX = 3;
                posY = i-9;
            }else if(i<20){
                posX = 4;
                posY = i-14;
            }else{
                posX = 5;
                posY = i-19;
            }
            //drawing bord
            showneTileSet.get(i).Display(posX,posY,(int) (100*scaleSize));
            showneTileSet.get(i).drawShopMenu(player,scaleSize);
            showneTileSet.get(i).checkIfMouseOver(posX,posY, (int) (100*scaleSize));

            //drawing boats
            drawShips(player,i,posX,posY);
            for(int j = 0;j < cpuArrayList.size();++j){
                drawShips(cpuArrayList.get(j),i,posX,posY);
            }


            //
            if(showneTileSet.get(i).cliked){
                if(showneTileSet.get(i).Contents.equals("SHOP")){
                    System.out.println("ShopTime");
                    showneTileSet.get(i).showShop(scaleSize);

                } else if(showneTileSet.get(i).Contents.equals("BORDER")){

                } else {
                    player.xPos =showneTileSet.get(i).xPos;
                    player.yPos =showneTileSet.get(i).yPos;
                    --turnCount;
                    if(turnCount <=0){
                        turnEnded = true;
                    }
                }

                showneTileSet.get(i).cliked = false;
            }


        }
        if(turnEnded){
            for(int j = 0;j < cpuArrayList.size();++j){
                cpuArrayList.get(j).Turn();
            }

            ++roundCount;
            turnCount = (int) p.random(0,6);
            turnEnded = false;

        }
        }
    }
    void reSizeGamebord(){
        settingMenu.reSizeBtn(scaleSize,btnMenu);

    }

    void drawShips(Boat boat,int i,int posX,int posY){
        ArrayList<Tile> showneTileSet = player.showneTileSet;
        if(boat.xPos == showneTileSet.get(i).xPos && boat.yPos == showneTileSet.get(i).yPos){
            //p.ellipse(100*posX + 50,100*posY+ 50,100,100);
            boat.displayBoat(posX,posY, (int) (100*scaleSize));
        }
    }

    void aktiverPauseMenu(){
        System.out.println("fuck!");
        pauseMenu.visible = true;
        visible = false;
        btnMenu.registrerRelease();
    }

    void boardmouseClicked(){
        ArrayList<Tile> showneTileSet = player.showneTileSet;


        btnMenu.registrerKlik(p.mouseX,p.mouseY);
        if(turnEnded == false){
           for(int i = 0;i<showneTileSet.size();++i){
            int posX = showneTileSet.get(i).xPos;
            int posY = showneTileSet.get(i).yPos;

               if(i<5){
                   posX = 1;
                   posY = i+1;
               }else if(i<10) {
                   posX = 2;
                   posY = i-4;
               }else if(i<15){
                   posX = 3;
                   posY = i-9;
               }else if(i<20){
                   posX = 4;
                   posY = i-14;
               }else{
                   posX = 5;
                   posY = i-19;
               }/**/

            showneTileSet.get(i).clickShop();
            showneTileSet.get(i).checkIfCliked(posX,posY,(int) (100*scaleSize));

        }
    }
    }

}

