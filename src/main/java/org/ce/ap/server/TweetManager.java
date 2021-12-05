package main.java.org.ce.ap.server;

import java.time.LocalDate;
import java.util.*;

public class TweetManager {
    private ArrayList<Tweet> tweets;

    public TweetManager(){
        getDataFromDatabase();
    }

    /**
     * get data from database
     */
    private void getDataFromDatabase(){
    }

    /**
     * this class sorts Tweets by time
     */
    private void sortTweetsByTime()
    {
        tweets.sort(new dateSorter());
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
     */
    public ArrayList findTweetsByTime(LocalDate date){
        ArrayList<Tweet> tweetArrayList= new ArrayList<>();
        for (Tweet tweet1:tweets) {
            if (tweet1.getSendDate().isAfter(date))
                tweetArrayList.add(tweet1);
        }
        return tweetArrayList;
    }

}

/**
 * this class can help us to sort the arraylist by date!
 */
class dateSorter implements Comparator<Tweet>
{
    @Override
    public int compare(Tweet o1, Tweet o2) {
        return o2.getSendDate().compareTo(o1.getSendDate());
    }
}
