package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the SkillTree class
public class SkillTreeTest {
    private Skill testSkill1;
    private Skill testSkill2;
    private Skill testSkill3;
    private SkillTree testST;

    @BeforeEach
    void runBefore() {
        testSkill1 = new Skill("Jump", 1);
        testSkill2 = new Skill("Duck", 1);
        testSkill3 = new Skill("Punch", 1);
        testST = new SkillTree();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testST.getSkillList().size());
    }

    @Test
    void testAddSkill() {
        testST.addSkill(testSkill1);
        assertEquals(1, testST.getSkillList().size());
        testST.addSkill(testSkill2);
        assertEquals(2, testST.getSkillList().size());
        testST.addSkill(testSkill3);
        assertEquals(3, testST.getSkillList().size());
        assertEquals(testSkill1, testST.getSkillList().get(0));
        assertEquals(testSkill2, testST.getSkillList().get(1));
        assertEquals(testSkill3, testST.getSkillList().get(2));
    }

    @Test
    void testCheckSkillExistenceOneLevelDeep() {
        testST.addSkill(testSkill1);
        testST.addSkill(testSkill2);
        testST.addSkill(testSkill3);
        assertEquals(testSkill2, testST.checkSkillExistence("Duck"));
        assertEquals(testSkill1, testST.checkSkillExistence("Jump"));
        assertEquals(testSkill3, testST.checkSkillExistence("Punch"));
        assertNull(testST.checkSkillExistence("Kick"));
    }

    @Test
    void testCheckSkillExistenceTwoLevelsDeep() {
        testST.addSkill(testSkill1);
        testSkill1.addSubSkill(testSkill2);
        testSkill1.addSubSkill(testSkill3);
        assertEquals(testSkill2, testST.checkSkillExistence("Duck"));
        assertEquals(testSkill1, testST.checkSkillExistence("Jump"));
        assertEquals(testSkill3, testST.checkSkillExistence("Punch"));
        assertNull(testST.checkSkillExistence("Kick"));
    }

    @Test
    void testCheckSkillExistenceThreeLevelsDeep() {
        testST.addSkill(testSkill1);
        testSkill1.addSubSkill(testSkill2);
        testSkill2.addSubSkill(testSkill3);
        assertEquals(testSkill2, testST.checkSkillExistence("Duck"));
        assertEquals(testSkill1, testST.checkSkillExistence("Jump"));
        assertEquals(testSkill3, testST.checkSkillExistence("Punch"));
        assertNull(testST.checkSkillExistence("Kick"));
    }
}
