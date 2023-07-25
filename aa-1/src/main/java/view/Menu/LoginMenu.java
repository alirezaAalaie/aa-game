package view.Menu;

import controller.menus.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Server;
import model.User;

public class LoginMenu extends Application {

    public Label label;
    private LoginController loginController;
    public TextField usernameText;
    public PasswordField passwordText;
    public Text errorUsername;
    public Text errorPassword;

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public LoginMenu(){
        this.loginController=new LoginController ( this );
    }
    public LoginMenu(String[] args){
        launch ( args );
        this.loginController=new LoginController ( this );


    }

    @Override

    public void start(Stage stage) throws Exception {
        LoginMenu.stage =stage;
        stage.setTitle ( "AA-aa" );
        stage.getIcons ().add ( new Image ( String.valueOf ( LoginMenu.class.getResource ( "/images/aa.png" ) ) ) );
        BorderPane pane= FXMLLoader.load ( LoginMenu.class.getResource ( "/fxml/Login.fxml" ) );
        Scene scene=new Scene ( pane );
        stage.setScene ( scene );
        stage.show ();

    }

    public void continueAsGust(MouseEvent mouseEvent) throws Exception {
        User user=new User ( "","" );
        user.setGust ( true );
        new Game ( user ).start ( LoginMenu.stage );
    }

    public void signUpMenu(MouseEvent mouseEvent) throws Exception {
        new SignupMenu ().start ( stage );

    }

    public void login(MouseEvent mouseEvent) throws Exception {
        if(!this.loginController.checkLoginIsValid ( usernameText.getText (),passwordText.getText () )){
            return;
        }
        label.setText ( "your login was successful" );
        new MainMenu (Server.checkUserByName ( usernameText.getText ().trim () )).start ( LoginMenu.stage);


    }
    public void initialize(){
        usernameText.textProperty ().addListener ( ((observable, oldValue, newValue) -> {
            changeUserNameText ();
        }) );

        passwordText.textProperty ().addListener ( ((observable, oldValue, newValue) -> {
            changePasswordText ();
        }) );

    }
    private void changeUserNameText() {
        if(errorUsername.getText ().equals ( "" )){
            return;
        }
        if(errorUsername.getText ().equals ( "Username should not be left blank" )){
            if(!usernameText.getText ().equals ( "" )){
                errorUsername.setText ( "" );
            }

        } else if(errorUsername.getText ().equals ( "Username not found" )){
            if(Server.checkUserByName ( usernameText.getText ().trim () ) != null){
                errorUsername.setText ( "" );
            }
        }


    }
    private void changePasswordText() {
        if(errorPassword.getText ().equals ( "" )){
            return;
        }
        if(errorPassword.getText ().equals ( "Password should not be left blank" )){
            if(!passwordText.getText ().equals ( "" )){
                errorPassword.setText ( "" );
            }
            return;
        }
    }
}
