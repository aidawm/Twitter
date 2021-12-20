package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.exceptions.*;
import main.java.org.ce.ap.server.*;

import java.time.LocalDate;
import java.util.Locale;

public class SignUp implements AuthenticationService{
    private UserManager userManager;
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
        this.userManager=UserManager.getInstance();
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
    private String checkAge(LocalDate birthDate) {
        if(LocalDate.now().isBefore(birthDate))
            return "the birthdate couldn't be after now :)";
        if((LocalDate.now().getYear()-birthDate.getYear())<13)
            return "the age must be greater than 13 ;) ";
        return null;
    }

    /**
     * check the username be valid
     * @param username the user's username
     * @throws InvalidUsernameException
     */
    private String checkUsername(String username) {
        if(username.length()<4 || username.length()>15)
            return "the username must be greater than 4 and less than 15";
        username=username.toLowerCase(Locale.ROOT);
        for (char c : username.toCharArray()) {
            if (!((c >= 'a' && c <= 'z') || c == '_' || (c >= '0' && c <= '9')))
                return "the username must have A-Z , 0-9 character ";
        }
        try {
            userManager.isNotUsernameExist(username);
        }catch (Exception e){
            return "username is exist!";
        }

        return null;
    }
    /**
     * @param text should be valid
     * @throws InvalidNameException if the text isn't valid
     */
    private String checkName(String text) {
        for (char c : text.toCharArray()) {
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z')) {
                return "name only can be a string of alphabets!";
            }
        }
        return null;
    }

    /**
     *
     * @return a new user
     */
    public User verify() throws IllegalStateException{
        Boolean isExeption=false;
        String str;
        if((str=checkName(firstName))!=null){
            System.out.println(str);
            isExeption=true;
        }
        if((str=checkName(lastName))!=null){
            System.out.println(str);
            isExeption=true;
        }
        if((str=checkUsername(username))!=null){
            System.out.println(str);
            isExeption=true;
        }
        if((str=checkAge(birthDate))!=null){
            System.out.println(str);
            isExeption=true;
        }
        if(isExeption)
            throw new IllegalStateException("signUp exception");
        User user =new User (firstName, lastName, username, password, birthDate);
        return user;
    }

}
