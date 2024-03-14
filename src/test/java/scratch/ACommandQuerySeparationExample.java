package scratch;

// START:class
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ACommandQuerySeparationExample {
    @Test
    void partitionsIntegerListIntoOddsAndEvens() {
        var list = List.of(1, 2, 3, 4, 5, 6);
        var evens = new ArrayList<Integer>();
        var odds = new ArrayList<Integer>();

        var iterator = list.iterator();
        while (iterator.hasNext()) {
            var element = iterator.next();
            if (element % 2 == 0) {
                evens.add(element);
            }
            else {
                System.out.println("odd item:" + iterator.next());
                odds.add(element);
            }
        }

        assertEquals(List.of(1, 3, 5), odds);
        assertEquals(List.of(2, 4, 6), evens);
    }
}
// END:class
