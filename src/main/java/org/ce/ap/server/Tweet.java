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

    /**
     *
     * @param liker is using for adding liker
     */
    public void likeTweet(User liker){
        likes.add(liker);
    }

    /**
     *
     * @param liker is using for removing liker
     */
    public void removeLike(User liker){
        likes.remove(liker);
        if (!likes.contains(liker))
        {
            System.err.println("liker not found");
        }
    }

    /**
     * add new reply for the tweet
     * @param tweet the tweet's reply
     */
    public void addNewReply(Tweet tweet){
        replies.add(tweet);
    }
    /**
     * remove the reply of the tweet
     * @param tweet the tweet's reply
     */
    public void removeReply(Tweet tweet){
        replies.remove(tweet);
        if (!likes.contains(tweet))
        {
            System.err.println("reply not found");
        }
    }

    /**
     * get the tweet's text
     * @return text field
     */
    public String getText() {
        return text;
    }

    /**
     * edit(set) the tweet's text
     * @param text tweet's text
     */
    public void editText(String text) {
        this.text = text;
    }

    /**
     * get the tweet's likes list
     * @return likes field
     */
    public HashSet<User> getLikes() {
        return likes;
    }

    /**
     * get the tweet's reply list
     * @return replies field
     */
    public ArrayList<Tweet> getReplies() {
        return replies;
    }

    /**
     * get the tweet's sendDate
     * @return sendDate field
     */
    public LocalDate getSendDate() {
        return sendDate;
    }

}
