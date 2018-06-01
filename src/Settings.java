import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Settings implements Serializable{

    private transient JFrame settingsFrame;
    private String lookAndFeel;
    private String saveToPath;
    private Boolean synchronicDownloadsLimited;
    private Integer maximumSynchronicDownloads;
    private String[] lAndFs;
    private String[] lAndFNames;
    private ArrayList<String> filteredSites;

    public Settings() {
        lAndFs = new String[] {UIManager.getSystemLookAndFeelClassName(), "javax.swing.plaf.nimbus.NimbusLookAndFeel", "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"};
        lAndFNames = new String[] {"System default", "Nimbus", "Windows classic"};
        maximumSynchronicDownloads = 10;
        synchronicDownloadsLimited = false;
        lookAndFeel = lAndFs[0];
        saveToPath = System.getProperty("user.home") + "\\Desktop";
        filteredSites = new ArrayList<String>();
    }

    public JFrame makeSettingsFrame() {
        settingsFrame = new JFrame("Settings");
        settingsFrame.setIconImage(new ImageIcon("src/icons/settings.png").getImage());
        settingsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //settingsFrame.setResizable(false);
        settingsFrame.setSize(800,650);
        settingsFrame.setLocationRelativeTo(GUI.getFrame());
        settingsFrame.add(makeMainPanel());
        return settingsFrame;
    }

    private JScrollPane makeMainPanel() {
        final int[] tempLimit = {maximumSynchronicDownloads};
        final String[] tempPath = {saveToPath};
        final String[] tempLAndF = {lookAndFeel};
        final boolean[] tempIsLimited = {synchronicDownloadsLimited};

        Border border = BorderFactory.createEmptyBorder(5,5,5,5);
        Border border2 = BorderFactory.createEmptyBorder(20,5,5,5);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(border2);
        main.setOpaque(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBorder(border2);

        JPanel limitPanel = new JPanel(new BorderLayout());
        limitPanel.setBorder(BorderFactory.createTitledBorder("Downloads' limit"));
        limitPanel.setOpaque(true);

        JCheckBox isLimited = new JCheckBox("Limit maximum number of synchronic downloads");
        isLimited.setSelected(isSynchronicDownloadsLimited());
        isLimited.setBorder(border);

        limitPanel.add(isLimited, BorderLayout.NORTH);

        JPanel maximumDownloadsPanel = new JPanel(new BorderLayout());
        maximumDownloadsPanel.setOpaque(true);
        panel.setBorder(border);


        JLabel maximum = new JLabel("Maximum number of synchronic downloads:");
        maximum.setBorder(border);

        SpinnerNumberModel model;
        int current = DownloadManager.INF;
        int min = 1;
        int max = DownloadManager.INF;
        int step = 1;
        model = new SpinnerNumberModel(current, min, max, step);
        JSpinner spinner = new JSpinner(model);
        spinner.setBorder(border);
        spinner.setValue(maximumSynchronicDownloads);
        spinner.setOpaque(true);
        spinner.setEnabled(synchronicDownloadsLimited);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tempLimit[0] = (int) spinner.getValue();
            }
        });

        isLimited.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempIsLimited[0] = isLimited.isSelected();
                spinner.setEnabled(isLimited.isSelected());
                //System.out.println(isLimited.isSelected());
            }
        });

        maximumDownloadsPanel.add(maximum, BorderLayout.WEST);
        maximumDownloadsPanel.add(spinner, BorderLayout.CENTER);

        limitPanel.add(maximumDownloadsPanel, BorderLayout.WEST);

        JPanel saveToPanel = new JPanel(new BorderLayout());
        saveToPanel.setBorder(BorderFactory.createTitledBorder("Save to:"));
        saveToPanel.setOpaque(true);

        JFileChooser chooser = new JFileChooser(saveToPath){
            @Override
            public void approveSelection() {
                if (getSelectedFile().isFile()) {
                }
                else {
                    String backSlash = "\\";
                    if (getCurrentDirectory().getPath().lastIndexOf("\\") == getCurrentDirectory().getPath().length()-1){
                        backSlash = "";
                    }
                    tempPath[0] = getCurrentDirectory().getPath() + backSlash + getSelectedFile().getName();
                    //System.out.println(saveToPath);
                    setCurrentDirectory(new File(tempPath[0]));
                }
            }

            @Override
            public void cancelSelection() {
            }
        };

        chooser.setOpaque(true);
        chooser.setApproveButtonText("Select");
        chooser.setControlButtonsAreShown(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setBorder(border);

        saveToPanel.add(chooser, BorderLayout.NORTH);

        JPanel lookAndFeelPanel = new JPanel(new BorderLayout());
        lookAndFeelPanel.setBorder(BorderFactory.createTitledBorder("Look & Feel"));
        lookAndFeelPanel.setOpaque(true);

        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.setBorder(border);
        comboPanel.setOpaque(true);

        JLabel label = new JLabel("Look and feel");

        JComboBox lf = new JComboBox(lAndFNames);
        lf.setPrototypeDisplayValue("..............................");
        comboPanel.add(lf, BorderLayout.WEST);
        int index = 0;
        for (int i = 0; i < lAndFs.length; i++){
            if (lookAndFeel == lAndFs[i]) {
                index = i;
            }
        }
        lf.setSelectedIndex(index);

        lf.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                tempLAndF[0] = lAndFs[lf.getSelectedIndex()];
            }
        });

        lookAndFeelPanel.add(label, BorderLayout.WEST);
        lookAndFeelPanel.add(comboPanel, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtered Sites"));
        filterPanel.setOpaque(true);

        JPanel filter = new JPanel(new BorderLayout());
        filter.setBorder(border);
        filter.setOpaque(true);

        JTextArea sites = new JTextArea();
        sites.setRows(5);
        sites.setEditable(true);
        for (String string: filteredSites) {
            sites.append(string + "\n");
        }
        sites.setToolTipText("Enter each site in a seperate line");

        filter.add(sites, BorderLayout.CENTER);

        filterPanel.add(filter, BorderLayout.CENTER);

        JButton ok = new JButton("OK");
        ok.setOpaque(true);

        JButton apply = new JButton("Apply");
        apply.setOpaque(true);

        JButton cancel = new JButton("Cancel");
        cancel.setOpaque(true);

        JPanel cancelButton = new JPanel(new BorderLayout());
        cancelButton.add(cancel, BorderLayout.EAST);

        JPanel cancelAndApply = new JPanel(new BorderLayout());
        //cancelAndApply.setBorder(border);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setBorder(border);

        cancelAndApply.add(apply, BorderLayout.EAST);
        cancelAndApply.add(cancelButton, BorderLayout.CENTER);

        buttonsPanel.add(ok, BorderLayout.EAST);
        buttonsPanel.add(cancelAndApply, BorderLayout.CENTER);


        JPanel settingsPanel = new JPanel(new BorderLayout());


        panel.add(limitPanel, BorderLayout.NORTH);
        panel.add(saveToPanel, BorderLayout.CENTER);
        //main.add(panel, BorderLayout.NORTH);
        settingsPanel.add(panel, BorderLayout.NORTH);
        settingsPanel.add(lookAndFeelPanel, BorderLayout.CENTER);


        JPanel sets = new JPanel(new BorderLayout());
        sets.setOpaque(true);
        sets.setBorder(border);
        sets.add(settingsPanel, BorderLayout.NORTH);
        sets.add(filterPanel, BorderLayout.CENTER);

        main.add(sets, BorderLayout.NORTH);
        main.add(buttonsPanel, BorderLayout.SOUTH);

        JScrollPane pane = new JScrollPane(main);
        //main.add(pane, BorderLayout.CENTER);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.dispatchEvent(new WindowEvent(GUI.getFrame(), WindowEvent.WINDOW_CLOSING));
            }
        });

        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(chooser.getSelectedFile().getPath());
                //System.out.println(tempPath[0]);
                if (chooser.getSelectedFile() != null){
                    saveToPath = chooser.getSelectedFile().getPath();
                }
                synchronicDownloadsLimited = tempIsLimited[0];
                maximumSynchronicDownloads = tempLimit[0];
                if (DownloadManager.getInProgressDownloads() > maximumSynchronicDownloads) {
                    JOptionPane.showMessageDialog(settingsFrame, "Some downloads will automatically get paused to match new limit", "Warning", 2);
                    DownloadManager.pauseSomeDownloads(DownloadManager.getInProgressDownloads() - maximumSynchronicDownloads);
                }
                if (lookAndFeel != tempLAndF[0]) {
                    lookAndFeel = tempLAndF[0];
                    DownloadManager.setLookAndFeel(lookAndFeel);
                    SwingUtilities.updateComponentTreeUI(GUI.getFrame());
                    SwingUtilities.updateComponentTreeUI(settingsFrame);
                    //settingsFrame.dispatchEvent(new WindowEvent(GUI.getFrame(), WindowEvent.WINDOW_CLOSING));
                }

                filteredSites.clear();
                for (String line : sites.getText().split("\\n")) filteredSites.add(line);

            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(chooser.getSelectedFile().getPath());
                //System.out.println(tempPath[0]);
                if (chooser.getSelectedFile() != null){
                    saveToPath = chooser.getSelectedFile().getPath();
                }
                synchronicDownloadsLimited = tempIsLimited[0];
                maximumSynchronicDownloads = tempLimit[0];
                if (DownloadManager.getInProgressDownloads() > maximumSynchronicDownloads) {
                    JOptionPane.showMessageDialog(settingsFrame, "Some downloads will automatically get paused to match new limit", "Warning", 2);
                    DownloadManager.pauseSomeDownloads(DownloadManager.getInProgressDownloads() - maximumSynchronicDownloads);
                }
                settingsFrame.dispatchEvent(new WindowEvent(GUI.getFrame(), WindowEvent.WINDOW_CLOSING));
                if (lookAndFeel != tempLAndF[0]) {
                    lookAndFeel = tempLAndF[0];
                    DownloadManager.setLookAndFeel(lookAndFeel);
                    SwingUtilities.updateComponentTreeUI(GUI.getFrame());

                }
                filteredSites.clear();
                for (String line : sites.getText().split("\\n")) filteredSites.add(line);
            }
        });


        return pane;
    }

    public String getSaveToPath() {
        return saveToPath;
    }

    public int getMaximumSynchronicDownloads() {
        return maximumSynchronicDownloads;
    }

    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public boolean isSynchronicDownloadsLimited() {
        return synchronicDownloadsLimited;
    }


    @Override
    public String toString() {
        return synchronicDownloadsLimited + ", " + maximumSynchronicDownloads + ", " + saveToPath + ", " + lookAndFeel;
    }
}
