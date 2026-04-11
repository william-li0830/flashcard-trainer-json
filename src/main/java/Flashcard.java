
/**
 *
 * @author willi
 */
public class Flashcard {

    private String question;
    private String answer;
    private String topic;

    public Flashcard(String question, String answer, String topic) {
        this.question = question;
        this.answer = answer;
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTopic() {
        return topic;
    }

}
