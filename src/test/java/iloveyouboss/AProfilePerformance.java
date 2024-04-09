package iloveyouboss;

// START:perf_test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static iloveyouboss.YesNo.NO;
import static iloveyouboss.YesNo.YES;

public class AProfilePerformance {
    Profile profile;
    Answer[] answersArray;
    int times = 1;
    List<Question> questions = new ArrayList<>();
    List<Answer> answers = new ArrayList<>();
    Criteria criteria;
    Random random = new Random();
    String[] NO_YES = {NO.toString(), YES.toString()};

    @BeforeEach
    void create() {
        create20QuestionsAndAnswers();
        createCriteria();
    }

    private void create20QuestionsAndAnswers() {
        IntStream.range(0, 20).forEach(i -> {
            var question = new Question("" + i, NO_YES, i);
            questions.add(question);
            var answer = new Answer(question, randomYesNoAnswer());
            answers.add(answer);
        });
    }

    int numberOfWeights = Weight.values().length;

    private Weight randomWeight() {
        return Weight.values()[random.nextInt(numberOfWeights)];
    }

    private YesNo randomYesNoAnswer() {
        return random.nextInt() % 2 == 0 ? NO : YES;
    }

    void createCriteria() {
        var items = new ArrayList<Criterion>();
        IntStream.range(0, times).forEach(i -> {
            var question = questions.get(i);
            var answer = new Answer(question, randomYesNoAnswer());
            items.add(new Criterion(answer, randomWeight()));
        });
        criteria = new Criteria(items);
        answersArray = answers.toArray(new Answer[0]);
    }

    // START:test
    @Test
    void executionTime() {
        var numberOfTimes = 1_000_000;
        var elapsedMs = time(numberOfTimes, i -> {
            profile = new Profile("");
            profile.add(answersArray);
            profile.matches(criteria);
        });
        System.out.println(elapsedMs);
    }
    // END:test

    long time(int times, Consumer<Integer> func) {
        var start = System.nanoTime();
        IntStream.range(0, times).forEach(i -> func.accept(i + 1));
        return (System.nanoTime() - start) / 1_000_000;
    }
}
// END:perf_test