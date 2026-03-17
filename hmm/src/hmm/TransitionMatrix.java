package hmm;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TransitionMatrix {
    private final Map<State, Map<State, Integer>> counts;

    public TransitionMatrix(List<State> states) {
        counts = new LinkedHashMap<>();
        for (State from : states) {
            counts.put(from, new LinkedHashMap<>());
        }
    }

    public void increment(State from, State to) {
        Map<State, Integer> row = counts.get(from);
        if (row == null) {
            throw new IllegalArgumentException("Unknown state: " + from);
        }
        row.put(to, row.getOrDefault(to, 0) + 1);
    }

    public void setCount(State from, State to, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Transition count must be non-negative.");
        }
        Map<State, Integer> row = counts.get(from);
        if (row == null) {
            throw new IllegalArgumentException("Unknown state: " + from);
        }
        if (count == 0) {
            row.remove(to);
        } else {
            row.put(to, count);
        }
    }

    public void clear() {
        for (Map<State, Integer> row : counts.values()) {
            row.clear();
        }
    }

    public int getCount(State from, State to) {
        Map<State, Integer> row = counts.get(from);
        if (row == null) {
            return 0;
        }
        return row.getOrDefault(to, 0);
    }

    public int getTotalOutgoing(State from) {
        Map<State, Integer> row = counts.get(from);
        if (row == null) {
            return 0;
        }
        int total = 0;
        for (int count : row.values()) {
            total += count;
        }
        return total;
    }

    public Map<State, Integer> getOutgoingCounts(State from) {
        Map<State, Integer> row = counts.get(from);
        if (row == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(row);
    }

    public Map<State, Map<State, Integer>> getAllCounts() {
        Map<State, Map<State, Integer>> result = new LinkedHashMap<>();
        for (Map.Entry<State, Map<State, Integer>> entry : counts.entrySet()) {
            result.put(entry.getKey(), Collections.unmodifiableMap(entry.getValue()));
        }
        return Collections.unmodifiableMap(result);
    }
}
