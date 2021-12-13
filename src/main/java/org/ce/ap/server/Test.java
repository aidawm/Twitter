package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.impl.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * for test the methods and classes in phase1
 */
public class Test {
    private static UserAccount userAccount;
    private static UserManager userManager = new UserManager();


    /**
     * print a format of inputs
     */
    private static void printFirstMenu() {
        System.out.println("1 ) sign up");
        System.out.println("2 ) sign in");
        System.out.println("0 ) exit");
    }

    private static void signInMenu() {
        System.out.println("1 ) manage tweets ");
        System.out.println("2 ) manage followers & followings ");
        System.out.println("0 ) exit");

    }

    private static void manageTweetsMenu() {
        userAccount.showTimeline();
        System.out.println("1 ) new tweet");
        System.out.println("2 ) new retweet");
        System.out.println("3 ) new reply");
        System.out.println("4 ) like a tweet");
        System.out.println("0 ) exit");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int input = scanner.nextInt();
            if (input == 1) {
                System.out.println("pls enter the text :");
                try {
                    userAccount.addNewTweet(scanner.nextLine());
                } catch (InvalidCharacterNumberException e) {
                    System.out.println(e.getMessage());
                }
            } else if (input == 2) {
                userAccount.showTimeline();
                System.out.println("pls enter the number of tweet that you want to retweet it : ");
                int tweetNumber = scanner.nextInt();
                System.out.println("pls enter the text : ");
                String text = scanner.nextLine();
                try {
                    userAccount.retweet(userAccount.getTweets().get(tweetNumber - 1), text);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (input == 3) {
                userAccount.showTimeline();
                System.out.println("pls enter the number of tweet that you want to reply it : ");
                int tweetNumber = scanner.nextInt();
                System.out.println("pls enter the text : ");
                String text = scanner.nextLine();
                try {
                    userAccount.reply(userAccount.getTweets().get(tweetNumber - 1), new Tweet(userAccount.getUser(), text));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (input == 4) {
                userAccount.showTimeline();
                System.out.println("pls enter the number of tweet that you want to like it : ");
                int tweetNumber = scanner.nextInt();
                try {
                    userAccount.like(userAccount.getTweets().get(tweetNumber));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            else if(input==0)
                break;
        }

    }

    private static void manageFollowers() {
        System.out.println("1 ) follow a user");
        System.out.println("2 ) unfollow a user");
        System.out.println("0 ) exit");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int input = scanner.nextInt();
            if (input == 1) {
                printUser(userManager.getUsers().get(0));
                printUser(userManager.getUsers().get(1));
                printUser(userManager.getUsers().get(2));
                System.out.println("pls enter the number of user : ");
                int userNumber = scanner.nextInt();
                userAccount.addFollowing(userManager.getUsers().get(userNumber));
            }
            if(input==0){
                break;
            }

        }
    }

    public static void defaultUsers() throws InvalidCharacterNumberException, NoSuchAlgorithmException {
        User user1 = new User("aida", "mobli", "aidawm", ToHexString.toHexString(ToHexString.getSHA("asdf")), LocalDate.of(2001, 3, 21));
        User user2 = new User("sara", "rouhani", "sa9978", ToHexString.toHexString(ToHexString.getSHA("qwer")), LocalDate.of(1999, 12, 20));
        User user3 = new User("sara", "rouhani", "qwerty", ToHexString.toHexString(ToHexString.getSHA("qwer")), LocalDate.of(1999, 12, 20));
        userManager.addNewUser(user1);
        userManager.addNewUser(user2);
        userManager.addNewUser(user3);
        UserAccount userAccount1 = new UserAccount(user1);
        UserAccount userAccount2 = new UserAccount(user2);
        UserAccount userAccount3 = new UserAccount(user3);
        userAccount1.addNewTweet("Hello world");
        userAccount3.addNewTweet("HIIIIIIIIIIIIIII^^");
        userAccount2.addNewTweet("bye :)");
    }

    public static void printUser(User user) {
        System.out.println(user.getFirstName() + " " + user.getLastName() + "\t@" + user.getUsername());
    }


    public void test_timeline() throws InvalidCharacterNumberException, InterruptedException {
        UserManager userManager = new UserManager();
        User user1 = new User("aida", "1", "aidawm", "asdf", LocalDate.of(2001, 3, 21));
        User user2 = new User("sara", "2", "sa9978", "qwer", LocalDate.of(1999, 12, 20));
        User user3 = new User("sara", "3", "qwerty", "qwer", LocalDate.of(1999, 12, 20));
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
        Thread.sleep(1000);
        userAccount3.addNewTweet("HIIIIIIIIIIIIIII^^");
        Thread.sleep(1000);
        userAccount2.addNewTweet("bye :)");
        Thread.sleep(1000);

        userAccount1.retweet(userAccount1.getTweets().get(2), "hello world retweet");
        Thread.sleep(1000);
        userAccount1.like(userAccount1.getTweets().get(2));
        userAccount1.unLike(userAccount1.getTweets().get(2));

//        Tweet reply1 = new Tweet(user3, "bye reply");
//        userAccount3.reply(userAccount2.getTweets().get(2), reply1);

        System.out.println("///////////////////////////////timeline user 1");
        userAccount1.showTimeline();

        userAccount1.removeTweet(userAccount1.getTweets().get(2));

//        System.out.println("///////////////////////////////timeline user 2");
//        userAccount2.showTimeline();
        System.out.println("///////////////////////////////timeline user 1");
        userAccount1.showTimeline();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidCharacterNumberException {
        boolean isExit =false;
        defaultUsers();
        AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
        Scanner scanner = new Scanner(System.in);
        int state;
        while (true) {
            while (true) {
                printFirstMenu();
                state = scanner.nextInt();
                if (state == 1) {
                    userAccount = new UserAccount(authenticationService.signUp());
                    userAccount = new UserAccount(authenticationService.signIn());
                    break;
                } else if (state == 2) {
                    userAccount = new UserAccount(authenticationService.signIn());
                    break;
                }
                else if(state==0){
                    isExit=true;
                    break;
                }else {
                    System.out.println("invalid input?");
                }
            }
            if(isExit)
                break;
            while (true) {

                signInMenu();
                scanner = new Scanner(System.in);
                int signinInput = scanner.nextInt();
                if (signinInput == 1) {
                    manageTweetsMenu();
                } else if (signinInput == 2) {
                    manageFollowers();
                }
                else if(signinInput ==0){
                    break;
                }else {
                    System.out.println("invalid input!!!!!!!!!!!!!!!");
                }
            }
        }


    }

}
