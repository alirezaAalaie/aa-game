package controller.menus;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.SaveGame;
import model.Server;
import model.User;
import view.Animation.RotateAnimation;
import view.Animation.ShootingAnimation;
import view.Menu.FinishGameMenu;
import view.Menu.Game;
import view.Menu.PauseMenu;
import view.shapes.Moon;
import view.shapes.Planet;
import view.shapes.Shooter;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private Game game;
    public GameController(Game game){
        this.game=game;
    }

    public Game getGame() {
        return game;
    }

    public void shoot(Shooter shooter, Pane pane, Label label, Planet planet, ProgressBar progressBar,Label label2){
        if(shooter.getNumber ()==0 || pane.getBackground ().equals ( new Background ( new BackgroundFill ( Color.RED , null , null ) ) )){
            return;
        }
        int number= (new Random ().nextInt (1000)%3)+1;
        Moon moon=new Moon (number,0);
        moon.setCenterX (  shooter.getTranslateX ()+80 );
        moon.setCenterY (  shooter.getTranslateY ()-20 );
        pane.getChildren ().add ( moon );
        shooter.setNumber ( shooter.getNumber ()-1 );
        label.setText ( String.valueOf ( shooter.getNumber () ) );
        this.game.setLeftBall ( shooter.getNumber () );

        this.game.getShootedMoon ().add ( moon );

        ShootingAnimation shootingAnimation=new ShootingAnimation (this, pane,shooter,moon,planet,progressBar,label2 );
        shootingAnimation.play ();
        if(!this.game.getCurrentUser ().isMute ()) {
            Media media = new Media ( getClass ().getResource ( "/media/shotgun-firing-3-14483.mp3" ).toString () );
            MediaPlayer mediaPlayer = new MediaPlayer ( media );
            mediaPlayer.setAutoPlay ( true );
        }

    }

    public void Finish(boolean win, Pane pane, User user,GameController gameController) {
        Label label=new Label ();
        label.setTranslateX ( 250 );
        label.setTranslateY ( 300 );
        label.setAlignment ( Pos.CENTER );
        if(win){
            pane.setBackground ( new Background ( new BackgroundFill ( Color.GREEN , null , null ) ) );
            label .setText ( "You Win");

        }else {
            pane.setBackground ( new Background ( new BackgroundFill ( Color.RED , null , null ) ) );
            label .setText ( "You Lose" );
        }
        pane.getChildren ().add ( label );
        this.game.getTimer ().cancel ();
        Server.removeGame ( user );
        Server.update ();

        if(this.game.getRotateAnimation ().timerPhase2!=null){
            this.game.getRotateAnimation ().timerPhase2.cancel ();
        }

        if(this.game.getRotateAnimation ().timerPhase3!=null){
            this.game.getRotateAnimation ().timerPhase3.cancel ();
        }
        new Timer ().schedule ( new TimerTask () {
            @Override
            public void run() {

                Platform.runLater ( ()->{
                    try {
                        if(Game.mediaPlayer!=null){
                            Game.mediaPlayer.stop ();
                        }
                        new FinishGameMenu ( user,gameController.game.getTime (),gameController.game.getScore (),gameController.game.getDifficulty () ).start ( Game.stage );

                    } catch (Exception e) {
                        throw new RuntimeException ( e );
                    }
                } );

            }
        },2*1000 );


    }

    public void  pause() throws Exception {
        this.game.getTimer ().cancel ();
        if(Game.mediaPlayer!=null){
            Game.mediaPlayer.stop ();
        }
        if(this.game.getRotateAnimation ().timerPhase2!=null){
            this.game.getRotateAnimation ().timerPhase2.cancel ();
        }

        if(this.game.getRotateAnimation ().timerPhase3!=null){
            this.game.getRotateAnimation ().timerPhase3.cancel ();
        }
        Server.addGame ( this.game.getCurrentUser (),new SaveGame ( this.game ) );
        new PauseMenu ( this.game.getCurrentUser () ).start ( Game.stage );

    }


    public void Freeze(Planet planet , ProgressBar progressBar) {
            double speed=this.game.getRotateAnimation ().getRateSpeed ();
            double time=9-2*this.game.getDifficulty ();

            if(progressBar.getProgress ()<1){
                return;
            }
        FadeTransition fadeTransition=new FadeTransition ( Duration.seconds ( 1 ),planet );
            fadeTransition.setFromValue ( 1 );
            fadeTransition.setToValue ( 0.5 );
            fadeTransition.play ();
         planet.setOpacity ( 0.5 );
         progressBar.setProgress ( 0 );
        this.game.getRotateAnimation ().setRate ( 0.7*speed );
        RotateAnimation rotateAnimation=this.game.getRotateAnimation ();
        new Timer ().schedule ( new TimerTask () {
            @Override
            public void run() {

                Platform.runLater ( ()->{

                    rotateAnimation.setRate ( speed );
                    planet.setOpacity ( 1 );

                } );

            }
        }, (long) (time*1000) );

    }


}
