package iloveyouboss;

import java.util.Map;
import static iloveyouboss.Weight.REQUIRED;

public class MatchSet {
    private final Criteria criteria;
    private final Map<String, Answer> answers;

    public MatchSet(Criteria criteria, Map<String, Answer> answers) {
        this.criteria = criteria;
        this.answers = answers;
    }

    // START:matches
    public boolean matches() {
        if (anyRequiredCriteriaNotMet())
            return false;
        return anyMatches();
    }
    // END:matches

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

    // START:score
    public int score() {
        return criteria.stream()
            .filter(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)))
            .mapToInt(criterion -> criterion.weight().value())
            .sum();
    }
    // END:score
}
