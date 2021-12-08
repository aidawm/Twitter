package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.*;

public interface AuthenticationService {
     User verify() throws InvalidUsernameException, InvalidPasswordException, InvalidNameException, InvalidAgeException;
}
