package ui;

import model.Event;
import model.EventLog;
import model.Skill;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//NOTE: this class's design is inspired by the design found in the TellerApp class
// Runs the UI of the Roll For Shoes game
public class RollForShoesGame extends JFrame {
    private static final String JSON_STORE = "./data/skillFile.json";
    private Skill doAnything;
    private Scanner input;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final JPanel menuPanel;
    private final JPanel centerPanel;

    // EFFECTS: runs the roll for shoes application
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public RollForShoesGame() throws FileNotFoundException {
        setTitle("Roll For Shoes GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLayout(new BorderLayout());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.menuPanel = new JPanel();
        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new BorderLayout());
        InstructionButton instructionButton = new InstructionButton(this, menuPanel);
        RollSkillButton rollSkillButton = new RollSkillButton(this, menuPanel);
        ViewTreeButton skillTreeButton = new ViewTreeButton(this, menuPanel);
        SaveButton saveButton = new SaveButton(this, menuPanel);
        LoadButton loadButton = new LoadButton(this, menuPanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("\n");
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runRollForShoes() {
        boolean keepGoing = true;
        String command;

        init();
        add(centerPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.SOUTH);
        setVisible(true);

        displayIntroduction();

        graphicDisplayIntroduction();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nI hope you had fun!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the Do Anything skill.
    private void init() {
        doAnything = new Skill("Do Anything", 1);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays the introduction to the game as well as the rules to the user on the console.
    private void displayIntroduction() {
        System.out.println("\nWelcome to Roll For Shoes! Roll For Shoes is a very simple game designed to be "
                + "played with you and your friends. To get started, pick who is going to be \nthe Narrator. "
                + "There is one narrator, and everyone else is a player. The narrator will describe the story, "
                + "and from there the rules are very simple:");
        System.out.println("\t1) Say what you do and roll a number of 6-sided dice, determined by the level of "
                + "relevant skill you have.");
        System.out.println("\t2) If the sum of your roll is higher than the difficulty set by the Narrator, "
                + "the thing you wanted to happen, happens.");
        System.out.println("\t3) At the beginning of the game, you have only one skill: Do Anything 1.");
        System.out.println("\t4) If you roll all sixes, you get a new skill specific to the action, with one more "
                + "die than the one you used.");
        System.out.println("\t5) For every roll you fail, you get 1 XP.");
        System.out.println("\t6) XP can be used to change a die into a 6 for advancement purposes only. This "
                + "means that you can't turn a failure into a\n\t   success with XP, but you can get a new skill.");
    }

    //EFFECTS: displays the introduction to the game as well as the rules on the GUI.
    protected void graphicDisplayIntroduction() {
        JLabel instructions = graphicText("<html><div style='font-size: 18px; margin: 100px;'>Welcome to Roll For "
                + "Shoes! Roll For Shoes is a very simple "
                + "game designed to be played with you and your friends. To get started, pick who is going to be the "
                + "Narrator. There is one narrator, and everyone else is a player. The narrator will describe the "
                + "story, and from there the rules are very simple:"
                + "<ol>"
                + "<li>Say what you do and roll a number of 6-sided dice, determined by the level of relevant skill "
                + "you have.</li>"
                + "<li>If the sum of your roll is higher than the difficulty set by the Narrator, the thing you wanted"
                + " to happen, happens.</li>"
                + "<li>At the beginning of the game, you have only one skill: Do Anything 1.</li>"
                + "<li>If you roll all sixes, you get a new skill specific to the action, with one more die than the "
                + "one you used.</li>"
                + "<li>For every roll you fail, you get 1 XP.</li>"
                + "<li>XP can be used to change a die into a 6 for advancement purposes only. This means that you "
                + "can't turn a failure into a success with XP, but you can get a new skill.</li>"
                + "</ol></div></html>");
        setCenter(instructions);
    }

    // EFFECTS: displays the menu to the user
    public void displayMenu() {
        printLineBreak();
        System.out.println("\nWhat would you like to do? (Remember, at the beginning of the game, you "
                + "can only roll for Do Anything.");
        System.out.println("\ti -> I'd like to see the instructions again");
        System.out.println("\tr -> I'd like to roll for a skill");
        System.out.println("\tt -> I'd like to view my entire skill tree");
        System.out.println("\ts -> I'd like to save my current skills to file");
        System.out.println("\tl -> I'd like to load this game with the skills on file");
        System.out.println("\tq -> Quit");
        System.out.print("Input: ");
    }

    // EFFECTS: takes in user input and runs the correct section based on that input
    private void processCommand(String command) {
        printLineBreak();
        switch (command) {
            case "i":
                displayIntroduction();
                break;
            case "r":
                rollSkill();
                break;
            case "t":
                System.out.println("\nYour current XP: " + Skill.getXP());
                System.out.println("\nYour current skill tree:");
                System.out.println(viewSkillTree(doAnything, 0));
                break;
            case "s":
                saveSkills();
                break;
            case "l":
                loadSkills();
                break;
            default:
                System.out.println("I didn't quite catch that...please type one of the valid letters");
                break;
        }
    }

    // EFFECTS: Allows the user to roll for a specific skill that they have unlocked on the console
    private void rollSkill() {
        ArrayList<Integer> rollList;
        System.out.println("\nGreat! What skill do you want to roll for?");
        String skillName = stringLineInput();

        Skill skillToRoll = doAnything.checkSkillExistenceFromSkill(skillName);

        if (skillToRoll != null) {
            rollList = skillToRoll.roll();
        } else {
            System.out.println("I don't seem to recognize that name. Either you mistyped something, or "
                    + "something in this program has gone very very wrong!");
            return;
        }

        int skillDice = skillToRoll.getDice();

        System.out.print("Nice! You're rolling " + skillToRoll.getName() + ". ");
        checkDC(skillDice, rollList);

        int xp = Skill.getXP();
        int xpPrice = skillDice - getSixes(rollList);

        handleNewSkills(skillToRoll, xp, xpPrice);
    }

    // EFFECTS: Picks the skill to roll on the GUI
    protected void graphicPickSkillToRoll() {
        DefaultMutableTreeNode treeRoot = convertToDefaultMutableTreeNode(doAnything);
        JTree tree = new JTree(treeRoot);
        tree.setCellRenderer(new ButtonCellRenderer());
        tree.addMouseListener(new ButtonMouseListener(tree, this));
        JScrollPane scrollPane = new JScrollPane(tree);
        setCenter(scrollPane);
    }

    // EFFECTS: allows the user to roll for a specific skill that they have unlocked on the GUI.
    protected void graphicRollSkill(String input) {
        ArrayList<Integer> rollList;

        Skill skillToRoll = doAnything.checkSkillExistenceFromSkill(input);

        if (skillToRoll != null) {
            rollList = skillToRoll.roll();
        } else {
            return;
        }

        int skillDice = skillToRoll.getDice();

        setCenter(graphicText("Nice! You're rolling " + skillToRoll.getName() + ". "));

        graphicCheckDC(skillDice, rollList);

        int xp = Skill.getXP();
        int xpPrice = skillDice - getSixes(rollList);

        graphicHandleNewSkills(skillToRoll, xp, xpPrice);
    }

    // EFFECTS: Allows the user to input a string
    private String stringLineInput() {
        input.nextLine();
        return input.nextLine();
    }

    // EFFECTS: Prints rolls and how they compared to the given difficulty on the console
    private void checkDC(int dice, ArrayList<Integer> rolls) {
        System.out.println("You rolled " + dice + "d6."
                + "\n\nWhat was the difficulty that you had to beat?");
        int dc = input.nextInt();
        System.out.println("You rolled: " + rolls + ", which adds up to "
                + Skill.getRollSum(rolls) + ".");
        if (Skill.checkPassesDC(rolls, dc)) {
            System.out.println("\nYou beat the opposing roll! Nice roll!");
        } else {
            System.out.println("You just missed the opposing roll. Nice try, here's 1 XP!");
            Skill.modifyXP(1);
        }
    }

    // EFFECTS: Prints rolls and how they compared to the given difficulty on the GUI
    private void graphicCheckDC(int dice, ArrayList<Integer> rolls) {
        int dc = -1;

        while (dc <= 0) {
            try {
                dc = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "You rolled " + dice + "d6."
                        + "\n\nWhat was the difficulty that you had to beat?"));
            } catch (NumberFormatException e) {
                dc = -1;
            }
        }

        if (Skill.checkPassesDC(rolls, dc)) {
            setCenter(graphicText("You rolled: " + rolls + ", which adds up to "
                    + Skill.getRollSum(rolls) + "."
                    + " You beat the opposing roll! Nice roll!"));
        } else {
            setCenter(graphicText("You rolled: " + rolls + ", which adds up to "
                    + Skill.getRollSum(rolls) + "."
                    + "You just missed the opposing roll. Nice try, here's 1 XP!"));
            Skill.modifyXP(1);
        }
    }

    // MODIFIES: numSixes
    // EFFECT: makes the provided integer = the number of sixes rolled in the provided set of rolls
    private int getSixes(ArrayList<Integer> rolls) {
        int numSixes = 0;
        for (int i : rolls) {
            if (i == 6) {
                numSixes++;
            }
        }
        return numSixes;
    }

    // MODIFIES: skill
    // EFFECT: Handles whether the user automatically gets a new skill, or whether they can or can't buy one.
    private void handleNewSkills(Skill skill, int xp, int xpPrice) {
        if (xpPrice == 0) {
            allSixesNewSkill(skill);
        } else if (xpPrice <= xp) {
            buyNewSkill(skill, xp, xpPrice);
        } else {
            System.out.println("Unfortunately, you don't have enough XP to buy a new skill.");
        }
    }

    // MODIFIES: skill
    // EFFECT: Handles whether the user automatically gets a new skill, or whether they can or can't buy one on the GUI
    private void graphicHandleNewSkills(Skill skill, int xp, int xpPrice) {
        if (xpPrice == 0) {
            graphicAllSixesNewSkill(skill);
        } else if (xpPrice <= xp) {
            graphicBuyNewSkill(skill, xp, xpPrice);
        } else {
            centerPanel.add(graphicText("Unfortunately, you don't have enough XP to buy a new skill."),
                    BorderLayout.SOUTH);
            setVisible(true);
        }
    }

    // MODIFIES: skill
    // EFFECTS: Allows the user to make a new skill when they roll all sixes.
    private void allSixesNewSkill(Skill skill) {
        System.out.println("You rolled all sixes! That means you get to create a new skill specific to "
                + "what you just did! What is this new skill called?");
        createNewSkill(skill);
    }

    // MODIFIES: skill
    // EFFECTS: Allows the user to make a new skill when they roll all sixes on the GUI
    private void graphicAllSixesNewSkill(Skill skill) {
        setCenter(graphicText("You rolled all sixes! That means you get to create a new skill specific to "
                + "what you just did!"));
        graphicCreateNewSkill(skill);
    }

    // MODIFIES: skill
    // EFFECTS: Allows the user to buy a new skill with XP.
    private void buyNewSkill(Skill skill, int xp, int xpPrice) {
        System.out.println("You have enough XP to buy a new skill! You have " + xp
                + " XP, and it will cost you " + xpPrice + " to buy a new skill. Would you like "
                + " to buy a new skill? (Type \"yes\" or \"no\")");
        String buySkillWithXP = "";
        while (!buySkillWithXP.equals("yes") && !buySkillWithXP.equals("no")) {
            buySkillWithXP = input.next().toLowerCase();
            if (buySkillWithXP.equals("yes")) {
                System.out.println("Amazing! What is this new skill called?");
                createNewSkill(skill);
                Skill.modifyXP(-xpPrice);
            } else if (buySkillWithXP.equals("no")) {
                System.out.println("Saving up? No problem! You'll be able to spend your XP on other "
                        + "skills later.");
            } else {
                System.out.println("Please type either \"yes\" or \"no\".");
            }
        }
    }

    // MODIFIES: skill
    // EFFECTS: Allows the user to buy a new skill with XP on the GUI
    private void graphicBuyNewSkill(Skill skill, int xp, int xpPrice) {
        String buySkillWithXP = "";
        while (!buySkillWithXP.equals("yes") && !buySkillWithXP.equals("no")) {
            buySkillWithXP = JOptionPane.showInputDialog(null,
                    "You have enough XP to buy a new skill! You have " + xp
                    + " XP, and it will cost you " + xpPrice + " to buy a new skill. Would you like "
                    + " to buy a new skill? (Type \"yes\" or \"no\")").toLowerCase();
            if (buySkillWithXP.equals("yes")) {
                graphicCreateNewSkill(skill);
                Skill.modifyXP(-xpPrice);
            } else if (buySkillWithXP.equals("no")) {
                setCenter(graphicText("Saving up? No problem! You'll be able to spend your XP on other "
                        + "skills later."));
            } else {
                setCenter(graphicText("Please type either \"yes\" or \"no\"."));
            }
        }
    }

    // MODIFIES: skill
    // EFFECT: creates a new sub-skill of the given skill
    private void createNewSkill(Skill skill) {
        String newSkillName = stringLineInput();
        while (doAnything.checkSkillExistenceFromSkill(newSkillName) != null) {
            System.out.println("Please pick a new skill name, not one you already have.");
            newSkillName = stringLineInput();
        }
        System.out.println("Awesome! Your new skill, " + newSkillName + ", is a sub-skill of "
                + skill.getName() + ". Feel free to check it out in your skill tree by typing "
                + "\"t\" in the main menu!");
        skill.addSubSkill(new Skill(newSkillName, (skill.getDice() + 1)));
    }

    private void graphicCreateNewSkill(Skill skill) {
        String newSkillName = JOptionPane.showInputDialog(null,
                "What is this new skill called?");
        while (doAnything.checkSkillExistenceFromSkill(newSkillName) != null) {
            newSkillName = JOptionPane.showInputDialog(null,
                    "Please pick a new skill name, not one you already have.");
        }
        setCenter(graphicText("Awesome! Your new skill, " + newSkillName + ", is a sub-skill of "
                + skill.getName() + ". Feel free to check it out in your skill tree by pressing the"
                + " View Skill Tree Button!"));
        skill.addSubSkill(new Skill(newSkillName, (skill.getDice() + 1)));
    }

    // EFFECTS: displays the skill tree
    private String viewSkillTree(Skill skill, int tabs) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tabs; i++) {
            if (tabs - i == 1 && tabs > 1) {
                result.append("|");
            }
            result.append("\t");
        }
        if (tabs > 0) {
            result.append("-> ");
        }
        result.append(skill.getName()).append("\n");
        for (Skill s : skill.getSubSkillTree().getSkillList()) {
            result.append(viewSkillTree(s, tabs + 1));
        }
        return result.toString();
    }

