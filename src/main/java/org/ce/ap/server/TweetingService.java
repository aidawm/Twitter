package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.exceptions.TweetDoesntExistException;

/**
 * The interface Tweeting service.
 */
public interface TweetingService {
    /**
     * Add new tweet tweet.
     *
     * @param author is the author of the given tweet
     * @param text   is the given tweet's text
     * @return tweet
     * @throws InvalidCharacterNumberException the invalid character number exception
     */
    Tweet addNewTweet(User author, String text) throws InvalidCharacterNumberException;

    /**
     * Remove tweet.
     *
     * @param tweet should be removed
     * @param user  is sb that wants to remove the tweet
     */
    void removeTweet(Tweet tweet, User user);

    /**
     * Like.
     *
     * @param tweet should be liked
     * @param user  is sb that wants to like the tweet
     * @throws TweetDoesntExistException the tweet doesnt exist exception
     */
    void like(Tweet tweet, User user) throws TweetDoesntExistException;

    /**
     * Un like.
     *
     * @param tweet should be unliked
     * @param user  is sb that wants to unlike the tweet
     * @throws TweetDoesntExistException the tweet doesnt exist exception
     */
    void unLike(Tweet tweet, User user) throws TweetDoesntExistException;

    /**
     * Reply.
     *
     * @param tweet      should be replied
     * @param replyTweet is the added tweet to the given tweet as reply
     */
    void reply(Tweet tweet, Tweet replyTweet);

    /**
     * Remove reply.
     *
     * @param tweet      is the replied tweet
     * @param replyTweet should be removed
     */
    void removeReply(Tweet tweet, Tweet replyTweet);

    /**
     * Retweet retweet.
     *
     * @param tweet is the retweeted tweet
     * @param user  is sb that wants to retweet the tweet
     * @param text  is the retweet text
     * @return retweet
     * @throws InvalidCharacterNumberException the invalid character number exception
     */
    Retweet retweet(Tweet tweet, User user, String text) throws InvalidCharacterNumberException;

    /**
     * Remove retweet.
     *
     * @param tweet   is the retweeted tweet
     * @param user    is sb that wants to remove the retweet
     * @param retweet is the retweeted tweet
     */
    void removeRetweet(Tweet tweet, User user, Retweet retweet);
}
