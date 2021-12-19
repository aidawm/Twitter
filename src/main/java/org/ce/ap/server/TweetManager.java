package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidDateException;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TweetManager extends Publisher{
    private ArrayList<Tweet> tweets=new ArrayList<>();

    public TweetManager(){
        getDataFromDatabase();
    }

    /**
     * get data from database
     */
    private void getDataFromDatabase(){
    }

    /**
     *
     * @param author is using for finding tweets by author
     * @return tweet array list
     */
    public ArrayList findTweetsByAuthor(User author){
        ArrayList<Tweet> tweetArrayList= new ArrayList<>();
        for (Tweet tweet1:tweets) {
            if (tweet1.getAuthor().equals(author))
                tweetArrayList.add(tweet1);
        }
        return tweetArrayList;
    }

    /**
     *
     * @param date is using for finding tweets by time
     * @return tweet array list
     * @throws InvalidDateException if the date is invalid
     */
    public ArrayList findTweetsByTime(LocalDateTime date) throws InvalidDateException {
        checkDate(date);
        ArrayList<Tweet> tweetArrayList= new ArrayList<>();
        for (Tweet tweet1:tweets) {
            if (tweet1.getSendDate().isAfter(date))
                tweetArrayList.add(tweet1);
        }
        return tweetArrayList;
    }

    /**
     *
     * @param date is the given date
     * @throws InvalidDateException if the date is invalid
     */
    private void checkDate(LocalDateTime date) throws  InvalidDateException{
        if (date.isAfter(LocalDateTime.now()))
        {
            throw new InvalidDateException("the chosen date should be before now");
        }
    }

    /**
     *
     * @param tweet is using for adding the tweet
     */
    public void addNewTweet(Tweet tweet){
        tweets.add(tweet);
        notify(tweet, true);
    }

    /**
     *
     * @param tweet is using for removing the tweet
     * @param user is sb that wants to remove the tweet
     */
    public void removeTweet(Tweet tweet, User user)
    {
        if (user.equals(tweet.getAuthor()))
        {
            tweets.remove(tweet);
            notify(tweet, false);
        }
        else
            System.out.println("You don't have access to remove another tweet! ");

    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

}




