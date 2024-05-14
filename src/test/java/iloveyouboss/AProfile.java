package iloveyouboss;

// START:test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
        // START:test
        @Test
        void whenAnyRequiredCriteriaNotMet() {
            // START_HIGHLIGHT
            profile.add(freeLunchNo, bonusYes); // save these args
            // END_HIGHLIGHT
            criteria = new Criteria(
                    new Criterion(freeLunchYes, REQUIRED),
                    new Criterion(bonusYes, IMPORTANT));

            // START_HIGHLIGHT
            var matches = profile.matches(criteria); // remove criteria arg
            // END_HIGHLIGHT

            assertFalse(matches);
        }
        // END:test

        @Test
        void whenNoneOfMultipleCriteriaMatch() {
            // START_HIGHLIGHT
            profile.add(bonusNo, freeLunchNo);
            // END_HIGHLIGHT
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
        void doesNotIncludeUnmetRequiredCriteria() {
            profile.add(bonusNo, freeLunchYes);
            criteria = new Criteria(
               new Criterion(bonusYes, REQUIRED),
               new Criterion(freeLunchYes, IMPORTANT));

            profile.matches(criteria);

            assertEquals(IMPORTANT.value(), profile.score());
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
}
// END:test
