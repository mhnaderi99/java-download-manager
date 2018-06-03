import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * This method is a an event handler for this project
 */
public class EventHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String key = null;
        if (e.getSource() instanceof JButton) {
            key = ((JButton) e.getSource()).getName();
        }
        if (e.getSource() instanceof JMenuItem) {
            key = ((JMenuItem) e.getSource()).getName();
        }

        if (key.equals("add")) {
            GUI.makeAddDownloadFrame().setVisible(true);
        }
        if (key.equals("pause")) {
            if (GUI.getList().getMode().equals(DownloadsList.state.Queue)) {
                if (DownloadManager.getQueue().getRunning()){
                    DownloadManager.pauseQueue();
                    DownloadManager.getQueue().setRunning(false);
                }
            }
            else {
                DownloadManager.pauseDownloads();
            }
        }
        if (key.equals("pause all")) {
            DownloadManager.pauseAllDownloads();
        }
        if (key.equals("resume")) {
            if (GUI.getList().getMode().equals(DownloadsList.state.Queue)) {
                if (! DownloadManager.getQueue().getRunning()){
                    DownloadManager.startQueue();
                    DownloadManager.getQueue().setRunning(true);
                }
            }
            else {
                DownloadManager.resumeDownloads();
            }
        }
        if (key.equals("resume all")) {
            DownloadManager.resumeAllDownloads();
        }
        if (key.equals("cancel")) {
            DownloadManager.cancelDownloads();
            if (e.getSource() instanceof JButton){
            }
        }
        if (key.equals("cancel all")) {
            DownloadManager.cancelAllDownloads();
        }
        if (key.equals("sort")) {
            GUI.makeSortFrame().setVisible(true);
        }
        if (key.equals("export")) {
            DownloadManager.export();
        }
        if (key.equals("remove")) {
            DownloadManager.removeDownloads();
        }
        if (key.equals("settings")) {
            DownloadManager.getSettings().makeSettingsFrame().setVisible(true);
        }
        if (key.equals("exit")){
            DownloadManager.exit();
        }
        if (key.equals("about")) {
            GUI.makeAboutFrame().setVisible(true);
        }
        if (key.equals("search")) {

        }

    }


}
