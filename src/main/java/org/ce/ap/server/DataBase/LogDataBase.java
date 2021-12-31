package main.java.org.ce.ap.server.DataBase;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Path;

/**
 * The type Log data base.
 */
public class LogDataBase {
    private Path path;

    /**
     * Instantiates a new Log data base.
     */
    public LogDataBase() {
        this.path = Path.of("./files/log");
    }

    /**
     * Write log file.
     *
     * @param detail   the detail
     * @param username the username
     * @param state    the state
     */
    public void writeLogFile(boolean state, String username, String detail) {
        String file = readLogFile();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path.toFile().getAbsoluteFile() + File.separator + "log"))) {
            out.write(file + attempt(detail, username, state));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read log file string.
     *
     * @return the string
     */
    public String readLogFile() {
        StringBuilder fileStr = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + File.separator + "log"))) {
            int count;
            char[] buffer = new char[20];
            while (reader.ready()) {
                count = reader.read(buffer);
                fileStr.append(new String(buffer, 0, count));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStr.toString();
    }

    /**
     * Attempt string.
     *
     * @param detail   the detail
     * @param username the username
     * @param state    the state
     * @return the string
     */
    private String attempt(String detail, String username, boolean state) {
        String str = "";
        if (!state) {
            str += "[INFI] Client@" + username + ", -" + detail + "\n";
        } else {
            str += "[ERROR] Client@" + username + ", -" + detail + "\n";
        }
        return str;
    }
}
