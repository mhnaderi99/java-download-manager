import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This class implements the menubar of the gui of download manager
 * Created by 9631815 on 5/12/2018.
 */
public class MenuBar {

    /**
     * instances of menubar
     */
    private static JMenuBar menuBar;
    JMenu download;
    JMenu help;
    private JMenuItem addNewDownload;
    private JMenuItem pauseAllDownloads;
    private JMenuItem resumeAllDownloads;
    private JMenuItem sortDownloads;
    private JMenuItem export;
    private JMenuItem settings;
    private JMenuItem exit;
    private JMenuItem about;

    /**
     * Constructor for menuBar class
     */
    public MenuBar() {
        initiateMenuBar();
    }

    /**
     * This method initializes menubar
     */
    private void initiateMenuItems(){
        download = new JMenu("Download");
        download.setMnemonic(KeyEvent.VK_D);

        help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);

        EventHandler handler = new EventHandler();

        addNewDownload = new JMenuItem("Add", KeyEvent.VK_A);
        addNewDownload.setAccelerator(KeyStroke.getKeyStroke("control N"));
        addNewDownload.setName("add");
        addNewDownload.addActionListener(handler);

        pauseAllDownloads = new JMenuItem("Pause all", KeyEvent.VK_P);
        pauseAllDownloads.setAccelerator(KeyStroke.getKeyStroke("control P"));
        pauseAllDownloads.setName("pause all");
        pauseAllDownloads.addActionListener(handler);

        resumeAllDownloads = new JMenuItem("Resume all", KeyEvent.VK_R);
        resumeAllDownloads.setAccelerator(KeyStroke.getKeyStroke("control R"));
        resumeAllDownloads.setName("resume all");
        resumeAllDownloads.addActionListener(handler);

        sortDownloads = new JMenuItem("Sort", KeyEvent.VK_T);
        sortDownloads.setAccelerator(KeyStroke.getKeyStroke("control T"));
        sortDownloads.setName("sort");
        sortDownloads.addActionListener(handler);

        export = new JMenuItem("Export", KeyEvent.VK_X);
        export.setAccelerator(KeyStroke.getKeyStroke("control U"));
        export.setName("export");
        export.addActionListener(handler);

        settings = new JMenuItem("Settings", KeyEvent.VK_S);
        settings.setAccelerator(KeyStroke.getKeyStroke("control S"));
        settings.setName("settings");
        settings.addActionListener(handler);

        exit = new JMenuItem("Exit", KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke("control E"));
        exit.setName("exit");
        exit.addActionListener(handler);

        about = new JMenuItem("About", KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke("control B"));
        about.setName("about");
        about.addActionListener(handler);


        download.add(addNewDownload);
        download.add(pauseAllDownloads);
        download.add(resumeAllDownloads);
        download.add(new JSeparator());
        download.add(sortDownloads);
        download.add(new JSeparator());
        download.add(export);
        download.add(settings);
        download.add(new JSeparator());
        download.add(exit);

        help.add(about);
    }

    /**
     * this method initializes menuBar
     */
    private void initiateMenuBar(){
        initiateMenuItems();
        menuBar = new JMenuBar();
        menuBar.add(download);
        menuBar.add(help);
    }


    /**
     * getter method for menubar field
     * @return
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
