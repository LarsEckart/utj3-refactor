package iloveyouboss;

// START:criterion
import static iloveyouboss.Weight.IRRELEVANT;

public record Criterion(Answer answer, Weight weight) {
    // ...
    // END:criterion
    boolean isMatch(Answer answer) {
        return weight() == IRRELEVANT || answer.match(answer());
    }

    // START:criterion
    String questionText() {
        return answer().questionText();
    }
}
// END:criterion
