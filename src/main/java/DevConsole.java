import processing.core.PApplet;
import processing.core.PVector;

public class DevConsole extends PApplet {
    //variable consoles
    PApplet p;

    //this variable tells if the console is visible
    boolean visible = false;

    //quick access to the game board
    GameBoard gameBoard;
    //textfield to which you type the commands
    TextField textField;
    //string contains the information that has occurred under the console
    String display = "...";
    //the position of the console
    PVector displayPos = new PVector(1, 1);
    //the size of the text
    float textSize = 30;

    //---------- CONSTRUCTOR :) ----------\\
    DevConsole(PApplet p, GameBoard gameBoard) {
        this.p = p;
        this.gameBoard = gameBoard;
        textField = new TextField(p, p.width / 12 / 2, p.height - p.height / 5, p.width - p.width / 12, p.width / 12 / 2, "DevConsole");
        displayPos = new PVector(1, 1);
    }
    //----------METHODS----------\\

    //this function draws the console
    void display(float s) {
        textSize = 30 * s;
        displayPos = new PVector(p.width / 12 / 2 + textSize, p.height - p.height / 4 - textSize );
        p.fill(0);
        p.rect((p.width / 12 / 2), 0, (p.width - p.width / 12), (p.height - p.height / 4));
        textField.drawField();
        textField.size = s;
        p.fill(255);

        p.textSize(textSize);

        p.text(display, displayPos.x, displayPos.y);
        p.textSize(12 * s);
        p.fill(0);
    }


    //this feature lets you click on the console
    void mouseClick() {
        if (visible) {
            textField.registerClick(p.mouseX, p.mouseY);
        }
    }

    //this feature lets you type in the console
    void keybordTyped() {
        if (visible) {

            if (p.key == ENTER && textField.input.length() > 0) {
                textField.clicked = false;
                display =  textField.input;

                comandos();
                textField.input = "";
            } else if (p.key == BACKSPACE && textField.input.length() > 0) {
                textField.input = textField.input.substring(0, textField.input.length() - 1);
            } else {
                textField.keyinput(p.key);
            }
            textField.clicked = true;
        } else {
            textField.clicked = false;
        }

    }

    //This console takes your input and tries to figure out what you would do
    void comandos() {
        String ip = textField.input;
        String[] letters = ip.split("-");
        for (int i = 0; i < letters.length; ++i)
            System.out.println("i: " + i + letters[i]);

        if (letters.length > 1 && letters[0].equalsIgnoreCase("g")) {
            if (letters.length > 2 && letters[1].equalsIgnoreCase("t")) {
                gameBoard.turnCount = Integer.valueOf(letters[2]);
                display = "Game Turns (" + gameBoard.turnCount + ")";

            }
        }

        if (letters.length > 1 && letters[0].equalsIgnoreCase("p")) {
            System.out.println("player");
            if (letters.length > 3 && letters[1].equalsIgnoreCase("pos")) {
                gameBoard.player.xPos = Integer.valueOf(letters[2]);
                gameBoard.player.yPos = Integer.valueOf(letters[3]);
                display = "Players postion (" + gameBoard.player.xPos + "x" + gameBoard.player.xPos + ")";

                System.out.println("postion");
            } else if (letters[1].equalsIgnoreCase("addM")) {
                gameBoard.player.money += Integer.valueOf(letters[2]);
                display += "Players money (" + gameBoard.player.money + ")";

                System.out.println("money");
            } else if (letters[1].equalsIgnoreCase("removeM")) {
                gameBoard.player.money -= Integer.valueOf(letters[2]);
                display += "Players money (" + gameBoard.player.money + ")";

            }

        }


    }
}

