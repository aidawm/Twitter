package main.java.org.ce.ap.server;


import main.java.org.ce.ap.server.managers.TweetManager;
import main.java.org.ce.ap.server.managers.UserManager;
import main.java.org.ce.ap.server.middleClasses.ServerProcessor;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Server.
 */
public class Server {

    /**
     * Configure server.
     */
    public static void configureServer() {
        UserManager.getInstance();
        TweetManager.getInstance();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        configureServer();
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket welcomingSocket = new ServerSocket(5000)) {
            System.out.print("Server started.\nWaiting for a client ... ");
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                pool.execute(new Thread(new ClientHandler(connectionSocket)));

            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
    }

}

/**
 * The type Client handler.
 */
class ClientHandler implements Runnable {

    private final Socket connectionSocket;

    /**
     * Instantiates a new Client handler.
     *
     * @param connectionSocket the connection socket
     */
    public ClientHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[4096];
            ServerProcessor serverProcessor = new ServerProcessor();
            while (true) {
                int read = in.read(buffer);
                String str = new String(buffer, 0, read);
                JSONObject jsonObject = new JSONObject(str);
                JSONObject response = serverProcessor.processRequest(jsonObject);
                out.write(response.toString().getBytes());
            }

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
