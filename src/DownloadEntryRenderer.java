import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.util.ArrayList;

public class DownloadEntryRenderer implements ListCellRenderer<DownloadEntry> {

    private boolean isCompleted;

    public DownloadEntryRenderer(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends DownloadEntry> list, DownloadEntry value, int index, boolean isSelected, boolean cellHasFocus) {

        JPanel panel = value.getPanel();
        panel.setOpaque(true);
        if (isSelected) {


            panel.setBackground(GUI.LEFT_SIDE_BACK_COLOR);
            panel.setForeground(list.getSelectionForeground());

            ArrayList<Component> components = GUI.getAllComponents(panel);

            for (Component component: components) {
                component.setBackground(GUI.LEFT_SIDE_BACK_COLOR);
                component.setForeground(GUI.TOOLBAR_COLOR);
                if (component instanceof JProgressBar) {
                    JProgressBar bar = (JProgressBar) component;
                    bar.setUI(new BasicProgressBarUI() {
                        protected Color getSelectionBackground() { return GUI.TOOLBAR_COLOR; }
                        protected Color getSelectionForeground() { return GUI.LEFT_SIDE_BACK_COLOR; }
                    });
                }
            }



        } else {
            panel.setBackground(list.getBackground());
            panel.setForeground(list.getForeground());
        }

        return panel;
    }
}
