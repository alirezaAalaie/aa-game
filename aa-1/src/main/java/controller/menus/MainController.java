package controller.menus;

import model.Server;
import view.Menu.*;

public class MainController {
    private static MainMenu mainMenu;


    public MainController(MainMenu mainMenu){
        MainController.mainMenu=mainMenu;
    }
    
    
    public   void chooseState(String state) throws Exception {
        switch (state) {
            case "NEW GAME","بازی جدید" -> newGame ();
            case "CONTINUE","ادامه" -> loadGame ();
            case "PROFILE","شخصی سازی" -> profileMenu ();
            case "SETTINGS","تنظیمات" -> settings ();
            case "EXIT","خروج" -> exit ();
            case "جدول امتیازات","RANK TABLE" ->rank();
        }

    }

    private  void exit() throws Exception {
        MainMenu.getStage ().close ();
    }

    private  void settings() throws Exception {
        new SettingsMenu ( MainController.mainMenu.getCurrentUser () ).start ( MainMenu.getStage () );
    }

    private  void profileMenu() throws Exception {
        new ProfileMenu ( MainController.mainMenu.getCurrentUser () ).start ( MainMenu.getStage ()  );

    }

    private  void loadGame() throws Exception {

        if(Server.checkGameByUser ( MainController .mainMenu.getCurrentUser ())==null){
            return;
        }else {
            new Game ( MainController.mainMenu.getCurrentUser (),Server.checkGameByUser ( MainController .mainMenu.getCurrentUser ()) ).start ( MainMenu.getStage () );
        }

    }

    private  void newGame() throws Exception {
        new Game (MainController.mainMenu.getCurrentUser ()).start ( MainMenu.getStage () );


    }
    private void rank(){
        try {
            new RankMenu ( MainController.mainMenu.getCurrentUser () ).start ( MainMenu.getStage () );
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }

}
