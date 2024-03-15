package iloveyouboss;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static iloveyouboss.Weight.REQUIRED;

public class Matcher {
    private final Criteria criteria;
    private final Map<String, Answer> answers;

    // TODO reference in chapter
    // START:ctor
    public Matcher(Criteria criteria, Answer... matcherAnswers) {
        this.criteria = criteria;
        this.answers = toMap(matcherAnswers);
    }

    private Map<String, Answer> toMap(Answer[] answers) {
        var answersMap = new HashMap<String, Answer>();
        Arrays.stream(answers).forEach(answer ->
            answersMap.put(answer.questionText(), answer));
        return answersMap;
    }
    // END:ctor

    public Matcher(Criteria criteria, Map<String, Answer> answers) {
        this.criteria = criteria;
        this.answers = answers;
    }

    // START:matches
    public boolean matches() {
        // START_HIGHLIGHT
        return allRequiredCriteriaMet() && anyMatches();
        // END_HIGHLIGHT
    }

    private boolean allRequiredCriteriaMet() {
        return criteria.stream()
            // START_HIGHLIGHT
            .filter(criterion -> criterion.weight() == REQUIRED)
            .allMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
        // END_HIGHLIGHT
    }
    // END:matches

    private boolean anyMatches() {
        return criteria.stream()
            .anyMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
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
