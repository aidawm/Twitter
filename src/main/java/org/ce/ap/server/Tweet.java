package main.java.org.ce.ap.server;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Tweet {

    private final User author;
    private String text;
    private HashSet<User> likes;
    private ArrayList<Tweet> replies;
    private final LocalDate sendDate;

    public Tweet(User author,String text){
        this.author=author;
        this.text=text;
        this.sendDate=LocalDate.now();
        this.likes=new HashSet<>();
        this.replies=new ArrayList<>();
    }
}
