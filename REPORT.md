
# DAA Assignment 4 — Report

## Experiment Design Card

### 1. Questions

Edit Distance:
How does the performance of recursive, table-based,
and two-row Edit Distance change as string length increases?

String Search:
How do Naive Search and KMP compare on random and
worst-case inputs?

### 2. Input Sizes

Recursive Edit Distance:
n = 4, 6, 8, 10

DP Table and Two Rows:
n = 50, 200, 800, 3200

String Search:
n = 1000, 4000, 16000, 64000

Recursive Edit Distance uses smaller sizes because
its running time grows exponentially.


### 3. Input Types

Edit Distance:
- EQUAL: Both strings contain n copies of "A".
  This shows the cost when every character matches.
- DIFFERENT: The first string contains only "A",
  and the second contains only "B".
  This forces the recursive algorithm to explore
  all three operations at each non-base state.

String Search:
- RANDOM: Random text and a 16-character pattern
  generated from the alphabet {A, B, C, D}.
  This represents ordinary search behavior.
- WORST_CASE: Text contains only "A", while
  the pattern contains 15 "A" characters
  followed by "B".
  This forces Naive Search to repeat many comparisons.

All random inputs use the fixed seed 42.
The search pattern length is fixed at 16.


### 4. Metrics

- Execution time in milliseconds.
- Character comparisons for string search.
- Recursive calls or DP cell updates for Edit Distance.

Time will be measured using the median of 5 runs.

### 5. Predictions

- Recursive Edit Distance will become very slow.
- DP Table and Two Rows will have similar time complexity.
- Two Rows will use less extra memory.
- KMP will perform fewer comparisons than Naive Search
  on worst-case inputs.

### 6. Planned Plots

1. Edit Distance operations vs string length.
2. Edit Distance execution time vs string length.
3. String Search comparisons vs text length.
4. String Search execution time vs text length.

At least one plot will use a logarithmic axis.
