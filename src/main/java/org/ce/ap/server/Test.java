package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;

import java.util.Scanner;

public class Test {
    public void test_Authentication(){
        AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
        printAuthentication();
        authenticationService.signUp();

//        authenticationService.signIn();

    }
    private void printAuthentication(){
        System.out.println("for sign up enter the info in this format:");
        System.out.println("\t firstName lastName username password birthDate(dd/MM/yyyy)");
        System.out.println("for sign in enter the info in this format:");
        System.out.println("\t username password");
    }
    public static String getData(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
