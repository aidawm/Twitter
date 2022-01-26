package org.ce.ap.client.GUI.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.View;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Tweet {
    private JSONObject tweetJson;
    @FXML
    private Label likeNumber;

    @FXML
    private Label replyNumber;

    @FXML
    private Label retweetNumber;

    @FXML
    private Label time;

    @FXML
    private Label tweetText;

    @FXML
    private Label username;

    public void update(JSONObject tweet) throws Exception {
        this.tweetJson = tweet;
        username.setText(tweet.getString("author"));
        tweetText.setText(tweet.getString("text"));
        time.setText(LocalDateTime.parse(tweetJson.getString("sendDate")).toLocalDate().toString());
        replyNumber.setText(String.valueOf(((JSONArray)tweet.get("replies")).length()));
        likeNumber.setText(String.valueOf(((JSONArray)tweet.get("likes")).length()));
        retweetNumber.setText(String.valueOf(((JSONArray)tweet.get("retweets")).length()));
    }
    @FXML
    void goToProfile(MouseEvent event) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username.getText());
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.USER_INFO,jsonObject);
        if(!response.getBoolean("hasError")){
            Stage stage = (Stage) ((Label)event.getSource()).getScene().getWindow();
            ViewService.showScene(stage,"profile.page",(JSONObject)((JSONArray) response.get("result")).get(0));
        }
    }
    @FXML
    void like(ActionEvent event) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tweet", tweetJson);
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.LIKE,jsonObject);
        if (!response.getBoolean("hasError")){
            int likes = response.getJSONArray("likes").length();
            likeNumber.setText(String.valueOf(likes));
//            update((JSONObject)((JSONArray)response.get("result")).get(0));
        }

    }

    @FXML
    void reply(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"reply.page",tweetJson);
    }

    @FXML
    void retweet(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"newRetweet.page",tweetJson);
    }

}
