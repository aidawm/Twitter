package  org.ce.ap.server.exceptions;

/**
 * this exception handles the date exceptions
 */
public class InvalidDateException extends Exception {
    /**
     * Instantiates a new Invalid date exception.
     *
     * @param message the message
     */
    public InvalidDateException(String message) {
        super(message);
    }
}
