package main.java.org.ce.ap.server.DataBase;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Path;

public class UserDataBase {
    private Path path;

    /**
     * Instantiates a new Database handler.
     */
    public UserDataBase() {
        path = Path.of("./files/model/users");
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
            e.printStackTrace();
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
