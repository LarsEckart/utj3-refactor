package iloveyouboss;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class AProfilePerformance {
    // START:perf_test
    @Test
    void createCriteria() {
        // ...
        // END:perf_test
        var items = new ArrayList<Criterion>();
        IntStream.range(0, times).forEach(i -> {
            var question = questions.get(i);
            var answer = new Answer(question, randomYesNoAnswer());
            items.add(new Criterion(answer, randomWeight()));
        });
        // START:perf_test
        criteria = new Criteria(items);
        // START_HIGHLIGHT
        answersArray = answers.toArray(new Answer[0]);
        // END_HIGHLIGHT
    }

    @Test
    void executionTime() {
        var numberOfTimes = 1_000_000;
        var elapsedMs = time(numberOfTimes, i -> {
            // START_HIGHLIGHT
            profile = new Profile("");
            profile.add(answersArray);
            profile.matches(criteria);
            // END_HIGHLIGHT
        });
        System.out.println(elapsedMs);
    }
    // ...
    // END:perf_test

    long time(int times, Consumer<Integer> func) {
        var start = System.nanoTime();
        IntStream.range(0, times).forEach(i -> func.accept(i + 1));
        return (System.nanoTime() - start) / 1_000_000;
    }
    // START:perf_test
}
// END:perf_test
}
