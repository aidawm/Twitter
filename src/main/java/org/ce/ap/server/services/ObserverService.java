package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.services.observer.Subscriber;
import main.java.org.ce.ap.server.model.user.User;

/**
 * this interface subscribes or unsubscribes the given user
 */
public interface ObserverService {
    /**
     * Subscribe.
     *
     * @param user           the user
     * @param subscriber     the subscriber
     * @param subscriberUser the subscriber user
     */
    void subscribe(User user, Subscriber subscriber, User subscriberUser);

    /**
     * Un subscribe.
     *
     * @param user           the user
     * @param subscriber     the subscriber
     * @param subscriberUser the subscriber user
     */
    void unSubscribe(User user, Subscriber subscriber, User subscriberUser);

}