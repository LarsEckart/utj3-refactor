package iloveyouboss;

import java.util.Map;
import static iloveyouboss.Weight.REQUIRED;

// START:class
public class Matcher {
    // ...
    // END:class
    private final Map<String, Answer> answers;
    private int score;

    public Matcher(Criteria criteria, Map<String, Answer> answers) {
        this.answers = answers;
        calculateScore(criteria);
    }

    private void calculateScore(Criteria criteria) {
        score = criteria.stream()
            .filter(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)))
            .mapToInt(criterion -> criterion.weight().value())
            .sum();
    }

    // START:class
    // START_HIGHLIGHT
    public boolean isMatchFor(Criteria criteria) {
        // END_HIGHLIGHT
        if (anyRequiredCriteriaNotMet(criteria))
            return false;
        return anyMatches(criteria);
    }

    // START_HIGHLIGHT
    private boolean anyMatches(Criteria criteria) {
        // END_HIGHLIGHT
        return criteria.stream()
            .anyMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }

    // START_HIGHLIGHT
    private boolean anyRequiredCriteriaNotMet(Criteria criteria) {
        // END_HIGHLIGHT
        return criteria.stream()
            .filter(criterion ->
                !criterion.isMatch(profileAnswerMatching(criterion)))
            .anyMatch(criterion -> criterion.weight() == REQUIRED);
    }

    Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }
    // ...
    // END:class

    public int score() {
        return score;
    }
    // START:class
}
// END:class
