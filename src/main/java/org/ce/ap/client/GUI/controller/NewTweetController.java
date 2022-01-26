package org.ce.ap.client.GUI.controller;

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.GUI.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * controller for newTweet.fxml file
 * make a new tweet
 */
public class NewTweetController {
    //////text of the tweet
    @FXML
    private TextArea text;

    /**
     * cancel tweeting
     * @param event
     * @throws Exception
     */
    @FXML
    void cancle(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"timeline.page");
    }

    /**
     * send the tweet
     * @param event
     * @throws Exception
     */
    @FXML
    void sendNewTweet(ActionEvent event) throws Exception {
        JSONObject request = toJson();
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(request);
        if (!response.getBoolean("hasError")){
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            ViewService.showScene(stage,"timeline.page");
        }
    }

    /**
     * convert informations to json to send them to server
     * @return
     */
    private JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", text.getText());
        JSONObject request= new JSONObject();
        request.put("method", ServiceWordsEnum.TWEET);
        request.put("parameterValues", jsonObject);
        return request;
    }
}
