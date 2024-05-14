package iloveyouboss;

import java.util.HashMap;
import java.util.Map;

import static iloveyouboss.Weight.REQUIRED;

public class Profile {
    private final Map<String,Answer> answers = new HashMap<>();
    private final String name;
    private int score;

    public Profile(String name) {
        this.name = name;
    }

    public void add(Answer... newAnswers) {
        for (var answer: newAnswers)
            answers.put(answer.questionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;

        var kill = false;
        var anyMatches = false;
        // START:loop
        for (var criterion: criteria) {
            // START:mouthful
            var answer = answers.get(criterion.answer().questionText());
            // END:mouthful
            // START_HIGHLIGHT
            var match = criterion.isMatch(answer);
            // END_HIGHLIGHT
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().value();
            }
            anyMatches |= match;
        }
        // END:loop
        if (kill) {
            score = 0;
            return false;
        }
        return anyMatches;
    }

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
