import processing.core.PApplet;
import processing.core.PVector;

public class DevConsole extends PApplet {
    PApplet p;
    boolean visibale = false;
    GameBoard gameBoard;
    TextField textField;
    String display = "...";
    PVector displayPos = new PVector(1, 1);
    float textSize = 30;
    int extra = 1;

    DevConsole(PApplet p, GameBoard gameBoard) {
        this.p = p;
        this.gameBoard = gameBoard;
        textField = new TextField(p, p.width / 12 / 2, p.height - p.height / 5, p.width - p.width / 12, p.width / 12 / 2, "DevConsole");
        displayPos = new PVector(1, 1);
    }

    void display(float s) {
        textSize = 30 * s;
        displayPos = new PVector(p.width / 12 / 2 + textSize, p.height - p.height / 4 - textSize - extra * s);
        p.fill(0);
        p.rect((p.width / 12 / 2), 0, (p.width - p.width / 12), (p.height - p.height / 4));
        textField.tegnTextFlet();
        textField.size = s;
        p.fill(255);

        p.textSize(textSize);

        p.text(display, displayPos.x, displayPos.y);
        p.textSize(12 * s);
        p.fill(0);
    }

    void mouseClick() {
        if (visibale) {
            textField.KlikTjek(p.mouseX, p.mouseY);
        }
    }

    void keybordTyped() {
        if (visibale) {

            if (p.key == ENTER && textField.indput.length() > 0) {
                textField.klikket = false;
                display += "\n" + textField.indput;
                extra += 30;
                comandos();
                textField.indput = "";
            } else if (p.key == BACKSPACE && textField.indput.length() > 0) {
                textField.indput = textField.indput.substring(0, textField.indput.length() - 1);
            } else {
                textField.keyindput(p.key);
            }
            textField.klikket = true;
        } else {
            textField.klikket = false;
        }

    }


    void comandos() {
        String ip = textField.indput;
        String[] letters = ip.split("-");
        for (int i = 0; i < letters.length; ++i)
            System.out.println("i: " + i + letters[i]);

        if (textField.indput.equalsIgnoreCase("Cunt")) {
            System.out.println("nå nå fuck dig");
        }

        if (letters.length > 1 && letters[0].equalsIgnoreCase("g")) {
            if (letters.length > 2 && letters[1].equalsIgnoreCase("t")) {
                gameBoard.turnCount = Integer.valueOf(letters[2]);
                display += "\nGame Turns (" + gameBoard.turnCount + ")";
                extra += 30;
            }
        }

        if (letters.length > 1 && letters[0].equalsIgnoreCase("p")) {
            System.out.println("player");
            if (letters.length > 3 && letters[1].equalsIgnoreCase("pos")) {
                gameBoard.player.xPos = Integer.valueOf(letters[2]);
                gameBoard.player.yPos = Integer.valueOf(letters[3]);
                display += "\nPlayers postion (" + gameBoard.player.xPos + "x" + gameBoard.player.xPos + ")";
                extra += 30;
                System.out.println("postion");
            } else if (letters[1].equalsIgnoreCase("addM")) {
                gameBoard.player.money += Integer.valueOf(letters[2]);
                display += "\nPlayers money (" + gameBoard.player.money + ")";
                extra += 30;
                System.out.println("money");
            } else if (letters[1].equalsIgnoreCase("removeM")) {
                gameBoard.player.money -= Integer.valueOf(letters[2]);
                display += "\nPlayers money (" + gameBoard.player.money + ")";
                extra += 30;
            }

        }


    }
}

