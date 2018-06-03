import java.io.*;
import java.util.ArrayList;

/**
 * This class serializes user data when app is being closed
 */
public class SerializationHandler {


    /**
     * This method is a general method for loading data
     * @param path
     * @param <T>
     * @return
     */
    private static <T>  ArrayList<T> load(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<T> list = (ArrayList<T>) ois.readObject();
            ois.close();
            fis.close();
            return list;
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
        catch (ClassNotFoundException e) { }
        return null;
    }

    /**
     * This method is a general method for saving data
     * @param path
     * @param list
     * @param <T>
     */
    private static <T> void save(String path, ArrayList<T> list) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();;
            fos.close();
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }

    }

    /**
     * This method loads settings
     * @return
     */
    public static Settings loadSettings() {
        Settings settings = new Settings();
        ArrayList list = load("settings.jdm");
        if (list != null){
            settings = (Settings) list.get(0);
        }
        return settings;
    }

    /**
     * This method saves settings
     */
    public static void saveSettings() {
        ArrayList list = new ArrayList();
        list.add(DownloadManager.getSettings());
        save("settings.jdm", list);
    }

    /**
     * This method loads processing downloads
     * @return
     */
    public static DownloadsList loadProcessing() {
        DownloadsList processing = new DownloadsList(DownloadsList.state.Processing);
        ArrayList list = load("list.jdm");
        if (list != null) {
            processing = (DownloadsList) list.get(0);
        }
        return processing;
    }

    /**
     * This method saves processing downloads
     */
    public static void saveProcessing() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getProccessing();
        //System.out.println(downloadsList.getDownloads().toString());
        list.add(downloadsList);
        save("list.jdm", list);
    }

    /**
     * This method loads queue
     * @return
     */
    public static DownloadsList loadQueue() {
        DownloadsList queue = new DownloadsList(DownloadsList.state.Queue);
        ArrayList list = load("queue.jdm");
        if (list != null) {
            queue = (DownloadsList) list.get(0);
        }

        return queue;
    }

    /**
     * This method saves queue
     */
    public static void saveQueue() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getQueue();
        list.add(downloadsList);
        save("queue.jdm", list);
    }

    /**
     * This method loads completed downloads
     * @return
     */
    public static DownloadsList loadCompleted() {
        DownloadsList completed = new DownloadsList(DownloadsList.state.Completed);
        ArrayList list = load("completed.jdm");
        if (list != null) {
            completed = (DownloadsList) list.get(0);
        }

        return completed;
    }

    /**
     * This method saves completed downloads
     */
    public static void saveCompleted() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getCompleted();
        list.add(downloadsList);
        save("completed.jdm", list);
    }

    /**
     * This method loads removed downloads
     * @return
     */
    public static DownloadsList loadRemoved() {
        DownloadsList removed = new DownloadsList(DownloadsList.state.Removed);
        ArrayList list = load("removed.jdm");
        if (list != null) {
            removed = (DownloadsList) list.get(0);
        }

        return removed;
    }

    /**
     * This method saves removed downloads
     */
    public static void saveRemoved() {
        ArrayList list = new ArrayList();
        DownloadsList downloadsList = DownloadManager.getRemoved();
        list.add(downloadsList);
        save("removed.jdm", list);
    }

    public static ArrayList<String> loadFilters() {
        ArrayList<String> filters = new ArrayList<String>();
        ArrayList list = load("filter.jdm");
        System.out.println(list == null);
        if (list != null) {
            filters = (ArrayList<String>) list.get(0);
        }
        return filters;
    }

    public static void saveFilters() {
        ArrayList list = new ArrayList();
        ArrayList<String> filters = DownloadManager.getSettings().getFilteredSites();
        list.add(filters);
        save("filter.jdm", list);
    }
}
