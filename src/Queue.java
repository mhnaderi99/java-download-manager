import java.util.*;

public class Queue {

    private Date startTime;
    private int maximumSynchronicDownloads;
    private ArrayList<Download> queue;

    public Queue() {
        startTime = Calendar.getInstance().getTime();
        maximumSynchronicDownloads = Integer.MAX_VALUE;
        queue = new ArrayList<Download>();
    }

    public void add(Download download) {
        queue.add(download);
    }

    public void remove(Download download) {
        queue.remove(download);
    }

    public void finish(Download download) {
        download.finish();
    }



}
