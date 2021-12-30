package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.*;
import main.java.org.ce.ap.*;
import main.java.org.ce.ap.client.exception.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/**
 * The type Command parser service.
 */
public class CommandParserServiceImpl implements CommandParserService {
    private final ConsoleViewServiceImpl consoleViewService;
    private ConnectionServiceImpl connectionService;

    /**
     * Instantiates a new Command parser service.
     *
     * @param client the client
     * @throws IOException the io exception
     */
    public CommandParserServiceImpl(Socket client) throws IOException {
        this.consoleViewService = new ConsoleViewServiceImpl();
        this.connectionService = new ConnectionServiceImpl(client);
    }

    /**
     * Run.
     *
     * @throws IOException the io exception
     */
    public void run() throws IOException {
        boolean exit = false;
        while (!exit) {
            consoleViewService.welcome();
            ServiceWordsEnum command = authentication();
            System.out.println(command);
            if (command.equals(ServiceWordsEnum.SIGNIN)) {
                signIn();
            } else if (command.equals(ServiceWordsEnum.SIGNUP)) {
                signUp();
            } else
                break;
            mainMenu();
        }
    }


    /**
     * Authentication service words enum.
     *
     * @return the service words enum
     */
    public ServiceWordsEnum authentication() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            int index;
            try {
                index = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("please enter a index!");
                continue;
            }
            if (index == 1)
                return ServiceWordsEnum.SIGNUP;
            if (index == 2)
                return ServiceWordsEnum.SIGNIN;
            if (index == 0)
                return ServiceWordsEnum.EXIT;
            else
                System.out.println("please enter a valid index");
        }
    }

    /**
     * Sign in.
     *
     * @throws IOException the io exception
     */
    public void signIn() throws IOException {
        String username, password;
        boolean isValid = false;
        while (!isValid) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("pls enter your username : ");
            username = scanner.nextLine();
            System.out.println();
            System.out.print("pls enter your password : ");
            password = scanner.nextLine();
            System.out.println();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            JSONObject request = new JSONObject();
            request.put("method", ServiceWordsEnum.SIGNIN);
            request.put("parameterValues", jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * Sign up.
     *
     * @throws IOException the io exception
     */
    public void signUp() throws IOException {
        String username, password, firstName, lastName, birthDateStr;
        LocalDate birthDate;
        boolean isValid = false;
        while (!isValid) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("pls enter your firstname : ");
            firstName = scanner.nextLine();
            System.out.println("pls enter your lastname : ");
            lastName = scanner.nextLine();
            System.out.println("pls enter your username : ");
            username = scanner.nextLine();
            System.out.println("pls enter your password : ");
            password = scanner.nextLine();
            System.out.println("pls enter your birthdate(dd/MM/yyyy) : ");
            birthDateStr = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                birthDate = LocalDate.parse(birthDateStr, formatter);
            } catch (Exception e) {
                System.out.println("please enter your birthdate in valid format (dd/MM/yyyy)");
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("birthDate", birthDate);

            JSONObject request = new JSONObject();
            request.put("method", ServiceWordsEnum.SIGNUP);
            request.put("parameterValues", jsonObject);
            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * the main menu analyzer
     *
     * @throws IOException
     */
    private void mainMenu() throws IOException {
        boolean isExit = false;
        while (!isExit) {
            consoleViewService.mainMenu();
            ServiceWordsEnum command = mainMenuCommandAnalyzer();
            if (command.equals(ServiceWordsEnum.MANAGE_TWEETS)) {
                consoleViewService.manageTweetsMenu();
                manageTweets();
            } else if (command.equals(ServiceWordsEnum.MANAGE_FOLLOWS)) {
                consoleViewService.manageFollowsMenu();
                manageFollows();
            } else
                break;
        }
    }

    /**
     * @return an enum that cast from an integer command
     */
    private ServiceWordsEnum mainMenuCommandAnalyzer() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            int index;
            try {
                index = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("please enter a index!");
                continue;
            }
            if (index == 1)
                return ServiceWordsEnum.MANAGE_TWEETS;
            if (index == 2)
                return ServiceWordsEnum.MANAGE_FOLLOWS;
            if (index == 0)
                return ServiceWordsEnum.EXIT;
            System.out.println("enter a valid index");
        }
    }

    /**
     * this method is using for manage the tweets commands
     *
     * @throws IOException
     */
    private void manageTweets() throws IOException {
        boolean isExit = false;
        while (!isExit) {  //finish the managetweet's loop when one command done successfully
            consoleViewService.mainMenu();
            ServiceWordsEnum command = manageTweetsCommandAnalyzer();
            if (command.equals(ServiceWordsEnum.TWEET)) {
                tweetCommand();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.REMOVETWEET)) {
                removeTweetCommand();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.RETWEET)) {
                retweetCommand();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.REPLY)) {
                replyCommand();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.REMOVEREPLY)) {
                removeReplyCommand();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.LIKE)) {
                likeCommand();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.DISLIKE)) {
                unlikeCommand();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.TIMELINE)) {
                showTimeLineTweets();
                isExit = true;
            } else if (command.equals(ServiceWordsEnum.EXIT))
                isExit = true;
        }
    }

    /**
     * @return an enum that cast from an integer command
     */
    private ServiceWordsEnum manageTweetsCommandAnalyzer() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            int index;
            try {
                index = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("please enter a index!");
                continue;
            }
            if (index == 1)
                return ServiceWordsEnum.TWEET;
            if (index == 2)
                return ServiceWordsEnum.REMOVETWEET;
            if (index == 3)
                return ServiceWordsEnum.RETWEET;
            if (index == 4)
                return ServiceWordsEnum.REPLY;
            if (index == 5)
                return ServiceWordsEnum.REMOVEREPLY;
            if (index == 6)
                return ServiceWordsEnum.LIKE;
            if (index == 7)
                return ServiceWordsEnum.DISLIKE;
            if (index == 8)
                return ServiceWordsEnum.TIMELINE;
            if (index == 0)
                return ServiceWordsEnum.EXIT;
            System.out.println("enter a valid index");
        }
    }

    /**
     * @return a JsonArray that contains Timeline's tweets
     * @throws IOException
     */
    private JSONArray showTimeLineTweets() throws IOException {
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.TIMELINE);
        request.put("parameterValues",new JSONObject());
        JSONObject response = connectionService.request(request);
        JSONArray tweets = (JSONArray) response.get("result");
        consoleViewService.showTimeline(tweets);
        return tweets;
    }

    /**
     * @param serviceWordsEnum is the given command
     * @param jsonObject       is the request
     * @return
     */
    private JSONObject makeRequest(ServiceWordsEnum serviceWordsEnum, JSONObject jsonObject) {
        JSONObject request = new JSONObject();
        request.put("method", serviceWordsEnum);
        request.put("parameterValues", jsonObject);
        return request;
    }

    /**
     * tweet command
     *
     * @throws IOException
     */
    private void tweetCommand() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {
            System.out.println("pls enter the text :");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", scanner.nextLine());

            JSONObject request = makeRequest(ServiceWordsEnum.TWEET, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * remove tweet command
     *
     * @throws IOException
     */
    private void removeTweetCommand() throws IOException {
        JSONArray tweets = showTimeLineTweets();
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {
            System.out.println("pls enter the number of tweet to remove it :");
            int tweetNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tweet", tweets.get(tweetNum - 1));

            JSONObject request = makeRequest(ServiceWordsEnum.REMOVETWEET, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * retweet command
     *
     * @throws IOException
     */
    private void retweetCommand() throws IOException {
        Scanner scanner = new Scanner(System.in);
        JSONArray tweets = showTimeLineTweets();

        boolean isValid = false;
        while (!isValid) {
            System.out.println("pls enter the number of tweet to retweet it :");
            int tweetNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tweet", tweets.get(tweetNum - 1));
            System.out.println("pls enter the text :");
            jsonObject.put("text", scanner.next());

            JSONObject request = makeRequest(ServiceWordsEnum.TWEET, jsonObject);

            JSONObject responseTweet = connectionService.request(request);
            consoleViewService.processServerResponse(responseTweet);
            if (!responseTweet.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * reply command
     *
     * @throws IOException
     */
    private void replyCommand() throws IOException {
        JSONArray tweets = showTimeLineTweets();

        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {

            System.out.println("pls enter the number of tweet to reply it :");
            int tweetNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tweet", tweets.get(tweetNum - 1));
            System.out.println("pls enter the text :");
            jsonObject.put("text", scanner.next());


            JSONObject request = makeRequest(ServiceWordsEnum.REPLY, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }

    }

    /**
     * remove reply command
     *
     * @throws IOException
     */
    private void removeReplyCommand() throws IOException {
        JSONArray tweets = showTimeLineTweets();

        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {

            System.out.println("pls enter the number of tweet to remove reply from it :");
            int tweetNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tweet", tweets.get(tweetNum - 1));

            JSONObject request = makeRequest(ServiceWordsEnum.REMOVEREPLY, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * like command
     *
     * @throws IOException
     */
    private void likeCommand() throws IOException {
        JSONArray tweets = showTimeLineTweets();

        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {

            System.out.println("pls enter the number of tweet to like it :");
            int tweetNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tweet", tweets.get(tweetNum - 1));

            JSONObject request = makeRequest(ServiceWordsEnum.LIKE, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * unlike command
     *
     * @throws IOException
     */
    private void unlikeCommand() throws IOException {
        JSONArray tweets = showTimeLineTweets();

        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {

            System.out.println("pls enter the number of tweet to unlike it :");
            int tweetNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tweet", tweets.get(tweetNum - 1));

            JSONObject request = makeRequest(ServiceWordsEnum.DISLIKE, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * @return a enum that cast from an integer command
     */
    private ServiceWordsEnum manageFollowsCommandAnalyzer() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            int index;
            try {
                index = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("please enter a index!");
                continue;
            }
            if (index == 1)
                return ServiceWordsEnum.FOLLOW;
            if (index == 2)
                return ServiceWordsEnum.UNFOLLOW;
        }
    }

    /**
     * @return a jsonArray that contains all users
     * @throws IOException
     */
    private JSONArray showAllUsers() throws IOException {
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.SHOW_USERS);

        JSONObject response = connectionService.request(request);
        JSONArray users = (JSONArray) response.get("users");

        consoleViewService.showAllUsers(users);
        return users;
    }

    /**
     * @return a jsonArray that contains followings
     * @throws IOException
     */
    private JSONArray showFollowings() throws IOException {
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.SHOW_FOLLOWINGS);

        JSONObject response = connectionService.request(request);
        JSONArray followings = (JSONArray) response.get("followings");

        consoleViewService.showAllUsers(followings);
        return followings;
    }

    /**
     * this method is using for manage the followings commands
     *
     * @throws IOException
     */
    private void manageFollows() throws IOException {
        boolean isExit = false;
        while (!isExit) {  //finish the managetweet's loop when one command done successfully
            consoleViewService.mainMenu();
            ServiceWordsEnum command = manageFollowsCommandAnalyzer();
            if (command.equals(ServiceWordsEnum.FOLLOW)) {
                followCommand();
                isExit = true;
            }
            if (command.equals(ServiceWordsEnum.UNFOLLOW)) {
                unfollowCommand();
                isExit = true;
            }
        }
    }

    /**
     * this method helps us to select a user between all the users, send a jsonObject to server that contains
     * the given user and finally follow it
     *
     * @throws IOException
     */
    private void followCommand() throws IOException {
        JSONArray users = showAllUsers();
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {

            System.out.println("pls enter the number of user to follow it :");
            int userNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", users.get(userNum - 1));

            JSONObject request = makeRequest(ServiceWordsEnum.FOLLOW, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }

    /**
     * this method helps us to select a user between following, send a jsonObject to server that contains
     * the given user and finally unfollow it
     *
     * @throws IOException
     */
    private void unfollowCommand() throws IOException {
        JSONArray followings = showFollowings();
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while (!isValid) {

            System.out.println("pls enter the number of user to unfollow it :");
            int userNum = scanner.nextInt();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", followings.get(userNum - 1));

            JSONObject request = makeRequest(ServiceWordsEnum.UNFOLLOW, jsonObject);

            JSONObject response = connectionService.request(request);
            consoleViewService.processServerResponse(response);
            if (!response.getBoolean("hasError"))
                isValid = true;
        }
    }
}




