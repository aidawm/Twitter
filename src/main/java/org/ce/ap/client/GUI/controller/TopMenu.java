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

    /**
     * about us page
     * @param event
     * @throws Exception
     */
    @FXML
    void about(ActionEvent event) throws Exception {
        Stage stage = (Stage) menu.getScene().getWindow();
        ViewService.showScene(stage,"about.page");
    }

    /**
     * swich between dark and light mode
     * @param event
     * @throws Exception
     */
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

    /**
     * exit from twitter
     * @param event
     */
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * help page
     * @param event
     */
    @FXML
    void help(ActionEvent event) {

    }

    /**
     * log out from twitter
     * @param event
     */
    @FXML
    void logOut(ActionEvent event) throws Exception {
        GraphicConfig.removeProperty("username.remember");
        GraphicConfig.removeProperty("password.remember");
        Stage stage = (Stage) menu.getScene().getWindow();
        ViewService.showScene(stage,"welcome.page");
    }


    /**
     * change screen size
     * @param event
     */
    @FXML
    void screenSize(ActionEvent event) {

    }
}
