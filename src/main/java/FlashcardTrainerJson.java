
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
    }

    private static void login() {
        while (true) {
            System.out.println("Login as:");
            System.out.println("1. User");
            System.out.println("2. Admin");

            String role = scanner.nextLine();

            switch (role) {
                case "1":
                    userLogin();
                    break;
                case "2":
                    adminLogin();
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void userLogin() {
        System.out.println("Enter username:");
        String name = scanner.nextLine().toLowerCase();

        if (userProfiles.containsKey(name)) {
            UserProfile profile = userProfiles.get(name);
            checkPassword(profile);
            activeProfile = profile;
        } else {
            activeProfile = createNewProfile(name);
        }

        userMenu();
    }

    private static void adminLogin() {
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        if (Admin.checkPassword(password)) {
            adminMenu();
        } else {
            System.out.println("Incorrect!");
            adminLogin();
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nLogged in as admin");
            System.out.println("======= MENU =======");
            System.out.println("1. View all users");
            System.out.println("2. Delete users");
            System.out.println("3. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Admin.viewAllUsers(userProfiles);
                    break;
                case "2":
                    Admin.deleteUsers(jsonManager, userProfiles);
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\nWelcome " + activeProfile.getUsername());
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
                    return; // back to login
                case "4":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
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

        if (topics.contains(topic.toLowerCase())) {
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
            askQuestion(card);
        }

        System.out.println("Complete!");
    }

    private static void studyTopic(String topic) {
        Collections.shuffle(flashcards);

        for (Flashcard card : flashcards) {
            if (card.getTopic().equalsIgnoreCase(topic.trim())) {
                askQuestion(card);
            }
        }

        System.out.println("Complete!");
    }

    private static void askQuestion(Flashcard card) {
        System.out.println(card.getQuestion());
        String userAnswer = scanner.nextLine().trim();

        if (userAnswer.equalsIgnoreCase(card.getAnswer().trim())) {
            System.out.println("Correct!");
            activeProfile.increaseCorrectCount();
        } else {
            System.out.println("Incorrect!");
            activeProfile.increaseIncorrectCount();
            System.out.println("Answer: " + card.getAnswer());
        }
        jsonManager.saveUserProfiles(userProfiles);
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
