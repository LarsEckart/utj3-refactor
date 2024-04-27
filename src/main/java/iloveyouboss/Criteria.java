package iloveyouboss;

import java.util.*;
import java.util.stream.Stream;
import static java.util.Arrays.asList;

public record Criteria(List<Criterion> criteria)
    implements Iterable<Criterion> {
    public Criteria(Criterion... criterion) {
        this(asList(criterion));
    }

    // START:stream
    public Stream<Criterion> stream() {
        return criteria.stream();
    }
    // END:stream

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }
}
// END:Criteria
