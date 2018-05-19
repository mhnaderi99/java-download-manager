
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class DownloadEntry {

    private Download download;
    private JPanel panel;
    private JPanel iconPanel;
    private JPanel titlePanel;
    private JPanel content;
    private JPanel details;
    private JPanel buttons;

    private JLabel icon;
    private JLabel progress;
    private JLabel title;
    private JLabel resume;
    private JLabel open;
    private JLabel cancel;
    private JLabel speed;

    private JProgressBar progressBar;

    public DownloadEntry(Download download) {
        this.download = download;
    }

    public Download getDownload() {
        return download;
    }

    public JPanel getPanel() {

        int sizeInBytes = download.getSizeInBytes();
        int downloadedBytes = download.getDownloadedBytes();
        String progressText = download.getProgress();

        //Color empty = new Color(230,230,230), full = new Color(20,150,70);

        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, sizeInBytes);
        
        //progressBar.setBackground(empty);
        //progressBar.setForeground(full);
        
        progressBar.setBorderPainted(true);
        progressBar.setString(download.getDownloadedPercentage() + "%");
        progressBar.setStringPainted(true);
        
//        progressBar.setUI(new BasicProgressBarUI() {
//            protected Color getSelectionBackground() { return full; }
//            protected Color getSelectionForeground() { return empty; }
//        });

        progressBar.setBorder(BorderFactory.createLineBorder(GUI.TOOLBAR_COLOR, 1));
        progressBar.setValue(downloadedBytes);

        iconPanel = new JPanel(new BorderLayout());
        iconPanel.setBackground(GUI.BACKGROUND_COLOR);
        iconPanel.setOpaque(true);
        iconPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        icon = new JLabel("");
        icon.setOpaque(true);
        icon.setBackground(GUI.BACKGROUND_COLOR);
        //Icon ico = javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemIcon(new File("src/icons/files/file." + download.fileFormat()));
        //icon.setIcon(ico);
        icon.setIcon(new ImageIcon(GUI.getScaledImage(new ImageIcon("src/icons/icon.png").getImage(),30,30)));
        iconPanel.add(icon, BorderLayout.CENTER);

        progress = new JLabel(progressText);
        progress.setFont(new Font("Arial", Font.BOLD, 12));
        progress.setForeground(GUI.LEFT_SIDE_BACK_COLOR_PRESSED);
        progress.setOpaque(true);
        progress.setBackground(GUI.BACKGROUND_COLOR);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(1,0,0,0));

        title = new JLabel(download.getName());
        title.setOpaque(true);
        title.setBackground(GUI.BACKGROUND_COLOR);
        title.setFont(new Font("Arial", Font.BOLD,14));
        titlePanel.add(title, BorderLayout.CENTER);

        resume = new JLabel("");
        resume.setOpaque(true);
        resume.setBackground(GUI.BACKGROUND_COLOR);
        //resume.setBorderPainted(false);
        resume.setFocusable(true);
        resume.setEnabled(true);
        resume.setIcon(new ImageIcon("src/icons/resume.png"));
        resume.setToolTipText("Resume");
        resume.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));



        open = new JLabel("");
        open.setOpaque(true);
        open.setBackground(GUI.BACKGROUND_COLOR);
        //open.setBorderPainted(false);
        open.setFocusable(false);
        open.setIcon(new ImageIcon("src/icons/folder.png"));
        open.setBorder(BorderFactory.createEmptyBorder(0,6,0,8));

        cancel = new JLabel("");
        cancel.setIcon(new ImageIcon("src/icons/cross.png"));
        cancel.setOpaque(true);
        cancel.setBackground(GUI.BACKGROUND_COLOR);
        //cancel.setBorderPainted(false);
        cancel.setBorder(BorderFactory.createEmptyBorder(0,8,0,0));

        speed = new JLabel(download.getSpeed());
        if (download.getState().equals(Download.status.Paused)) {
            speed.setText("paused");
        }
        if (download.getState().equals(Download.status.Cancelled)) {
            speed.setText("cancelled");
        }
        speed.setFont(new Font("Arial", Font.CENTER_BASELINE, 12));
        speed.setForeground(GUI.LEFT_SIDE_BACK_COLOR_PRESSED);
        speed.setIcon(new ImageIcon("src/icons/speed.png"));

        content = new JPanel(new GridLayout(3,1,1,1));
        details = new JPanel(new BorderLayout());
        buttons = new JPanel();

        buttons.add(resume);
        buttons.add(open);
        buttons.add(cancel);
        buttons.setOpaque(true);
        buttons.setBackground(GUI.BACKGROUND_COLOR);

        //details.add(buttons, BorderLayout.WEST);


        details.setOpaque(true);
        details.setBackground(GUI.BACKGROUND_COLOR);
        details.add(speed, BorderLayout.WEST);
        details.add(progress, BorderLayout.EAST);

        content.add(titlePanel);

        content.add(progressBar);
        content.add(details);
        content.setOpaque(true);
        content.setBackground(GUI.BACKGROUND_COLOR);
        content.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.add(iconPanel, BorderLayout.WEST);
        panel.add(content, BorderLayout.CENTER);

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);

        panel.add(separator, BorderLayout.SOUTH);
        panel.setBackground(GUI.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(-1,10,0,0));

        return panel;
    }

    @Override
    public String toString() {
        return download.getName() + ": " + download.getProgress();
    }

    public JFrame makeDetailsFrame() {

        JFrame details = new JFrame();
        details.setTitle(download.getName());
        details.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        details.setIconImage(new ImageIcon("src/icons/details.png").getImage());
        details.setSize(500,300);
        details.setResizable(false);

        Border border = BorderFactory.createEmptyBorder(5,5,5,5);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(border);

        JPanel mainPanel = new JPanel(new GridLayout(7,1,1,5));
        mainPanel.setBorder(BorderFactory.createTitledBorder("Information"));

        JPanel filePanel = new JPanel(new BorderLayout());
        JPanel statusPanel = new JPanel(new BorderLayout());
        JPanel sizePanel = new JPanel(new BorderLayout());
        JPanel saveToPanel = new JPanel(new BorderLayout());
        JPanel createdPaneel = new JPanel(new BorderLayout());
        JPanel finishedPanel = new JPanel(new BorderLayout());
        JPanel urlPanel = new JPanel(new BorderLayout());

        JLabel file_ = new JLabel("File:             ");
        JLabel status_ = new JLabel("Status:          ");
        JLabel size_ = new JLabel("Size:             ");
        JLabel saveTo_ = new JLabel("Save to:         ");
        JLabel created_ = new JLabel("Created:         ");
        JLabel finished_ = new JLabel("Finished:        ");
        JLabel url_ = new JLabel("URL:              ");

        JLabel file = new JLabel(download.getName());
        JLabel status = new JLabel(download.getState().toString() + " (" + download.getDownloadedPercentage() + "%)");
        JLabel size = new JLabel(Download.makePrefix(download.getSizeInBytes()) + " (" + download.getSizeInBytes() +" Bytes)");
        JLabel saveTo = new JLabel(download.getSaveTo());
        JLabel created = new JLabel(download.getCreationTime().toString());
        JLabel finished = new JLabel("n/a");
        if (download.getState().equals(Download.status.Finished)) {
            finished = new JLabel(download.getFinishTime().toString());
        }
        JLabel url = new JLabel(download.getLink());

        JButton copy = new JButton("Copy");
        copy.setOpaque(true);
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(download.getLink());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });


        filePanel.setBorder(border);
        filePanel.add(file_, BorderLayout.WEST);
        filePanel.add(file, BorderLayout.CENTER);

        statusPanel.setBorder(border);
        statusPanel.add(status_, BorderLayout.WEST);
        statusPanel.add(status, BorderLayout.CENTER);

        sizePanel.setBorder(border);
        sizePanel.add(size_, BorderLayout.WEST);
        sizePanel.add(size, BorderLayout.CENTER);

        saveToPanel.setBorder(border);
        saveToPanel.add(saveTo_, BorderLayout.WEST);
        saveToPanel.add(saveTo, BorderLayout.CENTER);

        createdPaneel.setBorder(border);
        createdPaneel.add(created_, BorderLayout.WEST);
        createdPaneel.add(created, BorderLayout.CENTER);

        finishedPanel.setBorder(border);
        finishedPanel.add(finished_, BorderLayout.WEST);
        finishedPanel.add(finished, BorderLayout.CENTER);

        urlPanel.setBorder(border);
        urlPanel.add(url_, BorderLayout.WEST);
        urlPanel.add(url, BorderLayout.CENTER);
        urlPanel.add(copy, BorderLayout.EAST);


        mainPanel.add(filePanel);
        mainPanel.add(statusPanel);
        mainPanel.add(sizePanel);
        mainPanel.add(saveToPanel);
        mainPanel.add(createdPaneel);
        mainPanel.add(finishedPanel);
        mainPanel.add(urlPanel);

        panel.add(mainPanel, BorderLayout.CENTER);
        details.add(panel);

        return details;
    }

}
