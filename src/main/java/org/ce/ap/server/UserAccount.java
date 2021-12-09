package main.java.org.ce.ap.server;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserAccount extends User{

    private ArrayList<Tweet> tweets = new ArrayList<>();
    private ArrayList<User> follower= new ArrayList<>();
    private ArrayList<User> following= new ArrayList<>();
    /**
     * @param firstName is using for setting firstName
     * @param lastName  is using for setting lastName
     * @param username  is using for setting username
     * @param password  is using for setting password
     * @param birthDate is using for setting birthDate
     */
    public UserAccount(String firstName, String lastName, String username, String password, LocalDate birthDate) {
        super(firstName, lastName, username, password, birthDate);
    }

}
