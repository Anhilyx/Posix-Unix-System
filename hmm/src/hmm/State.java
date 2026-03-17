package hmm;

import java.util.Objects;

public final class State {
    private final String name;

    public State(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("State name must not be empty.");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof State)) {
            return false;
        }
        State that = (State) other;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
