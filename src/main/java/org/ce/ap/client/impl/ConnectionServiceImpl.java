package main.java.org.ce.ap.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.json.JSONObject;

public class ConnectionServiceImpl {
    private Socket client;
    private OutputStream out;
    private InputStream in;
    private byte[] buffer = new byte[4096];

    public ConnectionServiceImpl(Socket client) throws IOException {
        this.client = client;
        this.out = client.getOutputStream();
        this.in = client.getInputStream();
    }

    public JSONObject request(JSONObject jsonObject) throws IOException {
        System.out.println(jsonObject);
        out.write(jsonObject.toString().getBytes());
        int read = in.read(buffer);
        String str = new String(buffer, 0, read);
        JSONObject response = new JSONObject(str);
        System.out.println(response);
        return response;
    }

}
