package iloveyouboss;

public record Answer(int i, Question question) {
    public Answer(Question question, String matchingValue) {
        this(question.indexOf(matchingValue), question);
    }
    public String questionText() {
        return question.text();
    }

    public boolean match(Answer otherAnswer) {
        return question.match(i, otherAnswer.i);
    }

    @Override
    public String toString() {
        return String.format("%s %s", question.text(), question.answerChoice(i));
    }
}
