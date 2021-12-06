package main.java.org.ce.ap.server.exceptions;

public class InvalidCharacterNumberException extends Exception{
    /**
     * exception for invalid character number(if the number is greater than 256)
     */
    public InvalidCharacterNumberException(String message){
        super(message);
    }
}
