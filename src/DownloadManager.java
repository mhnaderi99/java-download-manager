import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class DownloadManager implements Serializable {

    private static GUI gui;
    private static Settings settings;
    private static DownloadsList completed, proccessing, removed, queue;
    private static DownloadsList.state state;

    public static final transient int INF = Integer.MAX_VALUE;

    public DownloadManager() {
        settings = SerializationHandler.loadSettings();
        setLookAndFeel(settings.getLookAndFeel());
        completed = SerializationHandler.loadCompleted();
        proccessing = SerializationHandler.loadProcessing();
        queue = SerializationHandler.loadQueue();
        removed = SerializationHandler.loadRemoved();
        state = DownloadsList.state.Processing;
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

    public void addDownloadToList(DownloadEntry entry, DownloadsList list) {

    }

    public static void pauseDownloads() {
        int[] indices = GUI.getList().getSelectedIndices();
        for (int index : indices) {
            GUI.getList().getDownloadEntries().getModel().getElementAt(index).getDownload().setState(Download.status.Paused);
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
                GUI.getList().getModel().getElementAt(index).getDownload().setState(Download.status.Downloading);
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
                entry.getDownload().setState(Download.status.Downloading);
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
        for (int i = 0; i < GUI.getList().getModel().size(); i++) {
            DownloadEntry entry = GUI.getList().getModel().getElementAt(i);
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

}
