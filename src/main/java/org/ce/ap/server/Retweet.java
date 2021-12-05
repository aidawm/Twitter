package main.java.org.ce.ap.server;

public class Retweet extends Tweet{
    private Tweet retweet;


    /**
     * create a new object from tweet
     *
     * @param retweetAuthor is using for setting the retweet's author
     * @param retweetText   is using for setting the retweet's text
     */
    public Retweet(User retweetAuthor, String retweetText, Tweet retweet) {
        super(retweetAuthor, retweetText);
        this.retweet = retweet;
    }

    /**
     *
     * @return tweet
     */
    public Tweet getRetweet() {
        return retweet;
    }
}
