import processing.core.PApplet;

public class main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("main");
    }

    SettingMenu settingMenu;

    @Override
    public void settings() {
        size(1280,720);
    }

    @Override
    public void setup() {
        settingMenu = new SettingMenu(this);
    }

    @Override
    public void draw() {
        settingMenu.drawMenu();
    }

    @Override
    public void mouseClicked() {
        settingMenu.menuMouseClick();
    }

    @Override
    public void keyTyped() {
        settingMenu.menuKeyTyped();
    }
}
