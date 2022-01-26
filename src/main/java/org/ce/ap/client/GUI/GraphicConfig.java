package org.ce.ap.client.GUI;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class GraphicConfig {
    private static Properties applicationProperties;

    private static Path path = Path.of("./src/main/resources/application-config.properties");
    static {
        try (InputStream input = new FileInputStream(path.toFile().getAbsoluteFile())){

            applicationProperties =new Properties();
            applicationProperties.load(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key){
        return applicationProperties.getProperty(key);
    }
    public static void setProperty (String key,String value){
        try (OutputStream output = new FileOutputStream(path.toFile().getAbsoluteFile())) {

            applicationProperties.setProperty(key,value);
            System.out.println(applicationProperties);
            applicationProperties.store(output, null);



        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void removeProperty(String key){

        try (OutputStream output = new FileOutputStream(path.toFile().getAbsoluteFile())) {

            applicationProperties.remove(key);
            applicationProperties.store(output,null);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    public static boolean isContainProperty(String key){
        return applicationProperties.containsKey(key);
    }
}
