package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class holds the server's user information and process on it
 */
public class UserManager {

    public static ArrayList<User> users=new ArrayList<>();
    private SubscribersManager subscribersManager;

    public static HashMap<String,String> database=new HashMap<>();

    /**
     * create a new object from UserManager class
     */
    public UserManager(){
//        users=new ArrayList<>();
        getDataFromDatabase();
        subscribersManager =new SubscribersManager(users);
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
    public User findUser(String username) throws InvalidUsernameException{
        for (User user:users){
            if(user.getUsername().equals(username))
                return user;
        }
        throw new InvalidUsernameException("the username doesn't exist!!");
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
    public String findName(String username) throws InvalidUsernameException{
        User user = findUser(username);
        return user.getFirstName()+" "+user.getUsername();
    }

    /**
     *  get the user's password by username
     * @param username user's username
     * @return the user's password
     */
    public String getUserPassword(String username) throws InvalidUsernameException{
        return database.get(username);
//        User user = findUser(username);
//        return user.getPassword();
    }
    /**
     *  get the user's birthDate by username
     * @param username user's username
     * @return the user's birthDate
     */
    public LocalDate getUserBirthDate(String username) throws InvalidUsernameException{
        User user = findUser(username);
        return user.getBirthDate();
    }

    /**
     * check the username is taken or not
     * @param username the username
     * @return true if it is not taken
     */
    public boolean isNotUsernameExist(String username) throws InvalidUsernameException{
        try {
            findUser(username);
        }catch (InvalidUsernameException e){
            return true;
        }
        throw new InvalidUsernameException("the username is exist now!");
    }
//    private int findIndex(UserAccount user)
//    {
//        int i = 0;
//        for (User temp : users)
//        {
//            if (temp.equals(user))
//            {
//                return i;
//            }
//            i++;
//        }
//        return -1;
//    }

//    private ArrayList<Tweet> followingTweets(UserAccount user)
//    {
//        ArrayList<Tweet> followingTweet = new ArrayList<>();
//        UserAccount userAccount = users.get(findIndex(user));
//        int i = 0;
//        for (UserAccount followingUser: userAccount.getFollowing())
//        {
//            followingTweet.addAll(followingUser.getTweets());
//        }
//        return followingTweet;
//    }
    public void addNewUser(User user){
        users.add(user);
        database.put(user.getUsername(),user.getPassword());
        SubscribersManager.addNewUser(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
