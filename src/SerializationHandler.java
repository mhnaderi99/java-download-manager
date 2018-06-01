import java.io.*;
import java.util.ArrayList;

public class SerializationHandler {



    private static <T>  ArrayList<T> load(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<T> list = (ArrayList<T>) ois.readObject();
            return list;
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
        catch (ClassNotFoundException e) { }
        return null;
    }

    private static <T> void save(String path, ArrayList<T> list) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }

    }

    public static Settings loadSettings() {
        Settings settings = new Settings();
        ArrayList list = load("settings.jdm");
        if (list != null){
            settings = (Settings) list.get(0);
        }
        return settings;
    }

    public static void saveSettings() {
        ArrayList list = new ArrayList();
        list.add(DownloadManager.getSettings());
        save("settings.jdm", list);
    }

    public static DownloadsList loadProcessing() {
        DownloadsList processing = new DownloadsList(DownloadsList.state.Processing);
        ArrayList list = load("list.jdm");
        if (list != null) {
            processing = (DownloadsList) list.get(0);
        }
        return processing;
    }

    public static void saveProcessing() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getProccessing();
        //System.out.println(downloadsList.getDownloads().toString());
        list.add(downloadsList);
        save("list.jdm", list);
    }

    public static DownloadsList loadQueue() {
        DownloadsList queue = new DownloadsList(DownloadsList.state.Queue);
        ArrayList list = load("queue.jdm");
        if (list != null) {
            queue = (DownloadsList) list.get(0);
        }

        return queue;
    }

    public static void saveQueue() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getQueue();
        list.add(downloadsList);
        save("queue.jdm", list);
    }

    public static DownloadsList loadCompleted() {
        DownloadsList completed = new DownloadsList(DownloadsList.state.Completed);
        ArrayList list = load("completed.jdm");
        if (list != null) {
            completed = (DownloadsList) list.get(0);
        }

        return completed;
    }

    public static void saveCompleted() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getCompleted();
        list.add(downloadsList);
        save("completed.jdm", list);
    }

    public static DownloadsList loadRemoved() {
        DownloadsList removed = new DownloadsList(DownloadsList.state.Removed);
        ArrayList list = load("removed.jdm");
        if (list != null) {
            removed = (DownloadsList) list.get(0);
        }

        return removed;
    }

    public static void saveRemoved() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getRemoved();
        list.add(downloadsList);
        save("removed.jdm", list);
    }
}
