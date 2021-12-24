package main.java.org.ce.ap.server.DataBase;

import main.java.org.ce.ap.server.User;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TweetDataBase {
    private Path path;

    /**
     * Instantiates a new Database handler.
     */
    public TweetDataBase() {
        path = Path.of("./files/model/tweets");
    }

    /**
     * Write file.
     *
     * @param id         the id
     * @param username   the username
     * @param jsonObject the json object
     */
    public void writeFile(String id, String retweetedId, String username, JSONObject jsonObject) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path.toFile().getAbsoluteFile() + File.separator + id + " " + retweetedId + " " + username))) {
            out.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read file json object.
     *
     * @param id       the id
     * @param username the username
     * @return the json object
     */
    public Map<Long, JSONObject> readFile(String id, String username) {
        String fileStr = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path + File.separator + id + " " + retweetedId + " " + username))) {
            int count;
            char[] buffer = new char[20];
            while (reader.ready()) {
                count = reader.read(buffer);
                fileStr += new String(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject= new JSONObject(fileStr);
        return new Map<Long, JSONObject> ;
    }

    /**
     * Get directory files array list.
     *
     * @return the array list
     */
    public HashMap<Map<Long, Long>, JSONObject> getDirectoryFiles(User user) {
        ArrayList<JSONObject> files = new ArrayList<>();
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path p : directoryStream) {
                    String[] words = p.getFileName().toString().split("\\s");
                    System.out.println(words[1]);
                    if (user.getFollowings().contains(words[1]))
                        files.add(readFile(p.getFileName().toString(), user.getUsername()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    /**
     * Remove file.
     *
     * @param id the id
     */
    public void removeFile(String id) {
        try {
            File myObj = new File(path + File.separator + id);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
