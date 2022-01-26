package org.ce.ap.client.GUI.controller;

import com.google.gson.JsonArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.GUI.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.GraphicConfig;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;

public class TimelineController {
    private ArrayList<Tweet> tweetList;
    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vBox;

    @FXML
    void refresh(ActionEvent event) throws Exception {
        vBox.getChildren().clear();
        showTweets();
    }
    @FXML
    void newTweet(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"newTweet.page");
    }
    @FXML
    void goToProfile(ActionEvent event) throws Exception{
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.USER_INFO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", GraphicConfig.getProperty("username.logIn"));
        request.put("parameterValues", jsonObject);
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(request);
        if(!response.getBoolean("hasError")){
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(new File(ClientConfig.getProperty("profile.page")).toURI().toURL());
            Parent root = fxmlLoader.load();
            ProfileController profileController = (ProfileController) fxmlLoader.getController();
            profileController.update((JSONObject)((JSONArray) response.get("result")).get(0));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    private JSONObject toJson(){
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.TIMELINE);
        request.put("parameterValues", new JSONObject());
        return request;
    }
    private JSONArray tweetList() throws IOException {
        JSONObject request = toJson();
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(request);
        JSONArray tweets = (JSONArray) response.get("result");
        return tweets;
    }

    private void showTweets() throws Exception {
        JSONArray tweets = tweetList();
        if(tweets.length()==0){
            Label label = new Label("no tweet yet!");
            vBox.getChildren().add(label);
            scroll.setContent(vBox);
        }
        for(int i=0;i<tweets.length();i++){
            FXMLLoader loader;
            Parent tweet;

            if(((JSONObject)tweets.get(i)).keySet().contains("retweetedTweet")){
                loader = new FXMLLoader(new File(ClientConfig.getProperty("retweet.frame")).toURI().toURL());
                tweet=loader.load();
                Retweet tweetController = (Retweet) loader.getController();
                tweetController.update((JSONObject) tweets.get(i));
            }
            else {
                loader = new FXMLLoader(new File(ClientConfig.getProperty("tweet.frame")).toURI().toURL());
                tweet=loader.load();
                Tweet tweetController =(Tweet) loader.getController();
                tweetController.update((JSONObject) tweets.get(i));
            }

            vBox.getChildren().add(tweet);
        }
        scroll.setContent(vBox);
    }
}
