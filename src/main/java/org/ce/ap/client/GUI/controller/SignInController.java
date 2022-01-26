package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.ce.ap.ServiceWordsEnum;
import org.ce.ap.client.GUI.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.GraphicConfig;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * to sign in to the twitter
 */
public class SignInController {
    ///// the  password of user
    @FXML
    private PasswordField password;

    ////// the username of user
    @FXML
    private TextField username;


    ///// if this button is selected the information is saved for future
    @FXML
    private CheckBox rememberMe;

    /**
     * in initialize if have the information of user , the fields will be auto fill
     */
    @FXML
    public void initialize() {
        if(GraphicConfig.isContainProperty("username.remember")){
            username.setText(GraphicConfig.getProperty("username.remember"));
            password.setText(GraphicConfig.getProperty("password.remember"));
        }
    }

    /**
     * cancel to sign in and back to welcome page
     * @param event
     * @throws Exception
     */
    @FXML
    void cancle(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"welcome.page");

    }

    /**
     * sign in into twitter
     * @param event
     * @throws IOException
     */
    @FXML
    void signIn(ActionEvent event) throws Exception {
        JSONObject request = toJson();
        JSONObject response = ConnectionServiceImpl.getConnectionService().request(request);
        if(!response.getBoolean("hasError")){
            GraphicConfig.setProperty("username.logIn",username.getText());
            if(rememberMe.isSelected()){
                GraphicConfig.setProperty("username.remember",username.getText());
                GraphicConfig.setProperty("password.remember",password.getText());
            }
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            ViewService.showScene(stage,"timeline.page");
        }
    }

    /**
     * convert informations to json
     * @return
     * @throws IOException
     */
    private JSONObject toJson() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username.getText());
        jsonObject.put("password", password.getText());

        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.SIGNIN);
        request.put("parameterValues", jsonObject);
        return request;
    }
}
