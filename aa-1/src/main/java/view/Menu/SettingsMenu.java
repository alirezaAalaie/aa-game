package view.Menu;

import controller.menus.SettingsController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class SettingsMenu extends Application {

    public static Stage stage;

    private User currentUser;

    private static SettingsController settingsController;
    public SettingsMenu(User currentUser){
        SettingsMenu.settingsController=new SettingsController (  this );
        this.currentUser=currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SettingsMenu.stage=primaryStage;

        Scene scene=new Scene ( createContent () );
        stage.setScene ( scene );
        stage.show ();
    }


    private BorderPane createContent() {
        BorderPane pane=new BorderPane ();
        pane.setPrefSize ( 600.0,400.0 );

        Node choiceBoxDifficulty =choiceBoxDifficulty ();
        Node choiceBoxMap =choiceBoxMap ();

        Button back=new Button ();
        back.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    SettingsMenu.settingsController.back ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );

        if ( this.currentUser.isEnglish () ){
            back.setText ( "Back" );
        }else {
            back.setText ( "بازگشت" );
        }

        Node language=languageButton ();
        Node slider=selectBall ();
        Node mute= muteButton ();

        HBox hBox=new HBox (back);

        pane.setTop ( hBox );
        VBox vBox=new VBox ( choiceBoxDifficulty ,language,slider,mute, choiceBoxMap );
        vBox.setAlignment ( Pos.CENTER );
        pane.setCenter ( vBox );
        vBox.setSpacing ( 15 );
        return pane;

    }

    private Node choiceBoxDifficulty(){
        ChoiceBox choiceBox=new ChoiceBox<> ();

        if ( this.currentUser.isEnglish () ){
            choiceBox.getItems ().addAll ( "Hard","Medium","Easy" );
            switch (this.currentUser.getDifficulty ()){

                case 3:
                    choiceBox.setValue ( "Hard" );

                    break;
                case  2 :
                    choiceBox.setValue ( "Medium" );
                    break;
                case  1 :
                    choiceBox.setValue ( "Easy" );
                    break;

            }

        }else {
            choiceBox.getItems ().addAll ( "سخت","متوسط","اسان" );
            switch (this.currentUser.getDifficulty ()){

                case 3:
                    choiceBox.setValue ( "سخت" );

                    break;
                case  2 :
                    choiceBox.setValue ( "متوسط" );
                    break;
                case  1 :
                    choiceBox.setValue ( "اسان" );
                    break;

            }

        }


        choiceBox.getSelectionModel ().selectedItemProperty ().addListener ( (observable, oldValue, newValue) -> {
            SettingsMenu.settingsController.setDifficulty ( choiceBox );
        } );
        return choiceBox;
    }
    private Node languageButton(){
        Button button=new Button ();


        if(this.currentUser.isEnglish ()){
            button.setText ( "English" );
        }else {
            button.setText ( "فارسی" );
        }

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    settingsController.setLanguage ( button );
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }
            }
        });

        return button;
    }

    private Node selectBall(){
        Label label=new Label ();
        Slider slider=new Slider (5,25,this.currentUser.getNumberOfBall ());

        if(this.currentUser.isEnglish ()){
            label.setText ( "number of balls : " + this.currentUser.getNumberOfBall () );
        }else {
            label.setText ( "تعداد توپ ها :" +this.currentUser.getNumberOfBall () );
        }

        slider.valueChangingProperty ().addListener ( (observable, oldValue, newValue) -> {
            SettingsMenu.settingsController.setNumberOfBall ( slider.getValue (),label );


        } );

        slider.setMaxWidth ( 200 );
        slider.setMajorTickUnit (5);
        slider.setShowTickLabels ( true );
        slider.setMinorTickCount (1);
        slider.setShowTickMarks ( true );
        slider.setBlockIncrement ( 1 );


        VBox vBox=new VBox (label,slider);
        vBox.setSpacing ( 10 );
        vBox.setAlignment ( Pos.CENTER );
        return vBox;
    }


    private Node muteButton(){
        Button button=new Button ();

        if(!this.currentUser.isMute ()) {
            if ( this.currentUser.isEnglish () ) {
                button.setText ( "Mute" );
            }
            else {
                button.setText ( "بی صدا" );
            }
        }else {
            if ( this.currentUser.isEnglish () ) {
                button.setText ( "Unmute" );
            }
            else {
                button.setText ( "با صدا" );
            }


        }

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                settingsController.setMute ( button );
            }
        });

        return button;


    }

    private Node choiceBoxMap(){
        ChoiceBox choiceBox=new ChoiceBox<> ();
            Label label=new Label ();

        if(this.currentUser.isEnglish ()){
            label.setText ( "Map number" );
        }else {
            label.setText ( "شماره نقشه");
        }

            choiceBox.getItems ().addAll ( "1","2","3" );
            choiceBox.setValue ( this.currentUser.getMapNumber () );

        choiceBox.getSelectionModel ().selectedItemProperty ().addListener ( (observable, oldValue, newValue) -> {
            SettingsMenu.settingsController.setMapNumber ( choiceBox );
        } );
        VBox vBox=new VBox (label,choiceBox);
        vBox.setSpacing ( 10 );
        vBox.setAlignment ( Pos.CENTER );

        return vBox;
    }



}
