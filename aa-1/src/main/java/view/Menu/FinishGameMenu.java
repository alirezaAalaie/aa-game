package view.Menu;

import controller.menus.FinishGameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class FinishGameMenu extends Application {

    public static Stage stage;
    private static FinishGameController finishGameController;
    private User currentUser;

    public int time;
    public int score;
    public int difficulty;
    public FinishGameMenu(User user,int time,
                          int score,int difficulty){
        FinishGameMenu.finishGameController=new FinishGameController ( this );
        this.currentUser=user;
        this.time=70-time;
        this.score=score;
        this.difficulty=difficulty;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public static Stage getStage() {
        return stage;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        FinishGameMenu.stage=primaryStage;
        Scene scene=new Scene ( createContent () );
        primaryStage.setScene ( scene );
        primaryStage.show ();

    }


    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(600, 400);

        Button back=new Button ();
        back.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    FinishGameMenu.finishGameController.back ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );
        VBox vBox=new VBox ();

        vBox.setTranslateX ( 250 );
        vBox.setTranslateY ( 100 );



        Label score=new Label ();
        Label time=new Label ();
        String str="";
        int min=this.time/60;
        int sec=this.time%60;
        if(min<10){
            str=str+"0";
        }
        str=str+min+":";
        if(sec<10){
            str=str+"0";
        }
        str=str+sec;

        if ( this.currentUser.isEnglish () ){
            back.setText ( "Back" );
            score.setText ( "Your score in this game is\n\t\t  "+this.score );
            time.setText ( "time of your game is \n\t\t"+str );

        }else {
            back.setText ( "بازگشت" );
            score.setText ( "امتیاز کسب شذه توسط شما\t\t  \n"+this.score );
            time.setText ( "\n\t\tزمان طی شده در بازی "+str );

        }

        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 15 );
        vBox.getChildren ().addAll ( time,score,back );
        root.getChildren ().add ( vBox );


        return root;
    }

}
