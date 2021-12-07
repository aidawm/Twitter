package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidAgeException;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.exceptions.InvalidNameException;
import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;

import java.time.LocalDate;
import java.util.Locale;

public class SignUp{
    private User user;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;
    private final LocalDate registryDate;

    public SignUp(String firstName, String lastName, String username, String password, LocalDate birthDate, LocalDate registryDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.registryDate = registryDate;
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
        for (char c : username.toCharArray()){
            if(!((c>='a' && c<='z')|| c=='_' || (c>='0' && c<='9')))
                throw new InvalidUsernameException("the username must have A-Z , 0-9 character ");
        }
    }
    /**
     * @param text should be valid
     * @throws InvalidNameException if the text isn't valid
     */
    private void checkName(String text) throws InvalidNameException{
        for (char c : text.toCharArray())
        {
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z'))
            {
                throw new InvalidNameException("name only can be a string of alphabets!");
            }
        }
    }

    private User setUser() throws InvalidNameException, InvalidUsernameException, InvalidAgeException {
        try {
            checkName(firstName);
            checkName(lastName);
            checkUsername(username);

            checkAge(birthDate);
        }
        return user;
    }

}
