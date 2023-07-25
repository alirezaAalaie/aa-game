package model;

import view.Menu.LoginMenu;

public class User {
    private String userName;
    private String password;

    private int difficulty;
    private boolean isEnglish;

    private int numberOfBall;
    private int score;
    private boolean isGust;

    private boolean isMute;
    private int mapNumber;

    private String url;

    private int medium;
    private int easy;
    private int hard;
    private int music;

    public User (String userName,String password){
        this.userName=userName;
        this.password=password;
        this.difficulty=2;
        this.isEnglish=true;
        this.numberOfBall=20;
        this.isMute=false;
        this.score=0;
        this.isGust=false;
        this.mapNumber=1;
        this.url= LoginMenu.class.getResource("/images/avatar/1.png").toExternalForm();
        this.easy=0;
        this.hard=0;
        this.medium=0;
        this.music=0;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        if(Server.checkGameByUser ( this )!=null){
            SaveGame saveGame=Server.checkGameByUser ( this );
            Server.removeGame ( this );
            this.userName = userName;
            Server.addGame ( this,saveGame );
        }else {
            this.userName = userName;
        }

    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setEnglish(boolean english) {
        isEnglish = english;
    }

    public int getNumberOfBall() {
        return numberOfBall;
    }

    public void setNumberOfBall(int numberOfBall) {
        this.numberOfBall = numberOfBall;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGust(boolean gust) {
        isGust = gust;
    }

    public boolean isGust() {
        return isGust;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getEasy() {
        return easy;
    }

    public int getHard() {
        return hard;
    }

    public int getMedium() {
        return medium;
    }

    public void setEasy(int easy) {
        this.easy = easy;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public void setHard(int hard) {
        this.hard = hard;
    }

    public int getMusic () {
        return music;
    }

    public void setMusic (int music) {
        this.music = music;
    }
}
