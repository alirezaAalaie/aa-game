package controller.menus;

import model.Server;
import model.User;
import view.Menu.FinishGameMenu;
import view.Menu.LoginMenu;
import view.Menu.MainMenu;
import view.Menu.SignupMenu;

public class FinishGameController {

    private FinishGameMenu finishGameMenu;

    public FinishGameController(FinishGameMenu finishGameMenu){
        this.finishGameMenu=finishGameMenu;
    }


    public void back() throws Exception {

        if(this.finishGameMenu.getCurrentUser ().isGust ()){
            new LoginMenu ().start ( FinishGameMenu.stage );
        }else {
            this.finishGameMenu.getCurrentUser ().setScore ( this.finishGameMenu.getCurrentUser ().getScore ()+this.finishGameMenu.score );
            if(this.finishGameMenu.difficulty==1 && this.finishGameMenu.getCurrentUser ().getEasy ()<this.finishGameMenu.score){
                this.finishGameMenu.getCurrentUser ().setEasy ( this.finishGameMenu.score );
            }else if(this.finishGameMenu.difficulty==2 && this.finishGameMenu.getCurrentUser ().getMedium ()<this.finishGameMenu.score){
                this.finishGameMenu.getCurrentUser ().setMedium ( this.finishGameMenu.score );
            } else if(this.finishGameMenu.difficulty==3 && this.finishGameMenu.getCurrentUser ().getHard ()<this.finishGameMenu.score){
                this.finishGameMenu.getCurrentUser ().setHard ( this.finishGameMenu.score );
            }
            Server.update ();
        new MainMenu ( this.finishGameMenu.getCurrentUser () ).start ( FinishGameMenu.stage );
        }
    }
}
