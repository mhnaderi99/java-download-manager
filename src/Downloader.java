import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader extends Thread implements Serializable {

    private static final int BUFFER_SIZE = 4096;
    private Download download;
    private FileOutputStream outputStream;
    private InputStream inputStream;

    public Downloader(Download download) {
        this.download = download;

        URL url = null;
        try {
            url = new URL(download.getLink());
        }
        catch (MalformedURLException e) { }
        HttpURLConnection connection = new HttpURLConnection(url) {
            @Override
            public void disconnect() {
                try {
                    Downloader.this.wait();
                }
                catch (InterruptedException e) {}
            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() throws IOException {
                downloadFile();
            }
        };
    }

    @Override
    public void run() {
        try {
            downloadFile();
        }
        catch (IOException e) { }
    }

    public void downloadFile()
            throws IOException {
        String fileURL = download.getLink();
        String saveDir = download.getSaveTo();
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = download.getName();
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
            download.setSizeInBytes(contentLength);

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
            

            // opens input stream from the HTTP connection
            inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
            if (! fileName.contains(download.fileFormat())) {
                saveFilePath = saveDir + File.separator + fileName + download.fileFormat();
            }

            // opens an output stream to save into file
            outputStream = new FileOutputStream(new File(saveFilePath), true);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            long parts = 0;
            Long start = System.nanoTime(), end;
            int bytes = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                bytes += bytesRead;
                end = System.nanoTime();
                download.setDownloadedBytes(download.getDownloadedBytes() + bytesRead);
                //System.out.println(start + ", " + end);
                if (parts % 20 == 0) {
                    download.estimatedTimeRemaining();
                    GUI.getList().repaint();
                    long duration = end - start;
                    float seconds = (float) (((float) duration) / Math.pow(10, 9));
                    download.setBytesPerSecond((int) (bytes / seconds));
                    bytes = 0;
                    start = System.nanoTime();
                }
                parts++;
            }
            GUI.getList().repaint();
            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
            download.finish();
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
}
