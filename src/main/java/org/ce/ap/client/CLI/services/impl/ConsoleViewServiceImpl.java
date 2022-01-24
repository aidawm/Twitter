package main.java.org.ce.ap.client.services.impl;

import main.java.org.ce.ap.client.services.ConsoleViewService;
import main.java.org.ce.ap.server.model.tweet.Tweet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

import main.java.org.ce.ap.*;

/**
 * The type Console view service.
 */
public class ConsoleViewServiceImpl implements ConsoleViewService {
    @Override
    public void welcome() {
        System.out.println("Hi :) welcome to our Twitter ");
        System.out.println("1 ) sign up");
        System.out.println("2 ) sign in");
        System.out.println("0 ) exit");
    }

    /**
     * Main menu.
     */
    @Override
    public void mainMenu() {
        System.out.println("1 ) manage tweets ");
        System.out.println("2 ) manage followers & followings ");
        System.out.println("0 ) exit");
    }

    /**
     * Manage tweets menu.
     */
    @Override
    public void manageTweetsMenu() {
        System.out.println("1 ) new tweet");
        System.out.println("2 ) remove a tweet");
        System.out.println("3 ) new retweet");
        System.out.println("4 ) new reply");
        System.out.println("5 ) remove a reply");
        System.out.println("6 ) like a tweet");
        System.out.println("7 ) unlike a tweet");
        System.out.println("8 ) show timeline");
        System.out.println("0 ) exit");
    }

    /**
     * Manage follows menu.
     */
    @Override
    public void manageFollowsMenu() {
        System.out.println("1 ) follow a user");
        System.out.println("2 ) unfollow a user");
        System.out.println("0 ) exit");
    }

    /**
     * Show timeline.
     *
     * @param tweets the tweets
     */
    @Override
    public void showTimeline(JSONArray tweets) {
        for (int i = 0; i < tweets.length(); i++) {
            System.out.println(showTweet((JSONObject) tweets.get(i)));
            System.out.println("----------------------------------------------------");

        }
    }

    /**
     * Show all users.
     *
     * @param users the users
     */
    @Override
    public void showUsers(JSONArray users) {
        for (int i = 0; i < users.length(); i++) {

            System.out.println(showUser((JSONObject) users.get(i)));
        }
    }

    /**
     * Process server response.
     *
     * @param response the response
     */
    @Override
    public void processServerResponse(ServiceWordsEnum serviceWordsEnum, JSONObject response) {
        switch (serviceWordsEnum) {
            case SIGNIN:
                if (!response.getBoolean("hasError"))
                    System.out.println("welcome! sign_in attempt is successful");
                else {
                    if (response.get("errorCode").equals("InvalidUsernameException"))
                        System.out.println("username is not exist");
                    else if (response.get("errorCode").equals("InvalidPasswordException"))
                        System.out.println("password is incorrect");
                }
                break;
            case SIGNUP:
                if (!response.getBoolean("hasError"))
                    System.out.println("welcome! sign_up attempt is successful");
                else {
                    if (response.get("errorCode").equals("InvalidDateException"))
                        System.out.println("the birthdate couldn't be after now :)");
                    else if (response.get("errorCode").equals("InvalidAgeException"))
                        System.out.println("the age must be greater than 13 ;) ");
                    else if (response.get("errorCode").equals("InvalidUsernameSizeException"))
                        System.out.println("the username must be greater than 4 and less than 15");
                    else if (response.get("errorCode").equals("InvalidUsernameCharactersException"))
                        System.out.println("the username must have A-Z , 0-9 character ");
                    else if (response.get("errorCode").equals("ExistingUsername"))
                        System.out.println("username is exist!");
                    else if (response.get("errorCode").equals("InvalidNameException"))
                        System.out.println("name only can be a string of alphabets!");
                }
                break;


            case TIMELINE:
                if (!response.getBoolean("hasError"))
                    System.out.println("timeline attempt is successful");
                else {
                    System.out.println("cant show timeline!");
                }
                break;
            case SHOW_MY_TWEETS:
                if (!response.getBoolean("hasError"))
                    System.out.println("show_my_tweets attempt is successful");
                else {
                    System.out.println("cant show show_my_tweets!");
                }
                break;

            case TWEET:
                if (!response.getBoolean("hasError"))
                    System.out.println("add new tweet attempt is successful");
                else {
                    System.out.println("the tweet text must be less than 256 character");
                }
                break;

            case REMOVETWEET:
                if (!response.getBoolean("hasError"))
                    System.out.println("remove tweet attempt is successful");
                else {
                    System.out.println("you havent access to delete this tweet!");
                }
                break;

            case RETWEET:
                if (!response.getBoolean("hasError"))
                    System.out.println("add new retweet attempt is successful");
                else {
                    System.out.println("the tweet text must be less than 256 character");
                }
                break;

//            case REMOVERETWEET:
//                if (!response.getBoolean("hasError"))
//                    System.out.println("welcome! remove retweet attempt is successful");
//                else {
//                    System.out.println("you havent access to delete this tweet!");
//                }
//                break;

            case LIKE:
                if (!response.getBoolean("hasError"))
                    System.out.println("like tweet attempt is successful");
                else {
                    System.out.println("like is failed");
                }
                break;

            case DISLIKE:
                if (!response.getBoolean("hasError"))
                    System.out.println(" dislike tweet attempt is successful");
                else {
                    System.out.println("dislike is failed");
                }
                break;
            case REPLY:
                if (!response.getBoolean("hasError"))
                    System.out.println(" reply tweet attempt is successful");
                else {
                    System.out.println("the tweet text must be less than 256 character");
                }
                break;

            case REMOVEREPLY:
                if (!response.getBoolean("hasError"))
                    System.out.println(" remove reply tweet attempt is successful");
                else {
                    System.out.println("you havent access to delete this reply!");
                }
                break;

            case FOLLOW:
                if (!response.getBoolean("hasError"))
                    System.out.println(" follow a user attempt is successful");
                else {
                    System.out.println("follow is failed");
                }
                break;
            case UNFOLLOW:
                if (!response.getBoolean("hasError"))
                    System.out.println(" un follow a user attempt is successful");
                else {
                    System.out.println("unfollow is failed");
                }
                break;
            case SHOW_USERS:
                if (!response.getBoolean("hasError"))
                    System.out.println(" show users list attempt is successful");
                else {
                    System.out.println("show users list is failed");
                }
                break;
            case SHOW_FOLLOWINGS:
                if (!response.getBoolean("hasError"))
                    System.out.println(" show following list attempt is successful");
                else {
                    System.out.println("show following list is failed");
                }
                break;

        }
    }

