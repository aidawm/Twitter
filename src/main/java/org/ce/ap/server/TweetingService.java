package main.java.org.ce.ap.server;

public interface TweetingService {
    void like(Tweet tweet, User user);
    void unLike(Tweet tweet, User user);
    void reply(Tweet tweet);
    void removeReply(Tweet tweet);
    void retweet(Tweet tweet);
    void removeRetweet(Tweet tweet);
}
