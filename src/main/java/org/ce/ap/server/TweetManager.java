package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.*;
import main.java.org.ce.ap.server.DataBase.TweetDataBase;

import main.java.org.ce.ap.server.model.Retweet;
import main.java.org.ce.ap.server.model.Tweet;
import main.java.org.ce.ap.server.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.*;


/**
 * The type Tweet manager.
 */
public class TweetManager extends Publisher implements Subscriber {
    private UserManager userManager;
    private static TweetManager instance;
    private TweetDataBase database;
    private static HashMap<Long, Tweet> tweets;
    private static HashMap<String, HashSet<Tweet>> userToTweets;


    private TweetManager() {
        userManager = UserManager.getInstance();
        database = new TweetDataBase();
        tweets = new HashMap<>();
        userToTweets = new HashMap<>();
    }

    /**
     * Get instance tweet manager.
     *
     * @return the tweet manager
     */
    public static TweetManager getInstance() {
        if (instance == null)
            instance = new TweetManager();
        System.out.println(tweets.size());
        return instance;
    }

    /**
     * get data from database
     *
     * @param user the user
     * @return the data from database
     * @throws InvalidUsernameException the invalid username exception
     */
    public ArrayList<Tweet> getDataFromDatabase(User user) throws InvalidUsernameException {
        ArrayList<Tweet> tweetArrayList = new ArrayList<>();
        HashMap<Long, JSONObject> tweetJsonList = database.getDirectoryFiles(user);
        System.out.println(tweetJsonList.size());
        for (Long tweetID : tweetJsonList.keySet()) {
            if (tweets.containsKey(tweetID))
                continue;
            JSONObject tweet = tweetJsonList.get(tweetID);
            if (tweet.keySet().contains("retweetedTweet") && tweet.keySet().contains("newTweet")) {
                JSONObject retweetedTweetJson = (JSONObject) tweet.get("retweetedTweet");
                long retweetedID = retweetedTweetJson.getLong("id");

                if (tweets.containsKey(retweetedID)) {
                    Retweet retweet = new Retweet(tweet, getAuthor(((JSONObject) tweet.get("retweetedTweet")).getString("author")), tweets.get(retweetedID));
                    tweets.put(tweetID, retweet);
                    addToUserToTweets(retweet);
                } else {
                    Tweet retweetedTweet = new Tweet(retweetedTweetJson, getAuthor(((JSONObject) tweet.get("retweetedTweet")).getString("author")));
                    tweets.put(retweetedTweet.getId(), retweetedTweet);
                    addToUserToTweets(retweetedTweet);
                    Retweet retweet1 = new Retweet(tweet, getAuthor(((JSONObject) tweet.get("retweetedTweet")).getString("author")), retweetedTweet);
                    tweets.put(tweetID, retweet1);
                    addToUserToTweets(retweet1);
                    addReplies(retweetedTweetJson);
                    addLikes(retweetedTweetJson);
                }
            } else {
                Tweet tweet1 = new Tweet(tweet, getAuthor(tweet.getString("author")));
                tweets.put(tweetID, tweet1);
                addToUserToTweets(tweet1);
            }
            addReplies(tweet);
            addLikes(tweet);
        }
        System.out.println("FOLLOWINGS");
        System.out.println((user.getFollowings()));
        for (User us : user.getFollowings()) {
            if (userToTweets.get(us.getUsername()) == null) {
                continue;
            }
            tweetArrayList.addAll(userToTweets.get(us.getUsername()));
        }

        return tweetArrayList;
    }

    private void addReplies(JSONObject tweet) throws InvalidUsernameException {
        JSONArray jsonArray;
        if (tweet.keySet().contains("retweetedTweet")) {
            jsonArray = (JSONArray) ((JSONObject) tweet.get("retweetedTweet")).get("replies");
        } else {
            jsonArray = (JSONArray) tweet.get("replies");
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject reply = (JSONObject) jsonArray.get(i);
            tweets.get(tweet.getLong("id")).addNewReply(new Tweet(reply, getAuthor(reply.getString("author"))));
            addReplies(reply);
            addLikes(reply);
        }

    }

    private void addLikes(JSONObject tweet) throws InvalidUsernameException {
        JSONArray jsonArray;
        if (tweet.keySet().contains("retweetedTweet")) {
            jsonArray = (JSONArray) ((JSONObject) tweet.get("retweetedTweet")).get("likes");
        } else {
            jsonArray = (JSONArray) tweet.get("likes");
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.get(tweet.getLong("id")).likeTweet(getAuthor(String.valueOf(jsonArray.get(i))));
        }
    }


