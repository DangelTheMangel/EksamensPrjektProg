import processing.core.PApplet;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class AlmindeligKnap extends Knap {

    AlmindeligKnap(PApplet papp, int posX, int posY, int sizeX, int sizeY, String text) {
        super(papp, posX, posY, sizeX, sizeY, text);
    }

    @Override
    void registrerKlik(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (mouseX > positionX &&
                mouseX < positionX + sizeX &&
                mouseY > positionY &&
                mouseY < positionY + sizeY) {
            try {
                File file = new File("Music/k.wav");
                Scanner scanner = new Scanner(System.in);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();



            } catch (Exception e) {
                e.printStackTrace();
            }
            klikket = true;
        }
    }

    void registrerRelease() {
        klikket = false;
    }
}

