import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DownloadManager {

    private static GUI gui;
    private static DownloadsList completed, proccessing;
    private static ArrayList<Queue> queues;

    public DownloadManager() {
        queues = new ArrayList<Queue>();
        queues.add(new Queue("My queue1"));
        queues.add(new Queue("My queue2"));
        queues.add(new Queue("My queue3"));
        gui = new GUI();
        gui.showMainFrame();
        completed = new DownloadsList(true);
        proccessing = GUI.getList();
    }

    public static DownloadsList getProccessing() {
        return proccessing;
    }

    public static DownloadsList getCompleted() {
        return completed;
    }

    public static ArrayList<Queue> getQueues() {
        return queues;
    }

    public static void updateUI() {
        gui.update();
    }

    public void addDownloadToList(DownloadEntry entry, DownloadsList list) {

    }

    public static void pauseDownloads() {
        int[] indices = GUI.getList().getDownloadEntries().getSelectedIndices();
        for (int index : indices) {
            GUI.getList().getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Paused);
        }
        updateUI();
    }

    public static void resumeDownloads() {
        int[] indices = GUI.getList().getDownloadEntries().getSelectedIndices();
        for (int index : indices) {
            GUI.getList().getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Downloading);
        }
        updateUI();
    }

    public static void removeDownloads() {
        int value = JOptionPane.showConfirmDialog(GUI.getMainFrame(), "Are you sure you want to delete?", "Warning", 0,0);
        if (value != 0){
            return;
        }
        ArrayList<DownloadEntry> trash = new ArrayList<DownloadEntry>(proccessing.getSelectedItems());
        for (DownloadEntry t: trash) {
            proccessing.getModel().removeElement(t);
        }
        proccessing.setModel(proccessing.getModel());
        proccessing.getDownloads().clear();
        for (int i = 0; i < proccessing.getModel().size(); i++) {
            proccessing.getDownloads().add(proccessing.getModel().get(i));
        }
        proccessing.updateUI();
    }

    public static void cancelDownloads() {
        int[] indices = GUI.getList().getDownloadEntries().getSelectedIndices();
        for (int index : indices) {
            GUI.getList().getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Cancelled);
        }
        updateUI();
    }

    public static void pauseAllDownloads() {
        for (DownloadEntry entry: proccessing.getDownloads()) {
            entry.getDownload().setState(Download.status.Paused);
        }
        updateUI();
    }

    public static void resumeAllDownloads() {
        for (DownloadEntry entry: proccessing.getDownloads()) {
            entry.getDownload().setState(Download.status.Downloading);
        }
        updateUI();
    }

    public static void cancelAllDownloads() {
        for (DownloadEntry entry: proccessing.getDownloads()) {
            entry.getDownload().setState(Download.status.Cancelled);
        }
        updateUI();
    }
}
