package persistence;

import model.Skill;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//NOTE: this code is heavily based off of the code for the tests found in JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJson() {
        try {
            Skill s = new Skill("Do Anything", 1);
            JsonWriter writer = new JsonWriter("./data/testEmptyJson.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyJson.json");
            s = reader.read();
            assertEquals("Do Anything", s.getName());
            assertEquals(1, s.getDice());
            assertEquals(0, s.getSubSkillTree().getSkillList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOneSkillDeep() {
        try {
            Skill s = new Skill("Do Anything", 1);
            Skill kick = new Skill("Kick", 2);
            s.addSubSkill(kick);
            JsonWriter writer = new JsonWriter("./data/testOneSkillDeep.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testOneSkillDeep.json");
            s = reader.read();
            assertEquals("Do Anything", s.getName());
            assertEquals(1, s.getDice());
            assertEquals(1, s.getSubSkillTree().getSkillList().size());
            Skill testSkill = s.getSubSkillTree().getSkillList().get(0);
            assertEquals("Kick", testSkill.getName());
            assertEquals(2, testSkill.getDice());
            assertEquals(0, testSkill.getSubSkillTree().getSkillList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}