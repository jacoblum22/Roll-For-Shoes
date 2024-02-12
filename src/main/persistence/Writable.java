package persistence;

import org.json.JSONObject;

// NOTE: this code is heavily based on the sample code written for the JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
