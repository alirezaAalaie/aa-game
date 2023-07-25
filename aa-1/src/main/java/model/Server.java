package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static ArrayList<User> users;
    private static ArrayList<User> sortOfPlayer;


    private static ArrayList<SaveGame> loadGames;
   public Server()  {
       if( readUserFromJson ()==null){
           Server.users = new ArrayList<> ();

       }else {
           Server.users = new ArrayList<> ( readUserFromJson ());
       }

       if( readGameFromJson ()==null){
           Server.loadGames = new ArrayList<> ();

       }else {
           Server.loadGames=readGameFromJson ();
       }
        Server.sortOfPlayer=new ArrayList<> ();

   }
   private  static void writeUserInJson(){
        try {
            FileWriter fileWriter=new FileWriter (  "D:/aa/CE FILE/start again/AP/hw/aa-1/src/main/resources/users.json"  );
            fileWriter.write ( new Gson().toJson ( Server.users ) );
            fileWriter.close ();
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }

    }
    private static ArrayList<User> readUserFromJson(){
        try {
            String json = new String ( Files.readAllBytes ( new File("D:/aa/CE FILE/start again/AP/hw/aa-1/src/main/resources/users.json").toPath()  ) );
            return new Gson ().fromJson ( json,new TypeToken<List<User>> (){}.getType () );
        } catch (IOException e) {
            return null;
        }


    }


    public static User checkUserByName(String username){
       if(users.isEmpty ()){
           return null;
       }
       for ( User user:users ){
           if(user.getUserName ().equals ( username )){
               return user;
           }
       }

       return null;
    }

    public static void addUser(User user){
       Server.users.add ( user );

    }

    public static void update(){
       writeUserInJson ();
       writeGameInJson ();
    }
    public static void removeUser(User user){
        Server.users.remove ( user );
    }
    private  static void writeGameInJson(){
       if(Server.loadGames.isEmpty ()){
           return;
       }
        try {
            FileWriter fileWriter=new FileWriter (  "D:/aa/CE FILE/start again/AP/hw/aa-1/src/main/resources/LoadGame.json"  );
            fileWriter.write ( new Gson().toJson ( Server.loadGames ) );
            fileWriter.close ();
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }

    }
    private static ArrayList<SaveGame> readGameFromJson(){
        try {
            String json = new String ( Files.readAllBytes ( new File("D:/aa/CE FILE/start again/AP/hw/aa-1/src/main/resources/LoadGame.json").toPath()  ) );
            return new Gson ().fromJson ( json,new TypeToken<List<SaveGame >>(){}.getType() );
        } catch (IOException e) {
            return null;
        }


    }
    public static SaveGame checkGameByUser(User user) {
        if ( Server.loadGames.isEmpty () ) {
            return null;
        }
        for ( SaveGame saveGame : Server.loadGames ) {
            if ( saveGame.username.equals ( user.getUserName () ) ) {
                return saveGame;
            }
        }
            return null;
   }



    public static void addGame(User user,SaveGame saveGame){
        if(Server.checkGameByUser ( user )!=null){
            Server.loadGames.remove ( Server.checkGameByUser ( user ) );
        }
        Server.loadGames.add ( saveGame );

    }
    public static void removeGame(User user)
    {
        Server.loadGames.remove ( Server.checkGameByUser ( user ) );

    }


    public static void sortByDifficulty (int sort) {

        Server.sortOfPlayer.clear ();
        for ( int i = 0; i < Server.users.size (); i++ ) {
            User user = Server.users.get ( i );
            int j = 0;
            if ( ! Server.sortOfPlayer.isEmpty () ) {
                for ( ; j < Server.sortOfPlayer.size (); j++ ) {
                    User userj = Server.sortOfPlayer.get ( j );

                    if ( sort == 0 ) {
                        if ( user.getScore () > userj.getScore () ) break;
                    }
                    else if ( sort == 1 ) {
                        if ( user.getEasy () > userj.getEasy () ) break;
                    }
                    else if ( sort == 2 ) {
                        if ( user.getMedium () > userj.getMedium () ) break;
                    }
                    else {
                        if ( user.getHard () > userj.getHard () )  break;
                    }

                }

            }
            Server.sortOfPlayer.add ( j , user );

        }

    }

    public static ArrayList<User> getSortOfPlayer () {
        return sortOfPlayer;
    }
}



