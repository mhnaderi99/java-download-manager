import java.util.ArrayList;

public class DownloadManager {

    private static GUI gui;
    private static DownloadsList completed, proccessing;
    private static ArrayList<Queue> queues;

    public DownloadManager() {
        queues = new ArrayList<Queue>();
        queues.add(new Queue("My quwefwvwwveue1"));
        queues.add(new Queue("My queue2"));
        queues.add(new Queue("My queue3"));
        gui = new GUI();
        gui.showMainFrame();;
        completed = new DownloadsList(true);
        proccessing = new DownloadsList(false);
    }

    public static ArrayList<Queue> getQueues() {
        return queues;
    }
}
