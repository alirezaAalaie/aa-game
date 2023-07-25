package view.Menu;

import controller.menus.MainController;
import javafx.application.*;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;
import model.User;

public class MainMenu extends Application {


    private static MainController mainController;
    private static Stage stage;
    private User currentUser;
    public static Stage getStage() {
        return stage;
    }


    public MainMenu(User user){
        MainMenu.mainController=new MainController ( this );
        this.currentUser=user;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(600.0, 400.0);
       /* try(InputStream is = Files.newInputStream( Paths.get("/images/landscape-shot-white-full-moon-with-black-color-background.jpg"))){
            ImageView img = new ImageView(new Image (is));
            img.setFitWidth(600);
            img.setFitHeight(400);
            root.getChildren().add(img);
        }
        catch(IOException e) {
            System.out.println("Couldn't load image");
        }*/
        MenuBox vbox;
        Label label;
        if(this.currentUser.isEnglish ()) {
             label = new Label ( "welcome " + currentUser.getUserName () );
             vbox= new MenuBox (
                    new MenuItem ( "NEW GAME" ),
                    new MenuItem ( "CONTINUE" ),
                    new MenuItem ( "PROFILE" ),
                    new MenuItem ( "RANK TABLE" ),
                    new MenuItem ( "SETTINGS" ),
                    new MenuItem ( "EXIT" ) );

        }else {
            label = new Label (   currentUser.getUserName ()+"خوش امدید  " );
            vbox = new MenuBox (
                    new MenuItem ( "بازی جدید" ),
                    new MenuItem ( "ادامه" ),
                    new MenuItem ( "شخصی سازی" ),
                    new MenuItem ( "جدول امتیازات" ),
                    new MenuItem ( "تنظیمات" ),
                    new MenuItem ( "خروج" ) );

        }
        vbox.setTranslateX ( 200 );
        vbox.setTranslateY ( 100 );
        label.setTranslateX ( 200 );
        label.setTranslateY ( 50 );

        root.getChildren().addAll(vbox,label);
        return root;

    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        MainMenu.stage=primaryStage;
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class MenuBox extends VBox{
        public MenuBox(MenuItem...items) {
            for(MenuItem item : items) {
                getChildren().addAll(item);
            }
        }
    }
    private static class MenuItem extends StackPane{
        public MenuItem(String name) {
            Rectangle bg = new Rectangle(200,30);
            bg.setOpacity(0);
            Text text = new Text(name);
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                text.setFill(Color.GOLD);
            });
            setOnMouseExited(event -> {
                text.setFill(Color.BLACK);
            });
            setOnMousePressed(event -> {
                text.setFill(Color.WHITE);
                try {
                    MainMenu.mainController.chooseState ( text.getText ());
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }
            });
         /*   setOnMouseReleased(event -> {
                bg.setFill(Color.BLACK);
            });*/
        }
    }

    public static void main(String[] args) {
        launch(args);
    }





}
