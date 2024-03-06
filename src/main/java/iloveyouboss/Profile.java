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

    // START:required
    public boolean matches(Criteria criteria) {
        // START_HIGHLIGHT
        var kill = anyRequiredCriteriaNotMet(criteria);
        // END_HIGHLIGHT
        if (kill)
            return false;

        calculateScore(criteria);

        return anyMatches(criteria);
    }

    // START_HIGHLIGHT
    private boolean anyRequiredCriteriaNotMet(Criteria criteria) {
        var kill = false;
        for (var criterion: criteria) {
            var match = criterion.isMatch(profileAnswerMatching(criterion));
            if (!match && criterion.weight() == REQUIRED) {
                kill = true;
            }
        }
        return kill;
    }
    // END_HIGHLIGHT
    // END:required

    private void calculateScore(Criteria criteria) {
        score = 0;
        for (var criterion: criteria) {
            var match = criterion.isMatch(profileAnswerMatching(criterion));
            if (match) {
                score += criterion.weight().value();
            }
        }
    }

    private boolean anyMatches(Criteria criteria) {
        var anyMatches = false;
        for (var criterion: criteria) {
            var match = criterion.isMatch(profileAnswerMatching(criterion));
            anyMatches |= match;
        }
        return anyMatches;
    }

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
