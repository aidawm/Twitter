package main.java.org.ce.ap.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SubscribersManager {
    public static HashMap<User, HashSet<Subscriber>> subscribers=new HashMap<>();

    /**
     *
     * @param users is the given arraylist for putting in hashset
     */
    public SubscribersManager(ArrayList<User> users){
        for (User user:users){
            subscribers.put(user,new HashSet<>());
        }
    }

    /**
     *
     * @param user is using for adding subscriber
     * @param subscriber is the user that should follow the given user
     */
    public static void subscribe(User user, Subscriber subscriber, User subscriberUser){

        HashSet<Subscriber> subscriberList= subscribers.get(user);
        subscriberList.add(subscriber);
        subscribers.put(user,subscriberList);
        user.addFollowing(subscriberUser);
    }

    /**
     *
     * @param user is using for adding subscriber
     * @param subscriber is the user that should unfollow the given user
     */
    public static void unSubscribe(User user,Subscriber subscriber, User subscriberUser){
        HashSet<Subscriber> subscriberList= subscribers.get(user);
        subscriberList.remove(subscriber);
        subscribers.put(user,subscriberList);
        user.removeFollowing(subscriberUser);
    }

    /**
     *
     * @param user is using for adding to the hashset
     */
    public static void addNewUser(User user){
        subscribers.put(user,new HashSet<>());
    }

}
