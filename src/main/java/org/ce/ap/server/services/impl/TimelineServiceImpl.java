package  org.ce.ap.server.services.impl;

import  org.ce.ap.server.model.user.User;
import  org.ce.ap.server.services.observer.Subscriber;
import  org.ce.ap.server.services.TimelineService;
import  org.ce.ap.server.model.tweet.Tweet;
import  org.ce.ap.server.managers.TweetManager;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The type Timeline service.
 */
public class TimelineServiceImpl implements TimelineService, Subscriber {
    private User user;
    private ArrayList<Tweet> tweets = new ArrayList<>();
    /**
     * The Tweet manager.
     */
    TweetManager tweetManager = TweetManager.getInstance();

    /**
     * Instantiates a new Timeline service.
     */
    public TimelineServiceImpl(User user) {
        this.user=user;
    }

    /**
     * Add tweets from file.
     *
     * @param tweets the tweets
     */
    public void addTweetsFromFile(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    /**
     * this class sorts Tweets by time
     */
    private void sortTweetsByTime() {
        tweets.sort(new dateSorter());
    }

    @Override
    public void update(Tweet tweet, Boolean state) {
        if (state) {
            tweets = tweetManager.addToUserToTweets(tweet);
        } else {
            tweets = tweetManager.removeFromUserToTweets(tweet);
        }
        sortTweetsByTime();
    }

    @Override
    public ArrayList<Tweet> refresh() {
        tweets= tweetManager.getTimeline(user);
        sortTweetsByTime();
        return tweets;
    }

}

/**
 * this class can help us to sort the arraylist by date!
 */
class dateSorter implements Comparator<Tweet> {
    @Override
    public int compare(Tweet o1, Tweet o2) {
        return o2.getSendDate().compareTo(o1.getSendDate());
    }
}