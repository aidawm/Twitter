package org.ce.ap.client.GUI;

import org.ce.ap.ServiceWordsEnum;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * The type Connection service.
 */
public class ConnectionServiceImpl  {
    private Socket client;
    private OutputStream out;
    private InputStream in;
    private static ConnectionServiceImpl connectionService;

    /**
     * Instantiates a new Connection service.
     *
     * @param client the client
     * @throws IOException the io exception
     */
    private ConnectionServiceImpl(Socket client) throws IOException {
        this.client = client;
        this.out = client.getOutputStream();
        this.in = client.getInputStream();
    }

    public static void  makeConnectionService(Socket client) throws IOException {
        connectionService = new ConnectionServiceImpl(client);
    }

    public static ConnectionServiceImpl getConnectionService(){
        return connectionService;
    }

    /**
     * Request json object.
     *
     * @param jsonObject the json object
     * @return the json object
     * @throws IOException the io exception
     */

    public JSONObject request(JSONObject jsonObject) throws IOException {
        byte[] buffer = new byte[1000000000];
        System.out.println("**" + jsonObject);
        out.write(jsonObject.toString().getBytes());
        int read = in.read(buffer);
        String str = new String(buffer, 0, read);
        JSONObject response = new JSONObject(str);
        System.out.println(response);
        return response;
    }

    public JSONObject request(ServiceWordsEnum serviceWordsEnum, JSONObject jsonObject) throws IOException {
        JSONObject request = new JSONObject();
        request.put("method", serviceWordsEnum);
        request.put("parameterValues", jsonObject);
        return request(request);
    }
}
