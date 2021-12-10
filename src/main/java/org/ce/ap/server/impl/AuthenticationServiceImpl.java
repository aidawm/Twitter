package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;
import main.java.org.ce.ap.server.exceptions.InvalidAgeException;
import main.java.org.ce.ap.server.exceptions.InvalidNameException;
import main.java.org.ce.ap.server.exceptions.InvalidPasswordException;
import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AuthenticationServiceImpl implements AuthenticationService{
    private User user;
    ///// a instance from SignUp or SignIn class
    private AuthenticationService authenticationService;
    private UserManager userManager =new UserManager();
    ///// user's information from console
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;

    /**
     * do signUp process
     */
    public void signUp(){
        getData();
//        password=getHash(password);
        authenticationService = new SignUp(firstName,  lastName,  username,  password,  birthDate);
        verify();
    }

    /**
     * do sign in process
     */
    public void signIn(){
        getData();
//        password=getHash(password);
        authenticationService = new SignIn(username,password);
    }

    /**
     * verify the information for authentication
     * @return
     */
    @Override
    public User verify() {
        boolean isValid = false;
        while (!isValid){
            try{
                user= authenticationService.verify();
                isValid=true;
                if(authenticationService instanceof SignUp)
                    userManager.addNewUser(user);
            }

            catch (Exception e){
                if(authenticationService instanceof SignIn){
                    signIn();
                }
                if(authenticationService instanceof  SignUp){
                    signUp();
                }
            }
        }
        return user;
    }

    /**
     * convert the password string to hash code
     * @param password the user's password
     * @return hash code
     */
    private String getHash(String password){
        return null;
    }

    private void updateSignIn(String username,String password){
        this.username=username;
        this.password=password;
    }
    private void updateSignUp(String firstName, String lastName, String username, String password, LocalDate birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
    }

    /**
     * get data from console
     */
    private void getData(){
        String data =Test.getData();
        String[] dataArray = data.split(" ");
        if(dataArray.length==2){
            this.username=dataArray[0];
            this.password=dataArray[2];
        }
        else if(dataArray.length==5){
            this.firstName=dataArray[0];
            this.lastName=dataArray[1];
            this.username=dataArray[2];
            this.password=dataArray[3];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
            this.birthDate= LocalDate.parse(dataArray[4], formatter);
        }
        else {
            System.out.println("enter valid input ^^");
        }
    }
}
