package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AuthenticationServiceImpl implements AuthenticationService{
    private User user;
    ///// an instance from SignUp or SignIn class
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
    public User signUp() throws NoSuchAlgorithmException {
        getData(1);
        password=getHash(password);
        authenticationService = new SignUp(firstName,  lastName,  username,  password,  birthDate);
        return verify();
    }

    /**
     * do sign in process
     */
    public User signIn() throws NoSuchAlgorithmException {
        getData(2);
        password=getHash(password);
        authenticationService = new SignIn(username,password);
        return verify();
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
                    System.out.println(e.getMessage());
                    try {
                        signIn();
                    }
                    catch (NoSuchAlgorithmException ex){
                        System.out.println(ex.getMessage());
                    }
                }

                if(authenticationService instanceof  SignUp){
                    try {
                        signUp();
                    }
                    catch (NoSuchAlgorithmException ex){
                        System.out.println(ex.getMessage());
                    }
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
    private String getHash(String password) throws NoSuchAlgorithmException {

        return ToHexString.toHexString(ToHexString.getSHA(password));
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
    public void getData(int state){
        Scanner scanner = new Scanner(System.in);

        if(state == 2){
            System.out.println("pls enter your username : ");
            this.username=scanner.nextLine();
            System.out.println("pls enter your password : ");
            this.password=scanner.nextLine();
        }
        else if(state == 1){
            System.out.println("pls enter your firstname : ");
            this.firstName=scanner.nextLine();
            System.out.println("pls enter your lastname : ");
            this.lastName=scanner.nextLine();
            System.out.println("pls enter your username : ");
            this.username=scanner.nextLine();
            System.out.println("pls enter your password : ");
            this.password=scanner.nextLine();
            System.out.println("pls enter your birthdate(dd/MM/yyyy) : ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.birthDate= LocalDate.parse(scanner.nextLine(), formatter);
        }
        else {
            System.out.println("enter valid input ^^");
        }
    }
}
