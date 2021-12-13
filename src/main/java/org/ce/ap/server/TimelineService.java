package main.java.org.ce.ap.server;

import java.util.ArrayList;

/**
 * this interface handles the timeline
 */
public interface TimelineService {
    /**
     *
     * @param tweet is the given
     * @param state is a boolean shows that the tweet is in adding progress or not
     */
    public void update(Tweet tweet, Boolean state);

    /**
     *
     * @return a array list of tweets
     */
    public ArrayList<Tweet> refresh();
}
