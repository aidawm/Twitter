package org.ce.ap.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.client.ClientConfig;
import org.ce.ap.client.GUI.controller.ProfileController;
import org.ce.ap.client.GUI.controller.Retweet;
import org.ce.ap.client.GUI.controller.Tweet;
import org.ce.ap.client.GUI.controller.Updater;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;

public class ViewService {

    public static void showScene(Stage stage, String viewFxml , JSONObject jsonObject) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(ClientConfig.getProperty(viewFxml)).toURI().toURL());
        Parent root = fxmlLoader.load();
        Updater updater = (Updater) fxmlLoader.getController();
        updater.update(jsonObject);
        Scene scene = new Scene(root);
        setTheme(scene);
        stage.setScene(scene);
        stage.show();
    }

    public static void showScene(Stage stage, String viewFxml) throws Exception{
        Parent root = FXMLLoader.load(new File(ClientConfig.getProperty(viewFxml)).toURI().toURL());
        showScene(stage,root);
    }
    public static void showScene(Stage stage, Parent root) throws Exception{
        Scene scene =new Scene(root);
        setTheme(scene);
        stage.setScene(scene);
        stage.show();
    }

    public static void setTheme(Scene scene){
        if(GraphicConfig.getProperty("theme.mode").equals("light"))
            scene.getStylesheets().remove("styles.css");
        else
            scene.getStylesheets().add("styles.css");
    }

    public static void showTweets(JSONArray tweets, VBox vBox, ScrollPane scroll) throws Exception {
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
