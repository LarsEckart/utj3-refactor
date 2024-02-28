package iloveyouboss;

import java.util.*;

import static java.util.Arrays.asList;

public record Criteria(List<Criterion> criteria) implements Iterable<Criterion> {
    public Criteria(Criterion... criterion) {
        this(asList(criterion));
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }
}
