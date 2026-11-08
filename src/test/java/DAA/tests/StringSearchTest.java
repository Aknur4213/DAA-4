package DAA.tests;

import DAA.search.*;
import DAA.metrics.Metrics;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StringSearchTest {

    private final StringSearch naive = new NaiveSearch();
    private final KMPSearch kmp = new KMPSearch();

    @Test
    void testLps() {

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void testReferenceMatches() {

        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";

        List<Integer> expected = List.of(10);

        assertEquals(expected,
                naive.search(text, pattern, new Metrics()));

        assertEquals(expected,
                kmp.search(text, pattern, new Metrics()));
    }

    @Test
    void testOverlappingMatches() {

        List<Integer> expected1 = Arrays.asList(0, 1, 2, 3);

        assertEquals(expected1,
                naive.search("AAAAA", "AA", new Metrics()));

        assertEquals(expected1,
                kmp.search("AAAAA", "AA", new Metrics()));

        List<Integer> expected2 = Arrays.asList(0, 2, 4);

        assertEquals(expected2,
                naive.search("ABABABA", "ABA", new Metrics()));

        assertEquals(expected2,
                kmp.search("ABABABA", "ABA", new Metrics()));
    }

    @Test
    void testEmptyPattern() {

        assertThrows(IllegalArgumentException.class,
                () -> naive.search("abc", "", new Metrics()));

        assertThrows(IllegalArgumentException.class,
                () -> kmp.search("abc", "", new Metrics()));
    }

    @Test
    void testPatternLongerThanText() {

        assertEquals(List.of(),
                naive.search("ab", "abcd", new Metrics()));

        assertEquals(List.of(),
                kmp.search("ab", "abcd", new Metrics()));
    }

    @Test
    void testNoMatches() {

        assertEquals(List.of(),
                naive.search("AAAA", "B", new Metrics()));

        assertEquals(List.of(),
                kmp.search("AAAA", "B", new Metrics()));
    }

    @Test
    void testRandomTexts() {

        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {

            String text = randomString(random, 20);
            String pattern = randomString(random, 5);

            if (pattern.isEmpty()) {
                pattern = "a";
            }

            List<Integer> expected =
                    naive.search(text, pattern, new Metrics());

            List<Integer> actual =
                    kmp.search(text, pattern, new Metrics());

            assertEquals(expected, actual);
        }
    }

    private String randomString(Random random, int maxLength) {

        int length = random.nextInt(maxLength + 1);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(3));
            sb.append(c);
        }

        return sb.toString();
    }
}
