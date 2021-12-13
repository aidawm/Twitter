package main.java.org.ce.ap.server;

/**
 * this interface subscribes or unsubscribes the given user
 */
public interface ObserverService {
    void subscribe(User user, Subscriber subscriber);
    void unSubscribe(User user, Subscriber subscriber);

}
