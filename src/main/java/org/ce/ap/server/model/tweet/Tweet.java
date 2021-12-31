package main.java.org.ce.ap.server.model.tweet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.model.user.User;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The type Tweet.
 */
public class Tweet {
    private final long id;
    private final User author;
    private JSONObject jsonObject;
    private String text;
    private HashSet<User> likes = new HashSet<>();
    private ArrayList<Tweet> replies = new ArrayList<>();
    private HashSet<JSONObject> retweets = new HashSet<>();
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
        JSONArray jsonArray = (JSONArray) jsonObject.get("retweets");
        for (int i = 0; i < jsonArray.length(); i++) {
            retweets.add((JSONObject) jsonArray.get(i));
        }
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
        JSONObject retweetJson = new JSONObject();
        retweetJson.put("user", retweet.getAuthor());
        retweetJson.put("text", retweet.getText());
        retweetJson.put("sendDate", retweet.getSendDate());
        if (!retweets.contains(retweetJson))
            retweets.add(retweetJson);
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
    public JSONArray toJsonArrayRetweet(HashSet<JSONObject> list) {
        JSONArray jsonList = new JSONArray();
        for (JSONObject retweet : list) {
            jsonList.put(retweet);
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

    /**
     * Find reply tweet.
     *
     * @param id the id
     * @return the tweet
     */
    public Tweet findReply(long id) {
        for (Tweet tweet : replies) {
            if (tweet.getId() == id)
                return tweet;
        }
        return null;
    }

    /**
     * To json json object.
     *
     * @return the json object
     */
    public JSONObject toJson() {
        this.jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("author", author.getUsername());
        jsonObject.put("text", text);
        jsonObject.put("likes", toHashSetLikes(likes));
        jsonObject.put("replies", toJsonArrayTweet(replies));
        jsonObject.put("sendDate", sendDate);
        jsonObject.put("retweets", toJsonArrayRetweet(retweets));
        return jsonObject;
    }


}