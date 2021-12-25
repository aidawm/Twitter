package main.java.org.ce.ap.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SubscribersManager {
//    public static HashMap<User, HashSet<Subscriber>> subscribers=new HashMap<>();
    public static HashMap<User, HashMap<User,Subscriber>> subscribers=new HashMap<>();
    private HashMap<User,HashSet<User>> following;


    /**
     *
     * @param users is the given arraylist for putting in hashset
     */
    public SubscribersManager(ArrayList<User> users){
        for (User user:users){
            subscribers.put(user,new HashMap<>());
//            HashMap<User, Subscriber> userSubscriberHashMap = subscribers.get(user);
//            userSubscriberHashMap.put(user, )
        }

    }

    /**
     *
     * @param user is using for adding subscriber
     * @param subscriber is the user that should follow the given user
     */
    public static void subscribe(User user, Subscriber subscriber, User subscriberUser){
        HashMap<User,Subscriber> subscriberList= subscribers.get(user);
        subscriberList.put(subscriberUser,subscriber);
        subscribers.put(user,subscriberList);
        user.addFollower(subscriberUser);
        subscriberUser.addFollowing(user);

    }

    /**
     *
     * @param user is using for adding subscriber
     * @param subscriber is the user that should unfollow the given user
     */
    public static void unSubscribe(User user,Subscriber subscriber, User subscriberUser){
        HashMap<User,Subscriber> subscriberList= subscribers.get(user);
        subscriberList.remove(subscriberUser);
        subscribers.put(user,subscriberList);
        user.removeFollower(subscriberUser);
        subscriberUser.removeFollowing(subscriberUser);
    }

    /**
     *
     * @param user is using for adding to the hashset
     */
    public static void addNewUser(User user){
        subscribers.put(user,new HashMap<>());
    }



}
