import processing.core.PApplet;

public class main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("main");
    }

    SettingMenu mainMenu;

    @Override
    public void settings() {
        size(1280,720);
    }

    @Override
    public void setup() {
        mainMenu = new SettingMenu(this);
    }

    @Override
    public void draw() {
        mainMenu.drawMenu();
    }

    @Override
    public void mouseClicked() {
        mainMenu.menuMouseClick();
    }

    @Override
    public void keyTyped() {
        mainMenu.menuKeyTyped();
    }
}
