package iloveyouboss;

// START:class
import java.util.ArrayList;
import java.util.List;

public class Profile {
    private final List<Answer> answers = new ArrayList<>();
    private final String name;

    public Profile(String name) {
        this.name = name;
    }

    public void add(Answer... newAnswers) {
        for (var answer: newAnswers)
            answers.add(answer);
    }

    public List<Answer> answers() {
        return answers;
    }

    public String name() {
        return name;
    }
}
// END:class
