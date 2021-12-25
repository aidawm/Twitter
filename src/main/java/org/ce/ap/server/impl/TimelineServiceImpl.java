package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.Subscriber;
import main.java.org.ce.ap.server.TimelineService;
import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.TweetManager;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The type Timeline service.
 */
public class TimelineServiceImpl implements TimelineService, Subscriber {
    private ArrayList<Tweet> tweets = new ArrayList<>();
    /**
     * The Tweet manager.
     */
    TweetManager tweetManager = TweetManager.getInstance();

    /**
     * this class sorts Tweets by time
     */
    private void sortTweetsByTime() {
        tweets.sort(new dateSorter());
    }

    @Override
    public void update(Tweet tweet, Boolean state) {
        if (state) {
//            tweets.add(tweet);
            tweets = tweetManager.addToUserToTweets(tweet);
        } else {
//            tweets.remove(tweet);
            tweets = tweetManager.removeFromUserToTweets(tweet);
        }
        sortTweetsByTime();
    }

    @Override
    public ArrayList<Tweet> refresh() {
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