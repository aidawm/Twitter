package main.java.org.ce.ap.server.exceptions;

import java.util.ArrayList;

public class SignUpExceptions extends Exception{
    private ArrayList<String> messages;
    public SignUpExceptions(ArrayList<String> messages){
        this.messages=messages;
    }

    public ArrayList<String> getMessages(){
        return messages;
    }
}
