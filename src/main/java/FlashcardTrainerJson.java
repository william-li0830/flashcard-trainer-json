
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author willi
 */
public class FlashcardTrainerJson {

    private static final Scanner scanner = new Scanner(System.in);
    private static final JSONManager jsonManager = new JSONManager();

    private static ArrayList<Flashcard> flashcards;
    private static Map<String, UserProfile> userProfiles;

    private static UserProfile activeProfile;

    public static void main(String[] args) {

        flashcards = jsonManager.loadFlashcards();
        userProfiles = jsonManager.loadUserProfiles();

        System.out.println("Welcome to the flashcard trainer");

        login();

        // TODO: Add a password protected admin mode 
        // and have a menu to view all users, and delete users (save to Json)
        // Hint: userProfiles.remove(key);
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome " + activeProfile.getUsername());
            System.out.println("======= MAIN MENU =======");
            System.out.println("1. Study");
            System.out.println("2. View Your Stats");
            System.out.println("3. Switch Profile");
            System.out.println("4. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    study();
                    break;
                case "2":
                    viewStats();
                    break;
                case "3":
                    login();
                    break;
                case "4":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void login() {
        System.out.println("Enter username:");
        String name = scanner.nextLine();

        if (userProfiles.containsKey(name)) {
            UserProfile profile = userProfiles.get(name);
            checkPassword(profile);
            activeProfile = profile;
        } else {
            activeProfile = createNewProfile(name);
        }
    }

    private static void checkPassword(UserProfile profile) {
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (!profile.getPassword().equals(password)) {
            System.out.println("Incorrect!");
            checkPassword(profile);
        }
    }

    private static UserProfile createNewProfile(String name) {
        System.out.println("Creating new profile...");
        System.out.println("Enter a new password:");

        String password = scanner.nextLine();
        UserProfile newProfile = new UserProfile(name, password, 0, 0);

        userProfiles.put(name.toLowerCase(), newProfile);
        System.out.println("New profile created!");
        jsonManager.saveUserProfiles(userProfiles);

        return newProfile;
    }

    public static void study() {
        System.out.println("STUDY");
        System.out.println("1. Study all topics");
        System.out.println("2. Choose a topic");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                studyAllTopics();
                break;
            case "2":
                selectTopic();
                break;
            default:
                System.out.println("Invalid choice!");
                study();
                break;
        }
    }

    private static void selectTopic() {
        System.out.println("Select a topic from the following:");

        Set<String> topics = getAllTopics();
        for (String topic : topics) {
            System.out.print(topic + " ");
        }
        System.out.println();

        String topic = scanner.nextLine();

        if (topics.contains(topic)) {
            studyTopic(topic);
        } else {
            System.out.println("No topic exists!");
            selectTopic();
        }
    }

    private static Set<String> getAllTopics() {
        // Set eliminates duplicates
        Set<String> topics = new HashSet();

        for (Flashcard card : flashcards) {
            String topic = card.getTopic();
            topics.add(topic);
        }

        return topics;
    }

    private static void studyAllTopics() {
        Collections.shuffle(flashcards);

        for (Flashcard card : flashcards) {
            System.out.println(card.getQuestion());

            String userAnswer = scanner.nextLine().trim();
            String answer = card.getAnswer().trim();

            if (userAnswer.equalsIgnoreCase(answer)) {
                System.out.println("Correct!");
                activeProfile.increaseCorrectCount();
            } else {
                System.out.println("Incorrect!");
                activeProfile.increaseIncorrectCount();
                System.out.println("Answer: " + answer);
            }

            jsonManager.saveUserProfiles(userProfiles);
        }

        System.out.println("Complete!");
    }

    // TODO: Only study flashcards with the matching topic
    private static void studyTopic(String topic) {

    }

    private static void viewStats() {
        int correct = activeProfile.getCorrectCount();
        int incorrect = activeProfile.getIncorrectCount();
        int total = correct + incorrect;
        int rate = 100;
        
        if (total > 0) {
            rate = correct * 100 / total;
        }

        System.out.println("Profile: " + activeProfile.getUsername());
        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
        System.out.println("Correct Rate: " + rate + "%");
    }
}
