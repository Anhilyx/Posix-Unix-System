public abstract class AbstractEmetteur {
    protected AbstractPosition position;
    protected double distance; // Distance mesurée (d = c * t)

    public AbstractEmetteur(AbstractPosition position, double distance) {
        this.position = position;
        this.distance = distance;
    }

    // Méthode pour calculer l'écart entre la distance réelle et la distance géométrique
    public abstract double calculerErreur(AbstractPosition pointTest);
}