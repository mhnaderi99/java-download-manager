
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DownloadManager implements Serializable {

    private static GUI gui;
    private static Settings settings;
    private static DownloadsList completed, proccessing, removed, queue;
    private static DownloadsList.state state;
    //private static ExecutorService service;
    private static ExecutorService queueService;

    public static final transient int INF = Integer.MAX_VALUE;

    public DownloadManager() {
        //service = Executors.newCachedThreadPool();
        queueService = Executors.newCachedThreadPool();
        settings = SerializationHandler.loadSettings();
        setLookAndFeel(settings.getLookAndFeel());
        completed = SerializationHandler.loadCompleted();
        proccessing = SerializationHandler.loadProcessing();
        System.out.println(proccessing);
        queue = SerializationHandler.loadQueue();
        removed = SerializationHandler.loadRemoved();
        state = DownloadsList.state.Processing;
        reopen();
        gui = new GUI(DownloadsList.state.Processing);
        gui.showGUI();
    }

    public static DownloadsList.state getState() {
        return state;
    }

    public static GUI getGui() {
        return gui;
    }

    public static void setGui(GUI gui) {
        DownloadManager.gui = gui;
    }

    public static DownloadsList getProccessing() {
        return proccessing;
    }

    public static DownloadsList getCompleted() {
        return completed;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static DownloadsList getQueue() {
        return queue;
    }

    public static DownloadsList getRemoved() {
        return removed;
    }

    public static void updateUI() {
        gui.update();
    }

//    public static ExecutorService getService() {
//        return service;
//    }

    public static void pauseDownloads() {
        int[] indices = GUI.getList().getSelectedIndices();
        for (int index : indices) {
            getListByState().getModel().getElementAt(index).getDownload().pause();
            //GUI.getList().getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Paused);
        }
        updateUI();
    }

    public static void resumeDownloads() {
        boolean flag = false;
        int[] indices = GUI.getList().getDownloadEntries().getSelectedIndices();
        if (settings.isSynchronicDownloadsLimited()) {
            if (getInProgressDownloads() + indices.length > settings.getMaximumSynchronicDownloads()) {
                flag = true;
            }
        }

        if (flag) {
            JOptionPane.showMessageDialog(GUI.getFrame(), "JDM can't resume.(Maximum synchronic download limit exceeded)", "Error", 0);
        } else {
            for (int index : indices) {
                //getListByState().getModel().getElementAt(index).getDownload().getDownloader();
                if (getListByState().getModel().getElementAt(index).getDownload().getStarted()){
                    getListByState().getModel().getElementAt(index).getDownload().resume();
                }
                else {
                    getListByState().getModel().getElementAt(index).getDownload().start();
                }
            }
            updateUI();
        }
    }

    public static void removeDownloads() {
        int value = JOptionPane.showConfirmDialog(GUI.getFrame(), "Are you sure you want to delete?", "Warning", 0, 0);
        if (value != 0) {
            return;
        }
        int[] indices = GUI.getList().getSelectedIndices();
        ArrayList trashIndices = new ArrayList();

        for (int index : indices) {
            trashIndices.add(index);
        }
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < GUI.getList().getModel().size(); i++) {
            if (! trashIndices.contains(i)){
                model.addElement(GUI.getList().getModel().get(i));
            }
        }
        GUI.getList().setModel(model);
        getListByState().setModel(model);

        GUI.getList().updateUI();
    }

    public static void cancelDownloads() {
        int[] indices = GUI.getList().getSelectedIndices();
        for (int index : indices) {
            GUI.getList().getModel().getElementAt(index).getDownload().setState(Download.status.Cancelled);
        }
        getListByState().setModel(GUI.getList().getModel());
        updateUI();
    }

    public static void pauseAllDownloads() {
        for (int i = 0; i < GUI.getList().getModel().size(); i++) {
            DownloadEntry entry = GUI.getList().getModel().getElementAt(i);
            entry.getDownload().setState(Download.status.Paused);
        }
        getListByState().setModel(GUI.getList().getModel());
        updateUI();
    }

    public static void pauseQueue() {
        queue.setRunning(false);
        System.out.println(queue.getModel().size());
        queue.getModel().getElementAt(0).getDownload().pauseInQueue();
        System.out.println("OK");

    }

    public static void startQueue() {
        if (queue.getModel().getElementAt(0).getDownload().getStarted()){
            queue.getModel().getElementAt(0).getDownload().resume();
        }
        else {
            queue.getModel().getElementAt(0).getDownload().start();
        }
        queue.setRunning(true);
        //queueService.shutdown();
    }

    public static void setState(DownloadsList.state state) {
        DownloadManager.state = state;
    }

    public static DownloadsList getListByState() {
        if (state == DownloadsList.state.Processing) {return proccessing;}
        if (state == DownloadsList.state.Completed) {return completed;}
        if (state == DownloadsList.state.Removed) {return removed;}
        if (state == DownloadsList.state.Queue) {return queue;}
        return null;
    }

    public static void pauseSomeDownloads(int number) {
        int l = proccessing.getModel().size();
        for (int i = l - 1; i >= l - number; i--) {
            proccessing.getModel().get(i).getDownload().setState(Download.status.Paused);
        }
        updateUI();
    }

    public static void resumeAllDownloads() {
        boolean flag = false;
        if (proccessing.getModel().size() > settings.getMaximumSynchronicDownloads() && settings.isSynchronicDownloadsLimited()) {
            flag = true;
        }
        if (flag) {
            JOptionPane.showMessageDialog(GUI.getFrame(), "JDM can't resume.(Maximum synchronic download limit exceeded)", "Error", 0);
        } else {
            for (int i = 0; i < proccessing.getModel().size(); i++) {
                DownloadEntry entry = proccessing.getModel().get(i);
                entry.getDownload().resume();
            }
            updateUI();
        }
    }

    public static void cancelAllDownloads() {
        for (int i = 0; i < proccessing.getModel().size(); i++) {
            DownloadEntry entry = proccessing.getModel().get(i);
            entry.getDownload().setState(Download.status.Cancelled);
        }
        updateUI();
    }

    public static ExecutorService getQueueService() {
        return queueService;
    }

    public static int getInProgressDownloads() {
        int count = 0;
        for (int i = 0; i < proccessing.getModel().size(); i++) {
            DownloadEntry entry = proccessing.getModel().get(i);
            if (entry.getDownload().getState().equals(Download.status.Downloading)) {
                count++;
            }
        }
        for (int i = 0; i < queue.getModel().size(); i++) {
            DownloadEntry entry = queue.getModel().get(i);
            if (entry.getDownload().getState().equals(Download.status.Downloading)) {
                count++;
            }
        }

        return count;
    }

    public static void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
        }
    }

    public static DownloadsList searchResults(String text) {
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < getListByState().getModel().size(); i++) {
            DownloadEntry entry = getListByState().getModel().getElementAt(i);
            Download download = entry.getDownload();
            if (download.getName().toUpperCase().contains(text.toUpperCase()) || download.getLink().toUpperCase().contains(text.toUpperCase())) {
                //results.add(entry);
                model.addElement(entry);
            }
        }
        DownloadsList searchResults = new DownloadsList(DownloadsList.state.SearchResult);
        searchResults.setModel(model);
        return searchResults;
    }

    public static void exit() {
        SerializationHandler.saveSettings();
        SerializationHandler.saveProcessing();
        SerializationHandler.saveQueue();
        SerializationHandler.saveCompleted();
        SerializationHandler.saveRemoved();
        System.exit(0);
    }

    public static void reopen() {
        for (int i = 0; i < proccessing.getModel().size(); i++) {
            proccessing.getModel().getElementAt(i).getDownload().setDownloader(new Downloader(proccessing.getModel().getElementAt(i).getDownload()));
        }
        for (int i = 0; i < queue.getModel().size(); i++) {
            queue.getModel().getElementAt(i).getDownload().setDownloader(new Downloader(queue.getModel().getElementAt(i).getDownload()));
        }
    }


    public static void export() {
        new File("export.zip").delete();
        ZipOutputStream zos = null;
        File zip = new File("export.zip");
        String[] names = {"settings.jdm","list.jdm","completed.jdm","queue.jdm","removed.jdm"};
        try {
            zos = new ZipOutputStream(new FileOutputStream(zip, true));

            for (int i = 0; i < 5; i++) {
                String name = names[i];
                File file = new File(name);
                ZipEntry entry = new ZipEntry(name);
                zos.putNextEntry(entry);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    byte[] byteBuffer = new byte[1024];
                    int bytesRead = -1;
                    while ((bytesRead = fis.read(byteBuffer)) != -1) {
                        zos.write(byteBuffer, 0, bytesRead);
                    }
                    zos.flush();
                } finally {
                    try {
                        fis.close();
                    } catch (Exception e) {
                    }
                }
                zos.closeEntry();

                //zos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zos.close();
                JOptionPane.showMessageDialog(GUI.getFrame(), "Exported successfully", "Message", 1);
            } catch (Exception e) {
            }
        }
    }



    public static boolean isOneSubdomainOfTheOther(String a, String b) {

        try {
            URL first = new URL(a);
            String firstHost = first.getHost();
            firstHost = firstHost.startsWith("www.") ? firstHost.substring(4) : firstHost;

            URL second = new URL(b);
            String secondHost = second.getHost();
            secondHost = secondHost.startsWith("www.") ? secondHost.substring(4) : secondHost;

            /*
             Test if one is a substring of the other
             */
            if (firstHost.contains(secondHost)) {

                String[] firstPieces = firstHost.split("\\.");
                String[] secondPieces = secondHost.split("\\.");

                String[] longerHost = {""};
                String[] shorterHost = {""};

                if (firstPieces.length >= secondPieces.length) {
                    longerHost = firstPieces;
                    shorterHost = secondPieces;
                } else {
                    longerHost = secondPieces;
                    shorterHost = firstPieces;
                }
                //int longLength = longURL.length;
                int minLength = shorterHost.length;
                int i = 1;

                /*
                 Compare from the tail of both host and work backwards
                 */
                while (minLength > 0) {
                    String tail1 = longerHost[longerHost.length - i];
                    String tail2 = shorterHost[shorterHost.length - i];

                    if (tail1.equalsIgnoreCase(tail2)) {
                        //move up one place to the left
                        minLength--;
                    } else {
                        //domains do not match
                        return false;
                    }
                    i++;
                }
                if (minLength == 0) //shorter host exhausted. Is a sub domain
                    return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
