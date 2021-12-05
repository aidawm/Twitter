package main.java.org.ce.ap.server;

import java.util.ArrayList;

/**
 * this class holds the server's user information and process on it
 */
public class UserManager {
    private ArrayList<User> users;

    /**
     * create a new object from UserManager class
     */
    public UserManager(){
        getDataFromDatabase();
    }

    /**
     * get data from database
     */
    private void getDataFromDatabase(){

    }

}
