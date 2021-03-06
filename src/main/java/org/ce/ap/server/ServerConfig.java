package  org.ce.ap.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class ServerConfig {
    private static Properties serverProperties;
    private static Path path = Path.of("./src/main/resources/server-application.properties");
    static {
        try (InputStream input = new FileInputStream(path.toFile().getAbsoluteFile())){

            serverProperties=new Properties();
            serverProperties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key){
        return serverProperties.getProperty(key);
    }
}
