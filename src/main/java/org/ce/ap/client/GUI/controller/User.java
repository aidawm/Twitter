package org.ce.ap.client.GUI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.GraphicConfig;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class User implements Updater{
    @FXML
    private Label name;

    @FXML
    private Label username;


    @FXML
    void goToProfile(MouseEvent event) throws Exception {
        System.out.println(username.getText());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username.getText());
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.USER_INFO,jsonObject);
        if(!response.getBoolean("hasError")){
            Stage stage = (Stage) ((Label)event.getSource()).getScene().getWindow();
            ViewService.showScene(stage,"profile.page",(JSONObject)((JSONArray) response.get("result")).get(0));
        }
    }

    @Override
    public void update(JSONObject jsonObject) throws Exception {
        name.setText(jsonObject.getString("firstName")+" "+jsonObject.getString("lastName"));
        username.setText(jsonObject.getString("username"));
    }
}
