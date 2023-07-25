package controller.menus;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Server;
import model.User;
import view.Menu.MainMenu;
import view.Menu.RankMenu;
import view.Menu.SettingsMenu;

public class RankController {

    private RankMenu rankMenu;
    public RankController(RankMenu rankMenu){
        this.rankMenu=rankMenu;
    }

    public void back() throws Exception {
        new MainMenu ( this.rankMenu.getCurrentUser () ).start ( RankMenu.stage );
    }

    public void sort(ChoiceBox choiceBox, BorderPane pane){
        switch (choiceBox.getSelectionModel ().selectedItemProperty ().getValue ().toString ()){

            case "All",  "همه" :
                Server.sortByDifficulty ( 0 );
                break;
            case "Hard", "سخت" :
                Server.sortByDifficulty ( 3 );
                break;
            case "Medium", "متوسط" :
                Server.sortByDifficulty ( 2 );
                break;
            case "Easy", "اسان" :
                Server.sortByDifficulty ( 1 );
                break;
        }

        VBox vBox=new VBox ();

        Text text=new Text ("rank\t\t\t\tusername\t\t\t\tscore");
        vBox.getChildren ().add ( text );

        for (int i = 0;i<10 && i<Server.getSortOfPlayer ().size ();i++ ) {
            User user = Server.getSortOfPlayer ().get ( i );
            Label label=new Label ();

             String str=i+1+")\t\t\t\t"+user.getUserName ()+"\t\t\t\t\t";
            switch (choiceBox.getSelectionModel ().selectedItemProperty ().getValue ().toString ()){

                case "All",  "همه" :
                    str=str+user.getScore ();
                    break;
                case "Hard", "سخت" :
                    str=str+user.getHard ();
                    break;
                case "Medium", "متوسط" :
                    str=str+user.getMedium ();
                    break;
                case "Easy", "اسان" :
                    str=str+user.getEasy ();
                    break;
            }
            label.setText ( str);
            if(i==0) {
                label.setTextFill ( Color.GOLD );
            }else if ( i==1 ){
                label.setTextFill ( Color.GRAY );
            }else if(i==2){
                label.setTextFill ( Color.SADDLEBROWN );
            }
            vBox.getChildren ().add ( label );

        }

        vBox.setAlignment ( Pos.TOP_CENTER );
        vBox.setSpacing ( 5 );
        pane.setCenter ( vBox );
    }


}
