public class EuclideanDistance extends DistanceMetric {

    @Override
    public double compute(SignalVector a, SignalVector b) {

        double sum = 0;

        for (int i = 0; i < a.getValues().size(); i++) {

            double diff = a.getValues().get(i) - b.getValues().get(i);

            sum += diff * diff;
        }

        return Math.sqrt(sum);
    }
}