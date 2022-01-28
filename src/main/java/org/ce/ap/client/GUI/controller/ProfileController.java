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
import javafx.scene.layout.AnchorPane;
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

/**
 * controller for profilePage.fxml file
 * create user profile
 */
public class ProfileController implements Updater{
    JSONObject userJson ;
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

    @FXML
    private Button follow;

    @FXML
    private Label follower_num;

    @FXML
    private Label following_num;

    @FXML
    private AnchorPane anchor;
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
        userJson =user;

        JSONArray tweets = userInfo.getJSONArray("tweets");

        name.setText(user.getString("firstName")+" "+user.getString("lastName"));
        username.setText(user.getString("username"));
//        biography.setText(user.getString("biography"));
        ViewService.showTweets(tweets,vbox,scroll);

        if(username.getText().equals(GraphicConfig.getProperty("username.logIn"))){
            anchor.getChildren().remove(follow);
        }
        follower_num.setText(String.valueOf(user.getJSONArray("followers").length()));
        following_num.setText(String.valueOf(user.getJSONArray("followings").length()));
        if(isFollower(user.getJSONArray("followers")))
            follow.setText("unfollow");
        else
            follow.setText("follow");

        if(user.getString("biography")==null)
            biography.setText("");
        else
            biography.setText(user.getString("biography"));
    }

    /**
     * follow or unfollow a user
     * @param event
     * @throws Exception
     */
    @FXML
    void followOrUnfollow(ActionEvent event) throws Exception{
        if(follow.getText().equals("follow")){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", userJson);
            JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.FOLLOW,jsonObject);
            if (!response.getBoolean("hasError")){
                Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                ViewService.showScene(stage,"profile.page",(JSONObject) response.getJSONArray("result").get(0));
        }
        }
        else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", userJson);
            JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.UNFOLLOW,jsonObject);
            if (!response.getBoolean("hasError")){
                Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                ViewService.showScene(stage,"profile.page",(JSONObject) response.getJSONArray("result").get(0));
            }
        }
    }

    /**
     * follower or not
     * @param followers
     * @return
     */
    private boolean isFollower(JSONArray followers){
        for(int i=0;i<followers.length();i++){
            if(followers.get(i).equals(GraphicConfig.getProperty("username.logIn")))
                return true;
        }
        return false;
    }
    @FXML
    void searchUsers(ActionEvent event) {

    }
}
