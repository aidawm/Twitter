package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.managers.TweetManager;
import main.java.org.ce.ap.server.model.tweet.Retweet;
import main.java.org.ce.ap.server.model.tweet.Tweet;
import main.java.org.ce.ap.server.model.user.User;
import main.java.org.ce.ap.server.services.TweetingService;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The type Tweeting service.
 */
public class TweetingServiceImpl implements TweetingService {


//    public Tweet tweet;

    private TweetManager tweetManager;

    /**
     * Instantiates a new Tweeting service.
     *
     * @param user the user
     */
    public TweetingServiceImpl(User user) {
        tweetManager = TweetManager.getInstance();
    }

    /**
     * add a new tweet in Twitter
     *
     * @param author the user that want to tweet
     * @param text   the text of the tweet
     * @return
     */
    @Override
    public Tweet addNewTweet(User author, String text) throws InvalidCharacterNumberException {
        Tweet tweet = new Tweet(author, text, tweetManager.makeID());
        tweetManager.addNewTweet(tweet);
        return tweet;
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
        tweetManager.findTweet(tweet.getId()).likeTweet(user);
        tweetManager.update(tweet, true);
//        tweetManager.updateLike(tweet);
    }

    /**
     * remove the user's like from the tweet
     *
     * @param tweet the tweet
     * @param user  the user that want to remove his/her like
     */
    @Override
    public void unLike(Tweet tweet, User user) {
        tweetManager.findTweet(tweet.getId()).removeLike(user);
        tweetManager.update(tweet, true);
//        tweetManager.updateLike(tweet);
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
     * @return
     */
    @Override
    public Retweet retweet(Tweet tweet, User user, String text) throws InvalidCharacterNumberException {
        Retweet retweet = new Retweet(tweet, user, text, tweetManager.makeID());
        tweetManager.addNewTweet(retweet);
        tweet.addRetweet(retweet);
        tweetManager.update(tweet, true);
        System.out.println(retweet);
        return retweet;
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
        tweetManager.removeTweet(retweet, user);
        System.out.println(tweet.toJson());
        tweetManager.update(tweet, true);
//        tweetManager.update(retweet, false);
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
