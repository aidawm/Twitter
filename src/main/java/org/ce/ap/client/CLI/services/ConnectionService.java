package main.java.org.ce.ap.client.services;

import org.json.JSONObject;

import java.io.IOException;

/**
 * The interface Connection service.
 */
public interface ConnectionService {
    /**
     * Request json object.
     *
     * @param jsonObject the json object
     * @return the json object
     * @throws IOException the io exception
     */
    JSONObject request(JSONObject jsonObject) throws IOException;
}
