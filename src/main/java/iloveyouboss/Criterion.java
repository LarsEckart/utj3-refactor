package iloveyouboss;

// START:criterion
import static iloveyouboss.Weight.IRRELEVANT;

public record Criterion(Answer answer, Weight weight) {
    // START_HIGHLIGHT
    boolean isMatch(Answer answer) {
        return weight() == IRRELEVANT ||
            answer.match(answer());
    }
    // END_HIGHLIGHT
}
// END:criterion
