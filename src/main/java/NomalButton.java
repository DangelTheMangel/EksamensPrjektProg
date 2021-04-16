import processing.core.PApplet;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class NomalButton extends Button {

    //---------- CONSTRUCTOR :) ----------\\
    NomalButton(PApplet papp, int posX, int posY, int sizeX, int sizeY, String text) {
        super(papp, posX, posY, sizeX, sizeY, text);
    }
    //----------METHODS----------\\

    //This function checks if the mouse is over the button and is clicked change it clicked to true
    @Override
    void registerClick(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (mouseX > positionX &&
                mouseX < positionX + sizeX &&
                mouseY > positionY &&
                mouseY < positionY + sizeY) {
            try {
                File file = new File("Music/k.wav");
                Scanner scanner = new Scanner(System.in);
                AudioInputStream kas = AudioSystem.getAudioInputStream(file);
                Clip k = AudioSystem.getClip();
                k.open(kas);
                k.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            clicked = true;
        }
    }

    //This funktion set clicked to false
    void registrerRelease() {
        clicked = false;
    }
}

