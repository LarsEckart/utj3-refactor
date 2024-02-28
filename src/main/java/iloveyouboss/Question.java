package iloveyouboss;

public record Question(String text, String[] answerChoices, int id) {
    public int indexOf(String matchingAnswerChoice) {
        for (int i = 0; i < answerChoices.length; i++)
            if (answerChoices[i].equals(matchingAnswerChoice))
                return i;
        return -1;
    }

    public String answerChoice(int i) {
        return answerChoices[i];
    }

    public boolean match(int expected, int actual) {
        return expected == actual;
    }
}
