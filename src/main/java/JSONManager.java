
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author willi
 */
public class JSONManager {

    private static final String FLASHCARDS_FILE = "flashcards.json";
    private static final String PROFILES_FILE = "profiles.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // TODO: add more flashcards in flashcards.json
    public ArrayList<Flashcard> loadFlashcards() {
        File file = new File(FLASHCARDS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FLASHCARDS_FILE)) {

            Flashcard[] flashcardArray = gson.fromJson(reader, Flashcard[].class);
            if (flashcardArray == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(Arrays.asList(flashcardArray));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Map<String, UserProfile> loadUserProfiles() {
        File userFile = new File(PROFILES_FILE);
        if (!userFile.exists()) {
            return new HashMap();
        }
        try (FileReader reader = new FileReader(PROFILES_FILE)) {
            Type type = new TypeToken<Map<String, UserProfile>>() {
            }.getType();
            return gson.fromJson(reader, type);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return new HashMap();
    }

    public void saveUserProfiles(Map<String, UserProfile> profiles) {
        try (FileWriter writer = new FileWriter(PROFILES_FILE)) {
            gson.toJson(profiles, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
