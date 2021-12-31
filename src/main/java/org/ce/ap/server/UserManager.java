package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;
import main.java.org.ce.ap.server.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import main.java.org.ce.ap.server.DataBase.UserDataBase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class holds the server's user information and process on it
 */
public class UserManager {
    private static UserManager instance;
    private static UserDataBase databaseHandler;
    private static HashMap<String, User> users;
    private SubscribersManager subscribersManager;


    /**
     * create a new object from UserManager class
     */
    private UserManager() {
        databaseHandler = new UserDataBase();
        users = new HashMap<>();
        getDataFromDatabase();
        subscribersManager = new SubscribersManager(new ArrayList<>(users.values()));
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
            if (users.containsKey(username))
                continue;
            User user1 = new User(user);
            users.put(username, user1);
        }
        addFollowing();
        System.out.println("users count is :" + users.size());
    }

    private void addFollowing() {
        for (User user : users.values()) {
            JSONObject jsonObject = user.getJsonObject();
            JSONArray followings = (JSONArray) jsonObject.get("followings");
            for (int i = 0; i < followings.length(); i++) {
                System.out.println(followings.get(i));
                user.addFollowing(users.get(followings.getString(i)));
            }
        }
    }

    /**
     * search on list and find the user from its username
     *
     * @param username user's username
     * @return the user
     * @throws InvalidUsernameException the invalid username exception
     */
    public User findUser(String username) throws InvalidUsernameException {
        if (users.containsKey(username))
            return users.get(username);
        throw new InvalidUsernameException("the username doesn't exist!!");
    }

    /**
     * find the user's name by username
     *
     * @param username user's username
     * @return the user's name
     * @throws InvalidUsernameException the invalid username exception
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
     * @throws InvalidUsernameException the invalid username exception
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
     * @throws InvalidUsernameException the invalid username exception
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
     * @throws InvalidUsernameException the invalid username exception
     */
    public boolean isNotUsernameExist(String username) throws InvalidUsernameException {
        if (!users.containsKey(username)) {
            return true;
        }

        throw new InvalidUsernameException("the username is exist now!");
    }

    /**
     * if a new user sign up to twitter
     *
     * @param user the new user
     */
    public void addNewUser(User user) {
        users.put(user.getUsername(), user);
        SubscribersManager.addNewUser(user);
        databaseHandler.writeFile(user.getUsername(), user.toJson());
    }

    /**
     * get the list of the users
     *
     * @return users
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Update.
     *
     * @param user the user
     */
    public void update(User user) {
        databaseHandler.writeFile(user.getUsername(), user.toJson());
    }

}
