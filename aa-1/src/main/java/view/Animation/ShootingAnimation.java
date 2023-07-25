package view.Animation;

import controller.menus.GameController;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.shapes.Moon;
import view.shapes.Planet;
import view.shapes.Shooter;

import java.util.ArrayList;

public class ShootingAnimation extends Transition {
    private Pane pane;

    private Shooter shooter;

    private Moon moon;

    private Planet planet;
    private static int shooted;
    private GameController gameController;

    private ProgressBar progressBar;

    private Label label;
    public ShootingAnimation(GameController gameController ,
                             Pane pane,
                             Shooter shooter,
                             Moon moon,

                             Planet planet, ProgressBar progressBar
                             ,Label label) {
        this.pane = pane;
        this.shooter = shooter;
        this.moon = moon;
        ShootingAnimation.shooted=this.shooter.getNumber ()+1;
        this.planet=planet;
        this.setCycleDuration( Duration.seconds ( 1 ));
        this.setCycleCount(-1);
        this.gameController=gameController;
        this.progressBar=progressBar;
        this.label=label;
    }

    @Override
    protected void interpolate(double v) {
        double y = moon.getCenterY () - 5;

        if ( y <= planet.getCenterY () + 120 ) {
            ShootingAnimation.shooted--;

            for ( Moon meteorite : this.gameController.getGame ().getRotateMoons () ) {
                moon.setCenterY ( planet.getCenterY () + 120 );
                if ( Moon.isCollision ( moon,meteorite ) ) {
                        this.stop ();
                    try {
                        this.gameController.Finish (false,this.pane,this.gameController.getGame ().getCurrentUser (),gameController);
                    } catch (Exception e) {
                        throw new RuntimeException ( e );
                    }

                }

            }
            if ( shooter.getNumber () == 0 && ShootingAnimation.shooted==0 ) {
                this.stop ();

                try {
                    this.gameController.Finish ( true ,this.pane,this.gameController.getGame ().getCurrentUser (),gameController);
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }
            }

            this.stop ();
            this.progressBar.setProgress ( this.progressBar.getProgress ()+0.15 );
            this.gameController.getGame ().setProgressValue ( this.progressBar.getProgress () );
            moon.setAngle ( Math.PI );
            this.gameController.getGame ().getRotateMoons ().add ( moon );
            this.gameController.getGame ().getShootedMoon ().remove ( moon );
            this.gameController.getGame ().getRotateAnimation ().checkPhase ( this.gameController.getGame ().getBalls (),this.gameController.getGame ().getBalls ()-shooted );
            this.gameController.getGame ().setScore ( this.gameController.getGame ().getScore ()+this.gameController.getGame ().getDifficulty () );
            this.label.setText ( String.valueOf ( this.gameController.getGame ().getScore () ) );
            return;

        }

        moon.setCenterY (  y );
    }



}
