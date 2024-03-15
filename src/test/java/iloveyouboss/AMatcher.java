package iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static iloveyouboss.Weight.IMPORTANT;
import static iloveyouboss.Weight.REQUIRED;
import static iloveyouboss.YesNo.NO;
import static iloveyouboss.YesNo.YES;
import static org.junit.jupiter.api.Assertions.assertFalse;

// START:fieldsAndBefore
public class AMatcher {
    // END:fieldsAndBefore
    Criteria criteria;
    Matcher matcher;

    // START:fieldsAndBefore
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
    // END:fieldsAndBefore

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

//        @Test
//        void whenNoneOfMultipleCriteriaMatch() {
//            profile.add(bonusNo, freeLunchNo);
//            criteria = new Criteria(
//                new Criterion(bonusYes, IMPORTANT),
//                new Criterion(freeLunchYes, IMPORTANT));
//
//            var matches = profile.matches(criteria);
//
//            assertFalse(matches);
//        }
    }
// START:fieldsAndBefore
}
// END:fieldsAndBefore
