package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.exceptions.InvalidDateException;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class UserAccount {

    private final User user;
    private TweetingServiceImpl tweetingService = new TweetingServiceImpl();
    private TimelineService timelineService= new TimelineService();
    private ObserverService observerService = new ObserverServiceImpl();


    public UserAccount(User user) {
        this.user=user;
    }

    public void addFollowing(User user)
    {
        observerService.subscribe(user,timelineService);
    }
    public void removeFollowing(User user){
        observerService.unSubscribe(user,timelineService);
    }


}


