package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.GraphicConfig;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONObject;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private VBox parent;
    /**
     * in initialize if have the information of user , the fields will be auto fill
     */
//    @FXML
//    public void initialize() {
//        if(GraphicConfig.isContainProperty("username.remember")){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("username",GraphicConfig.getProperty("username.remember"));
//            jsonObject.put("password", GraphicConfig.getProperty("password.remember"));
//            try {
//                JSONObject response = ConnectionServiceImpl.getConnectionService().request(ServiceWordsEnum.SIGNIN,jsonObject);
//                if(!response.getBoolean("hasError")){
//                    Stage stage=(Stage) parent.getScene().getWindow();
//                    ViewService.showScene(stage,"timeline.page");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    @FXML
    void goToSignIn(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        ViewService.showScene(stage, "signIn.page");
    }

    @FXML
    void goToSignUp(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        ViewService.showScene(stage, "signUp.page");
    }
}
