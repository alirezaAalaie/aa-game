package controller.menus;

import model.Server;
import model.User;
import view.Menu.LoginMenu;

public class LoginController {

    private LoginMenu loginMenu;
    public LoginController(LoginMenu loginMenu){
        this.loginMenu=loginMenu;
    }


    public boolean checkLoginIsValid( String username,String password){
        username=username.trim ();
        boolean flag=true;
        if(username.equals ( "" )){
            loginMenu.errorUsername.setText ("Username should not be left blank");
            flag=false;
        }
        if(password.equals ( "" )){
            loginMenu.errorPassword.setText ( "Password should not be left blank");
            flag=false;
        }
        if(!flag){
            return false;
        }

        if( Server.checkUserByName ( username ) == null ){
            loginMenu.errorUsername.setText ("Username not found");
            return false;
        }
        User user=Server.checkUserByName ( username );
        if( ! user.getPassword ().equals ( password ) ){
            loginMenu.errorPassword.setText ( "Password is wrong");
            return false;
        }
        return true;
    }

}
