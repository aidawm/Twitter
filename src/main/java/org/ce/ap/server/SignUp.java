package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidAgeException;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.exceptions.InvalidNameException;
import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;

import java.time.LocalDate;
import java.util.Locale;

public class SignUp implements AuthenticationService{
    UserManager userManager = new UserManager();
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;

    /**
     *
     * @param firstName is using for setting firstname
     * @param lastName is using for setting lastName
     * @param username is using for setting username
     * @param password is using for setting password
     * @param birthDate  is using for setting birthDate
     */
    public SignUp(String firstName, String lastName, String username, String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
    }
    /**
     * @param birthDate the user's birthDate
     * @throws InvalidAgeException if the age isn't greater than 13, or it is after now
     */
    private void checkAge(LocalDate birthDate) throws InvalidAgeException {
        if(LocalDate.now().isBefore(birthDate))
            throw new InvalidAgeException("the birthdate couldn't be after now :)");
        if((LocalDate.now().getYear()-birthDate.getYear())<13)
            throw new InvalidAgeException("the age must be greater than 13 ;) ");
    }

    /**
     * check the username be valid
     * @param username the user's username
     * @throws InvalidUsernameException
     */
    private void checkUsername(String username) throws InvalidUsernameException{
        if(username.length()<4 || username.length()>15)
            throw new InvalidUsernameException("the username must be greater than 4 and less than 15");
        username=username.toLowerCase(Locale.ROOT);
        for (char c : username.toCharArray()) {
            if (!((c >= 'a' && c <= 'z') || c == '_' || (c >= '0' && c <= '9')))
                throw new InvalidUsernameException("the username must have A-Z , 0-9 character ");
        }
        userManager.isNotUsernameExist(username);
    }
    /**
     * @param text should be valid
     * @throws InvalidNameException if the text isn't valid
     */
    private void checkName(String text) throws InvalidNameException {
        for (char c : text.toCharArray()) {
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z')) {
                throw new InvalidNameException("name only can be a string of alphabets!");
            }
        }
    }

    /**
     *
     * @return a new uder
     * @throws InvalidNameException if the name is invalid
     * @throws InvalidUsernameException if the username is invalid
     * @throws InvalidAgeException if the age is invalid
     */
    public User verify() throws InvalidNameException, InvalidUsernameException, InvalidAgeException {
        checkName(firstName);
        checkName(lastName);
        checkUsername(username);
        checkAge(birthDate);
        return new User (firstName, lastName, username, password, birthDate);
    }

}