    /**
     * Add to user to tweets.
     *
     * @param tweet the tweet
     * @return the array list
     */
    public ArrayList<Tweet> addToUserToTweets(Tweet tweet) {
        String username = tweet.getAuthor().getUsername();
        if (userToTweets.containsKey(username)) {
            HashSet<Tweet> tweets = userToTweets.get(username);
            tweets.add(tweet);
            userToTweets.replace(username, tweets);
        } else {
            HashSet<Tweet> tweets = new HashSet<>();
            tweets.add(tweet);
            userToTweets.put(username, tweets);
        }
        return HashSetToArrayList(username);
    }

    /**
     * Remove from user to tweets.
     *
     * @param tweet the tweet
     * @return the array list
     */
    public ArrayList<Tweet> removeFromUserToTweets(Tweet tweet) {
        String username = tweet.getAuthor().getUsername();
        HashSet<Tweet> tweets;
        tweets = userToTweets.get(username);
        tweets.remove(tweet);
        userToTweets.replace(username, tweets);
        return HashSetToArrayList(username);
    }

    /**
     * Hash set to array list array list.
     *
     * @param username the username
     * @return the array list
     */
    public ArrayList<Tweet> HashSetToArrayList(String username) {
        HashSet<Tweet> tweets = userToTweets.get(username);
        return new ArrayList<>(tweets);
    }

    private User getAuthor(String username) throws InvalidUsernameException {
        return userManager.findUser(username);
    }

    /**
     * Find tweets by author array list.
     *
     * @param author is using for finding tweets by author
     * @return tweet array list
     */
    public ArrayList findTweetsByAuthor(User author) {
        String username = author.getUsername();
        return new ArrayList<>(userToTweets.get(username));
    }

    /**
     * Find tweets by time array list.
     *
     * @param date is using for finding tweets by time
     * @return tweet array list
     * @throws InvalidDateException if the date is invalid
     */
    public ArrayList findTweetsByTime(LocalDateTime date) throws InvalidDateException {
        checkDate(date);
        ArrayList<Tweet> tweetArrayList = new ArrayList<>();
        for (Tweet tweet1 : tweets.values()) {
            if (tweet1.getSendDate().isAfter(date))
                tweetArrayList.add(tweet1);
        }
        return tweetArrayList;
    }

    /**
     * @param date is the given date
     * @throws InvalidDateException if the date is invalid
     */
    private void checkDate(LocalDateTime date) throws InvalidDateException {
        if (date.isAfter(LocalDateTime.now())) {
            throw new InvalidDateException("the chosen date should be before now");
        }
    }

    /**
     * Add new tweet.
     *
     * @param tweet is using for adding the tweet
     */
    public void addNewTweet(Tweet tweet) {
        tweets.put(tweet.getId(), tweet);
        database.writeFile(String.valueOf(tweet.getId()), tweet.getAuthor().getUsername(), tweet.toJson());
        notify(tweet, true);
    }

    /**
     * Remove tweet.
     *
     * @param tweet is using for removing the tweet
     * @param user  is sb that wants to remove the tweet
     */
    public void removeTweet(Tweet tweet, User user) {
        if (user.equals(tweet.getAuthor())) {
            tweets.remove(tweet);
            notify(tweet, false);
            database.removeFile(String.valueOf(tweet.getId()), tweet.getAuthor().getUsername());
        } else
            System.out.println("You don't have access to remove another tweet! ");

    }

    /**
     * Gets tweets.
     *
     * @return the tweets
     */
    public ArrayList<Tweet> getTweets() {
        return new ArrayList<>(tweets.values());
    }

    /**
     * Find tweet tweet.
     *
     * @param id the id
     * @return the tweet
     */
    public Tweet findTweet(Long id) {
        if (tweets.containsKey(id))
            return tweets.get(id);
        return null;
    }

    /**
     * Is not exist id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean isNotExistID(long id) {
        if (findTweet(id) == null)
            return true;
        return false;
    }

    /**
     * Make id .
     *
     * @return the long
     */
    public long makeID() {
        Random rand = new Random();
        long id;
        do {
            id = Math.abs(rand.nextLong());
        } while (!isNotExistID(id) || id == 0);
        return id;
    }


    @Override
    public void update(Tweet tweet, Boolean state) {
        if (state)
            database.writeFile(String.valueOf(tweet.getId()), tweet.getAuthor().getUsername(), tweet.toJson());
        else
            database.removeFile(String.valueOf(tweet.getId()), tweet.getAuthor().getUsername());
    }


}



