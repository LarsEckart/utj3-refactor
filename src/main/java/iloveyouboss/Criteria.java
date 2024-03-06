package iloveyouboss;

// START:Criteria
import java.util.*;
import java.util.stream.Stream;
import static java.util.Arrays.asList;

public record Criteria(List<Criterion> criteria)
    implements Iterable<Criterion> {
    public Criteria(Criterion... criterion) {
        this(asList(criterion));
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }

    // START_HIGHLIGHT
    public Stream<Criterion> stream() {
        return criteria.stream();
    }
    // END_HIGHLIGHT
}
// END:Criteria

