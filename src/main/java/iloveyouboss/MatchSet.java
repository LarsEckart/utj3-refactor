package iloveyouboss;

// START:class
import java.util.Map;
import static iloveyouboss.Weight.REQUIRED;

public class MatchSet {
    private final Map<String, Answer> answers;
    private int score;

    public MatchSet(Criteria criteria, Map<String, Answer> answers) {
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

    public int score() {
        return score;
    }
}
// END:class
