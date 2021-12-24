package main.java.org.ce.ap.server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import org.json.JSONObject;

/**
 * The type Tweet.
 */
public class Tweet implements JsonInterface {
    private final long id;
    private long retweetedId;
    private boolean isRetweet; //0 for tweet & 1 for retweet
    private final User author;
    private JSONObject jsonObject;
    private String text;
    private HashSet<User> likes = new HashSet<>();
    private ArrayList<Tweet> replies = new ArrayList<>();
    private ArrayList<Retweet> retweets = new ArrayList<>();
    private final LocalDateTime sendDate;

    /**
     * create a new object from tweet
     *
     * @param author is using for setting the tweet's author
     * @param text   is using for setting the tweet's text
     * @param id     the id
     * @throws InvalidCharacterNumberException the invalid character number exception
     */
    public Tweet(User author, String text, long id) throws InvalidCharacterNumberException {
        checkTweetCharacters(text);
        this.author = author;
        this.text = text;
        this.id = id;
        this.sendDate = LocalDateTime.now();
        retweetedId = 0;
//        this.isRetweet = isRetweet;
//        this.likes = new HashSet<>();
//        this.replies = new ArrayList<>();
//        retweets = new ArrayList<>();
    }

    /**
     * Instantiates a new Tweet.
     *
     * @param jsonObject the json object
     * @param author     the author
     */
    public Tweet(JSONObject jsonObject, User author) {
        this.jsonObject = jsonObject;
        this.author = author;
        this.text = jsonObject.getString("text");
        this.sendDate = LocalDateTime.parse(jsonObject.getString("sendDate"));
        this.id = jsonObject.getLong("id");
    }

    /**
     * Sets likes.
     *
     * @param likes the likes
     */
    public void setLikes(HashSet<User> likes) {
        this.likes = likes;
    }

    /**
     * Sets replies.
     *
     * @param replies the replies
     */
    public void setReplies(ArrayList<Tweet> replies) {
        this.replies = replies;
    }

    /**
     * Sets retweets.
     *
     * @param retweets the retweets
     */
    public void setRetweets(ArrayList<Retweet> retweets) {
        this.retweets = retweets;
    }

    /**
     * Like tweet.
     *
     * @param liker is using for adding liker
     */
    public void likeTweet(User liker) {
        likes.add(liker);
    }

    /**
     * Remove like.
     *
     * @param liker is using for removing liker
     */
    public void removeLike(User liker) {
        if (!likes.contains(liker)) {
            System.err.println("liker not found");
        }
        likes.remove(liker);

    }

    /**
     * add new reply for the tweet
     *
     * @param tweet the tweet's reply
     */
    public void addNewReply(Tweet tweet) {
        replies.add(tweet);
    }

    /**
     * remove the reply of the tweet
     *
     * @param tweet the tweet's reply
     */
    public void removeReply(Tweet tweet) {
        if (!likes.contains(tweet)) {
            System.err.println("reply not found");
        }
        replies.remove(tweet);
    }

    /**
     * get the tweet's text
     *
     * @return text field
     */
    public String getText() {
        return text;
    }

    /**
     * edit(set) the tweet's text
     *
     * @param text tweet's text
     */
    public void editText(String text) {
        this.text = text;
    }

    /**
     * get the tweet's likes list
     *
     * @return likes field
     */
    public HashSet<User> getLikes() {
        return likes;
    }

    /**
     * get the tweet's reply list
     *
     * @return replies field
     */
    public ArrayList<Tweet> getReplies() {
        return replies;
    }

    /**
     * get the tweet's sendDate
     *
     * @return sendDate field
     */
    public LocalDateTime getSendDate() {
        return sendDate;
    }

    /**
     * Gets author.
     *
     * @return tweet 's author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @param text tweet's text
     * @throws InvalidCharacterNumberException if the number is greater than 256 or the text is null
     */
    private void checkTweetCharacters(String text) throws InvalidCharacterNumberException {
        if (text.length() == 0 || text == null) {
            throw new InvalidCharacterNumberException("Text shouldn't be empty!");
        } else if (text.length() > 256) {
            throw new InvalidCharacterNumberException("The number of characters is invalid, it should be lower than 256 characters!");
        }
    }

    /**
     * Add retweet.
     *
     * @param retweet is the tweet that we want to publish this tweet again
     */
    public void addRetweet(Retweet retweet) {
        if (!retweets.contains(retweet))
            retweets.add(retweet);
    }

    /**
     * Gets retweet number.
     *
     * @return the retweet number
     */
    public int getRetweetNumber() {
        return retweets.size();
    }

    /**
     * Gets like number.
     *
     * @return the like number
     */
    public int getLikeNumber() {
        return likes.size();
    }

    /**
     * Remove retweet.
     *
     * @param retweet is the tweet that we want to remove it
     */
    public void removeRetweet(Tweet retweet) {
        retweets.remove(retweet);
    }

    /**
     * Sets retweeted id.
     *
     * @param retweetedId the retweeted id
     */
    public void setRetweetedId(long retweetedId) {
        if (this.retweetedId == 0)
            this.retweetedId = retweetedId;
    }

    @Override
    public String toString() {
        String str = author + " : \t" + text + "\n";
        str += "retweets: " + retweets.size() + "\t" + "likes: " + likes.size() + "\n";
        if (LocalDateTime.now().getDayOfYear() - sendDate.getDayOfYear() < 7) {
            str += sendDate.getDayOfWeek() + "\t" + sendDate.getHour() + ":" + sendDate.getMinute() + ":" + sendDate.getSecond() + "\n";
        } else {
            str += sendDate.getDayOfMonth() + " " + sendDate.getMonth() + "\t" + sendDate.getHour() + "\n";
        }

        if (replies.size() != 0) {
            str += "replies :\n";
        }
        for (Tweet tweet : replies) {
            str = str + "\t" + tweet;
        }
        str += "----------------------------------------------------\n";
        return str;
    }

    /**
     * To hash set likes hash set.
     *
     * @param likes the likes
     * @return the hash set
     */
    public HashSet<String> toHashSetLikes(HashSet<User> likes) {
        HashSet<String> jsonHashSet = new HashSet<>();
        for (User user : likes) {
            jsonHashSet.add(user.getUsername());
        }
        return jsonHashSet;
    }

    /**
     * To json array tweet array list.
     *
     * @param list the list
     * @return the array list
     */
    public ArrayList<JSONObject> toJsonArrayTweet(ArrayList<Tweet> list) {
        ArrayList<JSONObject> jsonList = new ArrayList<>();
        for (Tweet tweet : list) {
            jsonList.add((tweet).toJson());
        }

        return jsonList;
    }

    /**
     * To json array retweet array list.
     *
     * @param list the list
     * @return the array list
     */
    public ArrayList<JSONObject> toJsonArrayRetweet(ArrayList<Retweet> list) {
        ArrayList<JSONObject> jsonList = new ArrayList<>();
        for (Retweet retweet : list) {
            jsonList.add((retweet).toJson());
        }

        return jsonList;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    @Override
    public JSONObject toJson() {
        this.jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("author", author.getUsername());
        jsonObject.put("text", text);
        jsonObject.put("likes", toHashSetLikes(likes));
        jsonObject.put("replies", toJsonArrayTweet(replies));
        jsonObject.put("sendDate", sendDate);
        jsonObject.put("retweets",toJsonArrayRetweet(retweets));
        return jsonObject;
    }


}
