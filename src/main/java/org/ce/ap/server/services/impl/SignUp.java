package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.exceptions.*;
import main.java.org.ce.ap.server.*;
import main.java.org.ce.ap.server.model.user.User;
import main.java.org.ce.ap.server.services.AuthenticationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The type Sign up.
 */
public class SignUp implements AuthenticationService {
    private UserManager userManager;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;

    private ArrayList<String> exceptions;

    /**
     * Instantiates a new Sign up.
     *
     * @param firstName is using for setting firstname
     * @param lastName  is using for setting lastName
     * @param username  is using for setting username
     * @param password  is using for setting password
     * @param birthDate is using for setting birthDate
     */
    public SignUp(String firstName, String lastName, String username, String password, LocalDate birthDate) {
        this.userManager = UserManager.getInstance();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.exceptions = new ArrayList<>();
    }

    /**
     * @param birthDate the user's birthDate
     * @throws InvalidAgeException if the age isn't greater than 13, or it is after now
     */
    private void checkAge(LocalDate birthDate) {
        if (LocalDate.now().isBefore(birthDate))
//            return "the birthdate couldn't be after now :)";
            exceptions.add("InvalidDateException");
        if ((LocalDate.now().getYear() - birthDate.getYear()) < 13 && (LocalDate.now().getYear() - birthDate.getYear()) > 0)
            exceptions.add("InvalidAgeException");
//            return "the age must be greater than 13 ;) ";
    }

    /**
     * check the username be valid
     *
     * @param username the user's username
     * @throws InvalidUsernameException
     */
    private void checkUsername(String username) {
        if (username.length() < 4 || username.length() > 15)
//            return "the username must be greater than 4 and less than 15";
            exceptions.add("InvalidUsernameSizeException");
        username = username.toLowerCase(Locale.ROOT);
        for (char c : username.toCharArray()) {
            if (!((c >= 'a' && c <= 'z') || c == '_' || (c >= '0' && c <= '9'))) {
                exceptions.add("InvalidUsernameCharactersException");
                break;
            }
//                return "the username must have A-Z , 0-9 character ";
        }
        try {
            userManager.isNotUsernameExist(username);
        } catch (Exception e) {
//            return "username is exist!";
            exceptions.add("ExistingUsername");
        }
    }

    /**
     * @param text should be valid
     * @throws InvalidNameException if the text isn't valid
     */
    private void checkName(String text) {
        for (char c : text.toCharArray()) {
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z')) {
//                return "name only can be a string of alphabets!";
                exceptions.add("InvalidNameException");
            }
        }
    }

    /**
     * @return a new user
     */
    public User verify() throws SignUpExceptions {
        checkName(firstName);
        checkName(lastName);
        checkUsername(username);
        checkAge(birthDate);
        if (exceptions.size() != 0)
            throw new SignUpExceptions(exceptions);
        User user = new User(firstName, lastName, username, password, birthDate);
        return user;
    }

}
