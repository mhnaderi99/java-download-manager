
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class GUI {

    private static JFrame frame;
    private static Toolbar toolbar;
    private static DownloadsList list;
    private static HashMap<JLabel, Boolean> categoriesClicked;

    public static Color LEFT_SIDE_BACK_COLOR = new Color(50, 54, 63);
    public static Color LEFT_SIDE_BACK_COLOR_PRESSED = new Color(100, 104, 113);
    public static Color BACKGROUND_COLOR = new Color(231, 239, 251);
    public static Color TOOLBAR_COLOR = new Color(208, 223, 248);

    public GUI(DownloadsList.state mode) {
        list = new DownloadsList(mode);
        initiateFrame(mode);
    }

    private static void initiateFrame(DownloadsList.state mode) {
        frame = new JFrame("JDM");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (SystemTray.isSupported()) {
                    SystemTray tray = SystemTray.getSystemTray();
                    TrayIcon trayIcon = new TrayIcon(new ImageIcon("src/icons/icon.png").getImage(), "JDM");
                    trayIcon.setImageAutoSize(true);
                    trayIcon.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            tray.remove(trayIcon);
                            frame.setVisible(true);
                        }
                    });
                    try {
                        tray.add(trayIcon);

                    } catch (AWTException e1) {

                    }
                }
            }
        });
        frame.setIconImage(new ImageIcon("src/icons/icon.png").getImage());
        frame.setBackground(BACKGROUND_COLOR);
        frame.setMinimumSize(new Dimension(650, 400));
        frame.setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBar().getMenuBar());
        frame.add(makeMainPanel(mode));
    }

    public static JFrame makeAddDownloadFrame() {
        JFrame addDownloadFrame = new JFrame();
        addDownloadFrame.setIconImage(new ImageIcon("src/icons/add.png").getImage());
        addDownloadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDownloadFrame.setBackground(BACKGROUND_COLOR);
        addDownloadFrame.setLocationRelativeTo(getFrame());

        JPanel addDownloadMainPanel = new JPanel(new BorderLayout());
        addDownloadMainPanel.setOpaque(true);
        addDownloadMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel fieldsPanel = new JPanel(new BorderLayout());
        fieldsPanel.setOpaque(true);
        //fieldsPanel.setBackground(BACKGROUND_COLOR);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel radioPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        radioPanel.setOpaque(true);
        radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(LEFT_SIDE_BACK_COLOR, 1, true), "Start with", TitledBorder.CENTER, TitledBorder.TOP));

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.add(radioPanel, BorderLayout.CENTER);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setOpaque(true);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));

        JPanel linkPanel = new JPanel(new BorderLayout());
        linkPanel.setOpaque(true);
        //linkPanel.setBackground(BACKGROUND_COLOR);

        JPanel fileNamePanel = new JPanel(new BorderLayout());
        fileNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        fileNamePanel.setOpaque(true);
        //fileNamePanel.setBackground(BACKGROUND_COLOR);

        JLabel linkIcon = new JLabel();
        linkIcon.setOpaque(true);
        //linkIcon.setBackground(BACKGROUND_COLOR);
        linkIcon.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        linkIcon.setIcon(new ImageIcon("src/icons/link.png"));

        JTextField link = new JTextField();
        JTextField fileName = new JTextField();

        JLabel fileIcon = new JLabel();
        fileIcon.setOpaque(true);
        String clipboard;
