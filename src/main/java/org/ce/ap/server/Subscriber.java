package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.model.Tweet;

/**
 * this interface update tweets
 */
public interface Subscriber {
    /**
     * Update.
     *
     * @param tweet the tweet
     * @param state the state
     */
    void update(Tweet tweet, Boolean state);
}
