package main.java.org.ce.ap.server;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * The type Database handler.
 */
public class DatabaseHandler {
    private Path path;

    /**
     * Instantiates a new Database handler.
     *
     * @param path the path
     */
    public DatabaseHandler(Path path) {
        this.path = path;
    }

    /**
     * Write file.
     *
     * @param id         the id
     * @param jsonObject the json object
     */
    public void writeFile(String id, JSONObject jsonObject) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path.toFile().getAbsoluteFile() + File.separator + id))) {
            out.write(jsonObject.toString());
        } catch (IOException e) {

        }
    }

    /**
     * Write file.
     *
     * @param id         the id
     * @param username   the username
     * @param jsonObject the json object
     */
    public void writeFile(String id, String username, JSONObject jsonObject) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path.toFile().getAbsoluteFile() + File.separator + id + " " + username))) {
            out.write(jsonObject.toString());
        } catch (IOException e) {

        }
    }

    /**
     * Read file json object.
     *
     * @param id the id
     * @return the json object
     */
    public JSONObject readFile(String id) {
        String fileStr = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path + File.separator + id))) {
            int count;
            char[] buffer = new char[20];
            while (reader.ready()) {
                count = reader.read(buffer);
                fileStr += new String(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(fileStr);
    }

    /**
     * Read file json object.
     *
     * @param id       the id
     * @param username the username
     * @return the json object
     */
    public JSONObject readFile(String id, String username) {
        String fileStr = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path + File.separator + id + " " + username))) {
            int count;
            char[] buffer = new char[20];
            while (reader.ready()) {
                count = reader.read(buffer);
                fileStr += new String(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(fileStr);
    }

    /**
     * Get directory files array list.
     *
     * @return the array list
     */
    public ArrayList<JSONObject> getDirectoryFiles() {
        ArrayList<JSONObject> files = new ArrayList<>();
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path p : directoryStream) {
                    files.add(readFile(p.getFileName().toString()));
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
