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

    // START:anyMatches
    public boolean matches(Criteria criteria) {
        score = 0;

        var kill = false;
        var anyMatches = false;
        for (var criterion: criteria) {
            var match = criterion.isMatch(profileAnswerMatching(criterion));
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().value();
            }
            anyMatches |= match;
        }
        if (kill)
            return false;

        // START_HIGHLIGHT
        return anyMatches(criteria);
        // END_HIGHLIGHT
    }

    // START_HIGHLIGHT
    private boolean anyMatches(Criteria criteria) {
        var anyMatches = false;
        for (var criterion: criteria) {
            var match = criterion.isMatch(profileAnswerMatching(criterion));
            anyMatches |= match;
        }
        return anyMatches;
    }
    // END_HIGHLIGHT
    // END:anyMatches

    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
