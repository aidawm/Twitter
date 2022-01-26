package  org.ce.ap.server.services.observer;

import  org.ce.ap.server.model.tweet.Tweet;

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
