import java.util.List;

public class FingerprintMain {

    public static void main(String[] args) {
        Environment env = EnvironmentFactory.createDefaultEnvironment();
        SignalVector targetVector = new SignalVector(List.of(-50,-40,-60,-30));
        MobileTerminal mt = new MobileTerminal(targetVector);
        DistanceMetric metric = new EuclideanDistance();
        PositioningAlgorithm algorithm = new BarycentricPositioning(metric);
        double[] executionTimes = new double[5];

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

        for (int k = 1; k <= 5; k++) {
            long startTime = System.nanoTime();

            List<FingerprintCell> nearest = KNearestAlgorithm.findNearest(
                    env.getCells(),
                    mt.getSignalVector(),
                    k,
                    metric
            );

            FingerprintPosition pos = algorithm.computePosition(
                    nearest,
                    mt.getSignalVector()
            );

            long endTime = System.nanoTime();
            executionTimes[k - 1] = (endTime - startTime) / 1_000_000.0;

            System.out.println("\n--- K nearest neighbors (k=" + k + ") ---");

            for (FingerprintCell c : nearest) {
                System.out.println(
                        "→ (" + c.getCenter().getX() + "," + c.getCenter().getY() + ") "
                                + " RSSI=" + c.getFingerprint().getSignalVector().getValues()
                );
            }

            System.out.println("\n--- ESTIMATED POSITION ---");
            System.out.println(
                    "X = " + String.format("%.2f", pos.getX()) +
                            " | Y = " + String.format("%.2f", pos.getY())
            );
            System.out.println(
                    "Execution time for k=" + k + ": "
                            + String.format("%.3f", executionTimes[k - 1]) + " ms"
            );
        }

        System.out.println("\n--- EXECUTION TIME TABLE ---");
        for (int k = 1; k <= 5; k++) {
            System.out.println(
                    "k=" + k + " -> " + String.format("%.3f", executionTimes[k - 1]) + " ms"
            );
        }
    }
}
