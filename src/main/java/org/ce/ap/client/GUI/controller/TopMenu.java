package org.ce.ap.client.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.ce.ap.client.GUI.GraphicConfig;
import org.ce.ap.client.GUI.ViewService;

public class TopMenu {
    @FXML
    private MenuItem theme;
    @FXML
    private MenuItem screen;

    @FXML
    private MenuBar menu;

    @FXML
    public void initialize(){
    }
    @FXML
    void about(ActionEvent event) throws Exception {
        Stage stage = (Stage) menu.getScene().getWindow();
        ViewService.showScene(stage,"about.page");
    }

    @FXML
    void chooseTheme(ActionEvent event) throws Exception {
        String currentTheme = GraphicConfig.getProperty("theme.mode");
        if(currentTheme.equals("light"))
            currentTheme="dark";
        else
            currentTheme="light";

        GraphicConfig.setProperty("theme.mode",currentTheme);
        Scene scene = menu.getScene();
        ViewService.setTheme(scene);
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void help(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
        GraphicConfig.removeProperty("username.remember");
        GraphicConfig.removeProperty("password.remember");
        Stage stage = (Stage) menu.getScene().getWindow();
        ViewService.showScene(stage,"welcome.page");
    }


    @FXML
    void screenSize(ActionEvent event) {

    }
}
