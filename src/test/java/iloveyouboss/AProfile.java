package iloveyouboss;

// START:test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static iloveyouboss.Weight.*;
import static iloveyouboss.YesNo.*;
import static org.junit.jupiter.api.Assertions.*;

class AProfile {
    Profile profile = new Profile("Geeks Inc.");
    Criteria criteria;

    Question freeLunch;
    Answer freeLunchYes;
    Answer freeLunchNo;

    Question bonus;
    Answer bonusYes;
    Answer bonusNo;

    Question hasGym;
    Answer hasGymNo;
    Answer hasGymYes;

    String[] NO_YES = {NO.toString(), YES.toString()};

    @BeforeEach
    void createQuestionsAndAnswers() {
        bonus = new Question("Bonus?", NO_YES, 1);
        bonusYes = new Answer(bonus, YES);
        bonusNo = new Answer(bonus, NO);

        freeLunch = new Question("Free lunch?", NO_YES, 1);
        freeLunchYes = new Answer(freeLunch, YES);
        freeLunchNo = new Answer(freeLunch, NO);

        hasGym = new Question("Gym?", NO_YES, 1);
        hasGymYes = new Answer(hasGym, YES);
        hasGymNo = new Answer(hasGym, NO);
    }

    @Nested
    class DoesNotMatch {
        @Test
        void whenAnyRequiredCriteriaNotMet() {
            profile.add(freeLunchNo, bonusYes);
            criteria = new Criteria(
                    new Criterion(freeLunchYes, REQUIRED),
                    new Criterion(bonusYes, IMPORTANT));

            var matches = profile.matches(criteria);

            assertFalse(matches);
        }

        @Test
        void whenNoneOfMultipleCriteriaMatch() {
            profile.add(bonusNo, freeLunchNo);
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT),
                new Criterion(freeLunchYes, IMPORTANT));

            var matches = profile.matches(criteria);

            assertFalse(matches);
        }
    }

    @Nested
    class Matches {
        @Test
        void whenCriteriaIrrelevant() {
            profile.add(freeLunchNo);
            criteria = new Criteria(
                new Criterion(freeLunchYes, IRRELEVANT));

            var matches = profile.matches(criteria);

            assertTrue(matches);
        }

        @Test
        void whenAnyOfMultipleCriteriaMatch() {
            profile.add(bonusYes, freeLunchNo);
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT),
                new Criterion(freeLunchYes, IMPORTANT));

            var matches = profile.matches(criteria);

            assertTrue(matches);
        }
    }

    @Nested
    class Score {
        @Test
        void isZeroWhenThereAreNoMatches() {
            profile.add(bonusNo);
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT));

            profile.matches(criteria);

            assertEquals(0, profile.score());
        }

        @Test
        void equalsCriterionValueForSingleMatch() {
            profile.add(bonusYes);
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT));

            profile.matches(criteria);

            assertEquals(IMPORTANT.value(), profile.score());
        }

        @Test
        void sumsCriterionValuesForMatches() {
            profile.add(bonusYes, freeLunchYes, hasGymNo);
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT),
                new Criterion(freeLunchYes, NICE_TO_HAVE),
                new Criterion(hasGymYes, VERY_IMPORTANT));

            profile.matches(criteria);

            assertEquals(IMPORTANT.value() + NICE_TO_HAVE.value(),
                profile.score());
        }
    }

    @Nested
    class Performance {
        int times = 1;
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        Criteria criteria;
        Random random = new Random();
        Answer[] answersArray;

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


        long time(int times, Consumer<Integer> func) {
            var start = System.nanoTime();
            IntStream.range(0, times).forEach(i -> func.accept(i + 1));
            return (System.nanoTime() - start) / 1_000_000;
        }
    }
}
// END:test
