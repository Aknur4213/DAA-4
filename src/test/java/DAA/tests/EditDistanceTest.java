package DAA.tests;

import DAA.edit.*;
import DAA.metrics.Metrics;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {

    private final EditDistance[] algorithms = {
            new RecursiveEditDistance(),
            new TableEditDistance(),
            new TwoRowEditDistance()
    };

    @Test
    void testReferenceValues() {

        for (EditDistance algorithm : algorithms) {

            assertEquals(3, algorithm.distance(
                    "kitten", "sitting", new Metrics()));

            assertEquals(2, algorithm.distance(
                    "flaw", "lawn", new Metrics()));

            assertEquals(5, algorithm.distance(
                    "intention", "execution", new Metrics()));
        }
    }

    @Test
    void testEmptyStrings() {

        for (EditDistance algorithm : algorithms) {

            assertEquals(0, algorithm.distance(
                    "", "", new Metrics()));

            assertEquals(3, algorithm.distance(
                    "", "abc", new Metrics()));

            assertEquals(3, algorithm.distance(
                    "abc", "", new Metrics()));

            assertEquals(0, algorithm.distance(
                    "abc", "abc", new Metrics()));
        }
    }

    @Test
    void testRandomStrings() {

        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {

            String a = randomString(random, 4);
            String b = randomString(random, 4);

            int expected = algorithms[0].distance(
                    a, b, new Metrics());

            for (EditDistance algorithm : algorithms) {

                int actual = algorithm.distance(
                        a, b, new Metrics());

                assertEquals(expected, actual);
            }
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
