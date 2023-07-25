package model;

import com.google.gson.Gson;
import controller.control.MoonInformation;
import view.Menu.Game;
import view.shapes.Moon;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveGame {

    public String username;
    public ArrayList<MoonInformation> rotateMoons;
    public ArrayList<MoonInformation> shootedMoon;
    public int time;
    public double progressValue;
    public int score;
    public int leftBall;
    public int difficulty;

    public  int phase;
    public double rate;
    public double timeForChange;

    public int direction;

    public int Balls;
    public int isVisible;

    public SaveGame(Game game){
        this.time=game.getTime ();
        this.leftBall=game.getLeftBall ();
        this.difficulty=game.getDifficulty ();
        this.score=game.getScore ();
        this.progressValue=game.getProgressValue ();
        this.phase=game.getPhase ();
        this.direction=game.getRotateAnimation ().getDirection ();
        this.rotateMoons=new ArrayList<> ();
        for (Moon moon: game.getRotateMoons () ){
            moon.getMoonInformation ().setR ( moon.getRadius () );
            moon.getMoonInformation ().setX ( moon.getCenterX () );
            moon.getMoonInformation ().setY ( moon.getCenterY () );
            this.rotateMoons.add ( moon.getMoonInformation () );
        }

        this.shootedMoon=new ArrayList<> ();
        for (Moon moon: game.getShootedMoon () ){
            moon.getMoonInformation ().setR ( moon.getRadius () );
            moon.getMoonInformation ().setX ( moon.getCenterX () );
            moon.getMoonInformation ().setY ( moon.getCenterY () );
            this.shootedMoon.add ( moon.getMoonInformation () );
        }

        this.timeForChange=game.getRotateAnimation ().getTimeForChange ();
        this.isVisible=game.getRotateAnimation ().getIsVisible ();
        this.Balls=game.getCurrentUser ().getNumberOfBall ();
        this.username=game.getCurrentUser ().getUserName ();
        this.rate=game.getRotateAnimation ().getRateSpeed ();
    }

    public ArrayList<MoonInformation> getRotateMoons() {
        return rotateMoons;
    }

    public ArrayList<MoonInformation> getShootedMoon() {
        return shootedMoon;
    }

    public int getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public int getLeftBall() {
        return leftBall;
    }

    public double getProgressValue() {
        return progressValue;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getPhase() {
        return phase;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public int getDirection() {
        return direction;
    }

    public double getTimeForChange() {
        return timeForChange;
    }

    public int getBalls() {
        return Balls;
    }
}
