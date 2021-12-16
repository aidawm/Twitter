package main.java.org.ce.ap.server;

import main.java.org.ce.ap.ServiceWordsEnum;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ServerProcessor {
    //// the user's information
    private final User user;
    //// to access the tweetingService
    private TweetingServiceImpl tweetingService = new TweetingServiceImpl();
    //// to access the timelineService
    private TimelineServiceImpl timelineService= new TimelineServiceImpl();
    //// to access the observerService
    private ObserverService observerService = new ObserverServiceImpl();

    /**
     * create a new object from UserAccount
     * @param user the user information
     */
    public ServerProcessor(User user) {
        this.user=user;
        observerService.subscribe(user,timelineService);
    }

    /**
     * subscribe a user
     * @param user the user that want to subscribe it
     */
    public void addFollowing(User user)
    {
        observerService.subscribe(user,timelineService);
    }

    /**
     * unsubscribe a user
     * @param user the user
     */
    public void removeFollowing(User user){
        observerService.unSubscribe(user,timelineService);
    }

    /**
     * add a new tweet for this userAccount
     * @param text the text of the tweet
     * @throws InvalidCharacterNumberException if the text has more than 256 character
     */
    public void addNewTweet(String text) throws InvalidCharacterNumberException{
        tweetingService.addNewTweet(user,text);
    }

    /**
     * remove a tweet from Twitter
     * @param tweet the tweet
     */
    public void removeTweet(Tweet tweet){
        tweetingService.removeTweet(tweet, user);
    }

    /**
     * like a tweet
     * @param tweet the tweet that want to like it
     */
    public void like(Tweet tweet){
        tweetingService.like(tweet,user);
    }

    /**
     * un like a tweet
     * @param tweet the tweet that want to unlike it
     */
    public void unLike(Tweet tweet){
        tweetingService.unLike(tweet,user);
    }

    /**
     * reply a tweet
     * @param tweet the tweet
     * @param replyTweet our reply tweet
     */
    public void reply(Tweet tweet, Tweet replyTweet){
        tweetingService.reply(tweet,replyTweet);
    }

    /**
     * remove a reply from a tweet
     * @param tweet the tweet
     * @param replyTweet our reply tweet
     */
    public void removeReply(Tweet tweet, Tweet replyTweet){
        tweetingService.removeReply(tweet,replyTweet);
    }

    /**
     * retweet a tweet
     * @param tweet the tweet
     * @param text the text that want to retweet the tweet with it
     * @throws InvalidCharacterNumberException if the text has more than 256 character
     */
    public void retweet(Tweet tweet, String text) throws InvalidCharacterNumberException{
        tweetingService.retweet(tweet,user,text);
    }

    /**
     * remove a retweet
     * @param tweet the tweet
     * @param retweet the retweet
     */
    public void removeRetweet(Tweet tweet, Retweet retweet){
        tweetingService.removeRetweet(tweet,user,retweet);
    }

    /**
     * show the timeline's tweet
     */
    public void showTimeline(){
        System.out.println("TIMELINE->");
        for (Tweet tweet :timelineService.refresh()){
            System.out.println(tweet);
        }
        System.out.println("---------------------");
    }

    /**
     * get the timeline's tweet
     * @return the tweet's list
     */
    public ArrayList<Tweet> getTweets()
    {
        return timelineService.refresh();
    }

    /**
     * the user information
     * @return user
     */
    public User getUser() {return user;}
    void SwitchCaseUsingJason() throws InvalidCharacterNumberException {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonParameters = (JSONObject) jsonObject.get("parameterValues");
        ServiceWordsEnum method = (ServiceWordsEnum) jsonObject.get("method");

        switch (method){
            case TWEET:
                addNewTweet((String) jsonParameters.get("text"));
                break;
            case REMOVETWEET:
                removeTweet((Tweet) jsonParameters.get("tweet"));
                break;
            case RETWEET:
                retweet((Tweet) jsonParameters.get("tweet"), (String) jsonParameters.get("text"));
                break;
            case REMOVERETWEET:
                removeRetweet((Tweet) jsonParameters.get("tweet"), (Retweet)jsonParameters.get("retweet"));
                break;
            case LIKE:
                like((Tweet) jsonParameters.get("tweet"));
                break;
            case DISLIKE:
                unLike((Tweet) jsonParameters.get("tweet"));
                break;
            case REPLY:
                reply((Tweet) jsonParameters.get("tweet"), (Tweet) jsonParameters.get("replyTweet"));
                break;
            case REMOVEREPLY:
                removeReply((Tweet) jsonParameters.get("tweet"), (Tweet) jsonParameters.get("replyTweet"));
                break;

        }
    }
}

