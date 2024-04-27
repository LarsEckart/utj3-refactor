package iloveyouboss;

// START:perf_test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static iloveyouboss.YesNo.NO;
import static iloveyouboss.YesNo.YES;
import static java.util.stream.IntStream.range;

class AProfilePerformance {
    int questionCount = 20;
    List<Question> questions;
    Answer[] answers;
    Criteria criteria;
    Random random = new Random();

    @BeforeEach
    void create() {
        createSomeQuestionsAndAnswers(questionCount);
        criteria = new Criteria(createCriteria());
    }

    void createSomeQuestionsAndAnswers(int number) {
        String[] noYes = {NO.toString(), YES.toString()};
        questions = new ArrayList<>();
        List<Answer> answerList = new ArrayList<>();
        range(0, number).forEach(i -> {
            var question = new Question("" + i, noYes, i);
            questions.add(question);
            answerList.add(new Answer(question, randomYesNoAnswer()));
        });
        answers = answerList.toArray(new Answer[0]);
    }

    int numberOfWeights = Weight.values().length;

    Weight randomWeight() {
        return Weight.values()[random.nextInt(numberOfWeights)];
    }

    YesNo randomYesNoAnswer() {
        return random.nextInt() % 2 == 0 ? NO : YES;
    }

    List<Criterion> createCriteria() {
        return range(0, questionCount).mapToObj(i -> {
            var question = questions.get(i);
            var answer = new Answer(question, randomYesNoAnswer());
            return new Criterion(answer, randomWeight());
        }).toList();
    }

    @Test
    void executionTime() {
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
}
// END:perf_test