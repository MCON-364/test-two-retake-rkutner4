[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/I-AIRXTV)
# MCON 364 Test Two Repo

## Overview
This test is designed to evaluate your understanding of concurrency that you built over the last several lessons.

The problems are intentionally similar in **style and skill level** to the practice work you already completed on:
- thread-safe state management
- `ExecutorService` and `Future`
- parallel task decomposition with `Callable`

The wording, class names, and scenarios are different, but the techniques are the same.

## Rules
- Use **Java 21**.
- Keep method signatures exactly as given.
- Do **not** rename packages, classes, or methods.
- Do **not** add external libraries.
- Prefer clear, direct solutions over clever ones.
- Follow the comments inside each class.

## Test Structure
There are **3 graded problems** worth 100 points total.

### Problem 1 — InventoryManager (25 pts)
Implement a thread-safe inventory store that supports:
1. Adding stock for an item (throws on zero/negative quantity).
2. Removing stock atomically — returns `false` instead of going negative.
3. Tracking the total units ever added (unaffected by removals).
4. Returning an unmodifiable snapshot of the current inventory.

### Problem 2 — TaskDispatcher (35 pts)
Implement a fixed-thread-pool dispatcher that:
1. Accepts a list of string tasks and submits each to a thread pool without blocking the caller.
2. Returns one `Future<String>` per task, where each result is the upper-cased input.
3. Records every completed result in a thread-safe list and exposes an accurate completed count.
4. Returns results as an unmodifiable copy via `getResults()`.

### Problem 3 — ParallelReportBuilder (40 pts)
Implement a parallel batch processor that:
1. Validates inputs (null/empty batches, zero workers → `IllegalArgumentException`).
2. Processes each batch concurrently using a configurable number of worker threads.
3. Aggregates per-batch stats (sum, count, min, max) into a single `ReportSummary`.
4. Exposes the total number of processed batches via `getProcessedBatchCount()`.

## Tips
- Read each method's Javadoc comment carefully before coding.
- I provided hints where appropriate to direct you.

## Run StarterSmokerTest locally to make sure your code compiles

## Files to complete
- `InventoryManager.java`
- `TaskDispatcher.java`
- `ParallelReportBuilder.java`

Good luck.
