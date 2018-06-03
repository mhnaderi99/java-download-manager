import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class renders cell of downloadslist
 */
public class DownloadEntryRenderer implements ListCellRenderer<DownloadEntry>, Serializable {

    /**
     * mode of list
     */
    private DownloadsList.state mode;

    /**
     * Constructor for the class
     * @param mode
     */
    public DownloadEntryRenderer(DownloadsList.state mode) {
        this.mode = mode;
    }

    /**
     * Override of getListCellRendererComponent method
     * @param list
     * @param value
     * @param index
     * @param isSelected
     * @param cellHasFocus
     * @return component
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends DownloadEntry> list, DownloadEntry value, int index, boolean isSelected, boolean cellHasFocus) {

        JPanel panel = null;

        panel = value.getPanel(mode);
        panel.setOpaque(true);
        if (isSelected) {
            colorWholeContainer(panel, GUI.LEFT_SIDE_BACK_COLOR, GUI.TOOLBAR_COLOR);
        }


        return panel;
    }

    /**
     * This method gets a container and a color and paints all the components of the container
     * @param container
     * @param backColor
     * @param foreColor
     */
    public static void colorWholeContainer(Container container, Color backColor, Color foreColor) {
        container.setBackground(backColor);
        container.setForeground(foreColor);

        ArrayList<Component> components = GUI.getAllComponents(container);

        for (Component component : components) {
            component.setBackground(backColor);
            component.setForeground(foreColor);
            if (component instanceof JProgressBar) {
                JProgressBar bar = (JProgressBar) component;
                bar.setUI(new BasicProgressBarUI() {
                    protected Color getSelectionBackground() {
                        return foreColor;
                    }

                    protected Color getSelectionForeground() {
                        return backColor;
                    }
                });
            }
        }
    }
}
