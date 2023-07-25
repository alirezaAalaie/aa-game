package view.Menu;

import controller.menus.SignupController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Server;
import model.User;

public class SignupMenu extends Application {
    public Text errorPassword;
    public PasswordField passwordText;
    public Text errorUsername;
    public TextField usernameText;

    private static Stage stage;
    public Label label;

    private SignupController signupController;

    public SignupMenu(){
        this.signupController=new SignupController (this);
    }


    @Override
    public void start(Stage stage) throws Exception {
        SignupMenu.stage=stage;
        BorderPane pane= FXMLLoader.load ( LoginMenu.class.getResource ( "/fxml/Signup.fxml" ) );
        Scene scene=new Scene ( pane );
        stage.setScene ( scene );
        stage.show ();
    }

    public void signup(MouseEvent mouseEvent) throws Exception {
        if(!this.signupController.checkSignupValid ( usernameText.getText (),passwordText.getText () )){
            return;
        }

        User user=new User ( usernameText.getText ().trim (),passwordText.getText ()  );
        Server.addUser ( user );
        label.setText ( "your signup was successful" );
       new MainMenu (user).start ( SignupMenu.stage );
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        new LoginMenu ().start ( SignupMenu.stage );
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

        }
        else if(errorUsername.getText ().equals ( "This username has already been used" )){
            if(Server.checkUserByName ( usernameText.getText ().trim () ) == null){
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
        }else if(errorPassword.getText ().equals ( "Password should be min 6 Characters" )){
            if(passwordText.getText ().length () > 6){
                errorPassword.setText ( "" );
            }
        }
    }


}


