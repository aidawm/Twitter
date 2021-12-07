package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.User;
import main.java.org.ce.ap.server.AuthenticationService;
import main.java.org.ce.ap.server.SignIn;
import main.java.org.ce.ap.server.SignUp;

import java.time.LocalDate;

public class AuthenticationServiceImpl implements AuthenticationService{
    private User user;
    private AuthenticationService authenticationService;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;

    public void signUp(){
        password=getHash(password);
        authenticationService = new SignUp(firstName,  lastName,  username,  password,  birthDate);
    }
    public void signIn(){
        password=getHash(password);
        authenticationService = new SignIn(username,password);
    }

    @Override
    public User verify() {
        boolean isValid = false;
        while (!isValid){
            try{
                user= authenticationService.verify();
            }
            catch (Exception e){
                getData();
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
    private void getData(){

    }
}
