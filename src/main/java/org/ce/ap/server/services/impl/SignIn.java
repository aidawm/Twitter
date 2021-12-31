package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.exceptions.*;
import main.java.org.ce.ap.server.*;
import main.java.org.ce.ap.server.model.User;
import main.java.org.ce.ap.server.services.AuthenticationService;


/**
 * The type Sign in.
 */
public class SignIn implements AuthenticationService {
    private String username;
    private String password;
    private UserManager userManager;

    /**
     * get username and password to try signIn
     *
     * @param username the user's username
     * @param password the user's password
     */
    public SignIn(String username, String password) {
        this.userManager = UserManager.getInstance();
        this.username = username;
        this.password = password;
    }

    /**
     * call this method to verify the information and login to the user
     *
     * @return the user information
     * @throws InvalidUsernameException if the username doesn't exist
     * @throws InvalidPasswordException if the password is incorrect
     */
    public User verify() throws InvalidUsernameException, InvalidPasswordException {
        if (!userManager.getUserPassword(username).equals(password)) {
            throw new InvalidPasswordException("incorrect password!");
        }
        return userManager.findUser(username);
    }
}
