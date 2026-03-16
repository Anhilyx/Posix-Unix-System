import java.util.List;

public interface PositioningAlgorithm {

    Position computePosition(List<Cell> cells, SignalVector target);

}