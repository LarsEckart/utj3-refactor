package iloveyouboss;

// START:Criteria
import java.util.*;
import java.util.stream.Stream;
import static java.util.Arrays.asList;

public record Criteria(List<Criterion> criteria) {
    public Criteria(Criterion... criterion) {
        this(asList(criterion));
    }

    // START_HIGHLIGHT
    // START:stream
    public Stream<Criterion> stream() {
        return criteria.stream();
    }
    // END:stream
    // END_HIGHLIGHT
}
// END:Criteria

