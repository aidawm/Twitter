package main.java.org.ce.ap.client.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import main.java.org.ce.ap.client.services.ConnectionService;
import org.json.JSONObject;

/**
 * The type Connection service.
 */
public class ConnectionServiceImpl implements ConnectionService {
    private Socket client;
    private OutputStream out;
    private InputStream in;
    private byte[] buffer = new byte[4096];

    /**
     * Instantiates a new Connection service.
     *
     * @param client the client
     * @throws IOException the io exception
     */
    public ConnectionServiceImpl(Socket client) throws IOException {
        this.client = client;
        this.out = client.getOutputStream();
        this.in = client.getInputStream();
    }

    /**
     * Request json object.
     *
     * @param jsonObject the json object
     * @return the json object
     * @throws IOException the io exception
     */
    @Override
    public JSONObject request(JSONObject jsonObject) throws IOException {
        out.write(jsonObject.toString().getBytes());
        int read = in.read(buffer);
        String str = new String(buffer, 0, read);
        JSONObject response = new JSONObject(str);
        return response;
    }

}
