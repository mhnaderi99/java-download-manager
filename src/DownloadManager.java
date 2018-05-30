import javax.swing.*;
import java.util.ArrayList;

public class DownloadManager {

    private static GUI gui;
    private static Settings settings;
    private static DownloadsList completed, proccessing, removed, queue;

    public static final transient int INF = Integer.MAX_VALUE;

    public DownloadManager() {
        settings = SerializationHandler.loadSettings();
        setLookAndFeel(settings.getLookAndFeel());
        completed = new DownloadsList(DownloadsList.state.Completed);
        proccessing = new DownloadsList(DownloadsList.state.Processing);
        queue = new DownloadsList(DownloadsList.state.Queue);
        removed = new DownloadsList(DownloadsList.state.Removed);
        gui = new GUI(DownloadsList.state.Processing);
        gui.showGUI();
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

    public void addDownloadToList(DownloadEntry entry, DownloadsList list) {

    }

    public static void pauseDownloads() {
        int[] indices = proccessing.getDownloadEntries().getSelectedIndices();
        for (int index : indices) {
            proccessing.getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Paused);
        }
        updateUI();
    }

    public static void resumeDownloads() {
        boolean flag = false;
        int[] indices = proccessing.getDownloadEntries().getSelectedIndices();
        if (settings.isSynchronicDownloadsLimited()) {
            if (getInProgressDownloads() + indices.length > settings.getMaximumSynchronicDownloads()) {
                flag = true;
            }
        }

        if (flag) {
            JOptionPane.showMessageDialog(GUI.getFrame(),"JDM can't resume.(Maximum synchronic download limit exceeded)", "Error", 0);
        }
        else {
            for (int index : indices) {
                proccessing.getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Downloading);
            }
            updateUI();
        }
    }

    public static void removeDownloads() {
        int value = JOptionPane.showConfirmDialog(GUI.getFrame(), "Are you sure you want to delete?", "Warning", 0,0);
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
        int[] indices = proccessing.getDownloadEntries().getSelectedIndices();
        for (int index : indices) {
            proccessing.getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Cancelled);
        }
        updateUI();
    }

    public static void pauseAllDownloads() {
        for (DownloadEntry entry: proccessing.getDownloads()) {
            entry.getDownload().setState(Download.status.Paused);
        }
        updateUI();
    }

    public static void pauseSomeDownloads(int number) {
        int l = proccessing.getDownloads().size();
        for (int i = l - 1; i >= l - number; i--) {
            proccessing.getDownloads().get(i).getDownload().setState(Download.status.Paused);
        }
        updateUI();
    }

    public static void resumeAllDownloads() {
        boolean flag = false;
        if (proccessing.getDownloads().size() > settings.getMaximumSynchronicDownloads() && settings.isSynchronicDownloadsLimited()) {
            flag = true;
        }
        if (flag) {
            JOptionPane.showMessageDialog(GUI.getFrame(),"JDM can't resume.(Maximum synchronic download limit exceeded)", "Error", 0);
        }
        else {
            for (DownloadEntry entry : proccessing.getDownloads()) {
                entry.getDownload().setState(Download.status.Downloading);
            }
            updateUI();
        }
    }

    public static void cancelAllDownloads() {
        for (DownloadEntry entry: proccessing.getDownloads()) {
            entry.getDownload().setState(Download.status.Cancelled);
        }
        updateUI();
    }

    public static int getInProgressDownloads() {
        int count = 0;
        for (DownloadEntry entry: proccessing.getDownloads()) {
            if (entry.getDownload().getState().equals(Download.status.Downloading)) {
                count++;
            }
        }
        return count;
    }

    public static void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e){}
    }
}
