package persistence;

import model.Skill;
import model.SkillTree;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Skills from JSON data stored in file
// NOTE: this code is heavily based on the sample code written for the JsonSerializationDemo
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Skill read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSkill(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Skill from JSON object and returns it
    private Skill parseSkill(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int dice = jsonObject.getInt("dice");
        Skill s = new Skill(name, dice);
        addSkills(s, jsonObject);
        return s;
    }

    // MODIFIES: wr
    // EFFECTS: parses SkillTree from JSON object and adds it to Skill
    private void addSkills(Skill s, JSONObject jsonObject) {
        JSONObject jsonSkillList = jsonObject.getJSONObject("subSkillTree");
        SkillTree subSkillTree = parseSkillTree(jsonSkillList);
        for (Skill skill : subSkillTree.getSkillList()) {
            s.addSubSkill(skill);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses SkillTree and returns it
    private SkillTree parseSkillTree(JSONObject jsonObject) {
        JSONArray jsonSkills = jsonObject.getJSONArray("skillList");
        SkillTree skillList = new SkillTree();
        for (int i = 0; i < jsonSkills.length(); i++) {
            JSONObject jsonSkill = jsonSkills.getJSONObject(i);
            Skill skill = parseSkill(jsonSkill);
            skillList.addSkill(skill);
        }
        return skillList;
    }
}
