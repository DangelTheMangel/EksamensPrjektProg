
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import processing.core.PApplet;
import processing.core.PVector;

import javax.print.DocFlavor;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class main extends PApplet {
    public static void main(String[] args) {

        PApplet.main("main");


    }



    public SettingMenu settingMenu;
    public static MainMenu mainMenu;
    public GameBoard gb = new GameBoard(this);
    @Override
    public void settings() {

        size(1280,720);
    }


    @Override
    public void setup() {
       // JFileChooser fc = new JFileChooser();//to choose mp3
      //int result = fc.showOpenDialog(null);

      // so if we chose file then we can proceed
    // if(result == JFileChooser.APPROVE_OPTION){ "C:\\Users\\Christian\\IdeaProjects\\EksamensPrjektProg1\\src\\main\\resources\\bgmusik.mp3"
            try {
               // File f =new File(lo)
//virker ikke!"!3
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            File file = new File("src/main/resources/bgmusik.mp3");

                            FileInputStream fis = new FileInputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(fis);
                            Player player = new Player(bis);
                        } catch (JavaLayerException | FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });


                        //  player.play();

                        //now try block for our player

            } catch (Exception e) {
                e.printStackTrace();
            }
   //}
        Item Banana = new Item(10,0,"Banana","idk");
        Item Rum = new Item(50,0,"Rum","idk");
        Item Eyepatch = new Item(30,0,"Eyepatch","idk");

        ArrayList StockInventory = new ArrayList<Item>();
        StockInventory.add(Banana);
        StockInventory.add(Rum);
        StockInventory.add(Eyepatch);

        Player KaptajnGokke = new Player(0f, 0f, 0f, 100f, StockInventory);




            settingMenu = new SettingMenu(this);
        mainMenu = new MainMenu(this);
        gb.visible = true;
        gb.player.boatPic = loadImage("Skibet32.png");

        PVector[] shopLoc = {new PVector(),new PVector(),new PVector()};
        for(int i=0; i<3;++i){
            int px = (int) random(1,33);
            int py = (int) random(1,33);
            shopLoc[i] = new PVector(px,py);
        }
        for(int x = 1;x<33;++x) {

            for (int j = 1; j < 33; ++j){


                System.out.println(x + " x " + j);

                Tile t = new TerrainTile(this,"",x ,j );

                for(int e=0; e<3;++e){
                    if(x == shopLoc[e].x&& j== shopLoc[e].y){
                        t = new ShopTile(this,"",x ,j);
                        System.out.println("SHOP:  "+x + " x " + j);
                    }else{
                        if(Math.random() > 0.3){
                            t.Contents ="WATER";
                        }else{
                            t.Contents ="SAND";
                        }
                    }
                }


            gb.tileSet.add(t);}
        }
    }



    @Override
    public void draw() {
        clear();
        background(200);
        if(settingMenu.visible){
            settingMenu.drawMenu();
        }
        if(gb.visible){
            gb.drawBoard();
        }

        if(mainMenu.visible){
            mainMenu.drawMenu();

        }

    }



    @Override
    public void mouseClicked() {

        if(settingMenu.visible){

            settingMenu.menuMouseClick();
            settingMenu.btnChangeScreen(mainMenu,gb);
        }
        if(gb.visible){
            gb.boardmouseClicked();
        }

        if(mainMenu.visible){

        }


    }

    @Override
    public void keyTyped() {

        //settingMenu.menuKeyTyped();
    }
}
