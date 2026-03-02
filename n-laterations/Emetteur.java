public class Emetteur extends AbstractEmetteur {
    public Emetteur(AbstractPosition position, double distance) {
        super(position, distance);
    }

    @Override
    public double calculerErreur(AbstractPosition pointTest) {
        double distanceGeometrique = this.position.distanceTo(pointTest);
        return Math.abs(distanceGeometrique - this.distance);
    }
}