package main.java.org.ce.ap.server;

public interface Subscriber {
    void update(Tweet tweet, Boolean state);
}
