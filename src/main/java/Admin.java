
import java.io.File;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author william.li
 */
public class Admin {
    private static final Scanner scanner = new Scanner(System.in);

    private static final String PASSWORD = "hello123";

    public static boolean checkPassword(String password) {
        return PASSWORD.equals(password);
    }

    public static void viewAllUsers(Map<String, UserProfile> userProfiles) {
        if (userProfiles.isEmpty()) {
            System.out.println("No user exist!");
            return;
        }
        
        for (Map.Entry<String, UserProfile> entry : userProfiles.entrySet()) {
            UserProfile profile = entry.getValue();
            System.out.println(entry.getKey()
                    + ": password " + profile.getPassword()
                    + ", correct " + profile.getCorrectCount()
                    + ", incorrect " + profile.getIncorrectCount());
        }
    }

    public static void deleteUsers(JSONManager manager, Map<String, UserProfile> userProfiles) {
        System.out.println("Enter name of the user you want to delete");
        String name = scanner.nextLine().toLowerCase();

        if (userProfiles.containsKey(name)) {
            userProfiles.remove(name);
            manager.saveUserProfiles(userProfiles);
            System.out.println("User " + name + " deleted!");
        } else {
            System.out.println("User " + name + " doesn't exist");
        }

    }
}
