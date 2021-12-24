package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * The type Tweeting service.
 */
public class TweetingServiceImpl implements TweetingService {


//    public Tweet tweet;

    private TweetManager tweetManager;

    public TweetingServiceImpl(User user) {
        tweetManager = TweetManager.getInstance();
    }

    /**
     * add a new tweet in Twitter
     *
     * @param author the user that want to tweet
     * @param text   the text of the tweet
     */
    @Override
    public void addNewTweet(User author, String text) {
        try {
            Tweet tweet = new Tweet(author, text, tweetManager.makeID());
            tweetManager.addNewTweet(tweet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * remove a tweet from database
     *
     * @param tweet
     */
    @Override
    public void removeTweet(Tweet tweet, User user) {
        tweetManager.removeTweet(tweet, user);
    }

    /**
     * like a tweet
     *
     * @param tweet the tweet that the user want to like it
     * @param user  the user that want to like the tweet
     */
    @Override
    public void like(Tweet tweet, User user) {
        tweet.likeTweet(user);
        tweetManager.update(tweet, true);
    }

    /**
     * remove the user's like from the tweet
     *
     * @param tweet the tweet
     * @param user  the user that want to remove his/her like
     */
    @Override
    public void unLike(Tweet tweet, User user) {
        tweet.removeLike(user);
        tweetManager.update(tweet, true);
    }

    /**
     * reply a tweet
     *
     * @param tweet      the tweet that a user want to like it
     * @param replyTweet the reply tweet of a user
     */
    @Override
    public void reply(Tweet tweet, Tweet replyTweet) {
        tweet.addNewReply(replyTweet);
        tweetManager.update(tweet, true);
    }

    /**
     * @param tweet
     * @param replyTweet
     */
    @Override
    public void removeReply(Tweet tweet, Tweet replyTweet) {
        tweet.removeReply(replyTweet);
        tweetManager.update(tweet, true);
    }

    /**
     * publish a tweet again !
     *
     * @param tweet the tweet that want to retweet it
     * @param user  the user that want to retweet the tweet
     * @param text  the text that user
     */
    @Override
    public void retweet(Tweet tweet, User user, String text) {
        try {
            Retweet retweet = new Retweet(tweet, user, text, tweetManager.makeID());
            tweetManager.addNewTweet(retweet);
            tweet.addRetweet(retweet);
            tweetManager.update(tweet, true);
            System.out.println(retweet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * remove a tweet
     *
     * @param tweet   the tweet retweeted it
     * @param retweet the retweet  that want to remove it
     */
    @Override
    public void removeRetweet(Tweet tweet, User user, Retweet retweet) {
        tweet.removeRetweet(retweet);
        tweetManager.removeTweet(tweet, user);
        tweetManager.update(tweet, true);
        tweetManager.update(retweet, false);
    }

    /**
     * To json array tweet array list.
     *
     * @return the array list
     */
    public ArrayList<JSONObject> toJsonArrayTweet() {
        ArrayList<JSONObject> jsonList = new ArrayList<>();
        for (Tweet tweet : tweetManager.getTweets()) {
            jsonList.add((tweet).toJson());
        }

        return jsonList;
    }

}
