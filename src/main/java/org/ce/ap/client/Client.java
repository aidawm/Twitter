package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.services.impl.CommandParserServiceImpl;


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
        try (Socket client = new Socket("127.0.0.1", 5000)) {
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