package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.Retweet;
import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.TweetingService;
import main.java.org.ce.ap.server.User;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;

public class TweetingServiceImpl implements TweetingService {

    public Tweet tweet;
    @Override
    public void addNewTweet(User author, String text){
        try {
            tweet = new Tweet(author, text);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void removeTweet(Tweet tweet) {

    }

    @Override
    public void like(Tweet tweet, User user) {
        tweet.likeTweet(user);
    }

    @Override
    public void unLike(Tweet tweet, User user) {
        tweet.removeLike(user);
    }

    @Override
    public void reply(Tweet tweet, Tweet replyTweet) {
        tweet.addNewReply(replyTweet);
    }

    @Override
    public void removeReply(Tweet tweet, Tweet replyTweet) {
        tweet.removeReply(replyTweet);
    }

    @Override
    public void retweet(Tweet tweet, User user, String text)  {
        try {
            Retweet retweet = new Retweet(tweet, user, text);
//            tweet.addRetweet(retweet);
            System.out.println(retweet.getRetweet().toString());
            System.out.println(retweet.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeRetweet(Tweet tweet, Retweet retweet) {
        tweet.removeRetweet(retweet);
    }
}
