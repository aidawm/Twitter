package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.*;

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
