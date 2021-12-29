package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;

public interface TweetingService {
    /**
     *
     * @param author is the author of the given tweet
     * @param text is the given tweet's text
     * @throws InvalidCharacterNumberException
     * @return
     */
    Tweet addNewTweet(User author, String text) throws InvalidCharacterNumberException;

    /**
     *
     * @param tweet should be removed
     * @param user is sb that wants to remove the tweet
     */
    void removeTweet(Tweet tweet, User user);

    /**
     *
     * @param tweet should be liked
     * @param user is sb that wants to like the tweet
     */
    void like(Tweet tweet, User user);

    /**
     *
     * @param tweet should be unliked
     * @param user is sb that wants to unlike the tweet
     */
    void unLike(Tweet tweet, User user);

    /**
     *
     * @param tweet should be replied
     * @param replyTweet is the added tweet to the given tweet as reply
     */
    void reply(Tweet tweet, Tweet replyTweet);

    /**
     *
     * @param tweet is the replied tweet
     * @param replyTweet should be removed
     */
    void removeReply(Tweet tweet, Tweet replyTweet);

    /**
     *
     * @param tweet is the retweeted tweet
     * @param user is sb that wants to retweet the tweet
     * @param text is the retweet text
     * @throws InvalidCharacterNumberException
     * @return
     */
    Retweet retweet(Tweet tweet, User user, String text) throws InvalidCharacterNumberException;

    /**
     *
     * @param tweet is the retweeted tweet
     * @param user is sb that wants to remove the retweet
     * @param retweet is the retweeted tweet
     */
    void removeRetweet(Tweet tweet,User user, Retweet retweet);
}
