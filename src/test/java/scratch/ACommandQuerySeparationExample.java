package scratch;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ACommandQuerySeparationExample {
    // START:bad
    @Test
    void partitionsIntegerListIntoOddsAndEvens() {
        var list = List.of(1, 2, 3, 4, 5, 6);

        var evens = new ArrayList<Integer>();
        var odds = new ArrayList<Integer>();
        var iterator = list.iterator();
        while (iterator.hasNext()) {
            var element = iterator.next();
            System.out.println("each " + element);
            if (element % 2 == 0) {
                System.out.println("even item: " + element);
                evens.add(element);
            } else {
                System.out.println("odd item:" + iterator.next());
                odds.add(element);
            }
        }

        assertEquals(List.of(1, 3, 5), odds);
        assertEquals(List.of(2, 4, 6), evens);
    }
    // END:bad

    // START:better
    @Test
    void partitionsIntegerListIntoOddsAndEvensCorrectly() {
        var list = List.of(1, 2, 3, 4, 5, 6);

        var evens = new ArrayList<Integer>();
        var odds = new ArrayList<Integer>();
        // START_HIGHLIGHT
        var iterator = new Jefferator(list);
        // END_HIGHLIGHT
        while (iterator.hasNext()) {
            // START_HIGHLIGHT
            iterator.next();
            var element = iterator.current();
            // END_HIGHLIGHT
            System.out.println("each " + element);
            if (element % 2 == 0) {
                System.out.println("even item: " + element);
                evens.add(element);
            } else {
                // START_HIGHLIGHT
                System.out.println("odd item:" + iterator.current());
                // END_HIGHLIGHT
                odds.add(element);
            }
        }

        assertEquals(List.of(1, 3, 5), odds);
        assertEquals(List.of(2, 4, 6), evens);
    }
}
// END:better
