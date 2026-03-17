package hmm;

import java.util.Objects;
import java.util.Optional;

public final class PredictionService {
    public Optional<State> predictMostProbableNext(MarkovModel model, State current) {
        Objects.requireNonNull(model, "model");
        Objects.requireNonNull(current, "current");
        if (!model.getStates().contains(current)) {
            throw new IllegalArgumentException("Unknown state: " + current);
        }
        int totalOutgoing = model.getTransitionMatrix().getTotalOutgoing(current);
        if (totalOutgoing == 0) {
            return Optional.empty();
        }
        State best = null;
        double bestProbability = -1.0;
        for (State candidate : model.getStates()) {
            double probability = model.getTransitionProbability(current, candidate);
            if (probability > bestProbability) {
                bestProbability = probability;
                best = candidate;
            }
        }
        return Optional.ofNullable(best);
    }
}
