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
import java.util.Comparator;

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


        GUI.getToolbar().getToolBar().addMouseMotionListener(new disableHandler());
        addMouseMotionListener(new disableHandler());
        addMouseListener(new disableHandler());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int index = locationToIndex(e.getPoint());
                    setSelectedIndex(index);
                    //Show details
                    JFrame details = getModel().get(index).makeDetailsFrame();
                    details.setVisible(true);
                }
            }
        });
        setBackground(GUI.BACKGROUND_COLOR);
        setVisibleRowCount(-1);
        setOpaque(true);
        model = new DefaultListModel<>();
    }


    public void addDownloadToList(Download download) {
        downloads.add(new DownloadEntry(download));
        model.addElement(new DownloadEntry(download));
        setModel(model);
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

    public ArrayList<DownloadEntry> getSelectedItems() {
        return (ArrayList<DownloadEntry>) getSelectedValuesList();
    }

    public ArrayList<DownloadEntry> getDownloads() {
        return downloads;
    }

    public void setDownloads(ArrayList<DownloadEntry> downloads) {
        this.downloads = new ArrayList<>(downloads);
    }

    public void sortDownloads(Comparator p1, boolean o1, Comparator p2, boolean o2) {
        ArrayList<Download> d = new ArrayList<Download>();
        for (DownloadEntry entry: downloads){
            d.add(entry.getDownload());
        }
        Download.sortDownloads(d, p1, o1, p2, o2);
        downloads.clear();
        model.clear();
        for (Download download: d){
            downloads.add(new DownloadEntry(download));
            model.addElement(new DownloadEntry(download));
        }
        setModel(model);
        updateUI();
    }

    private void checkDisablity() {
        if (isSelectionEmpty()) {
            GUI.getToolbar().setEnabledButtons(false);
        }
        else {
            GUI.getToolbar().setEnabledButtons(true);
        }
    }

    private class disableHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getSource() instanceof JList){
                checkDisablity();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            checkDisablity();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            checkDisablity();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            checkDisablity();
        }
    }
}
