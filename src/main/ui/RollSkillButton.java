package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RollSkillButton extends MenuButton {

    public RollSkillButton(RollForShoesGame rfs, JComponent parent) {
        super(rfs, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a instruction button
    @Override
    protected void createButton() {
        button = new JButton("Roll Skill Tree");
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new RollSkillButton.RollSkillButtonClickHandler());
    }

    private class RollSkillButtonClickHandler implements ActionListener {

        // EFFECTS: displays instructions on GUI when button is pressed.
        @Override
        public void actionPerformed(ActionEvent e) {
            rollForShoesGame.graphicPickSkillToRoll();
        }
    }
}
