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
import org.ce.ap.client.ClientConfig;
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

public class TimelineController extends TopMenu{
    private ArrayList<Tweet> tweetList;
    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vBox;

    @FXML
    public void initialize(){
        vBox.getChildren().clear();
        try {
            showTweets();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", GraphicConfig.getProperty("username.logIn"));
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.USER_INFO,jsonObject);
        if(!response.getBoolean("hasError")){
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            ViewService.showScene(stage,"profile.page",(JSONObject)((JSONArray) response.get("result")).get(0));
        }

    }

    private JSONArray tweetList() throws IOException {
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.TIMELINE,new JSONObject());
        JSONArray tweets = (JSONArray) response.get("result");
        return tweets;
    }

    public void showTweets() throws Exception {
        JSONArray tweets = tweetList();
        ViewService.showTweets(tweets,vBox,scroll);
    }

}
