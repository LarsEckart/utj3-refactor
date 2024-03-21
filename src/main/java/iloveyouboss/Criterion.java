package iloveyouboss;

import static iloveyouboss.Weight.IRRELEVANT;

public record Criterion(Answer answer, Weight weight) {
    boolean isMatch(Answer answer) {
        return weight() == IRRELEVANT || answer.match(answer());
    }

    String questionText() {
        return answer().questionText();
    }
}
