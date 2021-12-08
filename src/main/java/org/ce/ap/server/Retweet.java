package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;

public class Retweet extends Tweet{
    private Tweet retweet;


    /**
     * create a new object from tweet
     *
     * @param retweetAuthor is using for setting the retweet's author
     * @param retweetText   is using for setting the retweet's text
     */
    public Retweet(Tweet retweet, User retweetAuthor, String retweetText) throws InvalidCharacterNumberException {
        super(retweetAuthor, retweetText);
        this.retweet = retweet;
        retweet.addRetweet(this);
    }

    /**
     *
     * @return tweet
     */
    public Tweet getRetweet() {
        return retweet;
    }


}
