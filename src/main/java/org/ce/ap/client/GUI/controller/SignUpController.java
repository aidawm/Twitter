package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.ce.ap.client.GUI.ClientConfig;
import org.ce.ap.client.GUI.ConnectionServiceImpl;
import org.ce.ap.client.GUI.GraphicConfig;
import org.ce.ap.client.GUI.ViewService;
import org.json.JSONObject;
import org.ce.ap.ServiceWordsEnum;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SignUpController {
    ////// the lastname of user
    @FXML
    private TextField lastName;

    ////// the password of user
    @FXML
    private PasswordField password;

    ////// the birthdate of user
    @FXML
    private DatePicker birthDate;

    ////// the first name of user
    @FXML
    private TextField firstName;

    ////// the username of user
    @FXML
    private TextField username;

    ///// if this button is selected the information is saved for future
    @FXML
    private CheckBox rememberMe;



    /**
     * cancel to sign in and back to welcome page
     * @param event
     * @throws Exception
     */
    @FXML
    void cancle(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"welcome.page");
    }

    /**
     * sign up into twitter
     * @param event
     * @throws IOException
     */
    @FXML
    void signUp(ActionEvent event) throws Exception {
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
        jsonObject.put("firstName", firstName.getText());
        jsonObject.put("lastName", lastName.getText());
        jsonObject.put("username", username.getText());
        jsonObject.put("password", password.getText());

        LocalDate date = birthDate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String text = date.format(formatter);
        LocalDate parsedDate = LocalDate.parse(text, formatter);

        jsonObject.put("birthDate",parsedDate);

        JSONObject request = new JSONObject();
        request.put("method", ServiceWordsEnum.SIGNUP);
        request.put("parameterValues", jsonObject);
        return request;
    }
}
