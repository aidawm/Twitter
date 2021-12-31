package main.java.org.ce.ap.server.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import main.java.org.ce.ap.server.exceptions.InvalidAgeException;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import org.json.JSONObject;

/**
 * The type User.
 */
public class User {
    private JSONObject jsonObject;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;
    private final LocalDate registryDate;
    private String biography;
    private HashSet<User> followings = new HashSet<>();
    private HashSet<User> followers = new HashSet<>();


    /**
     * Instantiates a new User.
     *
     * @param firstName is using for setting firstName
     * @param lastName  is using for setting lastName
     * @param username  is using for setting username
     * @param password  is using for setting password
     * @param birthDate is using for setting birthDate
     * @throws InvalidAgeException if the age isn't greater than 13 or it is after now
     */
    public User(String firstName, String lastName, String username, String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.registryDate = LocalDate.now();

    }

    /**
     * Instantiates a new User.
     *
     * @param jsonObject the json object
     */
    public User(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.firstName = jsonObject.getString("firstName");
        this.lastName = jsonObject.getString("lastName");
        this.username = jsonObject.getString("username");
        this.password = jsonObject.getString("password");
        this.birthDate = LocalDate.parse(jsonObject.getString("birthDate"));
        this.registryDate = LocalDate.parse(jsonObject.getString("registryDate"));
    }

    /**
     * Gets first name.
     *
     * @return firstName first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName is using for setting first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName is using for setting last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets username.
     *
     * @return username username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username is using for setting username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return password password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password is using for setting password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets birth date.
     *
     * @return birthday birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate is using for setting birthday
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets registry date.
     *
     * @return registry date
     */
    public LocalDate getRegistryDate() {
        return registryDate;
    }

    /**
     * Gets biography.
     *
     * @return biography biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets biography.
     *
     * @param biography is using for setting biography
     * @throws InvalidCharacterNumberException the invalid character number exception
     */
    public void setBiography(String biography) throws InvalidCharacterNumberException {
        checkBiography(biography);
        this.biography = biography;
    }

    /**
     * Check biography.
     *
     * @param text the biography text
     * @throws InvalidCharacterNumberException if the number is greater than 256 or the text is null
     */
    public void checkBiography(String text) throws InvalidCharacterNumberException {
        if (text.length() == 0 || text == null) {
            throw new InvalidCharacterNumberException("biography shouldn't be empty!");
        } else if (text.length() > 256) {
            throw new InvalidCharacterNumberException("The number of characters is invalid, it should be lower than 256 characters!");
        }
    }

    /**
     * To json array user str array list.
     *
     * @param list the list
     * @return the array list
     */
    public ArrayList<String> toJsonArrayUserStr(HashSet<User> list) {
        ArrayList<String> jsonList = new ArrayList<>();
        for (User user : list) {
            jsonList.add(user.getUsername());
        }
        return jsonList;
    }

    /**
     * To json array user array list.
     *
     * @param list the list
     * @return the array list
     */
    public ArrayList<JSONObject> toJsonArrayUser(HashSet<User> list) {
        System.out.println("LIST= " + list);
        ArrayList<JSONObject> jsonList = new ArrayList<>();
        for (User user : list) {
            if (user.equals(this))
                continue;
            jsonList.add((user).toJson());
        }
        return jsonList;
    }

    @Override
    public String toString() {
        return "@" + username;
    }

    /**
     * To json json object.
     *
     * @return the json object
     */
    public JSONObject toJson() {
        this.jsonObject = new JSONObject();

        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("birthDate", birthDate);
        jsonObject.put("registryDate", registryDate);
        jsonObject.put("biography", biography);
        jsonObject.put("followings", toJsonArrayUserStr(followings));
        jsonObject.put("followers", toJsonArrayUserStr(followers));
        return jsonObject;
    }

    /**
     * Add follower.
     *
     * @param user the user
     */
    public void addFollower(User user) {
        followers.add(user);
    }

    /**
     * Remove follower.
     *
     * @param user the user
     */
    public void removeFollower(User user) {
        followers.remove(user);
    }

    /**
     * Add following.
     *
     * @param user the user
     */
    public void addFollowing(User user) {
        followings.add(user);
    }

    /**
     * Remove following.
     *
     * @param user the user
     */
    public void removeFollowing(User user) {
        followings.remove(user);
    }

    /**
     * Gets followings.
     *
     * @return the followings
     */
    public ArrayList<User> getFollowings() {
        return new ArrayList<>(followings);
    }

    /**
     * Gets followers.
     *
     * @return the followers
     */
    public ArrayList<User> getFollowers() {
        return new ArrayList<>(followers);
    }

    /**
     * Gets json object.
     *
     * @return the json object
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
