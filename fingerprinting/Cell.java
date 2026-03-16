public class Cell {

    private final Position center;
    private final Fingerprint fingerprint;

    public Cell(Position center, Fingerprint fingerprint) {
        this.center = center;
        this.fingerprint = fingerprint;
    }

    public Position getCenter() {
        return center;
    }

    public Fingerprint getFingerprint() {
        return fingerprint;
    }

}