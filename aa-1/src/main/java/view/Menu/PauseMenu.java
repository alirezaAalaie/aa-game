package view.Menu;

import controller.menus.PauseController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class PauseMenu extends Application {

    private static PauseController pauseController;

    public static Stage stage;
    private User currentUser;
    public boolean isSave;

    public PauseMenu(User currentUser){
        this.currentUser=currentUser;
        PauseMenu.pauseController=new PauseController ( this );
        this.isSave=false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PauseMenu.stage=primaryStage;

        Scene scene=new Scene ( createContent () );
        primaryStage.setScene ( scene );
        primaryStage.show ();

    }
    private Parent createContent(){
        Pane pane=new Pane ();
        pane.setPrefSize ( 600,400 );
        Node continueButton=continueButton ();
        Node stopMusicButton=muteButton ();
        Node restartButton=restartButton ();
        Node choiceBoxMusic=choiceBoxMusic ();
        VBox vBox=new VBox (continueButton);
        if( !this.currentUser.isGust ()){
            vBox.getChildren ().add ( 1,saveButton () );
        }

        Node exitButton=exitButton ();
        vBox.getChildren ().addAll ( stopMusicButton,choiceBoxMusic,restartButton,exitButton );



        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 15 );
        vBox.setTranslateX ( 300 );
        vBox.setTranslateY ( 100 );

        pane.getChildren ().addAll ( vBox );

        return pane;
    }
    private Node continueButton(){
        Button button=new Button ();

        if(this.currentUser.isEnglish ()){
            button.setText ( "continue" );
        }else {
            button.setText ( "ادامه" );
        }

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    PauseMenu.pauseController.Continue();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }
            }
        } );



        return button;
    }

    private Node restartButton(){
        Button button=new Button ();

        if(this.currentUser.isEnglish ()){
            button.setText ( "restart" );
        }else {
            button.setText ( "تلاش دوباره" );
        }

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    PauseMenu.pauseController.restart();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }
            }
        } );



        return button;
    }


    private Node exitButton(){
        Button button=new Button ();

        if(this.currentUser.isEnglish ()){
            button.setText ( "exit" );
        }else {
            button.setText ( "خروج" );
        }

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    PauseMenu.pauseController.exit();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );

        return button;
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
                pauseController.setMute ( button );
            }
        });

        return button;


    }    private Node saveButton(){
        Button button=new Button ();

        if(this.currentUser.isEnglish ()){
            button.setText ( "save" );
        }else {
            button.setText ( "ذخیره" );
        }

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    PauseMenu.pauseController.save(button);
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );

        return button;
    }

    private Node choiceBoxMusic(){
        ChoiceBox choiceBox=new ChoiceBox<> ();

        choiceBox.getItems ().addAll ( "1","2","3" );

        choiceBox.getSelectionModel ().selectedItemProperty ().addListener ( (observable, oldValue, newValue) -> {
            PauseMenu.pauseController.setMusic ( choiceBox );
        } );
        return choiceBox;
    }

}
