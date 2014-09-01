package pivotal.au.fe.anzpoc.main;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ServerLauncher {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(getResourceAsStream("/home/udo/projects/AnzSydneyPOC/Server/src/main/config/server/gemfire.properties"));
        Cache cache = new CacheFactory(properties).set("start-locator", "localhost[10334]").set("cache-xml-file", "/home/udo/projects/AnzSydneyPOC/Server/src/main/config/server/cache.xml").create();

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                cache.close();
            }
        }
    }

    private static InputStream getResourceAsStream(String path) {
        try {
            return new FileInputStream(path);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerLauncher.class.getResourceAsStream(path);
        }
    }
}
