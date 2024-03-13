package iloveyouboss;

// START:class
import java.util.Map;

public class Matcher {
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

    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    public int score() {
        return score;
    }
}
// END:class
