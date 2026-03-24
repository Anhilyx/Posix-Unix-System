import java.util.List;

public class FingerprintMain {

    public static void main(String[] args) {

        Environment env = EnvironmentFactory.createDefaultEnvironment();

        SignalVector targetVector = new SignalVector(List.of(-50,-40,-60,-30));

        MobileTerminal mt = new MobileTerminal(targetVector);

        DistanceMetric metric = new EuclideanDistance();

        System.out.println("\n--- FINGERPRINT POSITIONING TEST ---");

        System.out.println("Target vector: " + targetVector.getValues());

        System.out.println("\n--- Distances to cells ---");

        for (FingerprintCell c : env.getCells()) {
            double d = metric.compute(
                    c.getFingerprint().getSignalVector(),
                    mt.getSignalVector()
            );

            System.out.println(
                    "Cell (" + c.getCenter().getX() + "," + c.getCenter().getY() + ") "
                            + " | RSSI=" + c.getFingerprint().getSignalVector().getValues()
                            + " | Distance=" + String.format("%.2f", d)
            );
        }

        List<FingerprintCell> nearest = KNearestAlgorithm.findNearest(
                env.getCells(),
                mt.getSignalVector(),
                3,
                metric
        );

        System.out.println("\n--- K nearest neighbors (k=3) ---");

        for (FingerprintCell c : nearest) {
            System.out.println(
                    "→ (" + c.getCenter().getX() + "," + c.getCenter().getY() + ") "
                            + " RSSI=" + c.getFingerprint().getSignalVector().getValues()
            );
        }

        PositioningAlgorithm algorithm =
                new BarycentricPositioning(metric);

        FingerprintPosition pos = algorithm.computePosition(
                nearest,
                mt.getSignalVector()
        );

        System.out.println("\n--- ESTIMATED POSITION ---");

        System.out.println(
                "X = " + String.format("%.2f", pos.getX()) +
                        " | Y = " + String.format("%.2f", pos.getY())
        );
    }
}