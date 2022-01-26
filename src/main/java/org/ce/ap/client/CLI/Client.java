package org.ce.ap.client.CLI;

import  org.ce.ap.client.CLI.services.impl.CommandParserServiceImpl;


import java.io.*;
import java.net.Socket;

/**
 * The type Client.
 */
public class Client {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        int port = Integer.parseInt(ClientConfig.getProperty("client.port"));
        String host = ClientConfig.getProperty("server.host");
        try (Socket client = new Socket(host, port)) {
            System.out.println("Connected to server.");
            CommandParserServiceImpl commandParserService = new CommandParserServiceImpl(client);
            commandParserService.run();
            System.out.print("All messages sent.\nClosing ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
    }


}