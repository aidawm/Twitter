package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.Retweet;
import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.TweetingService;
import main.java.org.ce.ap.server.User;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;

public class TweetingServiceImpl implements TweetingService {

    @Override
    public void addNewTweet(User author, String text) throws InvalidCharacterNumberException {
        Tweet tweet = new Tweet(author, text);
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
    public void retweet(Tweet tweet, User user, String text) throws InvalidCharacterNumberException {
        Retweet retweet = new Retweet(tweet, user, text);
    }

    @Override
    public void removeRetweet(Tweet tweet, Retweet retweet) {
        tweet.removeRetweet(retweet);
    }
}
