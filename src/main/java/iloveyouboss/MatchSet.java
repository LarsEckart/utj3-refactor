package iloveyouboss;

import java.util.Map;
import static iloveyouboss.Weight.REQUIRED;

// START:class
public class MatchSet {
    private final Criteria criteria;
    private final Map<String, Answer> answers;
    private int score;

    public MatchSet(Criteria criteria, Map<String, Answer> answers) {
        this.criteria = criteria;
        this.answers = answers;
        calculateScore();
    }

    private void calculateScore() {
        score = criteria.stream()
            .filter(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)))
            .mapToInt(criterion -> criterion.weight().value())
            .sum();
    }

    public boolean isMatchFor() {
        if (anyRequiredCriteriaNotMet())
            return false;
        return anyMatches();
    }

    private boolean anyMatches() {
        return criteria.stream()
            .anyMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }

    private boolean anyRequiredCriteriaNotMet() {
        return criteria.stream()
            .filter(criterion ->
                !criterion.isMatch(profileAnswerMatching(criterion)))
            .anyMatch(criterion -> criterion.weight() == REQUIRED);
    }

    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    public int score() {
        return score;
    }
}
// END:class
