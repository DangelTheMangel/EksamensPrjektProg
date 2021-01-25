
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

        settingMenu = new SettingMenu(this);
        mainMenu = new MainMenu(this);
        settingMenu.visible = true;
        for(int x = 1;x<33;++x) {

            for (int j = 1; j < 33; ++j){
                System.out.println(x + " x " + j);
                Tile t = new Tile(this,"Water",x ,j );
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
