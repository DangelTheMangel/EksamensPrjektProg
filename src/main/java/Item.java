import processing.core.PApplet;

import javax.swing.text.AbstractDocument;

public class Item {
    PApplet p;
    float value, stockValue;
    String Name;
    String Img;
    int ammount;

    //----------CONSTRUCTOR----------
    Item(float v,int a, String n, String i){
        value = v;
        ammount = a;
        Name = n;
        Img = i;
        stockValue = (float)value;


    }
    //----------METHODS----------

}
