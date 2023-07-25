package view.shapes;

import controller.control.MoonInformation;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Moon extends Circle {



    private MoonInformation moonInformation;

    public Moon(int number,double angle){
        super(15);
        this.moonInformation=new MoonInformation ();
        this.moonInformation.setR ( 15 );
        this.moonInformation.setAngle ( angle);
        this.moonInformation.setNumber ( number);
        this.setFill ( new ImagePattern (
                new Image (Moon.class.getResource("/images/"+number+".png").toExternalForm())) );


    }

    public double getAngle() {
        return this.moonInformation.getAngle ();
    }

    public void setAngle(double angle) {
        this.moonInformation.setAngle (  angle);
    }


    public static boolean isCollision(Moon moon1,Moon moon2){
        double d=Math.sqrt ( (Math.pow ( moon1.getCenterX ()-moon2.getCenterX (),2 ))+(Math.pow ( moon1.getCenterY ()-moon2.getCenterY (),2 )) );
        return 1.15*15*2*1.01>=d;
    }

    public MoonInformation getMoonInformation() {
        return moonInformation;
    }
}
