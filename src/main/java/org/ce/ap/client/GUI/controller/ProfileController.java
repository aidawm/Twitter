package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.client.ClientConfig;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * controller for profilePage.fxml file
 * create user profile
 */
public class ProfileController implements Updater{
    ///// user's biography
    @FXML
    private Label biography;
    ///// user's profile image
    @FXML
    private ImageView image;

    @FXML
    private VBox vbox;
    ///// user's name
    @FXML
    private Label name;

    @FXML
    private ScrollPane scroll;
    ///// user's username
    @FXML
    private Label username;

    /**
     * go to the timeline
     * @param event
     * @throws Exception
     */
    @FXML
    void goBack(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"timeline.page");
    }
    ///TODO : find how change image!
    @FXML
    void changeImage(MouseEvent event) throws IOException {
    }

    /**
     * update profile information
     * @param userInfo
     * @throws Exception
     */
    @Override
    public void update(JSONObject userInfo) throws Exception {
        JSONObject user = userInfo.getJSONObject("user");
        JSONArray tweets = userInfo.getJSONArray("tweets");

        name.setText(user.getString("firstName")+" "+user.getString("lastName"));
        username.setText(user.getString("username"));
//        biography.setText(user.getString("biography"));
        ViewService.showTweets(tweets,vbox,scroll);
    }

}
