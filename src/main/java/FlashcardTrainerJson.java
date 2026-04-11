
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

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

        System.out.println("Enter a profile name or create a new one:");
        String name = scanner.nextLine();

        if (userProfiles.containsKey(name)) {
            UserProfile profile = userProfiles.get(name);
            checkPassword(profile);
            activeProfile = profile;
        } else {
            activeProfile = createNewProfile(name);
        }

        System.out.println("Welcome " + activeProfile.getUsername());
        System.out.println("======= MAIN MENU =======");

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Study");
            System.out.println("2. View Your Stats");
            System.out.println("3. Switch Profile");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    study();
                    break;
                case 2:
                    viewStats();
                    break;
                case 3:
                    break;
                case 4:
                    exit = true;
                    break;
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
        System.out.println("Enter a new password:");

        String password = scanner.nextLine();
        UserProfile newProfile = new UserProfile(name, password, 0, 0);

        userProfiles.put(name, newProfile);
        System.out.println("New profile created!");
        jsonManager.saveUserProfiles(userProfiles);

        return newProfile;
    }

    public static void study() {
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
    }
    
    private void viewStats() {
        System.out.println();
    }
}
