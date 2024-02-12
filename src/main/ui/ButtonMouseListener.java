package ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMouseListener extends MouseAdapter {
    private final JTree tree;
    protected RollForShoesGame rollForShoesGame;

    ButtonMouseListener(JTree tree, RollForShoesGame rfs) {
        this.tree = tree;
        this.rollForShoesGame = rfs;
    }

    // EFFECTS: checks if a node on the tree was clicked
    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selectedPath = tree.getPathForLocation(e.getX(), e.getY());

        if (selectedRow != -1) {
            Object selectedNode = selectedPath.getLastPathComponent();
            if (selectedNode instanceof DefaultMutableTreeNode) {
                rollForShoesGame.graphicRollSkill(selectedNode.toString());
            }
        }
    }
}
