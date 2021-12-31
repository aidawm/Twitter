package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.model.Tweet;

import java.util.ArrayList;

/**
 * this interface handles the timeline
 */
public interface TimelineService {
    /**
     * Update.
     *
     * @param tweet is the given
     * @param state is a boolean shows that the tweet is in adding progress or not
     */
    public void update(Tweet tweet, Boolean state);

    /**
     * Refresh array list.
     *
     * @return a array list of tweets
     */
    public ArrayList<Tweet> refresh();
}
