import java.util.List;

public interface PositioningAlgorithm {

    FingerprintPosition computePosition(List<FingerprintCell> cells, SignalVector target);

}