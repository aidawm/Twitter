package org.ce.ap.client.GUI.controller;

import org.json.JSONObject;

public interface Updater {
    void update(JSONObject jsonObject) throws Exception;
}
