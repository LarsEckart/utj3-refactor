package iloveyouboss;

// START:class
import java.util.Map;

public class MatchSet {
    private final Criteria criteria;
    private final Map<String, Answer> answers;
    private int score;

    public MatchSet(Criteria criteria, Map<String, Answer> answers) {
        this.criteria = criteria;
        this.answers = answers;
    }

    private void calculateScore(Criteria criteria) {
        score = criteria.stream()
            .filter(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)))
            .mapToInt(criterion -> criterion.weight().value())
            .sum();
    }

    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    private int score() {
        return score;
    }
}
// END:class
