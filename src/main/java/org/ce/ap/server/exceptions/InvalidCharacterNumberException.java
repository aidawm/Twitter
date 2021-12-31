package main.java.org.ce.ap.server.exceptions;

/**
 * The type Invalid character number exception.
 */
public class InvalidCharacterNumberException extends Exception {
    /**
     * exception for invalid character number(if the number is greater than 256)
     *
     * @param message the message
     */
    public InvalidCharacterNumberException(String message) {
        super(message);
    }
}
