
package DAA;
import DAA.search.*;
import DAA.metrics.Metrics;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";

        StringSearch naive = new NaiveSearch();
        StringSearch kmp = new KMPSearch();

        Metrics m1 = new Metrics();
        Metrics m2 = new Metrics();

        System.out.println("Naive matches: "
                + naive.search(text, pattern, m1));

        System.out.println("KMP matches: "
                + kmp.search(text, pattern, m2));

        System.out.println("Naive comparisons: "
                + m1.getOperations());

        System.out.println("KMP comparisons: "
                + m2.getOperations());

        KMPSearch kmpAlgorithm = new KMPSearch();

        System.out.println("LPS: "
                + Arrays.toString(kmpAlgorithm.lps(pattern)));
    }
}
