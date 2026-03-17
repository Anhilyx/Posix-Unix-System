package hmm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InputParser {
    public List<String> parseCsvList(String csvLine) {
        if (csvLine == null) {
            return List.of();
        }
        String[] parts = csvLine.split(",");
        List<String> result = new ArrayList<>();
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }

    public List<State> createStates(List<String> names) {
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("State list is empty.");
        }
        List<State> result = new ArrayList<>();
        for (String name : names) {
            result.add(new State(name));
        }
        return result;
    }

    public List<State> mapToStates(List<String> names, MarkovModel model) {
        Objects.requireNonNull(names, "names");
        Objects.requireNonNull(model, "model");
        List<State> result = new ArrayList<>();
        for (String name : names) {
            Optional<State> state = model.findState(name);
            if (state.isEmpty()) {
                throw new IllegalArgumentException("Unknown state in observed path: " + name);
            }
            result.add(state.get());
        }
        return result;
    }
}
