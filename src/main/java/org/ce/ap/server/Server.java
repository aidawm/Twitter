package main.java.org.ce.ap.server;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        int counter = 0;
        try (ServerSocket welcomingSocket = new ServerSocket(5757)) {
            System.out.print("Server started.\nWaiting for a client ... ");
            while (counter < 3) {
                Socket connectionSocket = welcomingSocket.accept();
                counter++;
                System.out.println("client accepted!");
                pool.execute(new ClientHandlerr(connectionSocket, counter));
            }
            pool.shutdown();
            System.out.print("done.\nClosing server ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");
    }

}

class ClientHandlerr implements Runnable {

    private final Socket connectionSocket;
    private final int clientNum;

    public ClientHandlerr(Socket connectionSocket, int clientNum) {
        this.connectionSocket = connectionSocket;
        this.clientNum=clientNum;
    }

    @Override
    public void run() {
        try {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
            String str = new String(buffer, 0, read);
            System.out.println(str);
            JSONObject jsonObject = new JSONObject(str);
            System.out.println(jsonObject.get("key"));
            System.out.print("All messages sent.\nClosing client ... ");
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
