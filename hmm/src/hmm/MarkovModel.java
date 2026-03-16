package hmm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MarkovModel {
    private final List<State> states;
    private final Map<String, State> stateByName;
    private final Map<State, Integer> visitCounts;
    private final TransitionMatrix transitionMatrix;

    public MarkovModel(List<State> states) {
        if (states == null || states.isEmpty()) {
            throw new IllegalArgumentException("State list is empty.");
        }
        this.states = Collections.unmodifiableList(new ArrayList<>(states));
        this.stateByName = new LinkedHashMap<>();
        this.visitCounts = new LinkedHashMap<>();
        for (State state : this.states) {
            String key = state.getName();
            if (stateByName.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate state name: " + key);
            }
            stateByName.put(key, state);
            visitCounts.put(state, 0);
        }
        this.transitionMatrix = new TransitionMatrix(this.states);
    }

    public List<State> getStates() {
        return states;
    }

    public Optional<State> findState(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(stateByName.get(name));
    }

    public int getVisitCount(State state) {
        return visitCounts.getOrDefault(state, 0);
    }

    public Map<State, Integer> getVisitCounts() {
        return Collections.unmodifiableMap(visitCounts);
    }

    public int getTransitionCount(State from, State to) {
        return transitionMatrix.getCount(from, to);
    }

    public double getTransitionProbability(State from, State to) {
        int total = transitionMatrix.getTotalOutgoing(from);
        if (total == 0) {
            return 0.0;
        }
        return ((double) transitionMatrix.getCount(from, to)) / total;
    }

    public TransitionMatrix getTransitionMatrix() {
        return transitionMatrix;
    }

    public void recordPath(List<State> path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Observed path is empty.");
        }
        resetCounts();
        State previous = null;
        for (State current : path) {
            requireKnownState(current);
            incrementVisit(current);
            if (previous != null) {
                transitionMatrix.increment(previous, current);
            }
            previous = current;
        }
    }

    public void recordTransitionCounts(int[][] counts) {
        if (counts == null || counts.length == 0) {
            throw new IllegalArgumentException("Transition count matrix is empty.");
        }
        int size = states.size();
        if (counts.length != size) {
            throw new IllegalArgumentException("Matrix row count does not match number of states.");
        }
        for (int[] row : counts) {
            if (row == null || row.length != size) {
                throw new IllegalArgumentException("Matrix must be square with size " + size + ".");
            }
        }

        resetCounts();
        for (int i = 0; i < size; i++) {
            State from = states.get(i);
            int rowTotal = 0;
            for (int j = 0; j < size; j++) {
                int count = counts[i][j];
                if (count < 0) {
                    throw new IllegalArgumentException("Transition count must be non-negative.");
                }
                State to = states.get(j);
                transitionMatrix.setCount(from, to, count);
                rowTotal += count;
            }
            visitCounts.put(from, rowTotal);
        }
    }

    private void incrementVisit(State state) {
        visitCounts.put(state, visitCounts.getOrDefault(state, 0) + 1);
    }

    private void requireKnownState(State state) {
        if (state == null || !stateByName.containsKey(state.getName())) {
            throw new IllegalArgumentException("Unknown state: " + state);
        }
    }

    private void resetCounts() {
        for (State state : states) {
            visitCounts.put(state, 0);
        }
        transitionMatrix.clear();
    }
}
