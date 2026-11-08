package DAA.search;

import DAA.metrics.Metrics;
import java.util.List;

public interface StringSearch {
    List<Integer> search(String text, String pattern, Metrics m);
}
