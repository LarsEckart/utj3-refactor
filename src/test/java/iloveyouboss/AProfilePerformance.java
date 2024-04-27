package iloveyouboss;

// START:perf_test
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static iloveyouboss.YesNo.NO;
import static iloveyouboss.YesNo.YES;
import static java.util.stream.IntStream.range;

class AProfilePerformance {
    int questionCount = 20;
    Random random = new Random();

    @Test
    void executionTime() {
        var questions = createQuestions();
        var answers = createAnswers(questions);
        var criteria = new Criteria(createCriteria(questions));

        // START_HIGHLIGHT
        var iterations = 1_000_000;
        var elapsedMs = time(iterations, i -> {
            var profile = new Profile("");
            profile.add(answers);
            profile.matches(criteria);
        });
        System.out.println(elapsedMs);
        // END_HIGHLIGHT
    }

    long time(int times, Consumer<Integer> func) {
        var start = System.nanoTime();
        range(0, times).forEach(i -> func.accept(i + 1));
        return (System.nanoTime() - start) / 1_000_000;
    }

    int numberOfWeights = Weight.values().length;

    Weight randomWeight() {
        return Weight.values()[random.nextInt(numberOfWeights)];
    }

    YesNo randomYesNoAnswer() {
        return random.nextInt() % 2 == 0 ? NO : YES;
    }

    Answer[] createAnswers(List<Question> questions) {
        return range(0, questionCount)
            .mapToObj(i -> new Answer(questions.get(i), randomYesNoAnswer()))
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
            .mapToObj(i -> new Criterion(
                new Answer(questions.get(i), randomYesNoAnswer()), randomWeight()))
            .toList();
    }
}
// END:perf_test