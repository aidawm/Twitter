package org.ce.ap.client.GUI.controller;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * control replyPage.fxml
 * manage replies of a tweet
 */
public class ReplyController {
    private JSONObject tweetJson;
    /////// new reply text
    @FXML
    private TextField replyText;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vbox;

    /**
     * update the information
     * @param tweetJson
     * @throws Exception
     */
    public void update(JSONObject tweetJson) throws Exception {
        this.tweetJson=tweetJson;
        JSONArray replies = (JSONArray) tweetJson.get("replies");
        if (replies.length() == 0) {
            Label label = new Label("no tweet yet!");
            vbox.getChildren().add(label);
            scroll.setContent(vbox);
        }
        for (int i = 0; i < replies.length(); i++) {
            FXMLLoader loader = new FXMLLoader(new File(ClientConfig.getProperty("tweet.frame")).toURI().toURL());
            Parent tweet = loader.load();
            Tweet tweetController = (Tweet) loader.getController();
            System.out.println(tweetController);
            tweetController.update((JSONObject) replies.get(i));
            vbox.getChildren().add(tweet);
        }
        scroll.setContent(vbox);
    }

    /**
     * go to the timeline page
     * @param event
     * @throws Exception
     */
    @FXML
    void goBack(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"timeline.page");
    }

    /**
     * send a new reply
     * @param event
     * @throws Exception
     */
    @FXML
    void sendReply(ActionEvent event) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tweet",tweetJson);
        jsonObject.put("text",replyText.getText());
        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.REPLY);
        request.put("parameterValues", jsonObject);
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(request);
        if (!response.getBoolean("hasError"))
            update((JSONObject) ((JSONArray)response.get("result")).get(0));
    }

}
