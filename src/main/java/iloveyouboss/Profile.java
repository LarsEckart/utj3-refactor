package iloveyouboss;

import java.util.List;

public record Profile(String name, List<Answer> answers) {
    public void add(Answer... newAnswers) {
        for (var answer: newAnswers)
            answers.add(answer);
    }
}
