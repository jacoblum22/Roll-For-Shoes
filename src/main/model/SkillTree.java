package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of sub-skills within a class
// NOTE: some of this code is based on the sample code written for the JsonSerializationDemo
public class SkillTree implements Writable {
    List<Skill> skillList;

    // EFFECTS: creates a new SkillTree with an empty list of skills.
    public SkillTree() {
        skillList = new ArrayList<>();
    }

    public List<Skill> getSkillList() {
        return this.skillList;
    }

    // REQUIRES: skill doesn't already exist in the tree.
    // MODIFIES: this
    // EFFECTS: adds skill to tree.
    public void addSkill(Skill skill) {
        this.skillList.add(skill);
    }

    // EFFECTS: returns the skill with the given name if it exists anywhere within the tree
    public Skill checkSkillExistence(String name) {
        name = name.toLowerCase();
        for (Skill s : skillList) {
            if (s.getName().toLowerCase().equals(name)) {
                return s;
            }
            if (s.getSubSkillTree().getSkillList().size() > 0) {
                Skill subTree = s.getSubSkillTree().checkSkillExistence(name);
                if (subTree != null) {
                    return subTree;
                }
            }
        }
        return null;
    }

    //EFFECTS: saves the SkillTree as a JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonSkills = new JSONArray();
        for (Skill skill : skillList) {
            jsonSkills.put(skill.toJson());
        }
        json.put("skillList", jsonSkills);
        return json;
    }
}
