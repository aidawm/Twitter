package main.java.org.ce.ap.server.exceptions;

/**
 * exception for invalid age( the age isn't greater than 13 or it is after now)
 */
public class InvalidAgeException extends Exception{
    public InvalidAgeException(String message){
        super(message);
    }
}
