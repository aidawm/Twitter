package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.ObserverService;
import main.java.org.ce.ap.server.Subscriber;
import main.java.org.ce.ap.server.SubscribersManager;
import main.java.org.ce.ap.server.User;
import main.java.org.ce.ap.server.UserManager;

/**
 * The type Observer service.
 */
public class ObserverServiceImpl implements ObserverService {
    private UserManager userManager = UserManager.getInstance();

    @Override
    public void subscribe(User user, Subscriber subscriber, User subscriberUser) {
        SubscribersManager.subscribe(user, subscriber, subscriberUser);
        if (!subscriberUser.equals(user)) {
            userManager.update(user);
            userManager.update(subscriberUser);
        }

    }

    @Override
    public void unSubscribe(User user, Subscriber subscriber, User subscriberUser) {
        SubscribersManager.unSubscribe(user, subscriber, subscriberUser);
        if (!subscriberUser.equals(user)) {
            userManager.update(user);
            userManager.update(subscriberUser);
        }
    }
}
