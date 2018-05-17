
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
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

        Color empty = new Color(230,230,230), full = new Color(20,150,70);

        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, sizeInBytes);
        
        progressBar.setBackground(empty);
        progressBar.setForeground(full);
        
        progressBar.setBorderPainted(true);
        progressBar.setString(download.getDownloadedPercentage() + "%");
        progressBar.setStringPainted(true);
        
        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return full; }
            protected Color getSelectionForeground() { return empty; }
        });

        progressBar.setBorder(BorderFactory.createLineBorder(GUI.TOOLBAR_COLOR, 1));
        progressBar.setValue(downloadedBytes);

        iconPanel = new JPanel(new BorderLayout());
        iconPanel.setBackground(GUI.BACKGROUND_COLOR);
        iconPanel.setOpaque(true);
        iconPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        icon = new JLabel("");
        icon.setOpaque(true);
        icon.setBackground(GUI.BACKGROUND_COLOR);
        icon.setIcon(new ImageIcon("src/icons/icon.png"));
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

    public JPanel gettPanel() {
        return panel;
    }

    public JPanel getContent() {
        return content;
    }

    public JPanel getDetails() {
        return details;
    }

    public JPanel getButtons() {
        return buttons;
    }

    public JPanel getIconPanel() {
        return iconPanel;
    }

    public JPanel getTitlePanel() {
        return titlePanel;
    }

    public JLabel getTitle() {
        return title;
    }

    public JLabel getIcon() {
        return icon;
    }

    public JLabel getProgress() {
        return progress;
    }

    public JLabel getSpeed() {
        return speed;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getResume() {
        return resume;
    }

    public JLabel getOpen() {
        return open;
    }

    public JLabel getCancel() {
        return cancel;
    }

    @Override
    public String toString() {
        return download.getName() + ": " + download.getProgress();
    }
}
