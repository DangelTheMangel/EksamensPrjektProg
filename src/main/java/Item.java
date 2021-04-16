import processing.core.PApplet;

public class Item {
    //Items variables
    PApplet p;
    //items normal price and its current price
    float value, stockValue;
    //the name of the variable
    String Name;
    //the number of the thing
    int ammount;

    //----------CONSTRUCTOR----------\\
    Item(float v, int a, String n) {
        value = v;
        ammount = a;
        Name = n;

        stockValue = value;


    }

}
