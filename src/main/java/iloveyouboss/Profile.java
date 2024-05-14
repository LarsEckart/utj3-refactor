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
        // START:matches
        for (var criterion: criteria) {
            var answer = answers.get(criterion.answer().questionText());
            // START_HIGHLIGHT
            var match = isMatch(criterion, answer);
            // END_HIGHLIGHT
            // ...
            // END:matches
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
            if (match) {
                score += criterion.weight().value();
            }
            anyMatches |= match;
            // START:matches
        }
        // END:matches
        if (kill)
            return false;
        return anyMatches;
    }

    // START:isMatch
    private boolean isMatch(Criterion criterion, Answer answer) {
        return criterion.weight() == IRRELEVANT ||
               answer.match(criterion.answer());
    }
    // END:isMatch

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
