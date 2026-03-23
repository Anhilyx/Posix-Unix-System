import java.util.*;

public class KNearestAlgorithm {

    public static List<FingerprintCell> findNearest(
            List<FingerprintCell> cells,
            SignalVector target,
            int k,
            DistanceMetric metric
    ){

        cells.sort(Comparator.comparingDouble(
                c -> metric.compute(c.getFingerprint().getSignalVector(), target)
        ));

        return cells.subList(0, k);
    }
}