package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;
import main.java.org.ce.ap.server.exceptions.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

/**
 * The type Authentication service.
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    private User user;
    private static boolean state=false;
    ///// an instance from SignUp or SignIn class
    private  AuthenticationService authenticationService;
    private UserManager userManager;
    ///// user's information from console
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate birthDate;

    /**
     * Instantiates a new Authentication service.
     */
    public AuthenticationServiceImpl() {
        this.userManager = UserManager.getInstance();
    }

    /**
     * do signUp process
     *
     * @return the user
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public User signUp(String firstName, String lastName, String username, String password, LocalDate birthDate) throws NoSuchAlgorithmException, InvalidUsernameException, InvalidPasswordException, SignUpExceptions {

//        getData(1);
        password = getHash(password);
        authenticationService = new SignUp(firstName, lastName, username, password, birthDate);
        verify();
        return user;
    }

    /**
     * do sign in process
     *
     * @return the user
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public User signIn(String username, String password) throws NoSuchAlgorithmException, InvalidUsernameException, InvalidPasswordException, SignUpExceptions {
//        getData(2);
        password = getHash(password);
        authenticationService = new SignIn(username, password);
        verify();
        return user;
    }

    /**
     * verify the information for authentication
     *
     * @return
     */
    @Override
    public User verify() throws InvalidUsernameException, InvalidPasswordException, SignUpExceptions {
        user = authenticationService.verify();
        if (authenticationService instanceof SignUp)
            userManager.addNewUser(user);

//        while (!state) {
//            try {
//                user = authenticationService.verify();
//                if (authenticationService instanceof SignUp)
//                    userManager.addNewUser(user);
//                state=true;
//            } catch (Exception e) {
//                if (authenticationService instanceof SignIn) {
//                    System.out.println(e.getMessage());
//                    try {
//                        signIn();
//                    } catch (NoSuchAlgorithmException ex) {
//                        System.out.println(ex.getMessage());
//                    }
//                }
//
//                if (authenticationService instanceof SignUp) {
//                    System.out.println("sign up exception");
//                    try {
//                        signUp();
//                    } catch (NoSuchAlgorithmException ex) {
//                        System.out.println(ex.getMessage());
//                    }
//                }
//            }
//        }
        return user;
    }

    /**
     * convert the password string to hash code
     *
     * @param password the user's password
     * @return hash code
     */
    private String getHash(String password) throws NoSuchAlgorithmException {

        return ToHexString.toHexString(ToHexString.getSHA(password));
    }

    private void updateSignIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void updateSignUp(String firstName, String lastName, String username, String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
    }

//    /**
//     * get data from console
//     *
//     * @param state the state
//     */
//    public void getData(int state) {
//        Scanner scanner = new Scanner(System.in);
//
//        if (state == 2) {
//            System.out.println("pls enter your username : ");
//            this.username = scanner.nextLine();
//            System.out.println("pls enter your password : ");
//            this.password = scanner.nextLine();
//        } else if (state == 1) {
//            System.out.println("pls enter your firstname : ");
//            this.firstName = scanner.nextLine();
//            System.out.println("pls enter your lastname : ");
//            this.lastName = scanner.nextLine();
//            System.out.println("pls enter your username : ");
//            this.username = scanner.nextLine();
//            System.out.println("pls enter your password : ");
//            this.password = scanner.nextLine();
//            System.out.println("pls enter your birthdate(dd/MM/yyyy) : ");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            this.birthDate = LocalDate.parse(scanner.nextLine(), formatter);
//        } else {
//            System.out.println("enter valid input ^^");
//        }
//    }
}
