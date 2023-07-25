package view.Menu;

import controller.menus.ProfileController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.User;

public class ProfileMenu extends Application {

    public static Stage stage;

    private User currentUser;

    private static ProfileController profileController;
    public ProfileMenu(User currentUser){
        ProfileMenu.profileController=new ProfileController ( this );
        this.currentUser=currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ProfileMenu.stage=primaryStage;

        Scene scene=new Scene ( createContent () );
        stage.setScene ( scene );
        stage.show ();
    }


    private BorderPane createContent() {
        BorderPane pane=new BorderPane ();
        pane.setPrefSize ( 600.0,400.0 );

        Node avatar=createAvatar (pane);

        Node username=userNamePart ();

        Node password=passwordPart ();

        Button back=new Button ();
        back.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ProfileMenu.profileController.back ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );

        HBox hBox=new HBox (avatar);
        hBox.setAlignment ( Pos.CENTER );

        pane.setTop ( hBox );

        Button logout=new Button ();

        logout.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ProfileMenu.profileController.logout ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );
        Button deleteAccount=new Button ();

        deleteAccount.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ProfileMenu.profileController.deleteAccount ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );

        if ( this.currentUser.isEnglish () ){
            back.setText ( "Back" );
            logout.setText ( "Logout" );
            deleteAccount.setText ( "Delete Account" );
        }else {
            back.setText ( "بازگشت" );
            logout.setText ( "خارج شدن از حساب کاربری" );
            deleteAccount.setText ( "حذف حساب کاربری" );

        }

        VBox vBox=new VBox (username,password,logout,deleteAccount,back);
        vBox.setSpacing ( 15 );
        vBox.getChildren ().get ( 0 ).setTranslateX ( 150 );
        vBox.getChildren ().get ( 1 ).setTranslateX ( 150 );




        vBox.setAlignment ( Pos.CENTER );
        pane.setCenter ( vBox );
        return pane;

    }

    private Node userNamePart(){
        VBox vBox=new VBox ();
        HBox hBox=new HBox ();
        hBox.setSpacing ( 10 );
        Text text=new Text ();
        TextField username=new TextField (this.currentUser.getUserName ());
        username.setAlignment ( Pos.CENTER );
        Button button=new Button ();
        Text usernameError=new Text ();
        usernameError.setTextAlignment ( TextAlignment.RIGHT );



        username.textProperty ().addListener ( (observable, oldValue, newValue) -> {
            ProfileMenu.profileController.changeUserNameText (usernameError,username,button);
        } );

        username.setOnMouseClicked (
                new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                resetUsername ( username );
            }
        } );

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                ProfileMenu.profileController.changeUsername ( username.getText ().trim () );
            }
        } );

        if(this.currentUser.isEnglish ()){
            text.setText ( "username :" );
            button.setText ( "change" );

            hBox.getChildren ().addAll ( text,username, button );

        }else {
            text.setText ( "نام کاربری :" );
            button.setText ( "تغییر" );
            hBox.getChildren ().addAll (  button,username,text );

        }

        vBox.getChildren ().addAll ( hBox,usernameError );
        return vBox;

    }

    public void resetUsername(TextField username){
        username.setText ( this.currentUser.getUserName () );
    }

    private Node passwordPart(){
        VBox vBox=new VBox ();
        HBox hBox=new HBox ();
        hBox.setSpacing ( 10 );
        Text text=new Text ();
        TextField password=new TextField (this.currentUser.getPassword ());
        password.setAlignment ( Pos.CENTER );

        Button button=new Button ();
        Text passwordError =new Text ();
        passwordError.setTextAlignment ( TextAlignment.RIGHT );

        password.textProperty ().addListener ( (observable, oldValue, newValue) -> {
            ProfileMenu.profileController.changePasswordText ( passwordError,password,button);
        } );

        password.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                resetPassword ( password );
            }
        } );

        button.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                ProfileMenu.profileController.changePassword ( password.getText () );
            }
        } );


        if(this.currentUser.isEnglish ()){
            text.setText ( "password :" );
            button.setText ( "change" );

            hBox.getChildren ().addAll ( text,password, button );

        }else {
            text.setText ( "رمز :" );
            button.setText ( "تغییر" );
            hBox.getChildren ().addAll (  button,password,text );

        }




        vBox.getChildren ().addAll ( hBox, passwordError );
        return vBox;

    }

    public void resetPassword(TextField password){
        password.setText ( this.currentUser.getPassword () );
    }

    private Node createAvatar(BorderPane pane){
        Rectangle avatar=new Rectangle (200,50,80,80);
        avatar.setFill(new ImagePattern (
                new Image (this.currentUser.getUrl ())));

        avatar.setOnMouseEntered(event -> {
            avatar.setOpacity ( 0.5 );
        });
        avatar.setOnMouseExited(event -> {
            avatar.setOpacity ( 1 );
        });
        avatar.setOnMousePressed(event -> {
            this.createMenu ( pane );
        });




        return avatar;
    }

    private void createMenu(BorderPane pane){
        Button random=new Button ();
        Button set=new Button ();
        Button upLoad=new Button ();

        if(this.currentUser.isEnglish ()){
            random.setText ( "set random avatar" );
            set.setText ( "choose random avatar" );
            upLoad.setText ( "upload" );
        }else {
            random.setText ( "عکس تصادفی" );
            set.setText ( "انتخاب از عکس های موجود" );
            upLoad.setText ( "اپلود عکس" );

        }
        VBox vBox=new VBox ( random,set,upLoad);

        random.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ProfileMenu.profileController.randomProfile ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            }
        } );

        set.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                    ProfileMenu.profileController.setProfile ( pane );
            }
        } );

        upLoad.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ProfileMenu.profileController.uploadProfile ();
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }
            }
        } );



        vBox.setAlignment ( Pos.TOP_CENTER );
        pane.setRight ( vBox );

    }




}
