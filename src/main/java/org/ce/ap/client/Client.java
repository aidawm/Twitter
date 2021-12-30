package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.impl.CommandParserServiceImpl;
import main.java.org.ce.ap.client.impl.ConnectionServiceImpl;
import org.json.JSONObject;
//import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try (Socket client = new Socket("127.0.0.1", 5000)) {
            System.out.println("Connected to server.");
            CommandParserServiceImpl commandParserService = new CommandParserServiceImpl(client);
            commandParserService.run();
//            UserInterface userInterface = new UserInterface(client);
//            userInterface.run();
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("key", 1234);
//                jsonObject.put("hello", 123);
//                System.out.println(jsonObject);
//                out.write(jsonObject.toString().getBytes());
//                int read = in.read(buffer);
//                String str = new String(buffer, 0, read);
//                System.out.println("server send :" + str);

            System.out.print("All messages sent.\nClosing ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
    }


}