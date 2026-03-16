import java.util.List;

public class BarycentricPositioning implements PositioningAlgorithm {

    private DistanceMetric distanceMetric;

    public BarycentricPositioning(DistanceMetric metric) {
        this.distanceMetric = metric;
    }

    @Override
    public Position computePosition(List<Cell> cells, SignalVector target) {

        double sumWeights = 0;
        double x = 0;
        double y = 0;

        for (Cell cell : cells) {

            double d = distanceMetric.compute(
                    cell.getFingerprint().getSignalVector(),
                    target
            );

            if (d == 0) {
                return cell.getCenter();
            }

            double w = 1.0 / d;

            sumWeights += w;

            x += w * cell.getCenter().getX();
            y += w * cell.getCenter().getY();
        }

        x /= sumWeights;
        y /= sumWeights;

        return new Position(x, y);
    }
}