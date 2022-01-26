package  org.ce.ap.server.exceptions;

/**
 * exception for invalid age( the age isn't greater than 13 or it is after now)
 */
public class InvalidAgeException extends Exception {
    /**
     * Instantiates a new Invalid age exception.
     *
     * @param message the message
     */
    public InvalidAgeException(String message) {
        super(message);
    }
}
