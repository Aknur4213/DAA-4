
package DAA.bench;

import DAA.edit.*;
import DAA.search.*;
import DAA.metrics.Metrics;
import DAA.util.InputGenerator;
import DAA.util.InputGenerator.InputPair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

public class Benchmark {

    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {

        Files.createDirectories(Path.of("results"));

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("results/results.csv"))) {

            writer.write("algorithm,input,n,run,time_ms,median_time_ms,operations,metric\n");

            runEditBenchmarks(writer);
            runSearchBenchmarks(writer);
        }

        System.out.println("Benchmark completed!");
        System.out.println("Results saved to results/results.csv");
    }

    private static void runEditBenchmarks(
            BufferedWriter writer) throws IOException {

        EditDistance[] algorithms = {
                new RecursiveEditDistance(),
                new TableEditDistance(),
                new TwoRowEditDistance()
        };

        String[] names = {
                "Recursive",
                "Table",
                "TwoRows"
        };

        for (int a = 0; a < algorithms.length; a++) {

            int[] sizes;

            if (a == 0) {
                sizes = new int[]{4, 6, 8, 10};
            } else {
                sizes = new int[]{50, 200, 800, 3200};
            }

            for (String input : new String[]{"EQUAL", "DIFFERENT"}) {

                for (int n : sizes) {

                    InputPair pair;

                    if (input.equals("EQUAL")) {
                        pair = InputGenerator.editEqual(n);
                    } else {
                        pair = InputGenerator.editDifferent(n);
                    }

                    final EditDistance algorithm = algorithms[a];

                    measure(writer, names[a], input, n,
                            "work_units",
                            () -> algorithm.distance(
                                    pair.a(), pair.b(),
                                    new Metrics()
                            ),
                            m -> algorithm.distance(
                                    pair.a(), pair.b(), m
                            ));
                }
            }
        }
    }

    private static void runSearchBenchmarks(
            BufferedWriter writer) throws IOException {

        StringSearch[] algorithms = {
                new NaiveSearch(),
                new KMPSearch()
        };

        String[] names = {
                "Naive",
                "KMP"
        };

        int[] sizes = {1000, 4000, 16000, 64000};

        for (int a = 0; a < algorithms.length; a++) {

            for (String input : new String[]{"RANDOM", "WORST_CASE"}) {

                for (int n : sizes) {

                    InputPair pair;

                    if (input.equals("RANDOM")) {
                        pair = InputGenerator.searchRandom(n);
                    } else {
                        pair = InputGenerator.searchWorstCase(n);
                    }

                    final StringSearch algorithm = algorithms[a];

                    measure(writer, names[a], input, n,
                            "char_comparisons",
                            () -> algorithm.search(
                                    pair.a(), pair.b(),
                                    new Metrics()
                            ),
                            m -> algorithm.search(
                                    pair.a(), pair.b(), m
                            ));
                }
            }
        }
    }

    @FunctionalInterface
    private interface MeasuredTask {
        void run(Metrics metrics);
    }

    private static void measure(
            BufferedWriter writer,
            String algorithm,
            String input,
            int n,
            String metric,
            Runnable warmup,
            MeasuredTask task) throws IOException {

        warmup.run();

        double[] times = new double[RUNS];
        long[] operations = new long[RUNS];

        for (int r = 0; r < RUNS; r++) {

            Metrics m = new Metrics();

            long start = System.nanoTime();

            task.run(m);

            long end = System.nanoTime();

            times[r] = (end - start) / 1_000_000.0;
            operations[r] = m.getOperations();
        }

        double[] sorted = times.clone();
        Arrays.sort(sorted);

        double median = sorted[RUNS / 2];

        for (int r = 0; r < RUNS; r++) {

            writer.write(String.format(Locale.US,
                    "%s,%s,%d,%d,%.6f,%.6f,%d,%s%n",
                    algorithm,
                    input,
                    n,
                    r + 1,
                    times[r],
                    median,
                    operations[r],
                    metric
            ));
        }

        System.out.printf(Locale.US,
                "%-12s %-12s n=%-6d median=%.6f ms%n",
                algorithm, input, n, median);
    }
}
