public class FingerprintCell {

    private final FingerprintPosition center;
    private final Fingerprint fingerprint;

    public FingerprintCell(FingerprintPosition center, Fingerprint fingerprint) {
        this.center = center;
        this.fingerprint = fingerprint;
    }

    public FingerprintPosition getCenter() {
        return center;
    }

    public Fingerprint getFingerprint() {
        return fingerprint;
    }

}