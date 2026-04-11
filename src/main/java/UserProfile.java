
/**
 *
 * @author willi
 */
public class UserProfile {

    private String username;
    private String password;
    private int correctCount;
    private int incorrectCount;

    public UserProfile(String username, String password, int correctCount, int incorrectCount) {
        this.username = username;
        this.password = password;
        this.correctCount = correctCount;
        this.incorrectCount = incorrectCount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

}
