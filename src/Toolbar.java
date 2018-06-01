import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class Toolbar {

    private JToolBar toolBar;

    private JButton addNewDownload;
    private JButton pauseAllDownloads;
    private JButton resumeAllDownloads;
    private JButton cancelAllDownloads;
    private JButton sortDownloads;
    private JButton removeAllDownloads;
    private JButton settings;
    private JTextField search;
    private JLabel searchButton;


    public Toolbar() {
        initiateToolbar();
    }

    public void setEnabledButtons(boolean state) {
        pauseAllDownloads.setEnabled(state);
        resumeAllDownloads.setEnabled(state);
        cancelAllDownloads.setEnabled(state);
        removeAllDownloads.setEnabled(state);
    }

    private void initiateButtons() {

        addNewDownload = new JButton("");
        addNewDownload.setName("add");
        addNewDownload.setToolTipText("Add a new download");
        addNewDownload.setIcon(new ImageIcon("src/icons/add.png"));
        addNewDownload.setBorderPainted(false);
        addNewDownload.setBackground(GUI.TOOLBAR_COLOR);
        addNewDownload.setFocusable(false);
        addNewDownload.setOpaque(true);
        addNewDownload.addActionListener(new EventHandler());
        addNewDownload.setPressedIcon(new ImageIcon("src/icons/pressed/add.png"));
        addNewDownload.addMouseListener(new mouseHandler());


        pauseAllDownloads = new JButton("");
        pauseAllDownloads.setName("pause");
        pauseAllDownloads.setToolTipText("Pause selected downloads");
        pauseAllDownloads.setIcon(new ImageIcon("src/icons/pause.png"));
        pauseAllDownloads.setBorderPainted(false);
        pauseAllDownloads.setBackground(GUI.TOOLBAR_COLOR);
        pauseAllDownloads.setEnabled(false);
        pauseAllDownloads.setFocusable(false);
        pauseAllDownloads.setOpaque(true);
        pauseAllDownloads.addActionListener(new EventHandler());
        pauseAllDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/pause.png"));
        pauseAllDownloads.addMouseListener(new mouseHandler());

        resumeAllDownloads = new JButton("");
        resumeAllDownloads.setName("resume");
        resumeAllDownloads.setToolTipText("Resume selected downloads");
        resumeAllDownloads.setIcon(new ImageIcon("src/icons/play.png"));
        resumeAllDownloads.setBorderPainted(false);
        resumeAllDownloads.setBackground(GUI.TOOLBAR_COLOR);
        resumeAllDownloads.setEnabled(false);
        resumeAllDownloads.setFocusable(false);
        resumeAllDownloads.setOpaque(true);
        resumeAllDownloads.addActionListener(new EventHandler());
        resumeAllDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/play.png"));
        resumeAllDownloads.addMouseListener(new mouseHandler());


        cancelAllDownloads = new JButton("");
        cancelAllDownloads.setName("cancel");
        cancelAllDownloads.setToolTipText("Cancel selected downloads");
        cancelAllDownloads.setIcon(new ImageIcon("src/icons/cancel.png"));
        cancelAllDownloads.setBorderPainted(false);
        cancelAllDownloads.setBackground(GUI.TOOLBAR_COLOR);
        cancelAllDownloads.setEnabled(false);
        cancelAllDownloads.setFocusable(false);
        cancelAllDownloads.setOpaque(true);
        cancelAllDownloads.addActionListener(new EventHandler());
        cancelAllDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/cancel.png"));
        cancelAllDownloads.addMouseListener(new mouseHandler());

        sortDownloads = new JButton("");
        sortDownloads.setName("sort");
        sortDownloads.setToolTipText("Sort downloads");
        sortDownloads.setIcon(new ImageIcon("src/icons/sort.png"));
        sortDownloads.setBorderPainted(false);
        sortDownloads.setBackground(GUI.TOOLBAR_COLOR);
        sortDownloads.setFocusable(false);
        sortDownloads.setOpaque(true);
        sortDownloads.addActionListener(new EventHandler());
        sortDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/sort.png"));
        sortDownloads.addMouseListener(new mouseHandler());


        removeAllDownloads = new JButton("");
        removeAllDownloads.setName("remove");
        removeAllDownloads.setToolTipText("Remove selected downloads");
        removeAllDownloads.setIcon(new ImageIcon("src/icons/remove.png"));
        removeAllDownloads.setBorderPainted(false);
        removeAllDownloads.setBackground(GUI.TOOLBAR_COLOR);
        removeAllDownloads.setEnabled(false);
        removeAllDownloads.setFocusable(false);
        removeAllDownloads.setOpaque(true);
        removeAllDownloads.addActionListener(new EventHandler());
        removeAllDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/remove.png"));
        removeAllDownloads.addMouseListener(new mouseHandler());

        settings = new JButton("");
        settings.setName("settings");
        settings.setToolTipText("Settings");
        settings.setIcon(new ImageIcon("src/icons/settings.png"));
        settings.setBorderPainted(false);
        settings.setBackground(new Color(208, 223, 248));
        settings.setFocusable(false);
        settings.setOpaque(true);
        settings.addActionListener(new EventHandler());
        settings.setPressedIcon(new ImageIcon("src/icons/pressed/settings.png"));
        settings.addMouseListener(new mouseHandler());

        search = new JTextField() {
            @Override
            public void setBorder(Border border) {
            }
        };
        search.setName("search text");
        search.setText("");
        search.setToolTipText("Search here");
        search.setBackground(GUI.TOOLBAR_COLOR);
        search.setHorizontalAlignment(JTextField.RIGHT);
        search.setSelectedTextColor(GUI.BACKGROUND_COLOR);
        search.setSelectionColor(GUI.LEFT_SIDE_BACK_COLOR_PRESSED);
        search.setOpaque(true);
        search.setFont(new Font("Arial", Font.PLAIN, 15));
        search.setForeground(GUI.LEFT_SIDE_BACK_COLOR);
        search.setFocusable(true);
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = search.getText();
                if (! text.equals("")) {
                    GUI.setList(DownloadManager.searchResults(text));
                }
                else {
                    GUI.setList(DownloadManager.getListByState());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = search.getText();
                if (! text.equals("")) {
                    GUI.setList(DownloadManager.searchResults(text));
                }
                else {
                    GUI.setList(DownloadManager.getListByState());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        search.setOpaque(true);

        searchButton = new JLabel("");
        searchButton.setName("search");
        searchButton.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        searchButton.setToolTipText("Search");
        searchButton.setIcon(new ImageIcon("src/icons/search.png"));
        searchButton.setBackground(new Color(208,223,248));
        searchButton.setFocusable(false);
        searchButton.setOpaque(true);
    }

    private class mouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!DownloadManager.getSettings().getLookAndFeel().equals("javax.swing.plaf.nimbus.NimbusLookAndFeel")) {
                JButton button = (JButton) e.getSource();
                button.setBackground(GUI.BACKGROUND_COLOR);
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (! DownloadManager.getSettings().getLookAndFeel().equals("javax.swing.plaf.nimbus.NimbusLookAndFeel")) {
                JButton button = (JButton) e.getSource();
                button.setBackground(GUI.TOOLBAR_COLOR);
            }

        }
    }

    private void initiateToolbar() {

        initiateButtons();


            toolBar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
            toolBar.setBackground(new Color(208,223,248));
            toolBar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (DownloadManager.getProccessing().getDownloadEntries().isSelectionEmpty()) {
                        GUI.getToolbar().setEnabledButtons(false);
                    } else {
                        GUI.getToolbar().setEnabledButtons(true);
                    }
                }
            });
            toolBar.setLayout(new BorderLayout());

            JPanel buttons = new JPanel(new GridLayout(1,7,0,0));
            buttons.setOpaque(true);
            buttons.setBackground(new Color(208,223,248));

            buttons.add(addNewDownload);
            buttons.add(pauseAllDownloads);
            buttons.add(resumeAllDownloads);
            buttons.add(cancelAllDownloads);
            buttons.add(sortDownloads);
            buttons.add(removeAllDownloads);
            buttons.add(settings);
            buttons.setBorder(BorderFactory.createEmptyBorder(1,1,1,10));

            toolBar.add(buttons, BorderLayout.WEST);

            JPanel searchPanel = new JPanel(new BorderLayout());
            JPanel searchButtonPanel = new JPanel(new BorderLayout());
            searchButtonPanel.setOpaque(true);
            searchButtonPanel.setBackground(new Color(208,223,248));
            searchButtonPanel.add(searchButton, BorderLayout.EAST);
            searchButtonPanel.add(search, BorderLayout.CENTER);

            searchPanel.setOpaque(true);
            searchPanel.setBackground(new Color(208,223,248));
            searchPanel.add(searchButtonPanel, BorderLayout.CENTER);
            //toolBar.add(search, BorderLayout.CENTER);
            //toolBar.add(searchButton, BorderLayout.EAST);
            toolBar.add(searchPanel, BorderLayout.CENTER);

//            toolBar.setOpaque(true);
//            toolBar.setBackground(new Color(208, 223, 248));
//            toolBar.setBorderPainted(false);
//            toolBar.add(new JSeparator(JSeparator.VERTICAL));
//
//            toolBar.setOpaque(true);
//            toolBar.add(addNewDownload);
//            toolBar.add(new JSeparator(JSeparator.VERTICAL));
//
//            toolBar.add(pauseAllDownloads);
//            toolBar.add(resumeAllDownloads);
//            toolBar.add(cancelAllDownloads);
//            toolBar.add(new JSeparator(JSeparator.VERTICAL));
//
//            toolBar.add(sortDownloads);
//            toolBar.add(new JSeparator(JSeparator.VERTICAL));
//
//            toolBar.add(removeAllDownloads);
//            toolBar.add(new JSeparator(JSeparator.VERTICAL));
//
//            toolBar.add(settings);
//            toolBar.add(new JSeparator(JSeparator.VERTICAL));
//
//            toolBar.add(search);
//            toolBar.add(searchButton);
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
