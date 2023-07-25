package view.Animation;

import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.util.Duration;
import view.Menu.Game;
import view.shapes.Moon;
import view.shapes.Planet;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RotateAnimation extends Transition {

    private ArrayList<Moon> moons;
    private Planet planet;
    private Game game;

    private int direction;

    private double rateSpeed;
    private double timeForChange;

    private int isVisible;
    public Timer timerPhase2;
    public Timer timerPhase3;
    public RotateAnimation(ArrayList<Moon> moons,Planet planet,double rateSpeed,Game game,int isVisible,int direction,double timeForChange){
        this.moons=moons;
        this.planet=planet;
        this.setCycleDuration( Duration.millis (  10 ));
        this.setCycleCount(INDEFINITE);
        this.setRate ( rateSpeed );
        this.rateSpeed=rateSpeed;
        this.game=game;
        this.direction=direction;
        this.isVisible=isVisible;
        this.timeForChange=timeForChange;
    }

    public double getRateSpeed() {
        return rateSpeed;
    }

    public void setRateSpeed(double rateSpeed) {
        this.rateSpeed = rateSpeed;
    }


    public int isVisible() {
        return isVisible;
    }

    public void setVisible(int visible) {
        isVisible = visible;
    }

    @Override
    protected void interpolate(double frac) {


        if(game.getPhase ()>=2){
            changeRMoons ();
        }


        for ( Moon moon:this.moons ){
            double angle =moon.getAngle ();
            angle=angle+0.01*direction;
            moon.setAngle ( angle );
            double x=planet.getCenterX ()+120*Math.sin ( angle);
            double y=planet.getCenterY ()-120*Math.cos ( angle );
            moon.setCenterX (  x );
            moon.setCenterY (  y );
        }



    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void checkPhase(int allBall,int shoot){
        int prePhase=this.game.getPhase ();
        this.game.setPhase ((shoot/(allBall/4))+1);

        if(prePhase+1==this.game.getPhase ()){
            if(this.game.getPhase ()==2){
                this.Phase2 ( this );
            }else if ( this.game.getPhase ()==3 ){
                this.Phase3 ( this );
            }else if ( this.game.getPhase ()==4 ){

            }
        }
    }



    public void Phase2(RotateAnimation rotateAnimation){

        Timer timer=new Timer ();
        rotateAnimation.timerPhase2=timer;

        timer.schedule ( new TimerTask () {
            @Override
            public void run() {

                Platform.runLater ( ()->{
                    int n=new Random ().nextInt (1);
                    n=n*2-1;
                    rotateAnimation.direction =( rotateAnimation.direction*n );
                } );

            }
        }, 0,4*1000 );


    }
    private void changeRMoons(){
        for ( Moon moon:this.moons  ){
            moon.setRadius ( 15+1.5*Math.cos ( 2*Math.PI*0.001*timeForChange/5 ) );
            moon.setOpacity ( this.isVisible );
        }

        timeForChange=timeForChange+10;

    }

    public void Phase3(RotateAnimation rotateAnimation){

        Timer timer=new Timer ();
        rotateAnimation.timerPhase3=timerPhase3;
        timer.schedule ( new TimerTask () {
            @Override
            public void run() {

                Platform.runLater ( ()->{
                    for ( Moon moon: rotateAnimation.moons ){
                        if(rotateAnimation.isVisible==0){
                            double i=0;
                            while (i<1){
                                moon.setOpacity ( i );
                                i=i+0.00001;
                            }
                        }else {
                            double i=1;
                            while (i>=0){
                                moon.setOpacity ( i );
                                i=i-0.00001;
                            }
                        }
                    }
                    if(rotateAnimation.isVisible==1) {
                        rotateAnimation.setVisible ( 0 );
                    }else {
                        rotateAnimation.setIsVisible ( 1 );
                    }
                } );

            }
        }, 0, (long) (1.5*1000) );

    }

    public double getTimeForChange() {
        return timeForChange;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public void setTimeForChange(double timeForChange) {
        this.timeForChange = timeForChange;
    }

    public int getIsVisible() {
        return isVisible;
    }
}
