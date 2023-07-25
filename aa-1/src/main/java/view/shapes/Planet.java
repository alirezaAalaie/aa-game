package view.shapes;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Planet extends Circle {

    public Planet(){
        super(60);
        this.setFill(new ImagePattern (
                new Image (Planet.class.getResource("/images/big.png").toExternalForm())));




    }
}
