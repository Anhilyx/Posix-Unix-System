public abstract class AbstractEmetteur {
    protected AbstractPosition position;
    protected double distance;

    public AbstractEmetteur(AbstractPosition position, double distance) {
        this.position = position;
        this.distance = distance;
    }

    public abstract double calculerErreur(AbstractPosition pointTest);
}