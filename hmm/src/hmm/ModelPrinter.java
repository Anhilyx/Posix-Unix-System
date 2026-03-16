package hmm;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class ModelPrinter {
    public void print(PrintStream out, MarkovModel model) {
        Objects.requireNonNull(out, "out");
        Objects.requireNonNull(model, "model");

        out.println("Nodes: " + joinStates(model.getStates()));
        out.println();

        out.println("Visit counts:");
        for (State state : model.getStates()) {
            out.println("  " + state.getName() + " = " + model.getVisitCount(state));
        }
        out.println();

        out.println("Transition counts (matrix):");
        printMatrix(out, model, false);
        out.println();

        out.println("Transition probabilities (matrix):");
        printMatrix(out, model, true);
    }

    private String joinStates(List<State> states) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < states.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(states.get(i).getName());
        }
        return builder.toString();
    }

    private void printMatrix(PrintStream out, MarkovModel model, boolean probabilities) {
        List<State> states = model.getStates();
        int width = computeCellWidth(states, probabilities);

        out.print(pad("", width));
        for (State col : states) {
            out.print(pad(col.getName(), width));
        }
        out.println();

        for (State row : states) {
            out.print(pad(row.getName(), width));
            for (State col : states) {
                String cell;
                if (probabilities) {
                    double p = model.getTransitionProbability(row, col);
                    cell = String.format(Locale.US, "%.3f", p);
                } else {
                    cell = Integer.toString(model.getTransitionCount(row, col));
                }
                out.print(pad(cell, width));
            }
            out.println();
        }
    }

    private int computeCellWidth(List<State> states, boolean probabilities) {
        int max = 6;
        for (State state : states) {
            max = Math.max(max, state.getName().length());
        }
        if (probabilities) {
            max = Math.max(max, "0.000".length());
        }
        return max + 2;
    }

    private String pad(String text, int width) {
        if (text == null) {
            text = "";
        }
        if (text.length() >= width) {
            return text + " ";
        }
        StringBuilder builder = new StringBuilder(width);
        builder.append(text);
        while (builder.length() < width) {
            builder.append(' ');
        }
        return builder.toString();
    }
}
