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
     * @param firstName is using for set firstName
     * @param lastName is using for set lastName
     * @param username is using for set username
     * @param password is using for set password
     * @param birthDate is using for set birthDate
     */
    public User(String firstName, String lastName, String username, String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.registryDate = LocalDate.now();

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRegistryDate() {
        return registryDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
