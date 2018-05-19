import java.util.*;

public class Queue {

    private String name;
    private Date startTime;
    private int maximumSynchronicDownloads;
    private ArrayList<DownloadEntry> queue;

    public Queue(String name) {
        this.name = name;
        startTime = Calendar.getInstance().getTime();
        maximumSynchronicDownloads = Integer.MAX_VALUE;
        queue = new ArrayList<DownloadEntry>();
    }

    public void add(Download download) {
        queue.add(new DownloadEntry(download));
    }

    public void remove(Download download) {
        queue.remove(download);
    }

    public void finish(Download download) {
        download.finish();
    }

    public String getName() {
        return name;
    }

    public ArrayList<DownloadEntry> getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return name;
    }
}
