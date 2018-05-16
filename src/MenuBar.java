import javax.swing.*;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class MenuBar {

    private JMenuBar menuBar;
    JMenu download;
    JMenu help;
    private JMenuItem addNewDownload;
    private JMenuItem pauseAllDownloads;
    private JMenuItem resumeAllDownloads;
    private JMenuItem sortDownloads;
    private JMenuItem settings;
    private JMenuItem exit;
    private JMenuItem about;

    public MenuBar(){
        initiateMenuBar();
    }

    private void initiateMenuItems(){
        download = new JMenu("Download");
        help = new JMenu("Help");

        addNewDownload = new JMenuItem("Add");
        pauseAllDownloads = new JMenuItem("Pause");
        resumeAllDownloads = new JMenuItem("Resume");
        sortDownloads = new JMenuItem("Sort");
        settings = new JMenuItem("Settings");
        exit = new JMenuItem("Exit");
        about = new JMenuItem("About");


        download.add(addNewDownload);
        download.add(pauseAllDownloads);
        download.add(resumeAllDownloads);
        download.add(new JSeparator());
        download.add(sortDownloads);
        download.add(new JSeparator());
        download.add(settings);
        download.add(new JSeparator());
        download.add(exit);

        help.add(about);
    }

    private void initiateMenuBar(){
        initiateMenuItems();
        menuBar = new JMenuBar();
        menuBar.add(download);
        menuBar.add(help);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