    // EFFECTS: displays the skill tree on the GUI
    protected void graphicViewSkillTree() {
        DefaultMutableTreeNode treeRoot = convertToDefaultMutableTreeNode(doAnything);
        CustomTreeCellRenderer renderer = new CustomTreeCellRenderer();
        JTree tree = new JTree(treeRoot);
        tree.setCellRenderer(renderer);
        tree.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JScrollPane scrollPane = new JScrollPane(tree);
        setCenter(scrollPane);
        centerPanel.add(new JLabel("Your current XP: " + Skill.getXP()), BorderLayout.NORTH);
        setVisible(true);
    }

    // EFFECTS: Prints a line of underscores
    private void printLineBreak() {
        System.out.print("\n");
        for (int i = 0; i < 150; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
    }

    // EFFECTS: saves skills to file
    protected void saveSkills() {
        try {
            jsonWriter.open();
            jsonWriter.write(doAnything);
            jsonWriter.close();
            System.out.println("Saved skills to " + JSON_STORE);
            setCenter(graphicText("Saved skills to " + JSON_STORE));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            setCenter(graphicText("Unable to write to file: " + JSON_STORE));
        }
    }

    // MODIFIES: this
    // EFFECTS: loads skills from file
    protected void loadSkills() {
        try {
            doAnything = jsonReader.read();
            System.out.println("Loaded skills from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Convert the custom hierarchy to DefaultMutableTreeNode
    private static DefaultMutableTreeNode convertToDefaultMutableTreeNode(Skill skill) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(skill.getName());
        for (Skill s : skill.getSubSkillTree().getSkillList()) {
            treeNode.add(convertToDefaultMutableTreeNode(s));
        }
        return treeNode;
    }

    // MODIFIES: this
    // EFFECTS: puts container into the center panel
    private void setCenter(Container container) {
        centerPanel.removeAll();
        centerPanel.add(container, BorderLayout.CENTER);
        setVisible(true);
    }

    // EFFECTS: Creates a String that can be displayed
    private JLabel graphicText(String message) {
        JLabel label = new JLabel("<html><div style='margin: 100px;'>"
                + message
                + "</div></html>");
        label.setFont(new Font("High Tower Text", Font.PLAIN, 18));
        return label;
    }
}
