package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.ce.ap.client.GUI.ViewService;

public class WelcomeController {

    @FXML
    public void initialize(){

    }
    @FXML
    void goToSignIn(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"signIn.page");
    }

    @FXML
    void goToSignUp(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"signUp.page");
    }
}
