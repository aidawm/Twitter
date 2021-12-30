package main.java.org.ce.ap.server.exceptions;

/**
 * The type Tweet doesn't exist exception.
 */
public class TweetDoesntExistException extends Exception {
    /**
     * Instantiates a new Tweet doesn't exist exception.
     *
     * @param message the message
     */
    public TweetDoesntExistException(String message) {
        super(message);
    }
}
