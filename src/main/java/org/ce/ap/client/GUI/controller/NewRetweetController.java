package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONObject;

import java.io.File;

/**
 * controller for newRetweet.fxml file
 * make a new retweet
 */
public class NewRetweetController {
    ////the tweet that want to retweet it
    private JSONObject tweetJSon;
    ////// the text of the tweet that is retweeted
    @FXML
    private Label retweetedTweet;

    //////text of the retweet
    @FXML
    private TextArea text;


    /**
     * cancel retweeting
     * @param event
     * @throws Exception
     */
    @FXML
    void cancle(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"timeline.page");
    }

    /**
     * update the retweeted tweet information
     * @param tweet
     */
    public void update(JSONObject tweet){
        this.tweetJSon=tweet;
        this.retweetedTweet.setText(tweet.getString("text"));
    }

    /**
     * send the retweet
     * @param event
     * @throws Exception
     */
    @FXML
    void sendNewRetweet(ActionEvent event) throws Exception{
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
        jsonObject.put("tweet", tweetJSon);
        jsonObject.put("text", text.getText());

        JSONObject request= new JSONObject();
        request.put("method", ServiceWordsEnum.RETWEET);
        request.put("parameterValues", jsonObject);
        return request;


    }
}
