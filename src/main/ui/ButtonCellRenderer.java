package ui;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class ButtonCellRenderer extends DefaultTreeCellRenderer {

    // EFFECTS: Styles the buttons
    @Override
    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {

        JButton button = new JButton(value.toString());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setFont(new Font("High Tower Text", Font.PLAIN, 18));

        return button;
    }
}
