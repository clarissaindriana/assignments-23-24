package assignments.assignment4.components.InterfacePack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomedRectangle {

    public static Rectangle RectangleCustom(int width, int height){
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.rgb(217, 174, 173, 0.5));
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);

        return rectangle;
    }
}