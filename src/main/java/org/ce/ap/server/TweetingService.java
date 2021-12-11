package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;

public interface TweetingService {
    void addNewTweet(User author,String text) throws InvalidCharacterNumberException;
    void removeTweet(Tweet tweet, User user);
    void like(Tweet tweet, User user);
    void unLike(Tweet tweet, User user);
    void reply(Tweet tweet, Tweet replyTweet);
    void removeReply(Tweet tweet, Tweet replyTweet);
    void retweet(Tweet tweet, User user, String text) throws InvalidCharacterNumberException;
    void removeRetweet(Tweet tweet, Retweet retweet);
}
