package hmm;

import java.util.List;
import java.util.Optional;

public final class Main {
    public static void main(String[] args) {
        InputParser parser = new InputParser();

        List<String> nodeNames = List.of("A", "B", "C", "D");

        int[][] transitionCounts = new int[][]{
                {0, 1, 0, 0},
                {0, 0, 2, 1},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };

        try {
            List<State> states = parser.createStates(nodeNames);
            MarkovModel model = new MarkovModel(states);

            model.recordTransitionCounts(transitionCounts);

            ModelPrinter printer = new ModelPrinter();
            printer.print(System.out, model);

            System.out.println();
            PredictionService predictor = new PredictionService();
            State current = model.findState("B").orElseThrow();
            Optional<State> prediction = predictor.predictMostProbableNext(model, current);
            if (prediction.isPresent()) {
                System.out.println("Most probable next state from B: " + prediction.get().getName());
            } else {
                System.out.println("No outgoing transitions from B; cannot predict next state.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Input error: " + ex.getMessage());
        }
    }
}
