package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the Skill class
class SkillTest {
    private Skill testSkill1;
    private Skill testSkill3;
    private Skill testSkill1000000;
    private ArrayList<Integer> roll1;
    private ArrayList<Integer> roll5;

    @BeforeEach
    void runBefore() {
        testSkill1 = new Skill("Do Anything", 1);
        testSkill3 = new Skill("Kick Bucket", 3);
        testSkill1000000 = new Skill("All Powerful", 1000000);
        roll1 = new ArrayList<>();
        roll1.add(1);
        roll5 = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            roll5.add(i);
        }
    }

    @Test
    void testConstructor() {
        assertEquals("Do Anything", testSkill1.getName());
        assertEquals("Kick Bucket", testSkill3.getName());
        assertEquals("All Powerful", testSkill1000000.getName());
        assertEquals(1, testSkill1.getDice());
        assertEquals(3, testSkill3.getDice());
        assertEquals(1000000, testSkill1000000.getDice());
    }

    @Test
    public void testRollRange() {
        ArrayList<Integer> result = testSkill1000000.roll();
        for (int i = 0; i < 1000000; i++) {
            assertFalse(result.get(i) < 1 || result.get(i) > 6);
        }
    }

    @Test
    public void testRollDistribution() {
        ArrayList<Integer> result = testSkill1000000.roll();
        int[] distribution = new int[6];
        for (int diceRoll : result) {
            distribution[diceRoll - 1]++;
        }

        for (int i = 0; i < 6; i++) {
            double probability = (double) distribution[i] / 1000000;
            assertTrue(Math.abs(probability - 1.0 / 6.0) < 0.01);
        }
    }

    @Test
    public void testGetRollSum() {
        assertEquals(1, Skill.getRollSum(roll1));
        assertEquals(1, Skill.getRollSum(roll1));
        assertEquals(15, Skill.getRollSum(roll5));
        assertEquals(15, Skill.getRollSum(roll5));
    }

    @Test
    public void testCheckPassesDC1d6() {
        assertTrue(Skill.checkPassesDC(roll1, 0));
        assertTrue(Skill.checkPassesDC(roll1, 1));
        assertFalse(Skill.checkPassesDC(roll1, 2));
        assertFalse(Skill.checkPassesDC(roll1, 5));
        assertTrue(Skill.checkPassesDC(roll1, 0));
        assertTrue(Skill.checkPassesDC(roll1, 1));
        assertFalse(Skill.checkPassesDC(roll1, 2));
        assertFalse(Skill.checkPassesDC(roll1, 5));
    }

    @Test
    public void testCheckPassesDC5d6() {
        assertTrue(Skill.checkPassesDC(roll5, 10));
        assertTrue(Skill.checkPassesDC(roll5, 14));
        assertTrue(Skill.checkPassesDC(roll5, 15));
        assertFalse(Skill.checkPassesDC(roll5, 16));
        assertFalse(Skill.checkPassesDC(roll5, 20));
        assertTrue(Skill.checkPassesDC(roll5, 14));
        assertTrue(Skill.checkPassesDC(roll5, 10));
        assertTrue(Skill.checkPassesDC(roll5, 15));
        assertFalse(Skill.checkPassesDC(roll5, 16));
        assertFalse(Skill.checkPassesDC(roll5, 20));
    }

    @Test
    public void testCheckSkillExistenceFromSkillOneLevelDeep() {
        assertEquals(testSkill1, testSkill1.checkSkillExistenceFromSkill("Do Anything"));
        assertNull(testSkill1.checkSkillExistenceFromSkill("Mega-punch"));
    }

    @Test
    public void testCheckSkillExistenceFromSkillTwoLevelsDeep() {
        testSkill1.addSubSkill(testSkill3);
        testSkill1.addSubSkill(testSkill1000000);
        assertEquals(testSkill3, testSkill1.checkSkillExistenceFromSkill("Kick Bucket"));
        assertEquals(testSkill1000000, testSkill1.checkSkillExistenceFromSkill("All Powerful"));
        assertNull(testSkill1.checkSkillExistenceFromSkill("Mega-punch"));
    }

    @Test
    public void testCheckSkillExistenceFromSkillThreeLevelsDeep() {
        testSkill1.addSubSkill(testSkill3);
        testSkill3.addSubSkill(testSkill1000000);
        assertEquals(testSkill1000000, testSkill1.checkSkillExistenceFromSkill("All Powerful"));
        assertNull(testSkill1.checkSkillExistenceFromSkill("Mega-punch"));
    }

    @Test
    public void testModifyXP() {
        Skill.modifyXP(1);
        assertEquals(1, Skill.getXP());
        Skill.modifyXP(5);
        assertEquals(6, Skill.getXP());
        Skill.modifyXP(-2);
        assertEquals(4, Skill.getXP());
    }

    @Test
    public void testAddSubskillAlreadyInList() {
        testSkill1.addSubSkill(testSkill3);
        assertEquals(1, testSkill1.getSubSkillTree().getSkillList().size());
        testSkill1.addSubSkill(testSkill3);
        assertEquals(1, testSkill1.getSubSkillTree().getSkillList().size());
    }

}