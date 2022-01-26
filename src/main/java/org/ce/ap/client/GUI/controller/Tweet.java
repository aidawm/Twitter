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
import org.ce.ap.client.GUI.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;

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
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.USER_INFO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username.getText());
        request.put("parameterValues", jsonObject);
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(request);
        if(!response.getBoolean("hasError")){
            Stage stage = (Stage) ((Label)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(new File(ClientConfig.getProperty("profile.page")).toURI().toURL());
            Parent root = fxmlLoader.load();
            ProfileController profileController = (ProfileController) fxmlLoader.getController();
            profileController.update((JSONObject)((JSONArray) response.get("result")).get(0));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void like(ActionEvent event) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tweet", tweetJson);
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.LIKE);
        request.put("parameterValues", jsonObject);

        JSONObject response = ConnectionServiceImpl.getConnectionService().request(request);
        if (!response.getBoolean("hasError")){
            System.out.println((JSONObject)((JSONArray)response.get("result")).get(0));
//            update((JSONObject)((JSONArray)response.get("result")).get(0));
        }

    }

    @FXML
    void reply(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(new File(ClientConfig.getProperty("reply.page")).toURI().toURL());
        Parent root = fxmlLoader.load();
        replyController reply = fxmlLoader.getController();
        reply.update(tweetJson);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void retweet(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader =new FXMLLoader(new File(ClientConfig.getProperty("newRetweet.page")).toURI().toURL());
        Parent root = fxmlLoader.load();
        newRetweetController retweetController = (newRetweetController)fxmlLoader.getController();
        retweetController.update(tweetJson);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
