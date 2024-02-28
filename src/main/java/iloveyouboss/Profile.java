package iloveyouboss;

import java.util.HashMap;
import java.util.Map;

import static iloveyouboss.Weight.DontCare;
import static iloveyouboss.Weight.MustMatch;

public class Profile {
    private Map<String,Answer> answers = new HashMap<>();
    private int score;
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public void add(Answer answer) {
        answers.put(answer.questionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;

        var kill = false;
        var anyMatches = false;
        for (var criterion: criteria) {
            var answer = answers.get(
                criterion.answer().questionText());
            var match =
                criterion.weight() == DontCare ||
                    answer.match(criterion.answer());
            if (!match && criterion.weight() == MustMatch) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().getValue();
            }
            anyMatches |= match;
            // ...
        }
        if (kill)
            return false;
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
