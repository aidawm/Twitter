package main.java.org.ce.ap.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class  Publisher {
    public void notify(Tweet tweet){
        User user = tweet.getAuthor();
        HashSet<Subscriber> subscribers = SubscribersManager.subscribers.get(user);
        for (Subscriber subscriber:subscribers){
            subscriber.update(tweet);
        }
    }
}
