package main.java.org.ce.ap.server.middleClasses;

import main.java.org.ce.ap.ServiceWordsEnum;
import main.java.org.ce.ap.server.exceptions.*;
import main.java.org.ce.ap.server.managers.TweetManager;
import main.java.org.ce.ap.server.managers.UserManager;
import main.java.org.ce.ap.server.services.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.model.tweet.Retweet;
import main.java.org.ce.ap.server.model.tweet.Tweet;
import main.java.org.ce.ap.server.model.user.User;
import org.json.*;
import com.google.gson.Gson;
import main.java.org.ce.ap.server.DataBase.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The type Server processor.
 */
public class ServerProcessor {
    private AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
    private TweetManager tweetManager = TweetManager.getInstance();
    private static UserAccount userAccount;
    private UserManager userManager = UserManager.getInstance();
    private JSONObject response = new JSONObject();
    private LogDataBase logDataBase = new LogDataBase();


    /**
     * To json array tweet json array.
     *
     * @param list the list
     * @return the json array
     */
    public JSONArray toJsonArrayTweet(ArrayList<Tweet> list) {
        JSONArray jsonList = new JSONArray();
        for (Tweet tweet : list) {
            jsonList.put(tweet.toJson());
        }
        return jsonList;
    }

    /**
     * To json array user json array.
     *
     * @param list the list
     * @return the json array
     */
    public JSONArray toJsonArrayUser(ArrayList<User> list) {
        JSONArray jsonList = new JSONArray();
        for (User user : list) {
            jsonList.put(user.toJson());
        }
        return jsonList;
    }

    /**
     * Gets ids.
     *
     * @param users the users
     * @return the ids
     */
    public ArrayList<String> getIds(ArrayList<User> users) {
        ArrayList<String> ids = new ArrayList<>();
        for (User user : users) {
            ids.add(user.getUsername());
        }
        return ids;
    }

    private Tweet findTweet(JSONObject jsonParameters) {
        long id;
        if (((JSONObject) jsonParameters.get("tweet")).keySet().contains("retweetedTweet")) {
            id = ((JSONObject) ((JSONObject) jsonParameters.get("tweet")).get("newTweet")).getLong("id");
        } else
            id = ((JSONObject) jsonParameters.get("tweet")).getLong("id");

        return tweetManager.findTweet(id);
    }

    /**
     * Process request json object.
     *
     * @param jsonObject the json object
     * @return the json object
     */
    public JSONObject processRequest(JSONObject jsonObject) {

        this.response = new JSONObject();
        JSONObject jsonParameters = (JSONObject) jsonObject.get("parameterValues");
        System.out.println();
        Gson gson = new Gson();
        ServiceWordsEnum method = gson.fromJson(jsonObject.getString("method"), ServiceWordsEnum.class);
        System.out.println(method);
        String username = "";
        if (userAccount != null) {
            username = userAccount.getUser().getUsername();
        }
        switch (method) {
            case SIGNIN:
                return signIn(jsonParameters);
//                tweetManager.getDataFromDatabase(userAccount.getUser());

            case SIGNUP:

                return signUp(jsonParameters);

            case TIMELINE:
                logDataBase.writeLogFile(false, username, "Attempt TIMELINE");
                try {
                    ArrayList<Tweet> tweets = userAccount.getTweets();
                    response.put("hasError", false);
                    logDataBase.writeLogFile(false, username, "Successful TIMELINE");
                    response.put("count", tweets.size());
                    response.put("result", toJsonArrayTweet(tweets));
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "TIMELINE Failed");
                    System.out.println(e);
                } finally {
                    return response;
                }
            case SHOW_MY_TWEETS:
                logDataBase.writeLogFile(false, username, "Attempt SHOW USER TWEETS");
                try {
                    ArrayList<Tweet> tweets = tweetManager.findTweetsByAuthor(userAccount.getUser());
                    response.put("hasError", false);
                    logDataBase.writeLogFile(false, username, "Successful SHOW_USER_TWEETS");
                    response.put("count", tweets.size());
                    response.put("result", toJsonArrayTweet(tweets));
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed SHOW_USER_TWEETS");
                    System.out.println(e);
                } finally {
                    return response;
                }
            case TWEET:
                logDataBase.writeLogFile(false, username, "Attempt TWEET");
                try {
                    Tweet tweet = userAccount.addNewTweet(jsonParameters.getString("text"));
                    response.put("hasError", false);
                    logDataBase.writeLogFile(false, username, "Successful TWEET");
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(tweet.toJson());
                    response.put("result", jsonArray);
                } catch (InvalidCharacterNumberException e) {
                    response.put("hasError", true);
                    response.put("errorCode", "InvalidCharacterNumberException");
                    logDataBase.writeLogFile(true, username, "Invalid Character Number");
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "AuthenticationException");
                    response.put("hasError", true);
                    response.put("errorCode", "AuthenticationException");
                } finally {
                    return response;
                }

