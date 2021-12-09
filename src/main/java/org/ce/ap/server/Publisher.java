package main.java.org.ce.ap.server;

import java.util.ArrayList;
import java.util.HashMap;

public class  Publisher {
    private HashMap<User,ArrayList<Subscriber>> subscribers;
    public Publisher(ArrayList<User> users){
        subscribers=new HashMap<>();
        for (User user:users){
            subscribers.put(user,new ArrayList<>());
        }
    }

    public void subscribe(User user,Subscriber subscriber){

    }


}
