package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.ce.ap.client.GUI.ViewService;

public class AboutController {
    @FXML
    /**
     * go to timeline
     */
    void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        ViewService.showScene(stage,"timeline.page");
    }
}
