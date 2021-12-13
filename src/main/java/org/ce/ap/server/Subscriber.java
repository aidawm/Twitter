package main.java.org.ce.ap.server;

/**
 * this interface update tweets
 */
public interface Subscriber {
    void update(Tweet tweet, Boolean state);
}
