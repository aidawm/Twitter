package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.*;
import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    public void mainMenu() {
        System.out.println("1 ) manage tweets ");
        System.out.println("2 ) manage followers & followings ");
        System.out.println("0 ) exit");
    }

    /**
     * Manage tweets menu.
     */
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

    public void manageFollowsMenu() {
        System.out.println("1 ) follow a user");
        System.out.println("2 ) unfollow a user");
    }

    /**
     * Show timeline.
     *
     * @param tweets the tweets
     */
    public void showTimeline(JSONArray tweets) {
        ArrayList<Tweet> tweetArrayList = new ArrayList<>();
        for (int i = 0; i < tweets.length(); i++) {
            tweetArrayList.add((Tweet) tweets.get(i));
        }
        for (int i = 0; i < tweetArrayList.size(); i++) {
            System.out.println(i + 1 + " : \n" + tweetArrayList.get(i).toString());
        }
    }

    public void showFollowings(JSONArray followings) {
        ArrayList<User> followingArrayList = new ArrayList<>();
        for (int i = 0; i < followings.length(); i++) {
            followingArrayList.add((User) followings.get(i));
        }
        for (int i = 0; i < followingArrayList.size(); i++) {
            System.out.println(i + 1 + " : \n" + followingArrayList.get(i).toString());
        }
    }

    public void showAllUsers(JSONArray users)
    {
        ArrayList<User> userArrayList = new ArrayList<>();
        for (int i = 0; i < users.length(); i++) {
            userArrayList.add((User) users.get(i));
        }
        for (int i = 0; i < userArrayList.size(); i++) {
            System.out.println(i + 1 + " : \n" + userArrayList.get(i).toString());
        }
    }

    /**
     * Process server response.
     *
     * @param response the response
     */
    public void processServerResponse(JSONObject response) {

    }
}
