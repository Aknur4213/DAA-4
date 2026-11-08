
import csv
import statistics
from pathlib import Path

import matplotlib.pyplot as plt


ROOT = Path(__file__).resolve().parent.parent
CSV_PATH = ROOT / "results" / "results.csv"
PLOT_DIR = ROOT / "results" / "plots"

PLOT_DIR.mkdir(parents=True, exist_ok=True)

COLORS = {
    "Recursive": "#e76f51",
    "Table": "#2563eb",
    "TwoRows": "#16a34a",
    "Naive": "#e76f51",
    "KMP": "#2563eb",
}


# -----------------------------------------
# Read CSV results
# -----------------------------------------

with open(CSV_PATH, newline="", encoding="utf-8") as file:
    rows = list(csv.DictReader(file))


groups = {}

for row in rows:
    key = (
        row["algorithm"],
        row["input"],
        int(row["n"])
    )

    groups.setdefault(key, []).append(row)


results = []

for (algorithm, input_type, n), group in groups.items():

    times = [float(row["time_ms"]) for row in group]
    operations = [int(row["operations"]) for row in group]

    results.append({
        "algorithm": algorithm,
        "input": input_type,
        "n": n,
        "time_ms": statistics.median(times),
        "operations": statistics.median(operations)
    })


# -----------------------------------------
# Helper functions
# -----------------------------------------

def get_data(algorithm, input_type, metric):

    selected = [
        row for row in results
        if row["algorithm"] == algorithm
        and row["input"] == input_type
    ]

    selected.sort(key=lambda row: row["n"])

    x = [row["n"] for row in selected]
    y = [row[metric] for row in selected]

    return x, y


def draw_line(ax, algorithm, input_type, metric):

    x, y = get_data(algorithm, input_type, metric)

    ax.plot(
        x, y,
        marker="o",
        linewidth=2,
        markersize=6,
        color=COLORS[algorithm],
        label=algorithm
    )


def format_dp_axis(ax):

    ax.set_xscale("log", base=2)

    ax.set_xticks([50, 200, 800, 3200])
    ax.set_xticklabels(["50", "200", "800", "3200"])


def finish_plot(fig, filename):

    fig.tight_layout(rect=[0, 0, 1, 0.95])

    fig.savefig(
        PLOT_DIR / filename,
        dpi=200,
        bbox_inches="tight"
    )

    plt.close(fig)


# -----------------------------------------
# Plot 1: Edit Distance operations
# -----------------------------------------

fig, axes = plt.subplots(2, 2, figsize=(13, 9))

for col, input_type in enumerate(["EQUAL", "DIFFERENT"]):

    # Recursive
    ax = axes[0, col]

    draw_line(
        ax, "Recursive", input_type, "operations"
    )

    ax.set_title(f"Recursive - {input_type}")
    ax.set_xlabel("String length n (characters)")
    ax.set_ylabel("Recursive calls")

    ax.set_xticks([4, 6, 8, 10])

    if input_type == "DIFFERENT":
        ax.set_yscale("log")

    ax.grid(True, alpha=0.3)
    ax.legend()

    # DP Table and Two Rows
    ax = axes[1, col]

    x, y = get_data("Table", input_type, "operations")

    ax.plot(
        x, y,
        marker="o",
        linewidth=2,
        markersize=6,
        color=COLORS["Table"],
        label="Table = TwoRows"
    )

    ax.set_title(f"DP Algorithms - {input_type}")
    ax.set_xlabel("String length n (characters)")
    ax.set_ylabel("DP cell updates")

    format_dp_axis(ax)

    ax.grid(True, alpha=0.3)
    ax.legend()

fig.suptitle(
    "Edit Distance - Operation Counts",
    fontsize=16
)

finish_plot(fig, "edit_operations.png")


# -----------------------------------------
# Plot 2: Edit Distance execution time
# -----------------------------------------

fig, axes = plt.subplots(2, 2, figsize=(13, 9))

for col, input_type in enumerate(["EQUAL", "DIFFERENT"]):

    # Recursive
    ax = axes[0, col]

    draw_line(
        ax, "Recursive", input_type, "time_ms"
    )

    ax.set_title(f"Recursive - {input_type}")
    ax.set_xlabel("String length n (characters)")
    ax.set_ylabel("Median execution time (ms)")

    ax.set_xticks([4, 6, 8, 10])

    ax.grid(True, alpha=0.3)
    ax.legend()

    # DP algorithms
    ax = axes[1, col]

    for algorithm in ["Table", "TwoRows"]:

        draw_line(
            ax, algorithm, input_type, "time_ms"
        )

    ax.set_title(f"DP Algorithms - {input_type}")
    ax.set_xlabel("String length n (characters)")
    ax.set_ylabel("Median execution time (ms)")

    format_dp_axis(ax)

    ax.grid(True, alpha=0.3)
    ax.legend()

fig.suptitle(
    "Edit Distance - Execution Time",
    fontsize=16
)

finish_plot(fig, "edit_time.png")


# -----------------------------------------
# Plot 3: String Search comparisons
# -----------------------------------------

fig, axes = plt.subplots(1, 2, figsize=(13, 5))

for col, input_type in enumerate(["RANDOM", "WORST_CASE"]):

    ax = axes[col]

    for algorithm in ["Naive", "KMP"]:

        draw_line(
            ax, algorithm, input_type, "operations"
        )

    ax.set_title(input_type)
    ax.set_xlabel("Text length n (characters)")
    ax.set_ylabel("Character comparisons")

    ax.grid(True, alpha=0.3)
    ax.legend()

fig.suptitle(
    "String Search - Character Comparisons",
    fontsize=16
)

finish_plot(fig, "search_comparisons.png")


# -----------------------------------------
# Plot 4: String Search execution time
# -----------------------------------------

fig, axes = plt.subplots(1, 2, figsize=(13, 5))

for col, input_type in enumerate(["RANDOM", "WORST_CASE"]):

    ax = axes[col]

    for algorithm in ["Naive", "KMP"]:

        draw_line(
            ax, algorithm, input_type, "time_ms"
        )

    ax.set_title(input_type)
    ax.set_xlabel("Text length n (characters)")
    ax.set_ylabel("Median execution time (ms)")

    ax.grid(True, alpha=0.3)
    ax.legend()

fig.suptitle(
    "String Search - Execution Time",
    fontsize=16
)

finish_plot(fig, "search_time.png")


print("All four plots created successfully!")
print("Saved in:", PLOT_DIR)
