package main.java.org.ce.ap.server;

public interface ObserverService {
    void subscribe(User user, Subscriber subscriber);
    void unSubscribe(User user, Subscriber subscriber);

}
