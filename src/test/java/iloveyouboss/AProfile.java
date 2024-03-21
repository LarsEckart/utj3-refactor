package iloveyouboss;

// START:class
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AProfile {
    Question question = new Question("?", new String[] {"Y","N"}, 1);
    Profile profile = new Profile("x");

    @Test
    void supportsAddingIndividualAnswers() {
        var answer = new Answer(question, "Y");

        profile.add(answer);

        assertEquals(List.of(answer), profile.answers());
    }
}
// END:class
