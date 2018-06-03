
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.InvalidPathException;

/**
 * This method is a graphical schema of download class
 * Created by 9631815 on 5/12/2018.
 */
public class DownloadEntry implements Serializable{

    /**
     * instances of download entry
     */
    private Download download;
    private transient JPanel panel;
    private transient JPanel iconPanel;
    private transient JPanel titlePanel;
    private transient JPanel content;
    private transient JPanel details;
    private transient JPanel buttons;

    private transient JLabel icon;
    private transient JLabel progress;
    private transient JLabel title;
    private transient JLabel resume;
    private transient JLabel open;
    private transient JLabel cancel;
    private transient JLabel speed;
    private transient JLabel timeRemaining;

    private transient JProgressBar progressBar;

    /**
     * Constructor for download entry class
     * @param download
     */
    public DownloadEntry(Download download) {
        this.download = download;
    }

    /**
     * getter method for download field
     * @return
     */
    public Download getDownload() {
        return download;
    }

    /**
     * getter method for panel field
     * @param mode
     * @return
     */
    public JPanel getPanel(DownloadsList.state mode) {

        int sizeInBytes = download.getSizeInBytes();
        int downloadedBytes = download.getDownloadedBytes();
        String progressText;
        if (mode != DownloadsList.state.Completed) {
            progressText = download.getProgress();
        }
        else {
            progressText = Download.makePrefix(download.getSizeInBytes());
        }
        String time = "";
        if (download.getState() == Download.status.Downloading) {
            time = "n/a";
            if (download.estimatedTimeRemaining() != null) {
                time = download.estimatedTimeRemaining().toString();
            }
        }
        else if (download.getState() == Download.status.Paused){
            time = "";
        }

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
        //icon.setIcon(new ImageIcon(GUI.getScaledImage(new ImageIcon("src/icons/icon.png").getImage(),30,30)));
        iconPanel.add(icon, BorderLayout.CENTER);

        String path = "src/icons/files/file" + download.fileFormat();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path);
        }
        catch (FileNotFoundException e) { }
        File file = new File(path);
        Icon icon1;
        try {
            icon1 = FileSystemView.getFileSystemView().getSystemIcon(file);
        }
        catch (InvalidPathException e) {
            icon1 = new ImageIcon("src/icons/files/file.bin");
        }
        icon.setIcon(new ImageIcon(GUI.getScaledImage(icon2image(icon1), 30,30)));
        try {
            writer.close();
            file.delete();
        }
        catch (NullPointerException e) {}


        progress = new JLabel(progressText);
        progress.setFont(new Font("Arial", Font.PLAIN, 12));
        progress.setForeground(GUI.LEFT_SIDE_BACK_COLOR_PRESSED);
        progress.setOpaque(true);
        progress.setBackground(GUI.BACKGROUND_COLOR);



        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(1,0,0,0));

        title = new JLabel(download.getName());
        title.setOpaque(true);
        title.setBackground(GUI.BACKGROUND_COLOR);
        title.setFont(new Font("Arial", Font.HANGING_BASELINE,14));
        titlePanel.add(title, BorderLayout.CENTER);

        resume = new JLabel("R");
        resume.setOpaque(true);
        resume.setBackground(GUI.BACKGROUND_COLOR);
        //resume.setBorderPainted(false);
        resume.setFocusable(true);
        resume.setEnabled(true);
        resume.setIcon(new ImageIcon("src/icons/resume.png"));
        resume.setToolTipText("Resume");
        resume.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));



        open = new JLabel("O");
        open.setOpaque(true);
        open.setBackground(GUI.BACKGROUND_COLOR);
        //open.setBorderPainted(false);
        open.setFocusable(false);
        open.setIcon(new ImageIcon("src/icons/folder.png"));
        open.setBorder(BorderFactory.createEmptyBorder(0,6,0,8));

        cancel = new JLabel("C");
        cancel.setIcon(new ImageIcon("src/icons/cross.png"));
        cancel.setOpaque(true);
        cancel.setBackground(GUI.BACKGROUND_COLOR);
        //cancel.setBorderPainted(false);
        cancel.setBorder(BorderFactory.createEmptyBorder(0,8,0,0));

        speed = new JLabel(download.getSpeed());
        setFixedSize(speed,100,20);
        if (download.getState().equals(Download.status.Paused)) {
            speed.setText("Paused");
        }
        if (download.getState().equals(Download.status.InQueue)) {
            speed.setText("In queue");
        }
        if (download.getState().equals(Download.status.Cancelled)) {
            speed.setText("Cancelled");
        }
        if (mode == DownloadsList.state.Completed) {
            speed.setText("Downloaded");
        }
        speed.setFont(new Font("Arial", Font.PLAIN, 12));
        speed.setForeground(GUI.LEFT_SIDE_BACK_COLOR_PRESSED);
        speed.setIcon(new ImageIcon("src/icons/speed.png"));

        timeRemaining = new JLabel(time);
        if (mode == DownloadsList.state.Completed) {
            timeRemaining.setText(download.getFinishTime().toString());
        }
        timeRemaining.setFont(new Font("Arial", Font.PLAIN, 12));
        timeRemaining.setForeground(GUI.LEFT_SIDE_BACK_COLOR_PRESSED);
        timeRemaining.setOpaque(true);
        timeRemaining.setBackground(GUI.BACKGROUND_COLOR);
        if (mode != DownloadsList.state.Completed){
            setFixedSize(timeRemaining, 100,20);
        }
        else {
            setFixedSize(timeRemaining, 200,20);
        }
        timeRemaining.setIcon((new ImageIcon("src/icons/time.png")));

        content = new JPanel(new GridLayout(3,1,1,1));
        details = new JPanel(new BorderLayout());
        buttons = new JPanel();

        buttons.add(resume);
        buttons.add(open);
        buttons.add(cancel);
        buttons.setOpaque(true);
        buttons.setBackground(GUI.BACKGROUND_COLOR);

        //details.add(buttons, BorderLayout.CENTER);


        details.setOpaque(true);
        details.setBackground(GUI.BACKGROUND_COLOR);
        details.add(speed, BorderLayout.WEST);
        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(true);
        info.setBackground(GUI.BACKGROUND_COLOR);
        info.add(timeRemaining, BorderLayout.WEST);
        details.add(info, BorderLayout.CENTER);
        details.add(progress, BorderLayout.EAST);

        content.add(titlePanel);

        if (mode != DownloadsList.state.Completed) {
            content.add(progressBar);
            content.add(details);
        }
        else {
            details.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
            content.add(details);
            JPanel p = new JPanel();
            p.setOpaque(true);
            p.setBackground(GUI.BACKGROUND_COLOR);
            p.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
            //content.add(p);
        }


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

    /**
     * override of tostring method
     * @return
     */
    @Override
    public String toString() {
        return download.getName() + ": " + download.getProgress();
    }

    /**
     * this method generates details frame of a download
     * @return
     */
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

    /**
     * this method fixes size of a component
     * @param c
     * @param width
     * @param height
     */
    private void setFixedSize(Component c, int width, int height) {
        Dimension d = new Dimension(width, height);
        c.setMinimumSize(d);
        c.setMaximumSize(d);
        c.setPreferredSize(d);
    }

    /**
     * this image converts an icon to image
     * @param icon
     * @return
     */
    public static Image icon2image(Icon icon) {
        if (icon == null) {
            return null;
        }
        if (icon instanceof ImageIcon) {
            return ((ImageIcon)icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }

    /**
     * override of equals method
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return (download.equals(((DownloadEntry)obj).getDownload()));
    }
}
