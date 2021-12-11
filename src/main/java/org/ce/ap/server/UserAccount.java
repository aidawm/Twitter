package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.exceptions.InvalidDateException;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class UserAccount {

    private final User user;
    private TweetingServiceImpl tweetingService = new TweetingServiceImpl();
    private TimelineServiceImpl timelineService= new TimelineServiceImpl();
    private ObserverService observerService = new ObserverServiceImpl();


    public UserAccount(User user) {
        this.user=user;
    }

    public void addFollowing(User user)
    {
        observerService.subscribe(user,timelineService);
    }
    public void removeFollowing(User user){
        observerService.unSubscribe(user,timelineService);
    }

    public void addNewTweet(User author,String text) throws InvalidCharacterNumberException{
        tweetingService.addNewTweet(author,text);
    }
    public void removeTweet(Tweet tweet){
        tweetingService.removeTweet(tweet);
    }
    public void like(Tweet tweet, User user){
        tweetingService.like(tweet,user);
    }
    public void unLike(Tweet tweet, User user){
        tweetingService.unLike(tweet,user);
    }
    public void reply(Tweet tweet, Tweet replyTweet){
        tweetingService.reply(tweet,replyTweet);
    }
    public void removeReply(Tweet tweet, Tweet replyTweet){
        tweetingService.removeReply(tweet,replyTweet);
    }
    public void retweet(Tweet tweet, User user, String text) throws InvalidCharacterNumberException{
        tweetingService.retweet(tweet,user,text);
    }
    public void removeRetweet(Tweet tweet, Retweet retweet){
        tweetingService.removeRetweet(tweet,retweet);
    }
    public void showTimeline(){

    }
}


