package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButton extends MenuButton {

    public SaveButton(RollForShoesGame rfs, JComponent parent) {
        super(rfs, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a save button
    @Override
    protected void createButton() {
        button = new JButton("Save Skills");
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new SaveButton.SaveButtonClickHandler());
    }

    private class SaveButtonClickHandler implements ActionListener {

        // EFFECTS: saves skills when button is pressed.
        @Override
        public void actionPerformed(ActionEvent e) {
            rollForShoesGame.saveSkills();
        }
    }
}
