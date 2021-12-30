package main.java.org.ce.ap.server;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void configureServer() {
        UserManager.getInstance();
        TweetManager.getInstance();
    }

    public static void main(String[] args) {
        configureServer();
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket welcomingSocket = new ServerSocket(5000)) {
            System.out.print("Server started.\nWaiting for a client ... ");
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                pool.execute(new ClientHandler(connectionSocket));
                System.out.print("done.\nClosing server ... ");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
    }

}

class ClientHandler implements Runnable {

    private final Socket connectionSocket;

    public ClientHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try {
            System.out.println("here");
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[2048];
            ServerProcessor serverProcessor = new ServerProcessor();
            while (true){
                System.out.println("***");
                int read = in.read(buffer);
                String str = new String(buffer, 0, read);
                JSONObject jsonObject = new JSONObject(str);
                JSONObject response =serverProcessor.processRequest(jsonObject);
                out.write(response.toString().getBytes());
            }
//            int read = in.read(buffer);
//            String str = new String(buffer, 0, read);
//            System.out.println(str);
//            JSONObject jsonObject = new JSONObject(str);
//            System.out.println(jsonObject.get("key"));
//            out.write(str.getBytes());
//            System.out.print("All messages sent.\nClosing client ... ");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionSocket.close();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
