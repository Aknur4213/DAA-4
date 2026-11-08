package DAA.search;

import DAA.metrics.Metrics;

import java.util.ArrayList;
import java.util.List;

public class KMPSearch implements StringSearch {

    public int[] lps(String pattern) {
        return buildLps(pattern, null);
    }

    private int[] buildLps(String pattern, Metrics m) {

        int n = pattern.length();
        int[] lps = new int[n];

        int len = 0;
        int i = 1;

        while (i < n) {

            if (m != null) {
                m.addOperation();
            }

            if (pattern.charAt(i) == pattern.charAt(len)) {

                len++;
                lps[i] = len;
                i++;

            } else {

                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

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

        int[] lps = buildLps(pattern, m);

        int i = 0;
        int j = 0;

        while (i < n) {

            m.addOperation();

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == k) {
                    matches.add(i - j);
                    j = lps[j - 1];
                }

            } else {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return matches;
    }
}
