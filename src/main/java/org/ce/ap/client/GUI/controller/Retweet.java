package org.ce.ap.client.GUI.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.ce.ap.client.GUI.ClientConfig;
import org.json.JSONObject;

import java.io.File;

/**
 * a frame for showing a retweet
 */
public class Retweet extends Tweet{

    /**
     * the tweet that is retweeted
     */
    @FXML
    private Pane retweetedTweet;


    /**
     * update the informations
     * @param retweet
     * @throws Exception
     */
    @Override
    public void update(JSONObject retweet) throws Exception{
        JSONObject retweetedTweet = retweet.getJSONObject("retweetedTweet");
        JSONObject newTweet = retweet.getJSONObject("newTweet");
        super.update(newTweet);

        FXMLLoader loader = new FXMLLoader(new File(ClientConfig.getProperty("tweet.frame")).toURI().toURL());
        Parent tweet = loader.load();
        Tweet tweetController =(Tweet) loader.getController();

        tweetController.update((retweetedTweet));
        this.retweetedTweet.getChildren().add(tweet);
    }

}
