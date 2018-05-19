import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
            if (! Settings.getLookAndFeel().equals("javax.swing.plaf.nimbus.NimbusLookAndFeel")) {
                JButton button = (JButton) e.getSource();
                button.setBackground(GUI.BACKGROUND_COLOR);
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (! Settings.getLookAndFeel().equals("javax.swing.plaf.nimbus.NimbusLookAndFeel")) {
                JButton button = (JButton) e.getSource();
                button.setBackground(GUI.TOOLBAR_COLOR);
            }

        }
    }

    private void initiateToolbar() {

        initiateButtons();


            toolBar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
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
            toolBar.setOpaque(true);
            toolBar.setBackground(new Color(208, 223, 248));
            toolBar.setBorderPainted(false);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.setOpaque(true);
            toolBar.add(addNewDownload);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.add(pauseAllDownloads);
            toolBar.add(resumeAllDownloads);
            toolBar.add(cancelAllDownloads);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.add(sortDownloads);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.add(removeAllDownloads);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.add(settings);
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
