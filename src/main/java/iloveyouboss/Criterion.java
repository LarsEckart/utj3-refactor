package iloveyouboss;

// START:criterion
import static iloveyouboss.Weight.IRRELEVANT;

public record Criterion(Answer answer, Weight weight) {
    // START_HIGHLIGHT
    boolean isMatch(Answer answer) {
    // END_HIGHLIGHT
        return weight() == IRRELEVANT || answer.match(answer());
    }
}
// END:criterion
