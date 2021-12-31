package main.java.org.ce.ap.server;

import java.util.HashSet;

/**
 * this class notifies all users by update them
 */
public class Publisher {
    /**
     * Notify.
     *
     * @param tweet is using for update subscribers
     * @param state is a boolean
     */
    public void notify(Tweet tweet, Boolean state) {
        User user = tweet.getAuthor();
        HashSet<Subscriber> subscribers = new HashSet<>(SubscribersManager.subscribers.get(user).values());
        for (Subscriber subscriber : subscribers) {
            System.out.println(subscribers.size());
            subscriber.update(tweet, state);
        }
    }
}
