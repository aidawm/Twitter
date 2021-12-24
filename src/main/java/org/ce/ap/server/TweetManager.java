package main.java.org.ce.ap.server;


import main.java.org.ce.ap.server.exceptions.InvalidDateException;
import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;
import main.java.org.ce.ap.server.DataBase.TweetDataBase;

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
    private HashMap<Long, Tweet> tweets;
//    private ArrayList<JSONObject> reTweetMakeList;

    private TweetManager() {
        userManager = UserManager.getInstance();
        database = new TweetDataBase();
        tweets = new HashMap<>();
//        reTweetMakeList = new ArrayList<>();
//        getDataFromDatabase();
    }

    /**
     * Get instance tweet manager.
     *
     * @return the tweet manager
     */
    public static TweetManager getInstance() {
        if (instance == null)
            instance = new TweetManager();
        return instance;
    }

    /**
     * get data from database
     */
    public void getDataFromDatabase(User user) throws InvalidUsernameException {
        HashMap<Long, JSONObject> tweetJsonList = database.getDirectoryFiles(user);
        for (Long tweetID : tweetJsonList.keySet()) {
            if (tweets.containsKey(tweetID))
                continue;
            JSONObject tweet = tweetJsonList.get(tweetID);
            if (tweet.keySet().contains("retweetedTweet") && tweet.keySet().contains("newTweet")) {
                JSONObject retweetedTweetJson = new JSONObject(tweet.get("retweetedTweet"));
                long retweetedID = retweetedTweetJson.getLong("id");

                if (tweets.containsKey(retweetedID)) {
                    tweets.put(tweetID, new Retweet(tweet, getAuthor(tweet), tweets.get(retweetedID)));
                } else {
                    Tweet retweetedTweet = new Tweet(retweetedTweetJson, getAuthor(retweetedTweetJson));
                    tweets.put(retweetedTweet.getId(), retweetedTweet);
                    tweets.put(tweetID, new Retweet(tweet, getAuthor(tweet), retweetedTweet));
                }
            }
            else {
                tweets.put(tweetID,new Tweet(tweet,getAuthor(tweet)));
            }

        }
//
//            System.out.println(tweet);
//            if (tweet.keySet().contains("retweetedTweet") && tweet.keySet().contains("newTweet")) {
//                reTweetMakeList.add(tweet);
//                continue;
//            }
//
//            try {
//                tweets.add(new Tweet(tweet, getAuthor(tweet)));
//            } catch (InvalidUsernameException e) {
//                e.printStackTrace();
//            }
//        }
    }
//
//    private void handleRetweets() {
//        while (reTweetMakeList.size() != 0) {
//            try {
//                JSONObject retweetJson = reTweetMakeList.get(0);
//                JSONObject retweetedTweetJson = (JSONObject) retweetJson.get("retweetedTweet");
//                JSONObject newTweet = (JSONObject) retweetJson.get("newTweet");
//                Tweet retweetedTweetObj = findTweet(retweetedTweetJson.getLong("id"));
//                Retweet retweetObj = new Retweet(retweetJson, getAuthor(newTweet), retweetedTweetObj);
//                retweetedTweetObj.addRetweet(retweetObj);
//                tweets.add(retweetObj);
//                reTweetMakeList.remove(retweetJson);
//            } catch (InvalidUsernameException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private User getAuthor(JSONObject tweet) throws InvalidUsernameException {
        JSONObject author = (JSONObject) tweet.get("author");
        return userManager.findUser(author.getString("username"));
    }

    /**
     * Find tweets by author array list.
     *
     * @param author is using for finding tweets by author
     * @return tweet array list
     */
    public ArrayList findTweetsByAuthor(User author) {
        ArrayList<Tweet> tweetArrayList = new ArrayList<>();
        for (Tweet tweet1 : tweets.values()) {
            if (tweet1.getAuthor().equals(author))
                tweetArrayList.add(tweet1);
        }
        return tweetArrayList;
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
        tweets.put(tweet.getId(),tweet);
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
//        for (Tweet tweet : tweets) {
//            if (tweet.getId() == id)
//                return tweet;
//        }
        if(tweets.containsKey(id))
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
            database.readFile(String.valueOf(tweet.getId()), tweet.getAuthor().getUsername());
    }
}




