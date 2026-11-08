package DAA.search;

import DAA.metrics.Metrics;
import java.util.ArrayList;
import java.util.List;

public class NaiveSearch implements StringSearch {

    @Override
    public List<Integer> search(String text, String pattern, Metrics m) {

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }

        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int k = pattern.length();

        if (k > n) {
            return matches;
        }

        for (int i = 0; i <= n - k; i++) {

            int j = 0;

            while (j < k) {

                m.addOperation();

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }

                j++;
            }

            if (j == k) {
                matches.add(i);
            }
        }

        return matches;
    }
}
