
package DAA;

import DAA.search.NaiveSearch;
import DAA.search.StringSearch;
import DAA.metrics.Metrics;

public class Main {
    public static void main(String[] args) {

        String text = "ABABABA";
        String pattern = "ABA";

        StringSearch algorithm = new NaiveSearch();

        Metrics m = new Metrics();

        System.out.println("Matches: "
                + algorithm.search(text, pattern, m));

        System.out.println("Comparisons: "
                + m.getOperations());
    }
}
