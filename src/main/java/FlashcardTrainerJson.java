
import java.util.ArrayList;
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
        System.out.println("MAIN MENU");

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
}
