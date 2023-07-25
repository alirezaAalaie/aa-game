package controller.menus;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import model.Server;
import view.Menu.MainMenu;
import view.Menu.SettingsMenu;

public class SettingsController {

    private SettingsMenu settingsMenu;

    public SettingsController(SettingsMenu settingsMenu){
        this.settingsMenu=settingsMenu;
    }

    public void back() throws Exception {
        new MainMenu ( this.settingsMenu.getCurrentUser () ).start ( SettingsMenu.stage );
    }

    public void setDifficulty(ChoiceBox choiceBox){
        switch (choiceBox.getSelectionModel ().selectedItemProperty ().getValue ().toString ()){

            case "Hard", "سخت" :
                this.settingsMenu.getCurrentUser ().setDifficulty ( 3 );
                break;
            case "Medium", "متوسط" :
                this.settingsMenu.getCurrentUser ().setDifficulty ( 2 );
                break;
            case "Easy", "اسان" :
                this.settingsMenu.getCurrentUser ().setDifficulty ( 1 );
                break;

        }

        Server.update ();

    }

    public void setLanguage(Button button) throws Exception {
        this.settingsMenu.getCurrentUser ().setEnglish ( !this.settingsMenu.getCurrentUser ().isEnglish () );
        Server.update ();

        if(this.settingsMenu.getCurrentUser ().isEnglish ()){
            button.setText ( "English" );
        }else {
            button.setText ( "فارسی" );
        }
        new SettingsMenu ( this.settingsMenu.getCurrentUser () ).start ( SettingsMenu.stage );

    }


    public void setNumberOfBall(double numberOfBall, Label label){
        this.settingsMenu.getCurrentUser ().setNumberOfBall ( (int) numberOfBall );
        if(this.settingsMenu.getCurrentUser ().isEnglish ()){
            label.setText ( "number of balls : " + this.settingsMenu.getCurrentUser ().getNumberOfBall () );
        }else {
            label.setText ( "تعداد توپ ها :" +this.settingsMenu.getCurrentUser ().getNumberOfBall () );
        }
        Server.update ();

    }

    public void setMute(Button button){
        this.settingsMenu.getCurrentUser ().setMute ( !this.settingsMenu.getCurrentUser ().isMute () );
        Server.update ();

        if(!this.settingsMenu.getCurrentUser ().isMute ()) {
            if ( this.settingsMenu.getCurrentUser ().isEnglish () ) {
                button.setText ( "Mute" );
            }
            else {
                button.setText ( "بی صدا" );
            }
        }else {
            if ( this.settingsMenu.getCurrentUser ().isEnglish () ) {
                button.setText ( "Unmute" );
            }
            else {
                button.setText ( "با صدا" );
            }


        }

    }


    public void setMapNumber(ChoiceBox choiceBox) {
        this.settingsMenu.getCurrentUser ().setMapNumber ( Integer.parseInt ( choiceBox.getSelectionModel ().selectedItemProperty ().getValue ().toString () ));
        Server.update ();
    }
}
