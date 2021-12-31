package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.*;
import main.java.org.ce.ap.server.exceptions.*;
import main.java.org.ce.ap.server.model.User;
import main.java.org.ce.ap.server.services.AuthenticationService;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

/**
 * The type Authentication service.
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    private User user;
    ///// an instance from SignUp or SignIn class
    private AuthenticationService authenticationService;
    private UserManager userManager;

    /**
     * Instantiates a new Authentication service.
     */
    public AuthenticationServiceImpl() {
        this.userManager = UserManager.getInstance();
    }

    /**
     * do signUp process
     *
     * @return the user
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public User signUp(String firstName, String lastName, String username, String password, LocalDate birthDate) throws NoSuchAlgorithmException, InvalidUsernameException, InvalidPasswordException, SignUpExceptions {
        password = getHash(password);
        authenticationService = new SignUp(firstName, lastName, username, password, birthDate);
        verify();
        return user;
    }

    /**
     * do sign in process
     *
     * @return the user
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public User signIn(String username, String password) throws NoSuchAlgorithmException, InvalidUsernameException, InvalidPasswordException, SignUpExceptions {
        password = getHash(password);
        authenticationService = new SignIn(username, password);
        verify();
        return user;
    }

    /**
     * verify the information for authentication
     *
     * @return
     */
    @Override
    public User verify() throws InvalidUsernameException, InvalidPasswordException, SignUpExceptions {
        user = authenticationService.verify();
        if (authenticationService instanceof SignUp)
            userManager.addNewUser(user);

        return user;
    }

    /**
     * convert the password string to hash code
     *
     * @param password the user's password
     * @return hash code
     */
    private String getHash(String password) throws NoSuchAlgorithmException {

        return ToHexString.toHexString(ToHexString.getSHA(password));
    }

}
