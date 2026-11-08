package DAA.edit;

import DAA.metrics.Metrics;

public class RecursiveEditDistance implements EditDistance {
    @Override
    public int distance(String a, String b, Metrics m) {
        return solve(a, b, a.length(), b.length(), m);
    }

    private int solve(String a, String b, int n, int k, Metrics m) {

        m.addOperation();

        if (n == 0) {
            return k;
        }

        if (k == 0) {
            return n;
        }

        if (a.charAt(n - 1) == b.charAt(k - 1)) {
            return solve(a, b, n - 1, k - 1, m);
        }

        int insert = solve(a, b, n, k - 1, m);
        int delete = solve(a, b, n - 1, k, m);
        int replace = solve(a, b, n - 1, k - 1, m);

        return 1 + Math.min(insert, Math.min(delete, replace));
    }
}
