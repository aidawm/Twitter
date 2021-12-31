package main.java.org.ce.ap.server.middleClasses;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.exceptions.InvalidUsernameException;
import main.java.org.ce.ap.server.managers.TweetManager;
import main.java.org.ce.ap.server.services.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.services.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.services.impl.TweetingServiceImpl;

import java.util.ArrayList;

import main.java.org.ce.ap.server.model.tweet.Retweet;
import main.java.org.ce.ap.server.model.tweet.Tweet;
import main.java.org.ce.ap.server.model.user.User;

/**
 * The type User account.
 */
public class UserAccount {
    //// the user's information
    private final User user;
    //// to access the tweetingService
    private TweetingServiceImpl tweetingService;
    //// to access the timelineService
    private TimelineServiceImpl timelineService = new TimelineServiceImpl();
    //// to access the observerService
    private ObserverServiceImpl observerService = new ObserverServiceImpl();
    //// to access the TweetManager
    private TweetManager tweetManager;

    /**
     * create a new object from UserAccount
     *
     * @param user the user information
     * @throws InvalidUsernameException the invalid username exception
     */
    public UserAccount(User user) throws InvalidUsernameException {
        this.user = user;
        observerService.subscribe(user, timelineService, this.user);
        tweetingService = new TweetingServiceImpl(user);
        tweetManager = TweetManager.getInstance();
        getTweetsFromDataBase(user);
        ArrayList<User> users = user.getFollowings();
        for (int i = 0; i < users.size(); i++) {
            observerService.subscribe(users.get(i), timelineService, this.user);
        }
    }

    private void getTweetsFromDataBase(User user) throws InvalidUsernameException {
        timelineService.addTweetsFromFile(TweetManager.getInstance().getDataFromDatabase(user));
    }

    /**
     * subscribe a user
     *
     * @param user the user that want to subscribe it
     * @throws InvalidUsernameException the invalid username exception
     */
    public void addFollowing(User user) throws InvalidUsernameException {
        observerService.subscribe(user, timelineService, this.user);
        getTweetsFromDataBase(user);
    }

    /**
     * unsubscribe a user
     *
     * @param user the user
     */
    public void removeFollowing(User user) {
        observerService.unSubscribe(user, timelineService, this.user);
    }

    /**
     * add a new tweet for this userAccount
     *
     * @param text the text of the tweet
     * @return the tweet
     * @throws InvalidCharacterNumberException if the text has more than 256 character
     */
    public Tweet addNewTweet(String text) throws InvalidCharacterNumberException {
        return tweetingService.addNewTweet(user, text);

    }

    /**
     * remove a tweet from Twitter
     *
     * @param tweet the tweet
     */
    public void removeTweet(Tweet tweet) {
        tweetingService.removeTweet(tweet, user);
    }

    /**
     * like a tweet
     *
     * @param tweet the tweet that want to like it
     */
    public void like(Tweet tweet) {
        tweetingService.like(tweet, user);
    }

    /**
     * un like a tweet
     *
     * @param tweet the tweet that want to unlike it
     */
    public void unLike(Tweet tweet) {
        tweetingService.unLike(tweet, user);
    }

    /**
     * reply a tweet
     *
     * @param tweet the tweet
     * @param text  our reply text
     * @throws InvalidCharacterNumberException the invalid character number exception
     */
    public void reply(Tweet tweet, String text) throws InvalidCharacterNumberException {
        Tweet replyTweet = new Tweet(user, text, tweetManager.makeID());
        tweetingService.reply(tweet, replyTweet);
    }
//    public void reply(Tweet tweet, Tweet replyTweet,String text) throws InvalidCharacterNumberException {
//        Tweet replyReplyTweet = new Tweet(user, text, tweetManager.makeID());
//        tweetingService.reply(replyTweet, replyReplyTweet);
//
//    }

    /**
     * remove a reply from a tweet
     *
     * @param tweet      the tweet
     * @param replyTweet our reply tweet
     */
    public void removeReply(Tweet tweet, Tweet replyTweet) {
        tweetingService.removeReply(tweet, replyTweet);
    }

    /**
     * retweet a tweet
     *
     * @param tweet the tweet
     * @param text  the text that want to retweet the tweet with it
     * @return the retweet
     * @throws InvalidCharacterNumberException if the text has more than 256 character
     */
    public Retweet retweet(Tweet tweet, String text) throws InvalidCharacterNumberException {
        return tweetingService.retweet(tweet, user, text);
    }

    /**
     * remove a retweet
     *
     * @param tweet   the tweet
     * @param retweet the retweet
     */
    public void removeRetweet(Tweet tweet, Retweet retweet) {
        tweetingService.removeRetweet(tweet, user, retweet);
    }

    /**
     * get the timeline's tweet
     *
     * @return the tweet's list
     */
    public ArrayList<Tweet> getTweets() {
        return timelineService.refresh();
    }

    /**
     * the user information
     *
     * @return user user
     */
    public User getUser() {
        return user;
    }

}


