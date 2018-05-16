import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class DownloadsList extends JList<DownloadEntry> {

    private ArrayList<DownloadEntry> downloads;
    private DownloadEntryRenderer renderer;
    private DefaultListModel<DownloadEntry> model;

    public DownloadsList(boolean isCompleted) {

        downloads = new ArrayList<DownloadEntry>();
        renderer = new DownloadEntryRenderer(isCompleted);
        initJlist();
    }

    private void initJlist() {
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setCellRenderer(renderer);
        setBackground(GUI.BACKGROUND_COLOR);
        setVisibleRowCount(-1);
        //setFixedCellWidth(580);
        //setFixedCellHeight(100);
        setOpaque(true);
        model = new DefaultListModel<>();
    }


    public void addDownloadToList(Download download) {
        downloads.add(new DownloadEntry(download));
        model.addElement(new DownloadEntry(download));
        //updateUI();
    }

    public JList<DownloadEntry> getDownloadEntries() {
        return this;
    }

    @Override
    public DefaultListModel<DownloadEntry> getModel() {
        return model;
    }

    public JScrollPane getList(){
        JScrollPane scrollPane = new JScrollPane(this);
        return scrollPane;
    }

    public ArrayList<DownloadEntry> getDownloads() {
        return downloads;
    }
}
