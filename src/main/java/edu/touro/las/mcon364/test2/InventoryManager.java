package edu.touro.las.mcon364.test2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ══════════════════════════════════════════════════════════════
 *  Problem 1 of 3
 * ══════════════════════════════════════════════════════════════
 *
 * SCENARIO
 * --------
 * A warehouse system lets many threads update item stock simultaneously.
 * You must make the {@code InventoryManager} thread-safe so that no stock
 * updates are lost and stock never goes negative.
 *
 * REQUIREMENTS  (read every TODO carefully — each is graded)
 *
 * 1. {@code addStock(String item, int qty)}
 *    - Adds {@code qty} units of {@code item} to the inventory.
 *    - {@code qty} must be > 0; throw {@link IllegalArgumentException} otherwise.
 *    - Must be thread-safe under concurrent calls.
 *    - Also increments the running {@code totalUnitsAdded} counter atomically.
 *
 * 2. {@code removeStock(String item, int qty)}
 *    - Removes {@code qty} units of {@code item} if sufficient stock exists.
 *    - Returns {@code true} if the removal succeeded, {@code false} if the
 *      current stock is less than {@code qty} (do NOT go negative).
 *    - {@code qty} must be > 0; throw {@link IllegalArgumentException} otherwise.
 *    - Must be thread-safe: two threads must not both succeed when only one
 *      unit of stock remains and both try to remove one unit.
 *    - Hint: once you have chosen the right Map implementation, its
 *      {@code compute()} method lets you read and write atomically.
 *
 * 3. {@code getStock(String item)}
 *    - Returns the current stock for {@code item}, or {@code 0} if the item
 *      has never been added.
 *
 * 4. {@code getTotalUnitsAdded()}
 *    - Returns the running total of every unit ever added across all items.
 *    - Must reflect concurrent additions accurately but you cannot mark the method as synchronized.
 *
 * 5. {@code getSnapshot()}
 *    - Returns an unmodifiable copy of the current inventory so callers
 *      cannot mutate internal state.
 *    - Hint: {@link Map#copyOf(Map)}.
 *
 *
 * DO NOT use any other concurrency utilities.
 */
public class InventoryManager {

    // TODO: initialise this field with a thread-safe Map implementation
    //       — which Map implementation from the lesson guarantees thread-safe reads and writes?
    private final Map<String, Integer> stock = new ConcurrentHashMap<>();

    // TODO: declare and initialise a private final field called totalUnitsAdded that tracks the
    //       running total of units ever added, thread-safely, without using synchronized
    private final AtomicInteger totalUnitsAdded =  new AtomicInteger(0);


    /**
     * Adds {@code qty} units of {@code item} to inventory.
     *
     * @param item the item name (non-null, non-blank)
     * @param qty  number of units to add (must be > 0)
     * @throws IllegalArgumentException if qty ≤ 0
     */
    public void addStock(String item, int qty) {
        // TODO: validate qty > 0
        if(qty <= 0) {
            throw new IllegalArgumentException("qty cannot be negative");
        }
        // TODO: atomically add qty to the item's current stock
        //       Hint: the thread-safe Map implementation you chose has a merge() method
        //             that can do this in one atomic step
        stock.merge(item, qty, Integer::sum);
        // TODO: atomically add qty to totalUnitsAdded
        totalUnitsAdded.addAndGet(qty);
    }

    /**
     * Removes {@code qty} units of {@code item} if sufficient stock exists.
     *
     * @param item the item name
     * @param qty  number of units to remove (must be > 0)
     * @return {@code true} if removal succeeded; {@code false} if insufficient stock
     * @throws IllegalArgumentException if qty ≤ 0
     */
    public boolean removeStock(String item, int qty) {
        // TODO: validate qty > 0
        if(qty <= 0) {
            throw new IllegalArgumentException("qty cannot be negative");
        }

        // TODO: atomically check-and-decrement.
        //       If current stock >= qty, subtract qty.
        //       Otherwise, leave stock unchanged.
        //       Return true if stock was depleted, false if unchanged
        //       Hint: your chosen Map has a compute() method that lets you
        //             read and write in one atomic step.

        boolean [] removed = {false};
        stock.compute(item, (key, value) -> {
            if (value != null && value >= qty) {
                removed[0] = true;
                return value - qty;
            }
            return value;
        });
        return removed[0];
    }

    /**
     * Returns the current stock for {@code item}, or 0 if unknown.
     */
    public int getStock(String item) {
        return stock.getOrDefault(item, 0);
    }

    /**
     * Returns the cumulative number of units ever added (all items combined).
     */
    public int getTotalUnitsAdded() {
        return totalUnitsAdded.get();
    }

    /**
     * Returns an unmodifiable snapshot of the current inventory.
     * Callers cannot use the returned map to change internal state.
     */
    public Map<String, Integer> getSnapshot() {
        // TODO: return a defensive copy
        return Map.copyOf(stock);
    }
}

