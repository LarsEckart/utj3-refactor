package iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static iloveyouboss.Weight.*;
import static org.junit.jupiter.api.Assertions.*;

public class AProfile {
    private Profile profile;
    private Criteria criteria;

    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Question questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Question questionOnsiteDaycare;
    private Answer answerNoOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;

    @BeforeEach
    public void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    @BeforeEach
    public void createCriteria() {
        criteria = new Criteria();
    }

    @BeforeEach
    public void createQuestionsAndAnswers() {
        questionIsThereRelocation =
            new BooleanQuestion(1, "Relocation package?");
        answerThereIsRelocation =
            new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNoRelocation =
            new Answer(questionIsThereRelocation, Bool.FALSE);

        questionReimbursesTuition =
            new BooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition =
            new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition =
            new Answer(questionReimbursesTuition, Bool.FALSE);

        questionOnsiteDaycare =
            new BooleanQuestion(1, "Onsite daycare?");
        answerHasOnsiteDaycare =
            new Answer(questionOnsiteDaycare, Bool.TRUE);
        answerNoOnsiteDaycare =
            new Answer(questionOnsiteDaycare, Bool.FALSE);
    }

    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(
            new Criterion(answerReimbursesTuition, MustMatch));

        var matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(
            new Criterion(answerReimbursesTuition, DontCare));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    public void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Important));
        criteria.add(new Criterion(answerReimbursesTuition, Important));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    public void matchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
        profile.add(answerThereIsNoRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Important));
        criteria.add(new Criterion(answerReimbursesTuition, Important));

        var matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @Test
    public void scoreIsZeroWhenThereAreNoMatches() {
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Important));

        profile.matches(criteria);

        assertEquals(0, profile.score());
    }

    @Test
    public void scoreIsCriterionValueForSingleMatch() {
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Important));

        profile.matches(criteria);

        assertEquals(Important.getValue(), profile.score());
    }

    @Test
    public void scoreAccumulatesCriterionValuesForMatches() {
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
