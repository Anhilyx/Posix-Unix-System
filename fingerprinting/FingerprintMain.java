import java.util.List;

public class FingerprintMain {

    public static void main(String[] args) {

        Environment env = EnvironmentFactory.createDefaultEnvironment();

        MobileTerminal mt = new MobileTerminal(
                new SignalVector(List.of(-50,-40,-60,-30))
        );

        DistanceMetric metric = new EuclideanDistance();

        List<FingerprintCell> nearest = KNearestAlgorithm.findNearest(
                env.getCells(),
                mt.getSignalVector(),
                3,
                metric
        );

        PositioningAlgorithm algorithm =
                new BarycentricPositioning(metric);

        FingerprintPosition pos = algorithm.computePosition(
                nearest,
                mt.getSignalVector()
        );

        System.out.println(
                "Position estimée : "
                        + pos.getX() + " , " + pos.getY()
        );
    }
}