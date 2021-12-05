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

    /**
     * create a new object from tweet
     * @param author is using for setting the tweet's author
     * @param text is using for setting the tweet's text
     */
    public Tweet(User author,String text){
        this.author=author;
        this.text=text;
        this.sendDate=LocalDate.now();
        this.likes=new HashSet<>();
        this.replies=new ArrayList<>();
    }

    public void likeTweet(User liker){
        likes.add(liker);
    }
    public void removeLike(User liker){
        if (likes.contains(liker))
            likes.remove(liker);
    }

}
