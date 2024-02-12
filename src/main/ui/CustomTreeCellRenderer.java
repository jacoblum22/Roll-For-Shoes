package ui;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

    // EFFECTS: Styles the tree
    @Override
    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {

        JLabel label = (JLabel) super.getTreeCellRendererComponent(
                tree, value, selected, expanded, leaf, row, hasFocus);

        // Set the desired font size
        label.setFont(new Font("High Tower Text", Font.PLAIN, 18));

        label.setIcon(null);

        return label;
    }
}
