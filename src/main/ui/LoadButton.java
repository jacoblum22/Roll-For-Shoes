package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Takes inspiration from DrawingPlayer
public class LoadButton extends MenuButton {

    public LoadButton(RollForShoesGame rfs, JComponent parent) {
        super(rfs, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a load button
    @Override
    protected void createButton() {
        button = new JButton("Load Skills");
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new LoadButton.LoadButtonClickHandler());
    }

    private class LoadButtonClickHandler implements ActionListener {

        // EFFECTS: loads skills when button is pressed.
        @Override
        public void actionPerformed(ActionEvent e) {
            rollForShoesGame.loadSkills();
        }
    }
}
