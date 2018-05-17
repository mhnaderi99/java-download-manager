import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Network {



    public static int getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }

    public static String generateFileName(URL url) {
        String link = url.toString();
        if (link.contains("/") && ! (link.lastIndexOf("/") == link.length() - 1)) {
            int index = link.lastIndexOf("/");
            String name = link.subSequence(index + 1, link.length()).toString();
            return name;
        }
        else {
            return link;
        }
    }

}
