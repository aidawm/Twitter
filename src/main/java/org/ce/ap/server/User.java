package main.java.org.ce.ap.server;

import java.time.LocalDate;
import main.java.org.ce.ap.server.exceptions.InvalidAgeException;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.exceptions.InvalidNameException;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;
    private final LocalDate registryDate;
    private String biography;

    /**
     *
     * @param firstName is using for setting firstName
     * @param lastName is using for setting lastName
     * @param username is using for setting username
     * @param password is using for setting password
     * @param birthDate is using for setting birthDate
     * @throws InvalidAgeException if the age isn't greater than 13 or it is after now
     */
    public User(String firstName, String lastName, String username, String password, LocalDate birthDate) throws InvalidAgeException, InvalidNameException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.registryDate = LocalDate.now();
        checkAge();
    }

    /**
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName is using for setting first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName is using for setting last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username is using for setting username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password is using for setting password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return birthday
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     *
     * @param birthDate is using for setting birthday
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * @return registry date
     */
    public LocalDate getRegistryDate() {
        return registryDate;
    }

    /**
     *
     * @return biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     *
     * @param biography is using for setting biography
     */
    public void setBiography(String biography) throws InvalidCharacterNumberException {
        checkBiography(biography);
        this.biography = biography;
    }

    /**
     * @throws InvalidAgeException if the age isn't greater than 13 or it is after now
     */
    private void checkAge() throws InvalidAgeException {
        if(LocalDate.now().isBefore(birthDate))
            throw new InvalidAgeException("the birthdate couldn't be after now :)");
        if((LocalDate.now().getYear()-birthDate.getYear())<13)
            throw new InvalidAgeException("the age must be greater than 13 ;) ");
    }
    /**
     *
     * @throws InvalidCharacterNumberException if the number is greater than 256 or the text is null
     */
    private void checkBiography(String text) throws InvalidCharacterNumberException{
        if (text.length() == 0 || text == null)
        {
            throw new InvalidCharacterNumberException("biography shouldn't be empty!");
        }
        else if (text.length() > 256)
        {
            throw new InvalidCharacterNumberException("The number of characters is invalid, it should be lower than 256 characters!");
        }
    }
}
