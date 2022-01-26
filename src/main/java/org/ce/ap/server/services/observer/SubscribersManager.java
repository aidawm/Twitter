package  org.ce.ap.server.services.observer;

import  org.ce.ap.server.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Subscribers manager.
 */
public class SubscribersManager {

    /**
     * The constant subscribers.
     */
    public static HashMap<User, HashMap<User, Subscriber>> subscribers = new HashMap<>();


    /**
     * Instantiates a new Subscribers manager.
     *
     * @param users is the given arraylist for putting in hashset
     */
    public SubscribersManager(ArrayList<User> users) {
        for (User user : users) {
            subscribers.put(user, new HashMap<>());
        }

    }

    /**
     * Subscribe.
     *
     * @param user           is using for adding subscriber
     * @param subscriber     is the user that should follow the given user
     * @param subscriberUser the subscriber user
     */
    public static void subscribe(User user, Subscriber subscriber, User subscriberUser) {
        HashMap<User, Subscriber> subscriberList = subscribers.get(user);
        subscriberList.put(subscriberUser, subscriber);
        subscribers.put(user, subscriberList);
        user.addFollower(subscriberUser);
        subscriberUser.addFollowing(user);

    }

    /**
     * Un subscribe.
     *
     * @param user           is using for adding subscriber
     * @param subscriber     is the user that should unfollow the given user
     * @param subscriberUser the subscriber user
     */
    public static void unSubscribe(User user, Subscriber subscriber, User subscriberUser) {
        if (user.equals(subscriberUser))
            return;
        HashMap<User, Subscriber> subscriberList = subscribers.get(user);
        subscriberList.remove(subscriberUser);
        subscribers.put(user, subscriberList);
        user.removeFollower(subscriberUser);
        subscriberUser.removeFollowing(user);
        System.out.println("user" + user.toJson());
        System.out.println("subscriberUser" + subscriberUser.toJson());
    }

    /**
     * Add new user.
     *
     * @param user is using for adding to the hashset
     */
    public static void addNewUser(User user) {
        subscribers.put(user, new HashMap<>());
    }


}