    private String showTweet(JSONObject tweet) {
        if(tweet.keySet().contains("retweetedTweet")){
            return showRetweet(tweet);
        }
        String author = "@" + (tweet.getString("author"));

        String text = tweet.getString("text");
        JSONArray retweets = (JSONArray) tweet.get("retweets");
        JSONArray likes = (JSONArray) tweet.get("likes");
        JSONArray replies = (JSONArray) tweet.get("replies");
        LocalDateTime sendDate = LocalDateTime.parse(tweet.getString("sendDate"));

        String str = author + " : \t" + text + "\n";
        str += "retweets: " + retweets.length() + "\t" + "likes: " + likes.length() + "\n";
        if (LocalDateTime.now().getDayOfYear() - sendDate.getDayOfYear() < 7) {
            str += sendDate.getDayOfWeek() + "\t" + sendDate.getHour() + ":" + sendDate.getMinute() + ":" + sendDate.getSecond() + "\n";
        } else {
            str += sendDate.getDayOfMonth() + " " + sendDate.getMonth() + "\t" + sendDate.getHour() + "\n";
        }

        if (replies.length() != 0) {
            str += "replies :\n";
        }
        for (int i = 0; i < replies.length(); i++) {
            str = str + "\t" + showTweet((JSONObject) replies.get(i));
        }

        return str;

    }

    private String showUser(JSONObject user) {
        String username, firstName, lastName;
        username = user.getString("username");
        firstName = user.getString("firstName");
        lastName = user.getString("lastName");

        return "@" + username + " (" + firstName + " " + lastName + ")";
    }
    private String showRetweet(JSONObject retweet){
        System.out.println(retweet);
        JSONObject retweetedTweet = (JSONObject) retweet.get("retweetedTweet");
        JSONObject newTweet = (JSONObject) retweet.get("newTweet");
        LocalDateTime sendDate = LocalDateTime.parse(newTweet.getString("sendDate"));
        JSONArray replies = (JSONArray) newTweet.get("replies");

        String str = newTweet.getString("author") + " : \t" + newTweet.getString("text") + "\n";

        str += "\t\t" + retweetedTweet.getString("author")  + " : \t" + retweetedTweet.getString("text")  + "\n";

        str += "retweets: " + ((JSONArray)newTweet.get("retweets")).length() + "\t" + "likes: " + ((JSONArray)newTweet.get("likes")).length()  + "\n";
        if (LocalDateTime.now().getDayOfYear() - sendDate.getDayOfYear() < 7) {
            str += sendDate.getDayOfWeek() + "\t" + sendDate.getHour() + ":" + sendDate.getMinute() + ":" + sendDate.getSecond() + "\n";
        } else {
            str += sendDate.getDayOfMonth() + " " + sendDate.getMonth() + "\t" + sendDate.getHour() + "\n";
        }
        if (replies.length() != 0) {
            str += "replies :\n";
        }
        for (int i=0;i<replies.length();i++){
            str = str + "\t" + showTweet((JSONObject) replies.get(i));
        }

        return str;
    }

}
