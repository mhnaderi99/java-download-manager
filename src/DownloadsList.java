import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class DownloadsList extends JList<DownloadEntry> implements Serializable{

    public enum state implements Serializable{
        Completed, Processing, Removed, Queue, SearchResult
    }

    private transient DownloadEntryRenderer renderer;
    private DefaultListModel<DownloadEntry> model;
    private state mode;
    private Date startTime;
    private Boolean isRunning;

    public DownloadsList(state mode) {
        this.mode = mode;
        model = new DefaultListModel<>();
        renderer = new DownloadEntryRenderer(mode);
        if (mode == state.Queue) {
            startTime = Calendar.getInstance().getTime();
        }
        if (mode != state.Queue) {
            isRunning = null;
        }
        else {
            isRunning = false;
        }
        initJlist();
    }

    private void initJlist() {
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setCellRenderer(renderer);
        addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (DownloadManager.getState() == state.Queue) {
                    if (DownloadManager.getQueue().getModel().size() > 0){
                        GUI.getToolbar().setEnabledButtons(-1);
                    }
                    else {
                        GUI.getToolbar().setEnabledButtons(-2);
                    }
                    return;
                }
                if (DownloadManager.getState() != state.Completed) {
                    if (isSelectionEmpty()) {
                        GUI.getToolbar().setEnabledButtons(1);
                    } else {
                        GUI.getToolbar().setEnabledButtons(2);
                    }
                }
                else if (! isSelectionEmpty()){
                    GUI.getToolbar().setEnabledButtons(3);
                }
                else {
                    GUI.getToolbar().setEnabledButtons(6);
                }
            }
        });
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

    public Boolean getRunning() {
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public state getMode() {
        return mode;
    }

    public void addDownloadToList(Download download) {
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

    private void checkDisablity(int mode) {
    }

    public void setModel(DefaultListModel<DownloadEntry> model) {
        this.model = model;
        getDownloadEntries().setModel(model);
    }

    public void setMode(state mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < model.size(); i++) {
            builder.append(model.getElementAt(i).toString());
        }
        return builder.toString();
    }

    private int runningDownloads() {
        int num = 0;
        for (int i = 0; i < model.size(); i++) {
            if (model.getElementAt(i).getDownload().getState().equals(Download.status.Downloading)) {
                num++;
            }
        }
        return num;
    }

    private int numberOfDownloads() {
        return model.getSize();
    }

    public String progress() {
        return "(" + runningDownloads() + "/" + numberOfDownloads() + ")";
    }
}
