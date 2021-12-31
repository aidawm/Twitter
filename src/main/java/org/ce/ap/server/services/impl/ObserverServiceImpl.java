package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.services.observer.Subscriber;
import main.java.org.ce.ap.server.services.observer.SubscribersManager;
import main.java.org.ce.ap.server.model.user.User;
import main.java.org.ce.ap.server.managers.UserManager;

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
