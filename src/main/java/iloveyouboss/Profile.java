package iloveyouboss;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private final Map<String,Answer> answers = new HashMap<>();
    private final String name;

    public Profile(String name) {
        this.name = name;
    }

    public void add(Answer... newAnswers) {
        for (var answer: newAnswers)
            answers.put(answer.questionText(), answer);
    }

    // START:matches
    public boolean matches(Criteria criteria) {
        return new Matcher(criteria, answers).matches();
    }

    // START_HIGHLIGHT
    public int score(Criteria criteria) {
        return new Matcher(criteria, answers).score();
        // END_HIGHLIGHT
    }
    // END:matches

    @Override
    public String toString() {
        return name;
    }
}
// END:class
