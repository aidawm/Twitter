package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.impl.*;

import java.time.LocalDate;
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

    public void test_tweeting() {
        User user1 = new User("aida","mobli","aidawm","asdf", LocalDate.of(2001,3,21));
        User user2 = new User("sara","rouhani","sa9978","qwer",LocalDate.of(1999,12,20));
        TweetingServiceImpl tweetingService=new TweetingServiceImpl();
        tweetingService.addNewTweet(user1,"Hello world");
        tweetingService.retweet(tweetingService.tweet,user2,"hi^^");


    }

}
