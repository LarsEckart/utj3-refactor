package scratch;

import java.util.List;

public class Jefferator implements JeffIterator<Integer> {
    private final List<Integer> list;
    private int currentIndex = -1;

    public Jefferator(List<Integer> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return currentIndex + 1 < list.size();
    }

    @Override
    public Integer current() {
        if (currentIndex == -1)
            throw new RuntimeException("iterator out of bounds");
        return list.get(currentIndex);
    }

    @Override
    public void next() {
        currentIndex++;
    }
}
