import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("main");
    }



    SettingMenu settingMenu;

    GameBoard gb = new GameBoard(this);
    @Override
    public void settings() {

        size(1280,720);
    }

    @Override
    public void setup() {

        settingMenu = new SettingMenu(this);

        for(int x = 1;x<33;++x) {

            for (int j = 1; j < 33; ++j){
                System.out.println(x + " x " + j);
                Tile t = new Tile(this,"Water",x ,j );
            gb.tileSet.add(t);}
        }
    }

    @Override
    public void draw() {

        gb.drawBoard();
    }



    @Override
    public void mouseClicked() {
        gb.boardmouseClicked();


    }

    @Override
    public void keyTyped() {

        //settingMenu.menuKeyTyped();
    }
}
