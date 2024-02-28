package iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static iloveyouboss.Weight.*;
import static iloveyouboss.YesNo.*;
import static org.junit.jupiter.api.Assertions.*;

class AProfile {
    Profile profile;
    Criteria criteria;

    Question questionReimbursesTuition;
    Answer answerReimbursesTuition;
    Answer answerDoesNotReimburseTuition;

    Question questionIsThereRelocation;
    Answer answerThereIsRelocation;
    Answer answerThereIsNoRelocation;

    Question questionOnsiteDaycare;
    Answer answerNoOnsiteDaycare;
    Answer answerHasOnsiteDaycare;

    @BeforeEach
    void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    @BeforeEach
    void createCriteria() {
        criteria = new Criteria();
    }

    @BeforeEach
    void createQuestionsAndAnswers() {
        questionIsThereRelocation =
            createBooleanQuestion(1, "Relocation package?");
        answerThereIsRelocation =
            new Answer(questionIsThereRelocation, YES.toString());
        answerThereIsNoRelocation =
            new Answer(questionIsThereRelocation, NO.toString());

        questionReimbursesTuition =
            createBooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition =
            new Answer(questionReimbursesTuition, YES.toString());
        answerDoesNotReimburseTuition =
            new Answer(questionReimbursesTuition, NO.toString());

        questionOnsiteDaycare =
            createBooleanQuestion(1, "Onsite daycare?");
        answerHasOnsiteDaycare =
            new Answer(questionOnsiteDaycare, YES.toString());
        answerNoOnsiteDaycare =
            new Answer(questionOnsiteDaycare, NO.toString());
    }

    Question createBooleanQuestion(int id, String text) {
        return new Question(text, new String[] { NO.toString(), YES.toString() }, id);
    }

    @Test
    void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(
            new Criterion(answerReimbursesTuition, MustMatch));

        var matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @Test
    void matchAnswersTrueForAnyDontCareCriteria() {
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(
            new Criterion(answerReimbursesTuition, DontCare));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Important));
        criteria.add(new Criterion(answerReimbursesTuition, Important));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    void matchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
        profile.add(answerThereIsNoRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Important));
        criteria.add(new Criterion(answerReimbursesTuition, Important));

        var matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @Test
    void scoreIsZeroWhenThereAreNoMatches() {
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Important));

        profile.matches(criteria);

        assertEquals(0, profile.score());
    }

    @Test
    void scoreIsCriterionValueForSingleMatch() {
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Important));

        profile.matches(criteria);

        assertEquals(Important.getValue(), profile.score());
    }

    @Test
    void scoreAccumulatesCriterionValuesForMatches() {
        profile.add(answerThereIsRelocation);
        profile.add(answerReimbursesTuition);
        profile.add(answerNoOnsiteDaycare);
        criteria.add(new Criterion(answerThereIsRelocation, Important));
        criteria.add(new Criterion(answerReimbursesTuition, WouldPrefer));
        criteria.add(new Criterion(answerHasOnsiteDaycare, VeryImportant));

        profile.matches(criteria);

        var expectedScore = Important.getValue() + WouldPrefer.getValue();
        assertEquals(expectedScore, profile.score());
    }

    // TODO: missing functionality--what if there is no matching profile answer for a criterion?
}
