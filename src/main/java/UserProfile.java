
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author willi
 */
public class UserProfile {

    private String username;
    private String password;
    private int correctCount;
    private int incorrectCount;

    // TODO: keep track of correct and incorrect counts for each topic
    // 1. Add an increment function to increment the count given a topic
    // 2. When a user answers a question, call increment and update the new profile to JSON
    // 3. Add a get function to get all the topics stats to review in viewStats
//    
//    private Map<String, TopicStats> topicStastMap = new HashMap();
//
//    public class TopicStats {
//
//        public int correct;
//        public int incorrect;
//    }

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
    
    public void increaseCorrectCount() {
        correctCount++;
    }

    public void increaseIncorrectCount() {
        incorrectCount++;
    }
}
