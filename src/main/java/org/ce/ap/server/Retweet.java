package main.java.org.ce.ap.server;

import com.google.gson.JsonArray;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import org.json.JSONObject;

import java.time.LocalDateTime;

/**
 * The type Retweet.
 */
public class Retweet extends Tweet{
    private Tweet retweet;

    /**
     * create a new object from tweet
     *
     * @param retweet       the retweet
     * @param retweetAuthor is using for setting the retweet's author
     * @param retweetText   is using for setting the retweet's text
     * @throws InvalidCharacterNumberException the invalid character number exception
     */
    public Retweet(Tweet retweet, User retweetAuthor, String retweetText) throws InvalidCharacterNumberException {
        super(retweetAuthor, retweetText);
        this.retweet = retweet;
        retweet.addRetweet(this);
    }

    /**
     * Gets retweet.
     *
     * @return tweet retweet
     */
    public Tweet getRetweet() {
        return retweet;
    }

    @Override
    public String toString() {
        String str = super.getAuthor() +" : \t"+ super.getText()+"\n";
        str+="\t\t"+retweet.getAuthor()+" : \t"+ retweet.getText()+"\n";
        str+="retweets: "+super.getRetweetNumber()+"\t"+"likes: "+super.getLikeNumber()+"\n";
        if(LocalDateTime.now().getDayOfYear()-super.getSendDate().getDayOfYear()<7){
            str+=super.getSendDate().getDayOfWeek()+"\t"+super.getSendDate().getHour()+":"+super.getSendDate().getMinute()+":"+super.getSendDate().getSecond()+"\n";
        }
        else {
            str+=super.getSendDate().getDayOfMonth()+" "+super.getSendDate().getMonth()+"\t"+super.getSendDate().getHour()+"\n";
        }
        if (super.getReplies().size() != 0)
        {
            str+= "replies :\n";
        }
        for (Tweet tweet:super.getReplies())
        {
            str = str + "\t" + tweet;
        }
        str+="----------------------------------------------------\n";

        return str;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tweet",retweet.toJson());
        jsonObject.put("retweet",super.toJson());
        return jsonObject;
    }
}
