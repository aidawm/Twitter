package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.*;

/**
 * this interface verifies the user
 */
public interface AuthenticationService {
     User verify() throws InvalidUsernameException, InvalidPasswordException, InvalidNameException, InvalidAgeException;
}
