package  org.ce.ap.server.exceptions;

import java.util.ArrayList;

/**
 * The type Sign up exceptions.
 */
public class SignUpExceptions extends Exception {
    private ArrayList<String> messages;

    /**
     * Instantiates a new Sign up exceptions.
     *
     * @param messages the messages
     */
    public SignUpExceptions(ArrayList<String> messages) {
        this.messages = messages;
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public ArrayList<String> getMessages() {
        return messages;
    }

}
