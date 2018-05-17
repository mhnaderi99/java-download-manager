
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.LabelUI;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class GUI {


    private static JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel toolbarPanel;
    private JPanel downloadsPanel;
    private JPanel leftBar;
    private static Toolbar toolbar;
    private static MenuBar menuBar;
    private static DownloadsList list;

    private static JFrame addDownloadFrame;

    private HashMap<JLabel, Boolean> categoriesClicked;

    public static Color LEFT_SIDE_BACK_COLOR = new Color(50,54,63);
    public static Color LEFT_SIDE_BACK_COLOR_PRESSED = new Color(100,104,113);
    public static Color BACKGROUND_COLOR = new Color(231,239,251);
    public static Color TOOLBAR_COLOR = new Color(208, 223, 248);

    public GUI() {

        /*UIManager.put("Label.font", new Font("Arial", 14, 10));
        UIManager.put("Button.font", new Font("Arial", 14, 12));
        UIManager.put("Menu.font", new Font("Arial", 14, 12));
        UIManager.put("MenuItem.font", new Font("Arial", 14, 12));
        UIManager.put("RadioButton.font", new Font("Arial", Font.PLAIN, 12));
        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 12));
        UIManager.put("JPanel.background", BACKGROUND_COLOR);
        */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initiateFrame();
        //initAddDownloadFrame();
    }

    private void initiateFrame() {
        mainFrame = new JFrame("JDM");
        mainFrame.setIconImage(new ImageIcon("src/icons/icon.png").getImage());
        mainFrame.setBackground(BACKGROUND_COLOR);
        mainFrame.setMinimumSize(new Dimension(650,400));
        mainFrame.setSize(800,600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new MenuBar();
        mainFrame.setJMenuBar(menuBar.getMenuBar());
        initiateMainPanel();
    }

    public static void initAddDownloadFrame() {
        addDownloadFrame = new JFrame();
        addDownloadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDownloadFrame.setBackground(BACKGROUND_COLOR);
        addDownloadFrame.setLocationRelativeTo(mainFrame);

        JPanel addDownloadMainPanel = new JPanel(new BorderLayout());
        addDownloadMainPanel.setOpaque(true);
        //addDownloadMainPanel.setBackground();
        addDownloadMainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel fieldsPanel = new JPanel(new BorderLayout());
        fieldsPanel.setOpaque(true);
        //fieldsPanel.setBackground(BACKGROUND_COLOR);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel radioPanel = new JPanel(new GridLayout(3,1,5,5));
        radioPanel.setOpaque(true);
        radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(LEFT_SIDE_BACK_COLOR, 1, true),"Start with", TitledBorder.CENTER, TitledBorder.TOP));

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.add(radioPanel, BorderLayout.CENTER);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setOpaque(true);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5,20,5,5));

        JPanel linkPanel = new JPanel(new BorderLayout());
        linkPanel.setOpaque(true);
        //linkPanel.setBackground(BACKGROUND_COLOR);

        JPanel fileNamePanel = new JPanel(new BorderLayout());
        fileNamePanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        fileNamePanel.setOpaque(true);
        //fileNamePanel.setBackground(BACKGROUND_COLOR);

        JLabel linkIcon = new JLabel();
        linkIcon.setOpaque(true);
        //linkIcon.setBackground(BACKGROUND_COLOR);
        linkIcon.setBorder(BorderFactory.createEmptyBorder(2,4,2,4));
        linkIcon.setIcon(new ImageIcon("src/icons/link.png"));

        JTextField link = new JTextField();
        JTextField fileName = new JTextField();

        JLabel fileIcon = new JLabel();
        fileIcon.setOpaque(true);
        String clipboard;
        try {
            clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            try {
                URL test = new URL(clipboard);
                link.setText(clipboard);
                fileName.setText(Network.generateFileName(new URL(clipboard)));

            } catch (MalformedURLException e) {
            }

        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //fileIcon.setBackground(BACKGROUND_COLOR);
        fileIcon.setBorder(BorderFactory.createEmptyBorder(2,4,2,4));
        fileIcon.setIcon(new ImageIcon("src/icons/exe.jpg"));



        ButtonGroup downloadOptions = new ButtonGroup();

        JRadioButton auto = new JRadioButton("Automatically");
        auto.setSelected(true);
        JRadioButton manual = new JRadioButton("Manually");
        JPanel queuesPanel = new JPanel(new BorderLayout());
        JRadioButton queue = new JRadioButton("Queues");
        JComboBox queues = new JComboBox(DownloadManager.getQueues().toArray());
        queues.setEnabled(false);
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(queues, BorderLayout.WEST);

        queuesPanel.add(queue, BorderLayout.WEST);
        queuesPanel.add(comboPanel, BorderLayout.CENTER);

        downloadOptions.add(auto);
        downloadOptions.add(manual);
        downloadOptions.add(queue);

        radioPanel.add(auto);
        radioPanel.add(manual);
        radioPanel.add(queuesPanel);

        JButton ok = new JButton("OK");
        ok.setOpaque(true);

        JButton cancel = new JButton("Cancel");
        cancel.setOpaque(true);

        JPanel cancelButton = new JPanel(new BorderLayout());
        cancelButton.add(cancel, BorderLayout.EAST);

        linkPanel.add(linkIcon, BorderLayout.WEST);
        linkPanel.add(link, BorderLayout.CENTER);

        fileNamePanel.add(fileIcon, BorderLayout.WEST);
        fileNamePanel.add(fileName, BorderLayout.CENTER);



        buttonsPanel.add(ok, BorderLayout.EAST);
        buttonsPanel.add(cancelButton, BorderLayout.CENTER);

        addDownloadMainPanel.add(fieldsPanel, BorderLayout.NORTH);
        addDownloadMainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        addDownloadMainPanel.add(optionsPanel, BorderLayout.CENTER);
        fieldsPanel.add(linkPanel, BorderLayout.NORTH);
        fieldsPanel.add(fileNamePanel, BorderLayout.CENTER);



        addDownloadFrame.setSize(500,250);
        addDownloadFrame.setTitle("New Downlaod");
        addDownloadFrame.setResizable(false);
        addDownloadFrame.setVisible(false);
        addDownloadFrame.add(addDownloadMainPanel);

        auto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queues.setEnabled(false);
            }
        });

        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queues.setEnabled(false);
            }
        });

        queue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queues.setEnabled(true);
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDownloadFrame.dispatchEvent(new WindowEvent(addDownloadFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = link.getText();
                String name = fileName.getText();

                try {
                    URL url1 = new URL(url);
                    Download download = new Download(name, url);
                    if (name.equals("")) {
                        JOptionPane.showMessageDialog(addDownloadMainPanel, "File name shouldn't be empty.");
                    }
                    else {
                        Download.status status;
                        if (downloadOptions.getSelection().equals(auto)) {
                            status = Download.status.Downloading;
                        }
                        else {
                            status = Download.status.Paused;
                            if (downloadOptions.getSelection().equals(queue)) {
                                int q = queues.getSelectedIndex();
                                DownloadManager.getQueues().get(q).add(download);
                            }
                        }

                        download.setState(status);
                        download.setCreationTime(Calendar.getInstance().getTime());
                        int size = Network.getFileSize(url1);
                        if (size != -1) {
                            download.setSizeInBytes(size);
                        }
                        else {
                            JOptionPane.showMessageDialog(addDownloadMainPanel, "Error");
                        }
                        list.addDownloadToList(download);

                        addDownloadFrame.dispatchEvent(new WindowEvent(addDownloadFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }catch (MalformedURLException e1) {
                    JOptionPane.showMessageDialog(addDownloadMainPanel, "The URL is not valid.");
                }
            }
        });

    }

    private void initiateMainPanel(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.setBackground(GUI.BACKGROUND_COLOR);
        initToolbarPanel();
        initDownloadsList();
        initLeftBar();
        mainPanel.add(leftBar, BorderLayout.WEST);
        //mainPanel.add(toolbarPanel, BorderLayout.NORTH);
        mainPanel.add(downloadsPanel, BorderLayout.CENTER);
        //mainPanel.add(toolbarPanel, BorderLayout.NORTH);
        mainFrame.add(mainPanel);
    }

    private void initLeftBar() {
        categoriesClicked = new HashMap<JLabel, Boolean>();

        leftBar = new JPanel(new BorderLayout());

        JLabel image = new JLabel();
        ImageIcon icon = new ImageIcon("src/icons/logo.png");
        image.setIcon(icon);
        image.setBorder(BorderFactory.createEmptyBorder(0,10,0,20));

        JToolBar options = new JToolBar();
        options.setOpaque(true);
        options.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        options.setBackground(LEFT_SIDE_BACK_COLOR);
        options.setBorderPainted(false);
        options.setOrientation(JToolBar.VERTICAL);

        JLabel processing = new JLabel("Processing");
        processing.setOpaque(true);
        processing.setForeground(BACKGROUND_COLOR);
        processing.setBackground(Color.BLACK);
        processing.setBorder(BorderFactory.createEmptyBorder(3,3,3,61));
        processing.setFont(new Font("Arial", Font.BOLD, 13));
        processing.setIcon(new ImageIcon("src/icons/processing.png"));
        processing.addMouseListener(new leftMenuMouseHandler());
        categoriesClicked.put(processing, true);


        JLabel completed = new JLabel("Completed");
        completed.setOpaque(true);
        completed.setForeground(BACKGROUND_COLOR);
        completed.setBackground(LEFT_SIDE_BACK_COLOR);
        completed.setBorder(BorderFactory.createEmptyBorder(3,3,3,65));
        completed.setFont(new Font("Arial", Font.BOLD, 13));
        completed.setIcon(new ImageIcon("src/icons/completed.png"));
        completed.addMouseListener(new leftMenuMouseHandler());
        categoriesClicked.put(completed, false);


        JLabel queues = new JLabel("Queues");
        queues.setOpaque(true);
        queues.setForeground(BACKGROUND_COLOR);
        queues.setBackground(LEFT_SIDE_BACK_COLOR);
        queues.setBorder(BorderFactory.createEmptyBorder(3,3,3,84));
        queues.setFont(new Font("Arial", Font.BOLD, 13));
        queues.setIcon(new ImageIcon("src/icons/queue.png"));
        queues.addMouseListener(new leftMenuMouseHandler());
        categoriesClicked.put(queues, false);

        options.add(processing);
        options.add(completed);
        options.add(queues);


        //leftBar.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        leftBar.setBackground(LEFT_SIDE_BACK_COLOR);
        leftBar.add(image, BorderLayout.NORTH);
        leftBar.add(options, BorderLayout.WEST);

        //leftBar.setPreferredSize(new Dimension(150,0));
    }

    private class leftMenuMouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            for (JLabel jLabel: categoriesClicked.keySet()) {
                if (categoriesClicked.get(jLabel) && jLabel!=label) {
                    jLabel.setBackground(LEFT_SIDE_BACK_COLOR);
                    categoriesClicked.put(jLabel, false);
                }
            }
            categoriesClicked.put(label, true);
            label.setBackground(Color.BLACK);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (! categoriesClicked.get(label)) {
                label.setBackground(LEFT_SIDE_BACK_COLOR_PRESSED);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (! categoriesClicked.get(label)) {
                label.setBackground(LEFT_SIDE_BACK_COLOR);
            }
        }
    }

    private void initToolbarPanel() {
        toolbarPanel = new JPanel(new BorderLayout());
        toolbarPanel.setBackground(TOOLBAR_COLOR);
        toolbar = new Toolbar(true);
        JToolBar jToolBar = toolbar.getToolBar();
        jToolBar.setFloatable(false);
        JPanel temp = new JPanel(new BorderLayout());
        temp.add(jToolBar);
        toolbarPanel.add(temp, BorderLayout.WEST);
        toolbarPanel.setBorder(BorderFactory.createCompoundBorder());
    }

    private void initDownloadsList() {
        list = new DownloadsList(true);

        for (int i=1;i<=3;i++){
            Download download = new Download("Download" + i, "Link" + i);
            download.setSizeInBytes(Math.abs(new Random().nextInt(1000000000)));
            download.setDownloadedBytes(new Random().nextInt(download.getSizeInBytes()));
            download.setBytesPerSecond(new Random().nextInt(2000000));

            list.addDownloadToList(download);
        }

        downloadsPanel = new JPanel(new BorderLayout());
        downloadsPanel.add(toolbarPanel, BorderLayout.NORTH);

        JScrollPane downloads = new JScrollPane(list.getDownloadEntries(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        downloads.setBackground(GUI.BACKGROUND_COLOR);
        downloads.setOpaque(true);
        downloadsPanel.add(downloads, BorderLayout.CENTER);
        downloadsPanel.setOpaque(true);
        downloadsPanel.setBackground(BACKGROUND_COLOR);

    }

    public static ArrayList<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        ArrayList<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }

    public static void showMainFrame() {
        mainFrame.setVisible(true);
    }

    public static void showAddDownloadFrame() {
        initAddDownloadFrame();
        addDownloadFrame.setVisible(true);
    }

    public static void hideDownloadFrame() {
        addDownloadFrame.setVisible(false);
    }

}
