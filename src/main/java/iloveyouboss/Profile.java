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

    // START:mouthful
    public boolean matches(Criteria criteria) {
        // ...
        // END:mouthful
        score = 0;

        var kill = false;
        var anyMatches = false;
        // START:mouthful
        for (var criterion: criteria) {
            // START_HIGHLIGHT
            var answer = answers.get(questionText(criterion));
            // END_HIGHLIGHT
            // ...
            // END:mouthful
            var match = criterion.isMatch(answer);
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().value();
            }
            anyMatches |= match;
            // START:mouthful
        }
        // ...
        // END:mouthful
        if (kill) {
            score = 0;
            return false;
        }

        return anyMatches;
        // START:mouthful
    }

    // START_HIGHLIGHT
    private String questionText(Criterion criterion) {
        // END_HIGHLIGHT
        return criterion.answer().questionText();
    }
    // END: mouthful

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