//        try {
//            clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
//            try {
//                URL test = new URL(clipboard);
//                link.setText(clipboard);
//                fileName.setText(Network.generateFileName(new URL(clipboard)));
//
//            } catch (MalformedURLException e) {
//            }
//
//        } catch (UnsupportedFlavorException e) {
//        } catch (IOException e) {
//        }

        //fileIcon.setBackground(BACKGROUND_COLOR);
        fileIcon.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        fileIcon.setIcon(new ImageIcon("src/icons/exe.jpg"));


        ButtonGroup downloadOptions = new ButtonGroup();

        JRadioButton auto = new JRadioButton("Automatically");
        auto.setSelected(true);
        JRadioButton manual = new JRadioButton("Manually");
        JPanel queuesPanel = new JPanel(new BorderLayout());
        JRadioButton queue = new JRadioButton("Queue");
        //JComboBox queues = new JComboBox();

        //queues.setPrototypeDisplayValue("...............");
        //queues.setEnabled(false);
        JPanel comboPanel = new JPanel(new BorderLayout());
        //comboPanel.add(queues, BorderLayout.WEST);

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


        addDownloadFrame.setSize(500, 250);
        addDownloadFrame.setTitle("New Downlaod");
        addDownloadFrame.setResizable(false);
        addDownloadFrame.add(addDownloadMainPanel);

        auto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //queues.setEnabled(false);
            }
        });

        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //queues.setEnabled(false);
            }
        });

        queue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //queues.setEnabled(true);
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

                //try {
                    //URL url1 = new URL(url);
                    Download download = new Download(name, url);
                    if (name.equals("")) {
                        JOptionPane.showMessageDialog(addDownloadMainPanel, "File name shouldn't be empty.");
                    } else {
                        Download.status status;
                        if (auto.isSelected()) {
                            status = Download.status.Downloading;
                        } else {
                            status = Download.status.Paused;
                            if (queue.isSelected()) {
                                DownloadManager.getQueue().addDownloadToList(download);
                            }
                        }
                        if (DownloadManager.getSettings().isSynchronicDownloadsLimited() && DownloadManager.getInProgressDownloads() >= DownloadManager.getSettings().getMaximumSynchronicDownloads() && status.equals(Download.status.Downloading)) {
                            download.setState(Download.status.Paused);
                            JOptionPane.showMessageDialog(frame, "Download status was automatically set to paused, due to maximum synchronic downloads limit.", "Message",1);
                        }
                        else {
                            download.setState(status);
                        }
                        download.setCreationTime(Calendar.getInstance().getTime());
                        int size = new Random().nextInt(1000000000);
                        int downloaded = new Random().nextInt(size);
                        //int size = Network.getFileSize(url1);
                        //if (size != -1) {
                            download.setSizeInBytes(size);
                            download.setDownloadedBytes(downloaded);
                        //} else {
                            //JOptionPane.showMessageDialog(addDownloadMainPanel, "Error");
                        //}

                        if (! queue.isSelected()) {DownloadManager.getProccessing().addDownloadToList(download);}

                        addDownloadFrame.dispatchEvent(new WindowEvent(addDownloadFrame, WindowEvent.WINDOW_CLOSING));
                    }
                //} catch (MalformedURLException e1) {
                  //  JOptionPane.showMessageDialog(addDownloadMainPanel, "The URL is not valid.");
                //}
            }
        });

        return addDownloadFrame;
    }

    private static JPanel makeMainPanel(DownloadsList.state mode) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.add(makeLeftBar(), BorderLayout.WEST);
        JPanel downloadsPanel = makeDownloadsListPanel(mode);
        mainPanel.add(downloadsPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private static JPanel makeLeftBar() {
        categoriesClicked = new HashMap<JLabel, Boolean>();

        JPanel leftBar = new JPanel(new BorderLayout());

        JLabel image = new JLabel();
        ImageIcon icon = new ImageIcon("src/icons/logo.png");
        image.setIcon(icon);
        image.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 20));

        JToolBar options = new JToolBar();
        options.setOpaque(true);
        options.setFloatable(false);
        options.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        options.setBackground(LEFT_SIDE_BACK_COLOR);
        options.setBorderPainted(false);
        options.setOrientation(JToolBar.VERTICAL);

        JLabel processing = new JLabel("Processing");
        processing.setName("proccessing");
        processing.setOpaque(true);
        processing.setToolTipText("Downloads in proccess");
        processing.setForeground(BACKGROUND_COLOR);
        processing.setBackground(Color.BLACK);
        processing.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 61));
        processing.setFont(new Font("Arial", Font.BOLD, 13));
        processing.setIcon(new ImageIcon("src/icons/processing.png"));
        processing.addMouseListener(new leftMenuMouseHandler());

        JLabel completed = new JLabel("Completed");
        completed.setName("completed");
        completed.setOpaque(true);
        completed.setToolTipText("Completed downloads");
        completed.setForeground(BACKGROUND_COLOR);
        completed.setBackground(LEFT_SIDE_BACK_COLOR);
        completed.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 65));
        completed.setFont(new Font("Arial", Font.BOLD, 13));
        completed.setIcon(new ImageIcon("src/icons/completed.png"));
        completed.addMouseListener(new leftMenuMouseHandler());

        JLabel queue = new JLabel("Queue");
        queue.setName("queue");
        queue.setOpaque(true);
        queue.setToolTipText("Downloads in queue");
        queue.setForeground(BACKGROUND_COLOR);
        queue.setBackground(LEFT_SIDE_BACK_COLOR);
        queue.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 84));
        queue.setFont(new Font("Arial", Font.BOLD, 13));
        queue.setIcon(new ImageIcon("src/icons/queue.png"));
        queue.addMouseListener(new leftMenuMouseHandler());

        categoriesClicked.put(processing, true);
        categoriesClicked.put(completed, false);
        categoriesClicked.put(queue, false);

        options.add(processing);
        options.add(completed);
        options.add(queue);

        leftBar.setBackground(LEFT_SIDE_BACK_COLOR);
        leftBar.add(image, BorderLayout.NORTH);
        leftBar.add(options, BorderLayout.WEST);

        return leftBar;
    }

    private static class leftMenuMouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            for (JLabel jLabel : categoriesClicked.keySet()) {
                if (categoriesClicked.get(jLabel) && jLabel != label) {
                    jLabel.setBackground(LEFT_SIDE_BACK_COLOR);
                    categoriesClicked.put(jLabel, false);
                }
            }
            categoriesClicked.put(label, true);
            label.setBackground(Color.BLACK);

            if (((JLabel) e.getSource()).getName().equals("proccessing")) {
                setList(DownloadManager.getProccessing());
                DownloadManager.setState(DownloadsList.state.Processing);
            }
            if (((JLabel) e.getSource()).getName().equals("completed")) {
                setList(DownloadManager.getCompleted());
                DownloadManager.setState(DownloadsList.state.Completed);
            }
            if (((JLabel) e.getSource()).getName().equals("queue")) {
                DownloadManager.setState(DownloadsList.state.Queue);
                setList(DownloadManager.getQueue());
            }

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
            if (!categoriesClicked.get(label)) {
                label.setBackground(LEFT_SIDE_BACK_COLOR_PRESSED);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (!categoriesClicked.get(label)) {
                label.setBackground(LEFT_SIDE_BACK_COLOR);
            }
        }
    }


    private static JPanel makeToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new BorderLayout());
        toolbarPanel.setBackground(TOOLBAR_COLOR);
        toolbar = new Toolbar();
        JToolBar jToolBar = toolbar.getToolBar();
        jToolBar.setFloatable(false);
        JPanel temp = new JPanel(new BorderLayout());
        temp.add(jToolBar);
        toolbarPanel.add(temp, BorderLayout.CENTER);
        toolbarPanel.setBorder(BorderFactory.createCompoundBorder());

        return toolbarPanel;
    }

    private static JPanel makeDownloadsListPanel(DownloadsList.state mode) {

        setList(getList(mode));

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setOpaque(true);
        listPanel.setBackground(BACKGROUND_COLOR);
        listPanel.add(makeToolbarPanel(), BorderLayout.NORTH);

        JScrollPane pane = new JScrollPane(list.getDownloadEntries(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.setOpaque(true);
        pane.setBackground(BACKGROUND_COLOR);
        listPanel.add(pane, BorderLayout.CENTER);

        return listPanel;
    }

    private static DownloadsList getList(DownloadsList.state mode) {
        if (mode == DownloadsList.state.Completed) {return DownloadManager.getCompleted();}
        if (mode == DownloadsList.state.Processing) {return DownloadManager.getProccessing();}
        if (mode == DownloadsList.state.Queue) {return DownloadManager.getQueue();}
        if (mode == DownloadsList.state.Removed) {return DownloadManager.getRemoved();}

        return null;
    }

    public static void setList(DownloadsList downloadsList) {
        DownloadsList.state mode = downloadsList.getMode();
        if (mode == DownloadsList.state.Processing) {
            list.setModel(DownloadManager.getProccessing().getModel());
        }
        if (mode == DownloadsList.state.Completed) {
            list.setModel(DownloadManager.getCompleted().getModel());
        }
        if (mode == DownloadsList.state.Removed) {
            list.setModel(DownloadManager.getRemoved().getModel());
        }
        if (mode == DownloadsList.state.Queue) {
            list.setModel(DownloadManager.getQueue().getModel());
        }
        if (mode == DownloadsList.state.SearchResult) {
            list.setModel(downloadsList.getModel());
        }
        SwingUtilities.updateComponentTreeUI(list);
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

    public void showGUI() {
        frame.setVisible(true);
    }

    public void update() {
        list.getDownloadEntries().setModel(list.getModel());
        list.getDownloadEntries().repaint();
    }

    public static JFrame makeAboutFrame() {
        JFrame about = new JFrame();
        about.setTitle("About");
        about.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        about.setSize(400, 200);
        about.setIconImage(new ImageIcon("src/icons/about.png").getImage());
        about.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JTextArea area = new JTextArea();
        area.setFont(new Font("Arial", 14,14));
        area.setBackground(BACKGROUND_COLOR);
        area.setEditable(false);
        area.setText("This software is developed by:" + '\n' + "Mohammadhossein Naderi 9631815" + '\n' + "Email:    mhnaderi99@gmail.com" + '\n' + "Phone:      +989383444200" +
                '\n' + "This is a simple download manager." + '\n' +"By using JDM you can easily manage your downloads." + '\n' + "12/5/2018 - 19/5/2018");
        panel.add(area, BorderLayout.CENTER);
        about.add(panel);

        return about;
    }

    public static JFrame makeSortFrame() {
        JFrame sort = new JFrame();
        sort.setTitle("Sort");
        sort.setIconImage(new ImageIcon("src/icons/sort.png").getImage());
        sort.setResizable(false);
        sort.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sort.setSize(400, 180);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        Border border = BorderFactory.createEmptyBorder(3, 5, 3, 5);

        JPanel filtersPanel = new JPanel(new BorderLayout());
        filtersPanel.setOpaque(true);
        filtersPanel.setBorder(border);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setOpaque(true);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));

        JPanel firstFilterPanel = new JPanel(new BorderLayout());
        firstFilterPanel.setOpaque(true);
        firstFilterPanel.setBorder(border);

        JPanel secondFilterPanel = new JPanel(new BorderLayout());
        secondFilterPanel.setOpaque(true);
        secondFilterPanel.setBorder(border);

        String[] arr = {"By name", "By size", "By creation time", "By downloaded percentage", "By downloaded amount"};
        JLabel filter1 = new JLabel("First filter:      ");
        JLabel filter2 = new JLabel("Second filter:  ");


        JComboBox firstFilter = new JComboBox(arr);
        JComboBox secondFilter = new JComboBox(arr);
        firstFilter.setSelectedIndex(0);
        secondFilter.setSelectedIndex(1);

        JPanel firstCombo = new JPanel(new BorderLayout());
        firstCombo.setBorder(border);
        firstCombo.setOpaque(true);
        firstCombo.add(firstFilter, BorderLayout.CENTER);

        JPanel secondCombo = new JPanel(new BorderLayout());
        secondCombo.setBorder(border);
        secondCombo.setOpaque(true);
        secondCombo.add(secondFilter, BorderLayout.CENTER);

        JToggleButton firstIsAscending = new JToggleButton(" Ascending ");
        firstIsAscending.setFocusable(false);
        firstIsAscending.setSelected(true);

        JPanel firstToggle = new JPanel(new BorderLayout());
        firstToggle.setBorder(border);
        firstToggle.setOpaque(true);
        firstToggle.add(firstIsAscending, BorderLayout.CENTER);

        JToggleButton secondIsAscending = new JToggleButton(" Ascending ");
        secondIsAscending.setFocusable(false);
        secondIsAscending.setSelected(true);

        JPanel secondToggle = new JPanel(new BorderLayout());
        secondToggle.setBorder(border);
        secondToggle.setOpaque(true);
        secondToggle.add(secondIsAscending, BorderLayout.CENTER);

        firstFilterPanel.add(filter1, BorderLayout.WEST);
        firstFilterPanel.add(firstToggle, BorderLayout.EAST);
        firstFilterPanel.add(firstCombo, BorderLayout.CENTER);

        secondFilterPanel.add(filter2, BorderLayout.WEST);
        secondFilterPanel.add(secondToggle, BorderLayout.EAST);
        secondFilterPanel.add(secondCombo, BorderLayout.CENTER);

        panel.add(filtersPanel, BorderLayout.NORTH);
        filtersPanel.add(firstFilterPanel, BorderLayout.NORTH);
        filtersPanel.add(secondFilterPanel, BorderLayout.CENTER);

        JButton ok = new JButton("OK");
        ok.setOpaque(true);

        JButton cancel = new JButton("Cancel");
        cancel.setOpaque(true);

        JPanel cancelButton = new JPanel(new BorderLayout());
        cancelButton.add(cancel, BorderLayout.EAST);

        buttonsPanel.add(ok, BorderLayout.EAST);
        buttonsPanel.add(cancelButton, BorderLayout.CENTER);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        sort.add(panel);

        firstIsAscending.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (firstIsAscending.isSelected()) {
                    firstIsAscending.setText(" Ascending ");
                } else {
                    firstIsAscending.setText("Descending");
                }
            }
        });

        secondIsAscending.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (secondIsAscending.isSelected()) {
                    secondIsAscending.setText(" Ascending ");
                } else {
                    secondIsAscending.setText("Descending");
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort.dispatchEvent(new WindowEvent(sort, WindowEvent.WINDOW_CLOSING));
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index1 = firstFilter.getSelectedIndex();
                int index2 = secondFilter.getSelectedIndex();
                boolean isFirstAscending = firstIsAscending.isSelected();
                boolean isSecondAscending = secondIsAscending.isSelected();
                Comparator priority1 = Download.getComparator(firstFilter.getItemAt(index1).toString());
                Comparator priority2 = Download.getComparator(secondFilter.getItemAt(index2).toString());
                sort.dispatchEvent(new WindowEvent(sort, WindowEvent.WINDOW_CLOSING));
                list.sortDownloads(priority1, isFirstAscending, priority2, isSecondAscending);
            }
        });

        return sort;
    }

    public static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public static Toolbar getToolbar() {
        return toolbar;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static DownloadsList getList() {
        return list;
    }
}
