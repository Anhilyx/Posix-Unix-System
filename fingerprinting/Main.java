import java.util.List;

public class Main {

    public static void main(String[] args) {

        Environment env = EnvironmentFactory.createDefaultEnvironment();

        MobileTerminal mt = new MobileTerminal(
                new SignalVector(List.of(-47,-59,-52))
        );

        DistanceMetric metric = new EuclideanDistance();

        List<Cell> nearest = KNearestAlgorithm.findNearest(
                env.getCells(),
                mt.getSignalVector(),
                3,
                metric
        );

        PositioningAlgorithm algorithm =
                new BarycentricPositioning(metric);

        Position pos = algorithm.computePosition(
                nearest,
                mt.getSignalVector()
        );

        System.out.println(
                "Position estimée : "
                        + pos.getX() + " , " + pos.getY()
        );
    }
}