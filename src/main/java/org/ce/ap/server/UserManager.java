package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;
import org.json.JSONArray;
import org.json.JSONObject;
import main.java.org.ce.ap.server.DataBase.UserDataBase;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class holds the server's user information and process on it
 */
public class UserManager {
    private static UserManager instance;
    private static UserDataBase databaseHandler;
    ////list
    private static HashMap<String, User> users;
    private SubscribersManager subscribersManager;
    //// for test the authentication service
    public static HashMap<String, String> database = new HashMap<>();

    /**
     * create a new object from UserManager class
     */
    private UserManager() {
        databaseHandler = new UserDataBase();
        users = new HashMap<>();
        getDataFromDatabase();
        subscribersManager = new SubscribersManager(new ArrayList<>(users.values()));
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * get data from database
     */
    private void getDataFromDatabase() {
        ArrayList<JSONObject> userJsonList = databaseHandler.getDirectoryFiles();
        for (JSONObject user : userJsonList) {
            String username = user.getString("username");
            User user1 = new User(user);
            users.put(username, user1);
            JSONArray followings = user.getJSONArray("followings");
            for (int i = 0; i < followings.length(); i++) {
                String followingUsername = (String) ((JSONObject)followings.get(i)).get("username");
                if (users.containsKey(followingUsername))
                    continue;
                else {
                    User followingUser = new User((JSONObject)followings.get(i));
                    users.put(followingUsername, followingUser);
                    user1.addFollowing(followingUser);
                }
            }
            JSONArray followers = user.getJSONArray("followers");
            for (int i = 0; i < followers.length(); i++) {
                String followerUsername = (String) ((JSONObject)followers.get(i)).get("username");
                if (users.containsKey(followerUsername))
                    continue;
                else {
                    User followerUser = new User((JSONObject)followers.get(i));
                    users.put(followerUsername, followerUser);
                    user1.addFollowing(followerUser);
                }
            }
        }
        System.out.println("users count is :" + users.size());
    }

    /**
     * search on list and find the user from its username
     *
     * @param username user's username
     * @return the user
     */
    public User findUser(String username) throws InvalidUsernameException {
        if (users.containsKey(username))
            return users.get(username);
        throw new InvalidUsernameException("the username doesn't exist!!");
    }

    /**
     * process on data
     */
    private void processOnData() {

    }

    /**
     * find the user's name by username
     *
     * @param username user's username
     * @return the user's name
     */
    public String findName(String username) throws InvalidUsernameException {
        User user = findUser(username);
        return user.getFirstName() + " " + user.getUsername();
    }

    /**
     * get the user's password by username
     *
     * @param username user's username
     * @return the user's password
     */
    public String getUserPassword(String username) throws InvalidUsernameException {
        User user = findUser(username);
        return user.getPassword();
    }

    /**
     * get the user's birthDate by username
     *
     * @param username user's username
     * @return the user's birthDate
     */
    public LocalDate getUserBirthDate(String username) throws InvalidUsernameException {
        User user = findUser(username);
        return user.getBirthDate();
    }

    /**
     * check the username is taken or not
     *
     * @param username the username
     * @return true if it is not taken
     */
    public boolean isNotUsernameExist(String username) throws InvalidUsernameException {
        if (!users.containsKey(username))
            return true;
        throw new InvalidUsernameException("the username is exist now!");
    }

    /**
     * if a new user sign up to twitter
     *
     * @param user the new user
     */
    public void addNewUser(User user) {
        users.put(user.getUsername(), user);
        database.put(user.getUsername(), user.getPassword());
        SubscribersManager.addNewUser(user);
        databaseHandler.writeFile(user.getUsername(), user.toJson());
    }

    /**
     * get the list of the users
     *
     * @return
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }


}
