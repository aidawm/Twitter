package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.exceptions.*;
import main.java.org.ce.ap.server.model.user.User;

/**
 * this interface verifies the user
 */
public interface AuthenticationService {
     /**
      * Verify user.
      *
      * @return the user
      * @throws InvalidUsernameException the invalid username exception
      * @throws InvalidPasswordException the invalid password exception
      * @throws SignUpExceptions         the sign up exceptions
      */
     User verify() throws InvalidUsernameException, InvalidPasswordException, SignUpExceptions;
}
