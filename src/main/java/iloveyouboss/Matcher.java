package iloveyouboss;

// START:class
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static iloveyouboss.Weight.REQUIRED;
import static java.util.Arrays.asList;

public record Matcher(Criteria criteria, Map<String, Answer> answers) {
    public Matcher(Criteria criteria, List<Answer> matcherAnswers) {
        this(criteria, toMap(matcherAnswers));
    }

    public Matcher(Criteria criteria, Answer... matcherAnswers) {
        this(criteria, asList(matcherAnswers));
    }

    private static Map<String, Answer> toMap(List<Answer> answers) {
        var answersMap = new HashMap<String, Answer>();
        answers.stream().forEach(answer ->
            answersMap.put(answer.questionText(), answer));
        return answersMap;
    }
    // ...
    // END:class

    public boolean matches() {
        return allRequiredCriteriaMet() && anyMatches();
    }

    private boolean allRequiredCriteriaMet() {
        return criteria.stream()
            .filter(criterion -> criterion.weight() == REQUIRED)
            .allMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }

    private boolean anyMatches() {
        return criteria.stream()
            .anyMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }

    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    public int score() {
        return criteria.stream()
            .filter(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)))
            .mapToInt(criterion -> criterion.weight().value())
            .sum();
    }
    // START:class
}
// END:class
