package persistence;


import model.Skill;
import model.SkillTree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//NOTE: this code is heavily based off of the code for the tests found in JsonSerializationDemo
public class JsonTest {

    //EFFECTS: checks that a given skill matches the given parameters
    protected void checkSkill(String name, int dice, SkillTree subSkillTree, Skill skill) {
        assertEquals(name, skill.getName());
        assertEquals(dice, skill.getDice());
        checkSkillTree(subSkillTree, skill.getSubSkillTree());
    }

    //EFFECTS: checks that a given SkillTree matches the given parameters
    private void checkSkillTree(SkillTree subSkillTree, SkillTree skillTree) {
        if (subSkillTree.getSkillList().size() != skillTree.getSkillList().size()) {
            fail("The skill lists were not the same size");
        }
        Skill testSkill;
        Skill actualSkill;
        for (int i = 0; i < subSkillTree.getSkillList().size(); i++) {
            testSkill = subSkillTree.getSkillList().get(i);
            actualSkill = skillTree.getSkillList().get(i);
            String testName = testSkill.getName();
            int testDice = testSkill.getDice();
            SkillTree testST = testSkill.getSubSkillTree();
            checkSkill(testName, testDice, testST, actualSkill);
        }
    }

}
