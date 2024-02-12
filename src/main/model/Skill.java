package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Random;

// represents a specific skill having a name, a number of dice, a list of sub skills, and the result
// of the most recent roll
// NOTE: some of this code is based on the sample code written for the JsonSerializationDemo
public class Skill implements Writable {
    private static final int MINIMUM_DICE_VALUE = 1;
    private static final int MAXIMUM_DICE_VALUE = 6;
    private static int xp = 0;
    private final String name;
    private final int dice;
    private final SkillTree subSkillTree;

    // REQUIRES: name has nonzero length, dice > 0
    // EFFECTS: creates a new skill with the given name and number of dice
    //          associated with it and an empty list of sub skills.
    public Skill(String name, int dice) {
        this.name = name;
        this.dice = dice;
        this.subSkillTree = new SkillTree();
        EventLog.getInstance().logEvent(new Event("New Skill, \"" + name + "\", created"));
    }

    // MODIFIES: this
    // EFFECTS: rolls the number of dice associated with the given skill and returns the list
    // of those rolls.
    public ArrayList<Integer> roll() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < this.dice; i++) {
            Random random = new Random();
            result.add(random.nextInt(MAXIMUM_DICE_VALUE
                    - MINIMUM_DICE_VALUE + 1) + MINIMUM_DICE_VALUE);
        }
        EventLog.getInstance().logEvent(new Event(this.dice + " dice rolled for "
                + this.name));
        return result;
    }

    // REQUIRES: a nonempty list for roll
    // EFFECTS: returns the sum of the values in roll
    public static int getRollSum(ArrayList<Integer> roll) {
        int result = 0;
        for (int i : roll) {
            result += i;
        }
        return result;
    }

    // EFFECTS: returns true if the roll passes the provided DC
    public static boolean checkPassesDC(ArrayList<Integer> roll, int dc) {
        return getRollSum(roll) >= dc;
    }

    // REQUIRES: subSkill is a skill with a unique name
    // MODIFIES: this
    // EFFECTS: adds subSkill to the list of sub-skills if it isn't already there
    public void addSubSkill(Skill subSkill) {
        for (Skill s : this.subSkillTree.getSkillList()) {
            if (s.equals(subSkill)) {
                return;
            }
        }
        this.subSkillTree.addSkill(subSkill);
        EventLog.getInstance().logEvent(new Event(subSkill.getName() + " added to " + this.name
                + " sub-skill tree"));
    }

    // REQUIRES: no two skills have the same name
    // EFFECTS: returns the skill with the given name if it exists anywhere within the tree, or if
    //         it is the skill it is called from.
    public Skill checkSkillExistenceFromSkill(String name) {
        name = name.toLowerCase();
        if (this.name.toLowerCase().equals(name)) {
            return this;
        }
        return this.subSkillTree.checkSkillExistence(name);
    }

    public String getName() {
        return this.name;
    }

    public int getDice() {
        return this.dice;
    }

    public SkillTree getSubSkillTree() {
        return this.subSkillTree;
    }

    public static int getXP() {
        return xp;
    }

    // MODIFIES: this
    // EFFECTS: Changes XP by amount.
    public static void modifyXP(int amount) {
        xp += amount;
        if (amount >= 0) {
            EventLog.getInstance().logEvent(new Event(amount + " XP earned"));
        } else {
            EventLog.getInstance().logEvent(new Event(Math.abs(amount) + " XP spent"));
        }
    }

    //EFFECTS: saves the Skill as a JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("dice", dice);
        json.put("subSkillTree", subSkillTree.toJson());
        EventLog.getInstance().logEvent(new Event("Saved" + this.name));
        return json;
    }
}