            case REMOVETWEET:
                logDataBase.writeLogFile(false, username, "Attempt REMOVE TWEET");
                try {
                    Tweet tweet = findTweet(jsonParameters);
                    if (tweet instanceof Retweet)
                        userAccount.removeRetweet(findTweet((JSONObject) jsonParameters.get("retweetedTweet")), (Retweet) tweet);
                    else
                        userAccount.removeTweet(tweet);
                    logDataBase.writeLogFile(false, username, "Successful REMOVE TWEET");
                    response.put("hasError", false);
                    response.put("count", 0);
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed REMOVE TWEET");
                    System.err.println(e);
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }

            case RETWEET:
                logDataBase.writeLogFile(false, username, "Attempt RETWEET");
                try {
                    Retweet retweet = userAccount.retweet(findTweet(jsonParameters), jsonParameters.getString("text"));
                    logDataBase.writeLogFile(false, username, "Successful RETWEET");
                    response.put("hasError", false);
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(retweet.toJson());
                    response.put("result", jsonArray);
                } catch (InvalidCharacterNumberException e) {
                    logDataBase.writeLogFile(true, username, "Invalid Character Number");
                    response.put("hasError", true);
                    response.put("errorCode", "InvalidCharacterNumberException");
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Authentication Exception");
                    response.put("hasError", true);
                    response.put("errorCode", "AuthenticationException");
                } finally {
                    return response;
                }

            case REMOVERETWEET:
                logDataBase.writeLogFile(false, username, "Attempt REMOVE RETWEET");
                try {
                    Tweet tweet = gson.fromJson(jsonParameters.getString("tweet"), Tweet.class);
                    Retweet retweet = gson.fromJson(jsonParameters.getString("retweet"), Retweet.class);
                    userAccount.removeRetweet(tweet, retweet);
                    logDataBase.writeLogFile(false, username, "Successful REMOVE RETWEET");
                    response.put("hasError", false);
                    response.put("count", 0);
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed REMOVE RETWEET");
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }

            case LIKE:
                logDataBase.writeLogFile(false, username, "Attempt LIKE");
                try {
                    Tweet tweet = findTweet(jsonParameters);
                    userAccount.like(tweet);
                    logDataBase.writeLogFile(false, username, "Successful LIKE");
                    response.put("hasError", false);
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(tweet.toJson());
                    response.put("result", jsonArray);
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed LIKE");
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }

