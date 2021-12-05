package main.java.org.ce.ap.server;

import java.time.LocalDate;

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
     */
    public User(String firstName, String lastName, String username, String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.registryDate = LocalDate.now();

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
    public void setBiography(String biography) {
        this.biography = biography;
    }
}
