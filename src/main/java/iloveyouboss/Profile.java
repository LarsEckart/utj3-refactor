package iloveyouboss;

// START:profile
import java.util.HashMap;
import java.util.Map;

import static iloveyouboss.Weight.IRRELEVANT;
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

    // START:loop
    public boolean matches(Criteria criteria) {
        // END:profile
        // ...
        // END:loop
        // START:profile
        score = 0;

        var kill = false;
        var anyMatches = false;
        // START:loop
        for (var criterion: criteria) {
            var answer = answers.get(criterion.answer().questionText());
            // START_HIGHLIGHT
            var match = criterion.weight() == IRRELEVANT ||
                        answer.match(criterion.answer());
            // END_HIGHLIGHT
            // END:profile
            // ...
            // END:loop
            // START:profile
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().value();
            }
            anyMatches |= match;
            // START:loop
        }
        // END:loop
        // END:profile
        // ...
        // END:loop
        // START:profile
        if (kill)
            return false;
        return anyMatches;
        // START:loop
    }
    // END:loop

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
// END:profile
