package org.ce.ap.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ce.ap.client.ClientConfig;

import java.io.File;

public class ViewService {

    public static void showScene(Stage stage, String viewFxml) throws Exception{
        Parent root = FXMLLoader.load(new File(ClientConfig.getProperty(viewFxml)).toURI().toURL());
        root.setStyle(String.format("-fx-background-color: %s;",getBackgroundColor()));
        stage.setScene(new Scene(root));
        stage.show();
    }


    public static String getBackgroundColor(){
        return ClientConfig.getProperty("color.background."+GraphicConfig.getProperty("theme.mode"));
    }

}
