package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.ObserverService;
import main.java.org.ce.ap.server.Subscriber;
import main.java.org.ce.ap.server.SubscribersManager;
import main.java.org.ce.ap.server.User;

public class ObserverServiceImpl implements ObserverService {
    @Override
    public void subscribe(User user, Subscriber subscriber) {
        SubscribersManager.subscribe(user,subscriber);
    }

    @Override
    public void unSubscribe(User user, Subscriber subscriber) {
        SubscribersManager.unSubscribe(user,subscriber);
    }
}
