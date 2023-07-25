package view.shapes;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Shooter extends Rectangle {


    private int number;
    public Shooter(int number){
        super(180,145,new ImagePattern (
                new Image (Shooter.class.getResource("/images/shooter-removebg-preview.png").toExternalForm())));
        this.number=number;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
