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
    // START:matches
    public boolean matches(Criteria criteria) {
        // END:matches
        // ...
        // START:matches
        // END:mouthful
        score = 0;

        var kill = false;
        var anyMatches = false;
        // START:mouthful
        for (var criterion: criteria) {
            // START_HIGHLIGHT
            var match = criterion.isMatch(profileAnswerMatching(criterion));
            // END_HIGHLIGHT
            // END:matches
            // ...
            // START:matches
            // END:mouthful
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().value();
            }
            anyMatches |= match;
            // START:mouthful
        }
        // END:matches
        // ...
        // START:matches
        // END:mouthful
        if (kill)
            return false;

        return anyMatches;
        // START:mouthful
    }
    // END:matches
    // END: mouthful

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
