package persistence;

import model.Skill;
import model.SkillTree;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

//NOTE: this code is heavily based off of the code for the tests found in JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Skill s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJson() {
        JsonReader reader = new JsonReader("./data/testEmptyJson.json");
        SkillTree st = new SkillTree();
        try {
            Skill s = reader.read();
            checkSkill("Do Anything", 1, st, s);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneSkillDeep() {
        JsonReader reader = new JsonReader("./data/testOneSkillDeep.json");
        Skill kick = new Skill("Kick", 2);
        SkillTree st = new SkillTree();
        st.addSkill(kick);
        try {
            Skill s = reader.read();
            checkSkill("Do Anything", 1, st, s);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneSkillDeepMultiple() {
        JsonReader reader = new JsonReader("./data/testOneSkillDeepMultiple.json");
        Skill kick = new Skill("Kick", 2);
        Skill punch = new Skill("Punch", 2);
        SkillTree st = new SkillTree();
        st.addSkill(kick);
        st.addSkill(punch);
        try {
            Skill s = reader.read();
            checkSkill("Do Anything", 1, st, s);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTwoSkillsDeep() {
        JsonReader reader = new JsonReader("./data/testTwoSkillsDeep.json");
        Skill kick = new Skill("Kick", 2);
        Skill tornadoKick = new Skill("Tornado Kick", 3);
        SkillTree st = new SkillTree();
        st.addSkill(kick);
        kick.addSubSkill(tornadoKick);

        try {
            Skill s = reader.read();
            checkSkill("Do Anything", 1, st, s);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTwoSkillsDeepMultipleOnLOne() {
        JsonReader reader = new JsonReader("./data/testTwoSkillsDeepMultipleOnLOne.json");
        Skill kick = new Skill("Kick", 2);
        Skill punch = new Skill("Punch", 2);
        Skill tornadoKick = new Skill("Tornado Kick", 3);
        Skill uppercut = new Skill("Uppercut", 3);
        SkillTree st = new SkillTree();
        st.addSkill(kick);
        st.addSkill(punch);
        kick.addSubSkill(tornadoKick);
        punch.addSubSkill(uppercut);
        try {
            Skill s = reader.read();
            checkSkill("Do Anything", 1, st, s);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTwoSkillsDeepMultipleOnLTwo() {
        JsonReader reader = new JsonReader("./data/testTwoSkillsDeepMultipleOnLTwo.json");
        Skill kick = new Skill("Kick", 2);
        Skill tornadoKick = new Skill("Tornado Kick", 3);
        Skill jumpFrontKick = new Skill("Jump Front Kick", 3);
        SkillTree st = new SkillTree();
        st.addSkill(kick);
        kick.addSubSkill(tornadoKick);
        kick.addSubSkill(jumpFrontKick);

        try {
            Skill s = reader.read();
            checkSkill("Do Anything", 1, st, s);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}