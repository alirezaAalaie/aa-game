package controller.menus;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Server;
import view.Menu.LoginMenu;
import view.Menu.MainMenu;
import view.Menu.ProfileMenu;

import java.util.Random;

public class ProfileController {
    private ProfileMenu profileMenu;



    public ProfileController(ProfileMenu profileMenu){
        this.profileMenu=profileMenu;
    }

    public void changeUsername(String username){
        this.profileMenu.getCurrentUser ().setUserName ( username );
        Server.update ();

    }

    public void back() throws Exception {
        new MainMenu ( this.profileMenu.getCurrentUser () ).start ( ProfileMenu.stage );
    }

    public void changeUserNameText(Text usernameError, TextField username, Button button) {
        String text=username.getText ().trim ();
        boolean flag=true;
        if(text.equals ( "" )){
            if(this .profileMenu.getCurrentUser ().isEnglish ()) {
                usernameError.setText ( "Username should not be left blank" );
            }else {
                usernameError.setText ( "نام کاربری نباید خالی باشد" );
            }
            flag=false;
        }else if( Server.checkUserByName ( text ) != null && !(text.equals ( this.profileMenu.getCurrentUser ().getUserName () )) ){
            if(this.profileMenu.getCurrentUser ().isEnglish ()) {
                usernameError.setText ( "This username has already been used" );
            }else {
                usernameError.setText ( "این نام کاربری قبلا استفاده شده است" );
            }
            flag=false;
        }

        if(!flag){
            button.setVisible ( false );
            return;
        }
        button.setVisible ( true );
        usernameError.setText ( "" );
    }

    public void changePasswordText(Text passwordError, TextField password, Button button) {
        String text=password.getText ();
        boolean flag=true;
        if(text.equals ( "" )){
            if(this.profileMenu.getCurrentUser ().isEnglish ()) {
                passwordError.setText ( "Password should not be left blank" );
            }else {
                passwordError.setText ( "رمز نباید خالی باشد" );
            }
            flag=false;
        }else if( text.length ()< 6 ){

            if(this.profileMenu.getCurrentUser ().isEnglish ()) {
                passwordError.setText ( "Password should be min 6 Characters" );
            }else {
                passwordError.setText ( "رمز باید حداقل شش حرف باشد" );
            }
            flag=false;
        }

        if(!flag){
            button.setVisible ( false );
            return;
        }
        button.setVisible ( true );
        passwordError.setText ( "" );
    }

    public void changePassword(String password){
        this.profileMenu.getCurrentUser ().setPassword ( password );
        Server.update ();
    }

    public void logout() throws Exception {
        new LoginMenu ().start ( ProfileMenu.stage );
    }

    public void deleteAccount() throws Exception {
        Server.removeUser ( this.profileMenu.getCurrentUser () );
        Server.update ();
        new LoginMenu ().start ( ProfileMenu.stage );
    }

    public void randomProfile() throws Exception {
        int n=new Random ().nextInt (1000);
        n=(n%4)+1;
        this.profileMenu.getCurrentUser ().setUrl ( LoginMenu.class.getResource("/images/avatar/"+n+".png").toExternalForm() );
        new ProfileMenu ( this.profileMenu.getCurrentUser () ).start ( ProfileMenu.stage );
    }
    public void setProfile(BorderPane pane){
        HBox hBox=new HBox ();
        for ( int i=1;i<5;i++ ){
            Rectangle avatar=new Rectangle (200,50,80,80);
            avatar.setFill(new ImagePattern (
                    new Image (LoginMenu.class.getResource("/images/avatar/"+i+".png").toExternalForm())));

            avatar.setOnMouseEntered(event -> {
                avatar.setOpacity ( 0.5 );
            });
            avatar.setOnMouseExited(event -> {
                avatar.setOpacity ( 1 );
            });
            int finalI = i;
            avatar.setOnMousePressed( event -> {
                this.profileMenu.getCurrentUser ().setUrl ( LoginMenu.class.getResource("/images/avatar/"+ finalI +".png").toExternalForm() );
                try {
                    new ProfileMenu ( this.profileMenu.getCurrentUser () ).start ( ProfileMenu.stage );
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }

            });

            hBox.getChildren ().add ( avatar );
        }
        hBox.setSpacing ( 10 );
        hBox.setAlignment ( Pos.CENTER );
        pane.setTop ( hBox );
    }

    public void uploadProfile() throws Exception {
        String url="";
        if(url.equals ( "" )){
            return;
        }
        this.profileMenu.getCurrentUser ().setUrl ( url );
        new ProfileMenu ( this.profileMenu.getCurrentUser () ).start ( ProfileMenu.stage );


    }




}
