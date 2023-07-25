package view.Menu;

import controller.menus.RankController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class RankMenu extends Application {


    public static RankController rankController;
    public static Stage stage;
    private User currentUser;
    public RankMenu(User currentUser){
        RankMenu.rankController=new RankController ( this );
        this.currentUser=currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RankMenu.stage=primaryStage;

        Scene scene=new Scene ( createContent () );
        primaryStage.setScene ( scene );
        primaryStage.show ();

    }

    private Parent createContent(){
        BorderPane pane=new BorderPane ();
        pane.setPrefSize ( 600,400 );





        Button back=new Button ();
        back.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    RankMenu.rankController.back ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );

        Label label=new Label ();
        if ( this.currentUser.isEnglish () ){
            back.setText ( "Back" );
            label.setText ( "sort by :" );

        }else {
            back.setText ( "بازگشت" );
            label.setText ( "مرتب سازی بر اساس" );

        }
        ChoiceBox choiceBox=choiceBoxDifficulty (pane);







        HBox hBox=new HBox (back,label,choiceBox);
        back.setTranslateX ( 0 );
        back.setTranslateY ( 0 );
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 50 );
        RankMenu.rankController.sort ( choiceBox,pane );

        pane.setTop ( hBox );

        return pane;
    }



    private ChoiceBox choiceBoxDifficulty(BorderPane pane){
        ChoiceBox choiceBox=new ChoiceBox<> ();

        if ( this.currentUser.isEnglish () ){
            choiceBox.getItems ().addAll ( "All","Hard","Medium","Easy" );
            choiceBox.setValue ( "All" );


        }else {
            choiceBox.getItems ().addAll ( "همه","سخت","متوسط","اسان" );
            choiceBox.setValue ( "همه" );
            }


        choiceBox.getSelectionModel ().selectedItemProperty ().addListener ( (observable, oldValue, newValue) -> {
           RankMenu.rankController.sort (  choiceBox,pane );
        } );


        return choiceBox;
    }
}
