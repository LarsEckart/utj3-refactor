package iloveyouboss;

// START:perf_test
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import static iloveyouboss.YesNo.*;
import static java.util.stream.IntStream.range;

class AProfilePerformance {
    int questionCount = 20;
    Random random = new Random();

    @Test
        // START_HIGHLIGHT
    void executionTime() {
        // END_HIGHLIGHT
        var questions = createQuestions();
        var criteria = new Criteria(createCriteria(questions));

        var iterations = 1_000_000;
        var matchCount = new AtomicInteger(0);
        var elapsedMs = time(iterations, i -> {
            var profile = new Profile("");
            profile.add(createAnswers(questions));
            if (profile.matches(criteria))
                matchCount.incrementAndGet();
        });
        System.out.println("elapsed: " + elapsedMs);
        System.out.println("matches: " + matchCount.get());
    }

    long time(int times, Consumer<Integer> func) {
        var start = System.nanoTime();
        range(0, times).forEach(i -> func.accept(i + 1));
        return (System.nanoTime() - start) / 1_000_000;
    }

    int numberOfWeights = Weight.values().length;

    Weight randomWeight() {
        if (isOneInTenTimesRandomly()) return Weight.REQUIRED;

        var nonRequiredWeightIndex =
            random.nextInt(numberOfWeights - 1) + 1;
        return Weight.values()[nonRequiredWeightIndex];
    }

    private boolean isOneInTenTimesRandomly() {
        return random.nextInt(10) == 0;
    }

    YesNo randomAnswer() {
        return random.nextInt() % 2 == 0 ? NO : YES;
    }

    Answer[] createAnswers(List<Question> questions) {
        return range(0, questionCount)
            .mapToObj(i -> new Answer(questions.get(i), randomAnswer()))
            .toArray(Answer[]::new);
    }

    List<Question> createQuestions() {
        String[] noYes = {NO.toString(), YES.toString()};
        return range(0, questionCount)
            .mapToObj(i -> new Question("" + i, noYes, i))
            .toList();
    }

    List<Criterion> createCriteria(List<Question> questions) {
        return range(0, questionCount)
            .mapToObj(i -> new Criterion(new Answer(
                questions.get(i), randomAnswer()), randomWeight()))
            .toList();
    }
}
// END:perf_test