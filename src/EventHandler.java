import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
            DownloadManager.pauseDownloads();
            if (e.getSource() instanceof JButton){
                GUI.getToolbar().setEnabledButtons(false);
            }
        }
        if (key.equals("pause all")) {
            DownloadManager.pauseAllDownloads();
        }
        if (key.equals("resume")) {
            DownloadManager.resumeDownloads();
            if (e.getSource() instanceof JButton){
                GUI.getToolbar().setEnabledButtons(false);
            }
        }
        if (key.equals("resume all")) {
            DownloadManager.resumeAllDownloads();
        }
        if (key.equals("cancel")) {
            DownloadManager.cancelDownloads();
            if (e.getSource() instanceof JButton){
                GUI.getToolbar().setEnabledButtons(false);
            }
        }
        if (key.equals("cancel all")) {
            DownloadManager.cancelAllDownloads();
        }
        if (key.equals("sort")) {
            GUI.makeSortFrame().setVisible(true);
        }
        if (key.equals("remove")) {
            DownloadManager.removeDownloads();
        }
        if (key.equals("settings")) {
            DownloadManager.getSettings().makeSettingsFrame().setVisible(true);
        }
        if (key.equals("exit")){
            SerializationHandler.saveSettings();
            SerializationHandler.saveProcessing();
            SerializationHandler.saveQueue();
            SerializationHandler.saveCompleted();
            SerializationHandler.saveRemoved();
            System.exit(0);
        }
        if (key.equals("about")) {
            GUI.makeAboutFrame().setVisible(true);
        }
        if (key.equals("search")) {

        }

    }


}
