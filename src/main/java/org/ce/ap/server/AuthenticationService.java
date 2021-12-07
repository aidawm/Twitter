package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidAgeException;
import main.java.org.ce.ap.server.exceptions.InvalidNameException;
import main.java.org.ce.ap.server.exceptions.InvalidPasswordException;
import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;

public interface AuthenticationService {
     User verify() throws InvalidUsernameException, InvalidPasswordException, InvalidNameException, InvalidAgeException;
}
