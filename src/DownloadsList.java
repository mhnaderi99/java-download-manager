import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class DownloadsList extends JList<DownloadEntry> implements Serializable{

    public enum state implements Serializable{
        Completed, Processing, Removed, Queue, SearchResult
    }

    //private ArrayList<DownloadEntry> downloads;
    private transient DownloadEntryRenderer renderer;
    private DefaultListModel<DownloadEntry> model;
    private state mode;

    public DownloadsList(state mode) {
        this.mode = mode;
        //downloads = new ArrayList<DownloadEntry>();
        model = new DefaultListModel<>();
        renderer = new DownloadEntryRenderer(mode);
        initJlist();
    }

    private void initJlist() {
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setCellRenderer(renderer);
        addMouseMotionListener(new disableHandler());
        addMouseListener(new disableHandler());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                if (index != -1) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        setSelectedIndex(index);
                        //Show details
                        JFrame details = getModel().get(index).makeDetailsFrame();
                        details.setVisible(true);
                    }
                    if (e.getClickCount() == 2) {
                        if (mode == state.Completed) {
                            getModel().get(index).getDownload().openFile();
                        } else if (mode == state.Processing || mode == state.Queue) {
                            getModel().get(index).getDownload().openFolder();
                        }
                    }
                }
            }
        });
        setBackground(GUI.BACKGROUND_COLOR);
        setVisibleRowCount(-1);
        setOpaque(true);
    }


    public state getMode() {
        return mode;
    }

    public void addDownloadToList(Download download) {
        //downloads.add(new DownloadEntry(download));
        model.addElement(new DownloadEntry(download));
        setModel(model);
    }

    public JList<DownloadEntry> getDownloadEntries() {
        return this;
    }

    @Override
    public DefaultListModel<DownloadEntry> getModel() {
        return model;
    }

    public JScrollPane getList() {
        JScrollPane scrollPane = new JScrollPane(this);
        return scrollPane;
    }

    public ArrayList<DownloadEntry> getSelectedItems() {
        return (ArrayList<DownloadEntry>) getSelectedValuesList();
    }

    //public ArrayList<DownloadEntry> getDownloads() {
        //return downloads;
//    }

    //public void setDownloads(ArrayList<DownloadEntry> downloads) {
        //this.downloads = new ArrayList<>(downloads);
    //}

    public void sortDownloads(Comparator p1, boolean o1, Comparator p2, boolean o2) {
        ArrayList<Download> d = new ArrayList<Download>();
        ArrayList<DownloadEntry> downloads = new ArrayList<DownloadEntry>();
        for (int i = 0; i < getModel().size(); i++) {
            d.add(getModel().get(i).getDownload());
            downloads.add(getModel().get(i));
        }
        Download.sortDownloads(d, p1, o1, p2, o2);
        downloads.clear();
        model.clear();
        for (Download download : d) {
            downloads.add(new DownloadEntry(download));
            model.addElement(new DownloadEntry(download));
        }
        setModel(model);
        updateUI();
    }

    private void checkDisablity() {
        if (isSelectionEmpty()) {
            GUI.getToolbar().setEnabledButtons(false);
        } else {
            GUI.getToolbar().setEnabledButtons(true);
        }
    }


    private class disableHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getSource() instanceof JList) {
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

    public void setModel(DefaultListModel<DownloadEntry> model) {
        this.model = model;
        getDownloadEntries().setModel(model);
    }
}
