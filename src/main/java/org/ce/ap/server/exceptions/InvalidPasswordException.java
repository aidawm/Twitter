package main.java.org.ce.ap.server.exceptions;

/**
 * if the password is incorrect
 */
public class InvalidPasswordException extends Exception {
    /**
     * Instantiates a new Invalid password exception.
     *
     * @param message the message
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}
