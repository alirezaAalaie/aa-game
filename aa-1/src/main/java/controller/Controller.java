package controller;

import javafx.stage.Stage;
import model.Server;
import view.Menu.LoginMenu;

import java.io.IOException;

public class Controller {

    private LoginMenu loginMenu;
    private Server server;

    private static Stage stage;
    public Controller () throws IOException {
        this.server=new Server ();


    }

    public void  run(String[] args ) throws Exception {
        this.loginMenu=new LoginMenu (args);


    }

}
