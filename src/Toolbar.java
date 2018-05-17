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
    private JButton removeAllDownloads;
    private JButton sortDownloads;
    private JButton settings;



    public Toolbar(boolean isHorizontal) {
            initiateToolbar(isHorizontal);
    }

    private void initiateButtons(boolean isHorizontal){
        if (isHorizontal) {
            addNewDownload = new JButton("");
            addNewDownload.setName("add");
            addNewDownload.setToolTipText("Add a new download");
            addNewDownload.setIcon(new ImageIcon("src/icons/add.png"));
            addNewDownload.setBorderPainted(false);
            addNewDownload.setBackground(GUI.TOOLBAR_COLOR);
            addNewDownload.setFocusable(false);
            addNewDownload.setOpaque(true);
            addNewDownload.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUI.initAddDownloadFrame();
                    GUI.showAddDownloadFrame();
                }
            });
            addNewDownload.setPressedIcon(new ImageIcon("src/icons/pressed/add.png"));
            addNewDownload.addMouseListener(new mouseHandler());


            pauseAllDownloads = new JButton("");
            pauseAllDownloads.setName("pause");
            pauseAllDownloads.setToolTipText("Pause selected downloads");
            pauseAllDownloads.setIcon(new ImageIcon("src/icons/pause.png"));
            pauseAllDownloads.setBorderPainted(false);
            pauseAllDownloads.setBackground(GUI.TOOLBAR_COLOR);
            pauseAllDownloads.setFocusable(false);
            pauseAllDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/pause.png"));
            pauseAllDownloads.addMouseListener(new mouseHandler());

            resumeAllDownloads = new JButton("");
            resumeAllDownloads.setName("resume");
            resumeAllDownloads.setToolTipText("Resume selected downloads");
            resumeAllDownloads.setIcon(new ImageIcon("src/icons/play.png"));
            resumeAllDownloads.setBorderPainted(false);
            resumeAllDownloads.setBackground(GUI.TOOLBAR_COLOR);
            resumeAllDownloads.setFocusable(false);
            resumeAllDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/play.png"));
            resumeAllDownloads.addMouseListener(new mouseHandler());

            removeAllDownloads = new JButton("");
            removeAllDownloads.setName("remove");
            removeAllDownloads.setToolTipText("Remove selected downloads");
            removeAllDownloads.setIcon(new ImageIcon("src/icons/remove.png"));
            removeAllDownloads.setBorderPainted(false);
            removeAllDownloads.setBackground(GUI.TOOLBAR_COLOR);
            removeAllDownloads.setFocusable(false);
            removeAllDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/remove.png"));
            removeAllDownloads.addMouseListener(new mouseHandler());

            sortDownloads = new JButton("");
            sortDownloads.setName("sort");
            sortDownloads.setToolTipText("Sort downloads");
            sortDownloads.setIcon(new ImageIcon("src/icons/sort.png"));
            sortDownloads.setBorderPainted(false);
            sortDownloads.setBackground(GUI.TOOLBAR_COLOR);
            sortDownloads.setFocusable(false);
            sortDownloads.setPressedIcon(new ImageIcon("src/icons/pressed/sort.png"));
            sortDownloads.addMouseListener(new mouseHandler());

            settings = new JButton("");
            settings.setName("name");
            settings.setToolTipText("Settings");
            settings.setIcon(new ImageIcon("src/icons/settings.png"));
            settings.setBorderPainted(false);
            settings.setBackground(new Color(208, 223, 248));
            settings.setFocusable(false);
            settings.setPressedIcon(new ImageIcon("src/icons/pressed/settings.png"));
            settings.addMouseListener(new mouseHandler());
        }
        else {

        }
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
            JButton button = (JButton) e.getSource();
            button.setBackground(GUI.BACKGROUND_COLOR);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setBackground(GUI.TOOLBAR_COLOR);
        }
    }

    private void initiateToolbar(boolean isHorizontal){

        initiateButtons(isHorizontal);

        if (isHorizontal){
            toolBar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
            toolBar.setOpaque(true);
            toolBar.setBackground(new Color(208, 223, 248));
            toolBar.setBorderPainted(false);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.setOpaque(true);
            toolBar.add(addNewDownload);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.add(pauseAllDownloads);
            toolBar.add(resumeAllDownloads);
            toolBar.add(removeAllDownloads);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.add(sortDownloads);
            toolBar.add(new JSeparator(JSeparator.VERTICAL));

            toolBar.add(settings);
        }
        else {

        }

    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
