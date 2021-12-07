package main.java.org.ce.ap.server.exceptions;

/**
 * if the password is incorrect
 */
public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message){
        super(message);
    }
}
