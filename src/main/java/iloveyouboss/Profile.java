package iloveyouboss;

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

    public boolean matches(Criteria criteria) {
        score = 0;

        var kill = false;
        var anyMatches = false;
        for (var criterion: criteria) {
            var answer = answers.get(
                criterion.answer().questionText());
            var match =
                criterion.weight() == IRRELEVANT ||
                    answer.match(criterion.answer());
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
