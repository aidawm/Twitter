package org.ce.ap.client.GUI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * update the scene
 */
public class SearchUsers implements Updater{
    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vBox;


    @Override
    public void update(JSONObject jsonObject) throws Exception {
        JSONArray users = jsonObject.getJSONArray("result");
        ViewService.showUsers(users,vBox,scroll);
    }
}
