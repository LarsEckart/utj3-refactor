package iloveyouboss;

public record Answer(Question question, Object matchingValue) {
    public String questionText() {
        return question.text();
    }

    public boolean match(Answer otherAnswer) {
        return matchingValue.equals(otherAnswer.matchingValue);
    }
}
