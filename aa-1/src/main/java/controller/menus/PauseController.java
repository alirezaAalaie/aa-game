package controller.menus;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import model.Server;
import view.Menu.Game;
import view.Menu.LoginMenu;
import view.Menu.MainMenu;
import view.Menu.PauseMenu;

public class PauseController {

    private PauseMenu pauseMenu;
    public PauseController(PauseMenu pauseMenu){
        this.pauseMenu=pauseMenu;
    }

    public void restart() throws Exception {
        Server.removeGame ( this.pauseMenu.getCurrentUser () );
        Server.update ();
        new Game ( this.pauseMenu.getCurrentUser () ).start ( PauseMenu.stage );
    }

    public void exit() throws Exception {

        if(this.pauseMenu.getCurrentUser ().isGust ()){
            new LoginMenu ().start ( PauseMenu.stage );
            Server.removeGame ( this.pauseMenu.getCurrentUser () );
            Server.update ();
        }else{
            if(!this.pauseMenu.isSave){
                Server.removeGame ( this.pauseMenu.getCurrentUser () );
                Server.update ();
            }
        new MainMenu ( this.pauseMenu.getCurrentUser () ).start ( PauseMenu.stage );
        }
    }


    public void Continue() throws Exception {

        new Game ( this.pauseMenu.getCurrentUser (), Server.checkGameByUser ( this.pauseMenu.getCurrentUser () ) ).start ( PauseMenu.stage );

    }


    public void save(Button button) throws Exception {
        Server.update ();
        this.pauseMenu.isSave=true;
        if(this.pauseMenu.getCurrentUser ().isEnglish ()){
            button.setText ( "saved" );
        }else {
            button.setText ( "ذخیره شد" );
        }

    }
    public void setMute(Button button){
        this.pauseMenu.getCurrentUser ().setMute ( !this.pauseMenu.getCurrentUser ().isMute () );
        Server.update ();

        if(!this.pauseMenu.getCurrentUser ().isMute ()) {
            if ( this.pauseMenu.getCurrentUser ().isEnglish () ) {
                button.setText ( "Mute" );
            }
            else {
                button.setText ( "بی صدا" );
            }
        }else {
            if ( this.pauseMenu.getCurrentUser ().isEnglish () ) {
                button.setText ( "Unmute" );
            }
            else {
                button.setText ( "با صدا" );
            }


        }

    }

    public void  setMusic(ChoiceBox choiceBox){
        this.pauseMenu.getCurrentUser ().setMusic ( Integer.parseInt ( choiceBox.getSelectionModel ().selectedItemProperty ().getValue ().toString () ) );



    }


}
