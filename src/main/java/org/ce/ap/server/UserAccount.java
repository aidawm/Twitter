package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidDateException;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;


public class UserAccount {

    private final User user;
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private ArrayList<User> follower= new ArrayList<>();
    private ArrayList<User> following= new ArrayList<>();
    private TimelineServiceImpl timelineService= new TimelineServiceImpl();
    private ObserverService observerService = new ObserverServiceImpl();


    public UserAccount(User user) {
        this.user=user;
        observerService.subscribe(user,timelineService);
    }

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
    public ArrayList findTweetsByTime(LocalDate date) throws InvalidDateException {
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
    private void checkDate(LocalDate date) throws  InvalidDateException{
        if (date.isAfter(LocalDate.now()))
        {
            throw new InvalidDateException("the chosen date should be before now");
        }
    }

    public void addFollower(User user)
    {
        if (!follower.contains(user))
            follower.add(user);
    }

    public void addFollowing(User user)
    {
        if (!following.contains(user))
            following.add(user);
        observerService.subscribe(user,timelineService);
    }
    public void removeFollowing(User user){
        observerService.unSubscribe(user,timelineService);
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void show(ArrayList<Tweet> tweets){
        for(Tweet tweet : tweets)
        {
            System.out.println(tweet);
        }
    }

//    public ArrayList<UserAccount> getFollower() {
//        return follower;
//    }
//
//    public ArrayList<UserAccount> getFollowing() {
//        return following;
//    }


}

/**
 * this class can help us to sort the arraylist by date!
 */
//class dateSorter implements Comparator<Tweet>
//{
//    @Override
//    public int compare(Tweet o1, Tweet o2) {
//        return o2.getSendDate().compareTo(o1.getSendDate());
//    }
//}

