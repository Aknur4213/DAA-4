package DAA.edit;

import DAA.metrics.Metrics;

public interface EditDistance {
    int distance(String a, String b, Metrics m);
}