            case DISLIKE:
                logDataBase.writeLogFile(false, username, "Attempt DISLIKE");
                try {
                    Tweet tweet = findTweet(jsonParameters);
                    userAccount.unLike(tweet);
                    logDataBase.writeLogFile(false, username, "Successful DISLIKE");
                    response.put("hasError", false);
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(tweet.toJson());
                    response.put("result", jsonArray);
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed DISLIKE");
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }
            case REPLY:
                logDataBase.writeLogFile(false, username, "Attempt REPLY");
                try {
                    Tweet tweet = findTweet(jsonParameters);
                    userAccount.reply(tweet, jsonParameters.getString("text"));
                    logDataBase.writeLogFile(false, username, "Successful REPLY");
                    response.put("hasError", false);
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(tweet.toJson());
                    response.put("result", jsonArray);
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed REPLY");
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }
            case REMOVEREPLY:
                logDataBase.writeLogFile(false, username, "Attempt REMOVE REPLY");
                try {
                    Tweet tweet = findTweet(jsonParameters);
                    long replyId = ((JSONObject) jsonParameters.get("reply")).getLong("id");
                    userAccount.removeReply(tweet, tweet.findReply(replyId));
                    logDataBase.writeLogFile(false, username, "Successful REMOVE REPLY");
                    response.put("hasError", false);
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(tweet.toJson());
                    response.put("result", jsonArray);
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed REMOVE REPLY");
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }
            case FOLLOW:
                logDataBase.writeLogFile(false, username, "Attempt FOLLOW");
                try {
                    userAccount.addFollowing(userManager.findUser(((JSONObject) jsonParameters.get("user")).getString("username")));
                    logDataBase.writeLogFile(false, username, "Successful FOLLOW");
                    response.put("hasError", false);
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(userAccount.getUser().toJson());
                    response.put("result", jsonArray);
                } catch (InvalidUsernameException e) {
                    logDataBase.writeLogFile(true, username, "Invalid Username");
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }
            case UNFOLLOW:
                logDataBase.writeLogFile(false, username, "Attempt UNFOLLOW");
                try {
                    userAccount.removeFollowing(userManager.findUser(((JSONObject) jsonParameters.get("user")).getString("username")));
                    logDataBase.writeLogFile(false, username, "Successful UNFOLLOW");
                    response.put("hasError", false);
                    response.put("count", 1);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(userAccount.getUser().toJson());
                    response.put("result", jsonArray);
                } catch (InvalidUsernameException e) {
                    logDataBase.writeLogFile(true, username, "Invalid Username");
                    response.put("hasError", true);
                    response.put("errorCode", "NotAccessException");
                } finally {
                    return response;
                }
            case SHOW_USERS:
                logDataBase.writeLogFile(false, username, "Attempt SHOW USERS");
                try {
                    ArrayList<User> users = userManager.getUsers();
                    logDataBase.writeLogFile(false, username, "Successful SHOW USERS");
                    response.put("hasError", false);
                    response.put("count", users.size());
                    response.put("result", toJsonArrayUser(users));
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed SHOW USERS");
                    System.out.println(e);
                } finally {
                    return response;
                }
            case SHOW_FOLLOWINGS:
                logDataBase.writeLogFile(false, username, "Attempt SHOW FOLLOWINGS");
                try {
                    ArrayList<User> users = userAccount.getUser().getFollowings();
                    logDataBase.writeLogFile(false, username, "Successful SHOW FOLLOWINGS");
                    response.put("hasError", false);
                    response.put("count", users.size());
                    response.put("result", toJsonArrayUser(users));
                } catch (Exception e) {
                    logDataBase.writeLogFile(true, username, "Failed SHOW FOLLOWINGS");
                    System.out.println(e);
                } finally {
                    return response;
                }

        }
        return null;
    }

    private JSONObject signUp(JSONObject jsonParameters) {
        String username = jsonParameters.getString("username");
        logDataBase.writeLogFile(false, username, "Attempt SIGN_UP");
        try {
            User user = authenticationService.signUp(jsonParameters.getString("firstName")
                    , jsonParameters.getString("lastName"), jsonParameters.getString("username"),
                    jsonParameters.getString("password"),
                    LocalDate.parse(jsonParameters.getString("birthDate")));
            userAccount = new UserAccount(user);
            response.put("hasError", false);
            logDataBase.writeLogFile(false, username, "Successful SIGN_UP");
            response.put("count", 1);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(user.toJson());
            response.put("result", jsonArray);
        } catch (SignUpExceptions e) {
            for (String s : e.getMessages()) {
                logDataBase.writeLogFile(true, username, s);
            }
            response.put("hasError", true);
            response.put("count", e.getMessages().size());
            response.put("errorCode", e.getMessages());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println(response);
            return response;
        }
    }

    private JSONObject signIn(JSONObject jsonParameters) {
        String username = jsonParameters.getString("username");
        logDataBase.writeLogFile(false, username, "Attempt LOG_IN");
        try {
            User user = authenticationService.signIn(jsonParameters.getString("username"),
                    jsonParameters.getString("password"));
            userAccount = new UserAccount(user);
            response.put("hasError", false);
            logDataBase.writeLogFile(false, username, "Successful LOG_IN");
            response.put("count", 1);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(user.toJson());
            response.put("result", jsonArray);
        } catch (InvalidUsernameException e) {
            System.out.println(e.getLocalizedMessage());
            logDataBase.writeLogFile(true, username, "Wrong Username");
            response.put("hasError", true);
            response.put("count", 1);
            response.put("errorCode", "InvalidUsernameException");
        } catch (InvalidPasswordException e) {
            System.out.println(e.getLocalizedMessage());
            logDataBase.writeLogFile(true, username, "Wrong Password");
            response.put("hasError", true);
            response.put("count", 1);
            response.put("errorCode","InvalidPasswordException");
        } finally {
            return response;
        }
    }

    /**
     * To json object json object.
     *
     * @return the json object
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", userAccount.getUser().toJson());
        jsonObject.put("tweets", toJsonArrayTweet(userAccount.getTweets()));
        jsonObject.put("followers", getIds(userAccount.getUser().getFollowers()));
        jsonObject.put("followings", getIds(userAccount.getUser().getFollowings()));
        return jsonObject;
    }


}

