package view.Menu;

import controller.control.MoonInformation;
import controller.menus.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.SaveGame;
import model.User;
import view.Animation.RotateAnimation;
import view.Animation.ShootingAnimation;
import view.shapes.Moon;
import view.shapes.Planet;
import view.shapes.Shooter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Application {

    private static GameController gameController;
    public static Stage stage;
    public Label l;
    private ArrayList<Moon> rotateMoons;
    private ArrayList<Moon> shootedMoon;

    private Planet planet;
    private User currentUser;
    private int time;

    private boolean isLoad;
    private int difficulty;
    private double progressValue;
    private int score;
    private int leftBall;
    private Timer timer;
    private RotateAnimation rotateAnimation;

    private int phase;
    private double rate;


    public double timeForChange;

    public int direction;

    private int isVisible;
    private int Balls;

    public static MediaPlayer mediaPlayer;
    public Game(User currentUser){
        Game.gameController=new GameController ( this );
        this.currentUser=currentUser;
        this.rotateMoons =new ArrayList<> ();
        this.time=70;
        this.shootedMoon=new ArrayList<> ();
        this.isLoad=false;
        this.difficulty=currentUser.getDifficulty ();
        this.leftBall= currentUser.getNumberOfBall ();
        this.progressValue=0;
        this.score=0;
        this.phase=1;
        this.timeForChange=0;
        this.isVisible=1;
        this.direction=1;
        this.Balls=currentUser.getNumberOfBall ();
        this.rate=0.3*this.difficulty;
    }

    public Game(User currentUser, SaveGame saveGame){
        Game.gameController=new GameController ( this );
        this.currentUser=currentUser;
        this.rotateMoons =new ArrayList<> ();
        this.time=saveGame.getTime ();
        this.shootedMoon=new ArrayList<> ();
        this.isLoad=true;
        this.difficulty=saveGame.getDifficulty ();
        this.leftBall= saveGame.getLeftBall ();
        this.progressValue=saveGame.getProgressValue ();
        this.score=saveGame.getScore ();
        this.phase=saveGame.getPhase ();
        this.Balls=saveGame.getBalls ();
        for ( MoonInformation moonInformation: saveGame.getRotateMoons () ){
            Moon moon=new Moon ( moonInformation.getNumber (),moonInformation.getAngle ()+Math.PI );
            moon.setRadius ( moonInformation.getR () );
            moon.setCenterX ( moonInformation.getX () );
            moon.setCenterY ( moonInformation.getY () );
            this.rotateMoons.add ( moon );
        }
        for ( MoonInformation moonInformation: saveGame.getShootedMoon () ){
            Moon moon=new Moon ( moonInformation.getNumber (),moonInformation.getAngle () );
            moon.setRadius ( moonInformation.getR () );
            moon.setCenterX ( moonInformation.getX () );
            moon.setCenterY ( moonInformation.getY () );
            this.shootedMoon.add ( moon );
        }

        this.timeForChange=saveGame.timeForChange;
        this.isVisible=saveGame.getIsVisible ();
        this.direction=saveGame.direction;
        this.rate=saveGame.rate;
    }

    public ArrayList<Moon> getRotateMoons() {
        return rotateMoons;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<Moon> getShootedMoon() {
        return shootedMoon;
    }

    public int getTime() {
        return time;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public double getProgressValue() {
        return progressValue;
    }

    public int getLeftBall() {
        return leftBall;
    }

    public int getScore() {
        return score;
    }

    public void setProgressValue(double progressValue) {
        this.progressValue = progressValue;
    }

    public void setLeftBall(int leftBall) {
        this.leftBall = leftBall;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public RotateAnimation getRotateAnimation() {
        return rotateAnimation;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getBalls() {
        return Balls;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game.stage=primaryStage;
        Scene scene=new Scene ( createContent () );
        primaryStage.setScene ( scene );
        primaryStage.show ();
    }
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(500, 650);
        if(!this.currentUser.isMute ()) {
            int n=0;
            if(this.currentUser.getMusic ()!=0){
                n=this.currentUser.getMusic ();
            }else {
                 n = (new Random ().nextInt ( 1000 ) % 3) + 1;
                }
            Media media = new Media ( getClass ().getResource ( "/media/" + n + ".mp3" ).toString () );
            MediaPlayer mediaPlayer = new MediaPlayer ( media );
            mediaPlayer.setAutoPlay ( true );
            Game.mediaPlayer = mediaPlayer;

        }

        ProgressBar progressBar = new ProgressBar(this.progressValue);
        Label label=new Label ();

        Shooter shooter= createShooter ( root,progressBar,label);

        Planet planet=new Planet ();
        planet.setCenterY (  160 );
        planet.setCenterX (  250 );
        this.planet=planet;

        if(!isLoad) {
            createMap ( planet , 25 , root );
        }else {
            for ( Moon moon:this.rotateMoons ){
                root.getChildren ().add ( moon );
            }
            for ( Moon moon:this.shootedMoon ){
                ShootingAnimation shootingAnimation=new ShootingAnimation (Game.gameController, root,shooter,moon,planet,progressBar,label );
                shootingAnimation.play ();

            }
        }

        RotateAnimation rotateAnimation=new RotateAnimation ( this.rotateMoons ,planet,this.rate,this,isVisible,direction,timeForChange);
        this.rotateAnimation=rotateAnimation;
        if(this.phase>=2){
            rotateAnimation.Phase2 ( rotateAnimation );
        }
        if(this.phase>=3){
            rotateAnimation.Phase3 ( rotateAnimation );
        }

        rotateAnimation.play ();

        Node timer=createTimer (root);



        HBox hBox=new HBox (timer,progressBar);

        Button pause = getPause (l,shooter,root,progressBar,label);

        hBox.setSpacing ( 10 );
        hBox.setAlignment ( Pos.CENTER );
        hBox.getChildren ().add ( pause );

        label.setText ( String.valueOf (this.score) );

        hBox.getChildren ().add ( label );

        root.getChildren ().addAll ( planet,hBox );
        root.getChildren ().get ( 0 ).requestFocus ();

        return root;

    }


    private Shooter createShooter(Pane pane,ProgressBar progressBar,Label label2){
        Shooter shooter=new Shooter (this.leftBall);
        shooter.setTranslateX ( 180 );
        shooter.setTranslateY ( 490 );
        Label label=new Label ();
        label.setText ( String.valueOf ( shooter.getNumber () ) );
        this.l=label;
        label.setTranslateX ( 250 );
        label.setTranslateY ( 553 );
        label.setTextFill ( Color.WHITE );


        pane.getChildren ().addAll ( shooter,label );

        return shooter;
    }
    private Button getPause (Label label,Shooter shooter ,Pane pane,ProgressBar progressBar,Label label2) {
        Button puase =new Button ();
        puase.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Game.gameController.pause ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );
        puase.setOnKeyPressed ( new EventHandler<KeyEvent> () {
            @Override
            public void handle(KeyEvent event) {
                String code=event.getCode ().getName ();
                if(code.equals ("Space")){
                    Game.gameController.shoot ( shooter,pane,label,planet,progressBar,label2 );

                }else if ( code.equals ( "Tab" ) ){
                    Game.gameController.Freeze ( planet,progressBar );

                }


            }
        } );


        if ( this.currentUser.isEnglish () ){
            puase.setText ( "pause" );
        }else {
            puase.setText ( "توقف" );
        }
        return puase;
    }


    private void createMap(Planet planet,int deg,Pane pane){

        for ( int i=0 ;i<5;i++ ){
            double angle = Math.toRadians(deg*this.currentUser.getMapNumber ());
            int number= (new Random ().nextInt (1000)%3)+1;
            Moon moon=new Moon (number,angle*i);
            double x=planet.getCenterX ()+120*Math.sin ( angle*i);
            double y=planet.getCenterY ()-120*Math.cos ( angle*i );

            moon.setCenterX (  x );
            moon.setCenterY (  y );

            this.rotateMoons.add ( moon );
            pane.getChildren ().add ( moon );
        }

    }

    private Node createTimer(Pane pane){
        Timer timer=new Timer ();
        this.timer=timer;
        Label label=new Label ();
        label.setAlignment ( Pos.TOP_RIGHT );
        timer.schedule ( new TimerTask () {
            @Override
            public void run() {
                Platform.runLater ( () ->{
                    setTime(label,pane);
                } );
            }
        },0,1000 );

        return label;


    }

    private void setTime(Label label,Pane pane){
        this.time--;
        if(time==0){
            Game.gameController.Finish ( false,pane,this.currentUser,Game.gameController );
        }
        String str="";
        int min=time/60;
        int sec=time%60;
        if(min<10){
            str=str+"0";
        }
        str=str+min+":";
        if(sec<10){
            str=str+"0";
        }
        str=str+sec;

        label.setText ( str );

    }





}
