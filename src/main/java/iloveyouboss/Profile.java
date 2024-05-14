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

    // START:matches
    public boolean matches(Criteria criteria) {
        score = 0;

        var kill = false;
        // START_HIGHLIGHT
        var anyMatches = false;
        for (var criterion: criteria) {
            var match = criterion.isMatch(profileAnswerMatching(criterion));
            // END_HIGHLIGHT
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().value();
            }
            // START_HIGHLIGHT
            anyMatches |= match;
            // END_HIGHLIGHT
        }
        if (kill)
            return false;

        // START_HIGHLIGHT
        return anyMatches;
        // END_HIGHLIGHT
    }
    // END:matches

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
