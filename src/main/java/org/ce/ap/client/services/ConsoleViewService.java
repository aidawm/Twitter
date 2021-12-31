package main.java.org.ce.ap.client.services;

import main.java.org.ce.ap.ServiceWordsEnum;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The interface Console view service.
 */
public interface ConsoleViewService {
    /**
     * Welcome.
     */
    void welcome();

    /**
     * Main menu.
     */
    void mainMenu();

    /**
     * Manage tweets menu.
     */
    void manageTweetsMenu();

    /**
     * Manage follows menu.
     */
    void manageFollowsMenu();

    /**
     * Show timeline.
     *
     * @param tweets the tweets
     */
    void showTimeline(JSONArray tweets);

    /**
     * Show users.
     *
     * @param users the users
     */
    void showUsers(JSONArray users);

    /**
     * Process server response.
     *
     * @param serviceWordsEnum the service words enum
     * @param response         the response
     */
    void processServerResponse(ServiceWordsEnum serviceWordsEnum, JSONObject response);


}
