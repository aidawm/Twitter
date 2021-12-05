package main.java.org.ce.ap.server;

import java.time.LocalDate;
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

    /**
     * search on list and find the user from its username
     * @param username user's username
     * @return the user
     */
    public User findUser(String username){
        for (User user:users){
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    /**
     * process on data
     */
    private void processOnData(){

    }

    /**
     *  find the user's name by username
     * @param username user's username
     * @return the user's name
     */
    public String findName(String username){
        User user = findUser(username);
        return user.getFirstName()+" "+user.getUsername();
    }

    /**
     *  get the user's password by username
     * @param username user's username
     * @return the user's password
     */
    public String getUserPassword(String username){
        User user = findUser(username);
        return user.getPassword();
    }
    /**
     *  get the user's birthDate by username
     * @param username user's username
     * @return the user's birthDate
     */
    public LocalDate getUserBirthDate(String username){
        User user = findUser(username);
        return user.getBirthDate();
    }

}
