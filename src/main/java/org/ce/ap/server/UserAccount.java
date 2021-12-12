package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;

import java.util.ArrayList;

public class UserAccount {

    private final User user;
    private TweetingServiceImpl tweetingService = new TweetingServiceImpl();
    private TimelineServiceImpl timelineService= new TimelineServiceImpl();
    private ObserverService observerService = new ObserverServiceImpl();


    public UserAccount(User user) {
        this.user=user;
        observerService.subscribe(user,timelineService);
    }

    public void addFollowing(User user)
    {
        observerService.subscribe(user,timelineService);
    }
    public void removeFollowing(User user){
        observerService.unSubscribe(user,timelineService);
    }

    public void addNewTweet(String text) throws InvalidCharacterNumberException{
        tweetingService.addNewTweet(user,text);
    }
    public void removeTweet(Tweet tweet){
        tweetingService.removeTweet(tweet, user);
    }
    public void like(Tweet tweet){
        tweetingService.like(tweet,user);
    }
    public void unLike(Tweet tweet){
        tweetingService.unLike(tweet,user);
    }
    public void reply(Tweet tweet, Tweet replyTweet){
        tweetingService.reply(tweet,replyTweet);
    }
    public void removeReply(Tweet tweet, Tweet replyTweet){
        tweetingService.removeReply(tweet,replyTweet);
    }
    public void retweet(Tweet tweet, String text) throws InvalidCharacterNumberException{
        tweetingService.retweet(tweet,user,text);
    }
    public void removeRetweet(Tweet tweet, Retweet retweet){
        tweetingService.removeRetweet(tweet,user,retweet);
    }
    public void showTimeline(){
        System.out.println("TIMELINE->");
        for (Tweet tweet :timelineService.refresh()){
            System.out.println(tweet);
        }
        System.out.println("---------------------");
    }
    public ArrayList<Tweet> getTweets()
    {
        return timelineService.refresh();
    }

    public User getUser() {
        return user;
    }
}


