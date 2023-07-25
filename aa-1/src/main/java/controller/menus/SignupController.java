package controller.menus;

import model.Server;
import view.Menu.SignupMenu;

public class SignupController {

    private SignupMenu signupMenu;

    public SignupController(SignupMenu signupMenu){
        this.signupMenu=signupMenu;
    }

    public boolean checkSignupValid(String username , String password){
        username=username.trim ();
        boolean flag=true;
        if(username.equals ( "" )){
            signupMenu.errorUsername.setText ("Username should not be left blank");
            flag=false;
        }
        if(password.equals ( "" )){
            signupMenu.errorPassword.setText ( "Password should not be left blank");
            flag=false;
        }
        if(!flag){
            return false;
        }

        if( Server.checkUserByName ( username ) != null ){
            signupMenu.errorUsername.setText ("This username has already been used");
            flag=false;
        }

        if(password.length () < 6) {
            signupMenu.errorPassword.setText ( "Password should be min 6 Characters" );
            flag=false;
        }


        return flag;
    }
}


