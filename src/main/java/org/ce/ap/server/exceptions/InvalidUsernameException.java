package main.java.org.ce.ap.server.exceptions;

/**
 * if the username is not valid or if the username is taken
 */
public class InvalidUsernameException extends Exception {
    /**
     * Instantiates a new Invalid username exception.
     *
     * @param message the message
     */
    public InvalidUsernameException(String message) {
        super(message);
    }
}
