package iloveyouboss;

import java.util.*;

public class Criteria implements Iterable<Criterion> {
    private List<Criterion> criteria = new ArrayList<>();

    public void add(Criterion criterion) {
        criteria.add(criterion);
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }
}
