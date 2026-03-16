public class Fingerprint {

    private final SignalVector signalVector;

    public Fingerprint(SignalVector signalVector) {
        this.signalVector = signalVector;
    }

    public SignalVector getSignalVector() {
        return signalVector;
    }

}