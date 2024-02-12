package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

//Represents a menu button that executes a specific method when pressed. Takes inspiration from Tool class
//in DrawingPlayer
public abstract class MenuButton {
    protected RollForShoesGame rollForShoesGame;
    protected JButton button;

    public MenuButton(RollForShoesGame rfs, JComponent parent) {
        createButton();
        addToParent(parent);
        this.rollForShoesGame = rfs;
        addListener();
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton();

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }
}
