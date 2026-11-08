package DAA.util;

import java.util.Random;

public class InputGenerator {

    private static final long SEED = 42;

    public record InputPair(String a, String b) {
    }

    public static InputPair editEqual(int n) {

        String a = "A".repeat(n);

        return new InputPair(a, a);
    }

    public static InputPair editDifferent(int n) {

        String a = "A".repeat(n);
        String b = "B".repeat(n);

        return new InputPair(a, b);
    }

    public static InputPair searchRandom(int n) {

        Random random = new Random(SEED + n);

        String text = randomString(random, n);
        String pattern = randomString(random, 16);

        return new InputPair(text, pattern);
    }

    public static InputPair searchWorstCase(int n) {

        String text = "A".repeat(n);
        String pattern = "A".repeat(15) + "B";

        return new InputPair(text, pattern);
    }

    private static String randomString(Random random, int length) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {

            char c = (char) ('A' + random.nextInt(4));

            sb.append(c);
        }

        return sb.toString();
    }
}
