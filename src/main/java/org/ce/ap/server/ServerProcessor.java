package main.java.org.ce.ap.server;

import main.java.org.ce.ap.ServiceWordsEnum;
import main.java.org.ce.ap.server.exceptions.InvalidCharacterNumberException;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ServerProcessor {
    AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
    UserAccount userAccount;

    public ArrayList<JSONObject> toJsonArrayTweet(ArrayList<Tweet> list){
        ArrayList<JSONObject> jsonList = new ArrayList<>();
        for (Tweet tweet : list)
        {
            jsonList.add((tweet).toJson());
        }
        return jsonList;
    }

    public ArrayList<String> getIds(ArrayList<User> users)
    {
        ArrayList<String> ids = new ArrayList<>();
        for (User user : users)
        {
            ids.add(user.getUsername());
        }
        return ids;
    }

    void SwitchCaseUsingJason() throws InvalidCharacterNumberException, NoSuchAlgorithmException {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonParameters = (JSONObject) jsonObject.get("parameterValues");
        ServiceWordsEnum method = (ServiceWordsEnum) jsonObject.get("method");
        switch (method) {
            case SIGNIN:
                userAccount = new UserAccount(authenticationService.signIn());
            case SIGNUP:
                userAccount = new UserAccount(authenticationService.signUp());
            case TWEET:
                userAccount.addNewTweet((String) jsonParameters.get("text"));
                break;
            case REMOVETWEET:
                userAccount.removeTweet((Tweet) jsonParameters.get("tweet"));
                break;
            case RETWEET:
                userAccount.retweet((Tweet) jsonParameters.get("tweet"), (String) jsonParameters.get("text"));
                break;
            case REMOVERETWEET:
                userAccount.removeRetweet((Tweet) jsonParameters.get("tweet"), (Retweet) jsonParameters.get("retweet"));
                break;
            case LIKE:
                userAccount.like((Tweet) jsonParameters.get("tweet"));
                break;
            case DISLIKE:
                userAccount.unLike((Tweet) jsonParameters.get("tweet"));
                break;
            case REPLY:
                userAccount.reply((Tweet) jsonParameters.get("tweet"), (Tweet) jsonParameters.get("replyTweet"));
                break;
            case REMOVEREPLY:
                userAccount.removeReply((Tweet) jsonParameters.get("tweet"), (Tweet) jsonParameters.get("replyTweet"));
                break;

        }
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", userAccount.getUser().toJson());
        jsonObject.put("tweets", toJsonArrayTweet(userAccount.getTweets()));
        jsonObject.put("followers", getIds(userAccount.getUser().getFollowers()));
        jsonObject.put("followings", getIds(userAccount.getUser().getFollowings()));
        return jsonObject;
    }

}

