
package DAA.edit;

import DAA.metrics.Metrics;

public class TwoRowEditDistance implements EditDistance {
    @Override
    public int distance(String a, String b, Metrics m) {

        int n = a.length();
        int k = b.length();

        int[] prev = new int[k + 1];
        int[] curr = new int[k + 1];

        for (int j = 0; j <= k; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= n; i++) {

            curr[0] = i;

            for (int j = 1; j <= k; j++) {

                m.addOperation();

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    int insert = curr[j - 1];
                    int delete = prev[j];
                    int replace = prev[j - 1];

                    curr[j] = 1 + Math.min(
                            insert,
                            Math.min(delete, replace)
                    );
                }
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[k];
    }
}
