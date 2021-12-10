package main.java.org.ce.ap.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SubscribersManager {
    public static HashMap<User, HashSet<Subscriber>> subscribers;
    public SubscribersManager(ArrayList<User> users){
        subscribers=new HashMap<>();
        for (User user:users){
            subscribers.put(user,new HashSet<>());
        }
    }
    public static void subscribe(User user,Subscriber subscriber){
        HashSet<Subscriber> subscriberList= subscribers.get(user);
        subscriberList.add(subscriber);
        subscribers.put(user,subscriberList);
    }
    public static void unSubscribe(User user,Subscriber subscriber){
        HashSet<Subscriber> subscriberList= subscribers.get(user);
        subscriberList.remove(subscriber);
        subscribers.put(user,subscriberList);
    }
    public static void addNewUser(User user){
        subscribers.put(user,new HashSet<>());
    }
}
