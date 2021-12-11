package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.impl.*;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * for test the methods and classes in phase1
 */
public class Test {
    /**
     * test authentication service
     */
    public void test_Authentication(){
        AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
        printAuthentication();
        UserManager userManager = new UserManager();
        User user1 = new User("aida","mobli","aidawm","asdf", LocalDate.of(2001,3,21));
        User user2 = new User("sara","rouhani","sa9978","qwer",LocalDate.of(1999,12,20));
        User user3 = new User("sara","rouhani","qwerty","qwer",LocalDate.of(1999,12,20));
        userManager.addNewUser(user1);
        userManager.addNewUser(user2);
        userManager.addNewUser(user3);

        authenticationService.signIn();

//        authenticationService.signIn();
    }

    /**
     * print a format of inputs
     */
    private void printAuthentication(){
        System.out.println("for sign up enter the info in this format:");
        System.out.println("\t firstName lastName username password birthDate(dd/MM/yyyy)");
        System.out.println("for sign in enter the info in this format:");
        System.out.println("\t username password");
    }

    /**
     * get inputs from the user
     * @return string of the inputs
     */
    public static String getData(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * test the tweeting service
     */
    public void test_tweeting() {
        User user1 = new User("aida","mobli","aidawm","asdf", LocalDate.of(2001,3,21));
        User user2 = new User("sara","rouhani","sa9978","qwer",LocalDate.of(1999,12,20));
        TweetingServiceImpl tweetingService=new TweetingServiceImpl();
        tweetingService.addNewTweet(user1,"Hello world");
//        tweetingService.retweet(tweetingService.tweet,user2,"hi^^");

    }
    public void test_timeline() throws InvalidCharacterNumberException {
        UserManager userManager = new UserManager();
        User user1 = new User("aida","mobli","aidawm","asdf", LocalDate.of(2001,3,21));
        User user2 = new User("sara","rouhani","sa9978","qwer",LocalDate.of(1999,12,20));
        User user3 = new User("sara","rouhani","qwerty","qwer",LocalDate.of(1999,12,20));
        userManager.addNewUser(user1);
        userManager.addNewUser(user2);
        userManager.addNewUser(user3);

        UserAccount userAccount1 = new UserAccount(user1);
        UserAccount userAccount2 = new UserAccount(user2);
        UserAccount userAccount3 = new UserAccount(user3);

        userAccount1.addFollowing(user2);
        userAccount1.addFollowing(user3);
        userAccount2.addFollowing(user1);
        userAccount2.addFollowing(user3);

        userAccount1.addNewTweet("Hello world");
        userAccount3.addNewTweet("HIIIIIIIIIIIIIII^^");
        userAccount2.addNewTweet("bye :)");
        System.out.println("///////////////////////////////timeline user 2");
        userAccount2.showTimeline();
        System.out.println("///////////////////////////////timeline user 1");
        userAccount1.showTimeline();
    }

}
