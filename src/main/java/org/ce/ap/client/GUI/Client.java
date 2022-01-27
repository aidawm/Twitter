package org.ce.ap.client.GUI;


import javafx.application.Application;
import javafx.stage.Stage;
import org.ce.ap.client.ClientConfig;

import java.io.IOException;
import java.net.Socket;

/**
 * The type Client.
 */
public class Client extends Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        int port = Integer.parseInt(ClientConfig.getProperty("client.port"));
        String host = ClientConfig.getProperty("server.host");
        try (Socket client = new Socket(host, port)) {
           ConnectionServiceImpl.makeConnectionService(client);
            System.out.println("Connected to server.");
            launch(args);
            System.out.print("All messages sent.\nClosing ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
    }


    /**
     * start javafx
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Twitter");
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        ViewService.showScene(primaryStage,"welcome.page");
    }
}