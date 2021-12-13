package main.java.org.ce.ap.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * this class notifies all users by update them
 */
public class  Publisher {
    /**
     *
     * @param tweet is using for update subscribers
     * @param state is a boolean
     */
    public void notify(Tweet tweet, Boolean state){
        User user = tweet.getAuthor();
        HashSet<Subscriber> subscribers = SubscribersManager.subscribers.get(user);
        for (Subscriber subscriber:subscribers){
            subscriber.update(tweet, state);
        }
    }
}
