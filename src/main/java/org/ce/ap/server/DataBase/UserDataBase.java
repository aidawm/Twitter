package  org.ce.ap.server.DataBase;

import  org.ce.ap.server.ServerConfig;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * The type User data base.
 */
public class UserDataBase {
    private Path path;

    /**
     * Instantiates a new Database handler.
     */
    public UserDataBase() {
        path = Path.of(ServerConfig.getProperty("server.users.file"));
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
     * Get directory files array list.
     *
     * @return the array list
     */
    public ArrayList<JSONObject> getDirectoryFiles(){
        ArrayList<JSONObject> files = new ArrayList<>();
        if(Files.isDirectory(path)){
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)){
                for (Path p: directoryStream){
                    files.add(readFile(p.getFileName().toString()));
                }
            }
            catch (IOException e){
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
