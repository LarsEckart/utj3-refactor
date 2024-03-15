package iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static iloveyouboss.Weight.*;
import static iloveyouboss.Weight.NICE_TO_HAVE;
import static iloveyouboss.YesNo.NO;
import static iloveyouboss.YesNo.YES;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AMatcher {
    Criteria criteria;
    Matcher matcher;

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
        // START:test
        @Test
        void whenAnyRequiredCriteriaNotMet() {
            criteria = new Criteria(
                new Criterion(freeLunchYes, REQUIRED),
                new Criterion(bonusYes, IMPORTANT));
            // START_HIGHLIGHT
            matcher = new Matcher(criteria, freeLunchNo, bonusYes);
            // END_HIGHLIGHT

            // START_HIGHLIGHT
            var matches = matcher.matches();
            // END_HIGHLIGHT

            assertFalse(matches);
        }
        // END:test

        @Test
        void whenNoneOfMultipleCriteriaMatch() {
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT),
                new Criterion(freeLunchYes, IMPORTANT));
            matcher = new Matcher(criteria, bonusNo, freeLunchNo);

            var matches = matcher.matches();

            assertFalse(matches);
        }
    }

    @Nested
    class Matches {
        @Test
        void whenCriteriaIrrelevant() {
            criteria = new Criteria(
                new Criterion(freeLunchYes, IRRELEVANT));
            matcher = new Matcher(criteria, freeLunchNo);

            var matches = matcher.matches();

            assertTrue(matches);
        }

        @Test
        void whenAnyOfMultipleCriteriaMatch() {
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT),
                new Criterion(freeLunchYes, IMPORTANT));
            matcher = new Matcher(criteria, bonusYes, freeLunchNo);

            var matches = matcher.matches();

            assertTrue(matches);
        }
    }

    @Nested
    class Score {
        @Test
        void isZeroWhenThereAreNoMatches() {
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT));
            matcher = new Matcher(criteria, bonusNo);

            var score = matcher.score();

            assertEquals(0, score);
        }

        @Test
        void equalsCriterionValueForSingleMatch() {
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT));
            matcher = new Matcher(criteria, bonusYes);

            var score = matcher.score();

            assertEquals(IMPORTANT.value(), score);
        }

        @Test
        void sumsCriterionValuesForMatches() {
            criteria = new Criteria(
                new Criterion(bonusYes, IMPORTANT),
                new Criterion(freeLunchYes, NICE_TO_HAVE),
                new Criterion(hasGymYes, VERY_IMPORTANT));
            matcher = new Matcher(criteria, bonusYes, freeLunchYes, hasGymNo);

            var score = matcher.score();

            assertEquals(IMPORTANT.value() + NICE_TO_HAVE.value(),
                score);
        }
    }
}
