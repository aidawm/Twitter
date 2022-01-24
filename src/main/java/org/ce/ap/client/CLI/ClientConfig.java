package main.java.org.ce.ap.client;

        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.InputStream;
        import java.nio.file.Path;
        import java.util.Properties;

public class ClientConfig {
    private static Properties serverProperties;
    private static Path path = Path.of("./src/main/resources/client-application.properties");
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
