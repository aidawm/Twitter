package main.java.org.ce.ap.server.model.tweet;

import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.model.user.User;
import org.json.JSONObject;

import java.time.LocalDateTime;

/**
 * The type Retweet.
 */
public class Retweet extends Tweet {
    private Tweet retweetedTweet;

    /**
     * create a new object from tweet
     *
     * @param retweet       the retweet
     * @param retweetAuthor is using for setting the retweet's author
     * @param retweetText   is using for setting the retweet's text
     * @param id            the id
     * @throws InvalidCharacterNumberException the invalid character number exception
     */
    public Retweet(Tweet retweet, User retweetAuthor, String retweetText, Long id) throws InvalidCharacterNumberException {
        super(retweetAuthor, retweetText, id);

        this.retweetedTweet = retweet;
        retweet.addRetweet(this);

    }

    /**
     * Instantiates a new Retweet.
     *
     * @param jsonObject the json object
     * @param auther     the auther
     * @param tweet      the tweet
     */
    public Retweet(JSONObject jsonObject, User auther, Tweet tweet) {
        super((JSONObject) jsonObject.get("newTweet"), auther);
        this.retweetedTweet = tweet;
    }

    /**
     * Gets retweet.
     *
     * @return tweet retweet
     */
    public Tweet getRetweetedTweet() {
        return retweetedTweet;
    }

    @Override
    public String toString() {
        String str = super.getAuthor() + " : \t" + super.getText() + "\n";
        str += "\t\t" + retweetedTweet.getAuthor() + " : \t" + retweetedTweet.getText() + "\n";
        str += "retweets: " + super.getRetweetNumber() + "\t" + "likes: " + super.getLikeNumber() + "\n";
        if (LocalDateTime.now().getDayOfYear() - super.getSendDate().getDayOfYear() < 7) {
            str += super.getSendDate().getDayOfWeek() + "\t" + super.getSendDate().getHour() + ":" + super.getSendDate().getMinute() + ":" + super.getSendDate().getSecond() + "\n";
        } else {
            str += super.getSendDate().getDayOfMonth() + " " + super.getSendDate().getMonth() + "\t" + super.getSendDate().getHour() + "\n";
        }
        if (super.getReplies().size() != 0) {
            str += "replies :\n";
        }
        for (Tweet tweet : super.getReplies()) {
            str = str + "\t" + tweet;
        }
        str += "----------------------------------------------------\n";

        return str;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retweetedTweet", retweetedTweet.toJson());
        jsonObject.put("newTweet", super.toJson());
        return jsonObject;
    }
}
